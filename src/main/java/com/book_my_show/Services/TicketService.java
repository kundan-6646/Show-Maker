package com.book_my_show.Services;

import com.book_my_show.Convertors.TicketConvertor;
import com.book_my_show.DTOs.EntryDTOs.TicketEntryDTO;
import com.book_my_show.Entities.*;
import com.book_my_show.Enums.TicketStatus;
import com.book_my_show.Repository.ShowRepository;
import com.book_my_show.Repository.TicketRepository;
import com.book_my_show.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    //Convenience fee
    private final int baseAmount = 100;

    public String bookTickets(TicketEntryDTO ticketEntryDTO) throws Exception{

        ShowEntity showEntity = showRepository.findById(ticketEntryDTO.getShowId()).get();
        UserEntity userEntity = userRepository.findById(ticketEntryDTO.getUserId()).get();
        if(showEntity == null) throw  new Exception("Show Not Found!");
        if(userEntity == null) throw new Exception("User Not Found!");

        //Convert ticket entry DTO into entity
        TicketEntity ticket = TicketConvertor.convertEntryDtoToEntity(showEntity);

        //check if requested seats are available or not
        int totalSeatsPrice = checkSeatAvailabilityAndGetPrice(ticketEntryDTO);
        if(totalSeatsPrice == -1) throw new Exception("Show seats are already booked!");

        int ticketPrice = totalSeatsPrice + baseAmount;
        ticket.setTicketPrice(ticketPrice);
        ticket.setBookedSeats(ticketEntryDTO.getRequestedSeats().toString());
        ticket.setShowEntity(showEntity);
        ticket.setUserEntity(userEntity);

        ticket = ticketRepository.save(ticket);

        //setting parents attribute and saving them
        showEntity.getBookedTickets().add(ticket);
        userEntity.getBookedTickets().add(ticket);

        showRepository.save(showEntity);
        userRepository.save(userEntity);

        sendMailToUser(ticket, true);

        return "Ticket Booked Successfully | " + ticket.getMovieName() + " | Date: " + ticket.getMovieDate()
                + " | Time: "
                + ticket.getMovieTime() + " | Seats are: " + ticket.getBookedSeats() + " | Price: " +
                ticket.getTicketPrice();
    }

    public String getTicket(int ticketId) throws Exception {
        TicketEntity ticket = ticketRepository.findById(ticketId).get();
        if(ticket == null) throw new Exception("Ticket Id Invalid");

        sendMailToUser(ticket, true);
        return "Movie: " + ticket.getMovieName() + " | Price: " + ticket.getTicketPrice() +
                " | Ticket ID: " + ticket.getTicketId();

    }

    public String cancel(int ticketId) throws Exception {
        TicketEntity ticket = ticketRepository.findById(ticketId).get();
        if(ticket == null) throw new Exception("Ticket Id Invalid");
        if(ticket.getTicketStatus().equals(TicketStatus.CANCELLED)) throw new Exception("Ticket is already cancelled");

        ShowEntity showEntity = ticket.getShowEntity();

        //making show seats again available
        List<ShowSeatEntity> showSeats = showEntity.getShowSeats();
        String ticketBookedSeats = ticket.getBookedSeats().substring(1, ticket.getBookedSeats().length()-1);
        String[] bookedSeats = ticketBookedSeats.split(",");
        for (ShowSeatEntity showSeat: showSeats) {
            String currSeatNumber = showSeat.getSeatNo();
            for (String bookedSeatNumber: bookedSeats) {
                String seatNumber = bookedSeatNumber.trim();

                if(seatNumber.equals(currSeatNumber)) {
                    showSeat.setBookedAt(null);
                    showSeat.setBooked(false);
                }
            }
        }

        ticket.setTicketStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
        showRepository.save(showEntity);

        sendMailToUser(ticket, false);

        return "Booking Cancelled Successfully";
    }

    private int checkSeatAvailabilityAndGetPrice(TicketEntryDTO ticketEntryDTO) {
        ShowEntity showEntity = showRepository.findById(ticketEntryDTO.getShowId()).get();
        List<ShowSeatEntity> showSeats = showEntity.getShowSeats();

        //Creating requested seats set
        List<String> requestedSeats = ticketEntryDTO.getRequestedSeats();
        //storing seat number with boolean as is Available
        HashSet<String> requestedSeatsSet = new HashSet<>();
        for (String seat: requestedSeats) {
            requestedSeatsSet.add(seat);
        }
        int seatsPrice = 0;
        //Iterating through show seats and checking with req. seats
        for (ShowSeatEntity showSeat: showSeats) {

            if(requestedSeatsSet.contains(showSeat.getSeatNo())) {
                if(showSeat.isBooked()) return -1;
                else {
                    showSeat.setBooked(true);
                    showSeat.setBookedAt(new Date());
                    seatsPrice += showSeat.getPrice();
                }
            }
        }

        showEntity.setShowSeats(showSeats);
        return seatsPrice;
    }


    private void sendMailToUser(TicketEntity ticket, boolean booking) throws Exception {
        String body = booking ? getHTMLBodyOfBooking(ticket): getHTMLBodyOfCancellation(ticket);
        String subject = booking ? "Booking Confirmed: " + ticket.getMovieName(): "Booking Cancelled: " + ticket.getMovieName();

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessage.setContent(body, "text/html");
        mimeMessageHelper.setFrom("confirm@showmaker.com");
        mimeMessageHelper.setTo(ticket.getUserEntity().getEmail());
        mimeMessageHelper.setSubject(subject);

        javaMailSender.send(mimeMessage);
    }


    private String getHTMLBodyOfBooking(TicketEntity ticket) {
        MovieEntity movie = ticket.getShowEntity().getMovieEntity();
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"width: 600px; padding: 10px; text-align: center; margin: auto; background-color: #EEE;\">\n" +
                "        <h2 style=\"border-bottom: 2px solid gray;\">Booking Confirmed</h2>\n" +
                "        <p style=\"text-align: left;  font-family: Arial, Helvetica, sans-serif; line-height: 1.5rem;\">Dear customer,<br>Your movie booking is confirmed, Download the M-ticket on your mobile to show at cinema entrance.</p>\n" +
               "<br>\n" +
                "        <div style=\"background-color: gray;  border-radius: 12px; padding: 10px; display: flex;\">\n" +
                "            <div style=\"width: 40%;\">\n" +
                "               <img style=\"width: 70%; border-radius: 12px;\" src=" +
                movie.getPosterUrl() +
                " alt=\"movie-poster\">\n" +
                "            </div>\n" +
                "            <div style=\"width: 60%; text-align: left; color: #fff;\">\n" +
                "                <h3 style=\"font-family: Lucida Console;\">" +
                ticket.getMovieName() +
                "</h3>\n" +
                "                <h4 style=\"font-family: Lucida Console;\">" +
                movie.getLanguage() + ", " + ticket.getShowEntity().getShowType() +
                "</h4>\n" +
                "                <h4 style=\"font-family: Lucida Console;\">" +
                ticket.getMovieDate() + ", " + ticket.getMovieTime() +
                "</h4>\n" +
                "                <h4 style=\"font-family: Lucida Console;\">" +
                ticket.getTheatreName() + ", " + ticket.getShowEntity().getTheatreEntity().getLocation() +
                "</h4>\n" +
                "                <button style=\"padding: 10px; background-color: coral; color: #20262E; border: none; border-radius: 6px;\">Download M-Ticket</button>\n" +
                "            </div>\n" +
                "            \n" +
                "        </div>\n" +
                "        <div style=\"background-color: gray; border-radius: 12px; padding: 10px; border-top: 1px solid #20262E; color: #fff;\">\n" +
                "            <img src=\"https://i.ibb.co/84pxZJp/qr.png\" alt=\"qr\" height=\"150px\"width=\"150px\">\n" +
                "            <h4 style=\"font-family: Lucida Console;\">Booking ID:" +
                ticket.getTicketId() +
                "</h4>\n" +
                "            <h2>" +
                ticket.getBookedSeats().split(",").length + " " + "Tickets</h2>\n" +
                "            <h4 style=\"font-family: Lucida Console;\">Seats:" + " " +
                ticket.getBookedSeats() +
                "</h4>\n" +
                "        </div>\n" +
                "        <br>\n" +
                "        <div style=\"text-align: left; padding: 10px;\">\n" +
                "            <h3>Booking Summary</h3>\n" +
                "            <h6>Ticket: " +
                (ticket.getTicketPrice() - baseAmount) +
                "</h6>\n" +
                "            <h6>Convinience fee: " +
                baseAmount +
                "</h6>\n" +
                "            <h6>Total Price: " +
                ticket.getTicketPrice() +
                "</h6>\n" +
                "        </div>\n" +
                "\n" +
                "        <footer style=\"padding: 10px; background-color: black; color: #fff;\">\n" +
                "            Thanks for booking with us! Your Show Maker.\n" +
                "        </footer>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        return htmlBody;
    }

    private String getHTMLBodyOfCancellation(TicketEntity ticket) {
        MovieEntity movie = ticket.getShowEntity().getMovieEntity();
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"width: 600px; padding: 10px; text-align: center; margin: auto; background-color: #EEE;\">\n" +
                "        <h2 style=\"border-bottom: 2px solid gray; padding: 5px;\">Booking Cancelled</h2>\n" +
                "        <p style=\"text-align: left;  font-family: Arial, Helvetica, sans-serif; line-height: 1.5rem;\">Dear customer,<br>We have received your request for the folowing show cancellation, It will be processed soon.</p>\n" +
                "        <br>\n" +
                "        <div style=\"background-color: gray;  border-radius: 12px; padding: 10px; display: flex;\">\n" +
                "            <div style=\"width: 40%;\">\n" +
                "               <img style=\"width: 70%; border-radius: 12px;\" src=" +
                movie.getPosterUrl() +
                " alt=\"movie-poster\">\n" +
                "            </div>\n" +
                "            <div style=\"width: 60%; text-align: left; color: #fff; font-family: Lucida Console;\">\n" +
                "                <h3>" +
                ticket.getMovieName() +
                "</h3>\n" +
                "                <h4>" +
                movie.getLanguage() + ", " + ticket.getShowEntity().getShowType() +
                "</h4>\n" +
                "                <h4>" +
                ticket.getMovieDate() + ", " + ticket.getMovieTime() +
                "</h4>\n" +
                "                <h4 style=\"font-family: Lucida Console;\">" +
                ticket.getTheatreName() + ", " + ticket.getShowEntity().getTheatreEntity().getLocation() +
                "</h4>\n" +
                "                <h4>" +
                ticket.getBookedSeats() +
                "</h4>\n" +
                "                <button style=\"padding: 10px; background-color: coral; color: #20262E; border: none; border-radius: 6px;\">Track Status</button>\n" +
                "            </div>\n" +
                "            \n" +
                "        </div>\n" +
                "        <br>\n" +
                "\n" +
                "\n" +
                "        <footer style=\"padding: 10px; background-color: black; color: #fff;\">\n" +
                "            Thanks for booking with us! Your Show Maker.\n" +
                "        </footer>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";



        return htmlBody;
    }

}
