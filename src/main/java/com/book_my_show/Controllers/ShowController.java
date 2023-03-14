package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.ShowEntryDTO;
import com.book_my_show.DTOs.ResponseDTOs.MovieShowsResponseDTO;
import com.book_my_show.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping("add")
    public ResponseEntity<String> addShow(@RequestBody ShowEntryDTO showEntryDTO) {
        try {
            String res = showService.addShow(showEntryDTO);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Get all shows of a movie on a given date
    @GetMapping("get_shows")
    public ResponseEntity<List<MovieShowsResponseDTO>> getMovieShows(@RequestParam("movieId") int movieId, @RequestParam("date") LocalDate date) {
        List<MovieShowsResponseDTO> movieShows = new ArrayList<>();
        try {
            movieShows = showService.getMovieShows(movieId, date);
            return new ResponseEntity<>(movieShows, HttpStatus.FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(movieShows, HttpStatus.BAD_REQUEST);
        }
    }

    //get movie with max shows booked across all theatres
    @GetMapping("get_movie_with_highest_shows")
    public ResponseEntity<String> getMovieWithMaxShows() {
        try {
            String res = showService.getMovieWithMaxShows();
            return new ResponseEntity<>(res, HttpStatus.FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //unique locations of theatres(cities)
}
