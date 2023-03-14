package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.TheatreEntryDTO;
import com.book_my_show.DTOs.ResponseDTOs.AddTheatreResponseDTO;
import com.book_my_show.Services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("theatre")
public class TheatreController {
    @Autowired
    TheatreService theatreService;

    @PostMapping("add")
    public ResponseEntity<String> addTheatre(@RequestBody TheatreEntryDTO theatreEntryDTO) {
        try {
           String res = theatreService.addTheatre(theatreEntryDTO);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
