package com.book_my_show.Services;

import com.book_my_show.Convertors.ShowConvertor;
import com.book_my_show.DTOs.EntryDTOs.ShowEntryDTO;
import com.book_my_show.Entities.*;
import com.book_my_show.Enums.SeatType;
import com.book_my_show.Repository.MovieRepository;
import com.book_my_show.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheatreRepository theatreRepository;

    public String addShow(ShowEntryDTO showEntryDTO) {
        ShowEntity showEntity = ShowConvertor.convertDtoToEntity(showEntryDTO);
        MovieEntity movieEntity = movieRepository.findById(showEntryDTO.getMovieId()).get();
        TheatreEntity theatreEntity = theatreRepository.findById(showEntryDTO.getTheatreId()).get();

        showEntity.setMovieEntity(movieEntity);
        showEntity.setTheatreEntity(theatreEntity);

        List<ShowSeatEntity> showSeatEntityList = createShowSeats(showEntryDTO, showEntity);
        showEntity.setShowSeats(showSeatEntityList);

        movieEntity.getShows().add(showEntity);
        theatreEntity.getShows().add(showEntity);

        movieRepository.save(movieEntity);
        theatreRepository.save(theatreEntity);

        return "Show created Successfully" + " | Movie - " + movieEntity.getName() + " | " + theatreEntity.getName()
                + " on " + showEntity.getShowDate() + " at " + showEntity.getShowTime();
    }

    private List<ShowSeatEntity> createShowSeats(ShowEntryDTO showEntryDTO, ShowEntity showEntity) {
        List<TheatreSeatEntity> theatreSeatEntityList = showEntity.getTheatreEntity().getTheatreSeats();
        List<ShowSeatEntity> showSeatEntityList = new ArrayList<>();

        for (TheatreSeatEntity theatreSeat: theatreSeatEntityList) {
            //Getting seat price based on seat type that we are getting from theatre seats
            int seatPrice = theatreSeat.getSeatType().equals(SeatType.Classic) ? showEntryDTO.getClassicSeatPrice()
                    : showEntryDTO.getPremiumSeatPrice();

            ShowSeatEntity showSeatEntity = ShowSeatEntity.builder()
                    .seatNo(theatreSeat.getSeatNumber())
                    .seatType(theatreSeat.getSeatType())
                    .isBooked(false)
                    .showEntity(showEntity)
                    .price(seatPrice)
                    .build();

            showSeatEntityList.add(showSeatEntity);
        }

        return showSeatEntityList;
    }
}
