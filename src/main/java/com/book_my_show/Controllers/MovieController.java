package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.MovieEntryDTO;
import com.book_my_show.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("add")
    public ResponseEntity<String> addMovie(@RequestBody MovieEntryDTO movieEntryDTO) {
        try {
            String res = movieService.addMovie(movieEntryDTO);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
