package com.book_my_show.Controllers;

import com.book_my_show.DTOs.EntryDTOs.TicketEntryDTO;
import com.book_my_show.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("book")
    public ResponseEntity<String> bookTickets(@RequestBody TicketEntryDTO ticketEntryDTO) {
        try {
            String res = ticketService.bookTickets(ticketEntryDTO);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get_ticket")
    public ResponseEntity<String> getTicket(@RequestParam int ticketId) {
        try {
            String res = ticketService.getTicket(ticketId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("cancel")
    public ResponseEntity<String> cancel(@RequestParam int ticketId) {
        try {
            String res = ticketService.cancel(ticketId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
