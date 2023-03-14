package com.book_my_show.Services;

import com.book_my_show.Convertors.ShowConvertor;
import com.book_my_show.DTOs.EntryDTOs.ShowEntryDTO;
import com.book_my_show.DTOs.ResponseDTOs.MovieShowsResponseDTO;
import com.book_my_show.Entities.*;
import com.book_my_show.Enums.SeatType;
import com.book_my_show.Repository.MovieRepository;
import com.book_my_show.Repository.ShowRepository;
import com.book_my_show.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheatreRepository theatreRepository;

    public String addShow(ShowEntryDTO showEntryDTO) throws Exception{
        ShowEntity showEntity = ShowConvertor.convertDtoToEntity(showEntryDTO);
        MovieEntity movieEntity = movieRepository.findById(showEntryDTO.getMovieId()).get();
        TheatreEntity theatreEntity = theatreRepository.findById(showEntryDTO.getTheatreId()).get();

        showEntity.setMovieEntity(movieEntity);
        showEntity.setTheatreEntity(theatreEntity);

        List<ShowSeatEntity> showSeatEntityList = createShowSeats(showEntryDTO, showEntity);
        showEntity.setShowSeats(showSeatEntityList);
        showEntity = showRepository.save(showEntity);

        movieEntity.getShows().add(showEntity);
        theatreEntity.getShows().add(showEntity);

        movieRepository.save(movieEntity);
        theatreRepository.save(theatreEntity);

        return "Show created Successfully" + " | Movie - " + movieEntity.getName() + " | " + theatreEntity.getName()
                + " on " + showEntity.getShowDate() + " at " + showEntity.getShowTime();
    }

    private List<ShowSeatEntity> createShowSeats(ShowEntryDTO showEntryDTO, ShowEntity showEntity) throws Exception{
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

    public List<MovieShowsResponseDTO> getMovieShows(int movieId, LocalDate dateOfShow) throws Exception{
        MovieEntity movie = movieRepository.findById(movieId).get();

        List<ShowEntity> movieShows = showRepository.findShowsOfMovieByDate(movieId, dateOfShow);
        List<MovieShowsResponseDTO> movieShowsResponseDTOList = new ArrayList<>();

        for (ShowEntity show: movieShows) {
            MovieShowsResponseDTO movieShowsResponseDTO = MovieShowsResponseDTO.builder()
                    .movieName(movie.getName())
                    .showDate(dateOfShow)
                    .showTime(show.getShowTime())
                    .theatreName(show.getTheatreEntity().getName())
                    .theatreLocation(show.getTheatreEntity().getLocation())
                    .build();

            movieShowsResponseDTOList.add(movieShowsResponseDTO);
        }
        return movieShowsResponseDTOList;
    }

    public String getMovieWithMaxShows() throws Exception{
        HashMap<MovieEntity, Integer> movieShowsCountMap = new HashMap<>();
        List<ShowEntity> showEntityList = showRepository.findAll();

        for (ShowEntity show: showEntityList) {
            movieShowsCountMap.put(show.getMovieEntity(),
                    movieShowsCountMap.getOrDefault(show.getMovieEntity(), 0) + 1);
        }

        if(movieShowsCountMap.size() == 0) throw new Exception("Shows not found");

        MovieEntity movieEntityWithMaxShows = null;
        int showsCount = 0;
        for (MovieEntity movieEntity: movieShowsCountMap.keySet()) {
            if(movieEntityWithMaxShows == null) {
                movieEntityWithMaxShows = movieEntity;
                showsCount = movieShowsCountMap.get(movieEntity);
            }

            if(movieShowsCountMap.get(movieEntity) > showsCount) {
                movieEntityWithMaxShows = movieEntity;
                showsCount = movieShowsCountMap.get(movieEntity);
            }
        }

        return "Movie with maximum shows across all theatres is " + movieEntityWithMaxShows.getName() +
                " having " + showsCount + " shows.";
    }
}
