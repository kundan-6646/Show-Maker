package com.book_my_show.Services;

import com.book_my_show.Convertors.TicketConvertor;
import com.book_my_show.DTOs.EntryDTOs.TicketEntryDTO;
import com.book_my_show.Entities.ShowEntity;
import com.book_my_show.Entities.ShowSeatEntity;
import com.book_my_show.Entities.TicketEntity;
import com.book_my_show.Entities.UserEntity;
import com.book_my_show.Repository.ShowRepository;
import com.book_my_show.Repository.TicketRepository;
import com.book_my_show.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

    //Convenience fee
    private final int baseAmount = 100;
    private final int gstPercentage = 18;

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

        int ticketPrice = totalSeatsPrice + baseAmount + ((totalSeatsPrice*gstPercentage) / 100);
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

        return "Ticket Booked Successfully | " + ticket.getMovieName() + " | Date: " + ticket.getMovieDate()
                + " | Time: "
                + ticket.getMovieTime() + " | Seats are: " + ticket.getBookedSeats() + " | Price: " +
                ticket.getTicketPrice();
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
}
