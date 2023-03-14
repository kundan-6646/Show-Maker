package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.ShowEntryDTO;
import com.book_my_show.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //get shows of a particular movie on a particular date in a theatre
    //get movie with max shows booked across all theatres
    //get all theatre with particular movie
    //unique locations of theatres(cities)
}
