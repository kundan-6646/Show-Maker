package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.UserEntryDTO;
import com.book_my_show.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("add")
    public ResponseEntity<String> addUser(@RequestBody UserEntryDTO userEntryDTO) {
        try {
            String res = userService.addUser(userEntryDTO);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
