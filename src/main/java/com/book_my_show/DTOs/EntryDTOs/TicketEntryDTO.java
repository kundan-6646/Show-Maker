package com.book_my_show.DTOs.EntryDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntryDTO {
    private int showId;
    private int userId;
    private List<String> requestedSeats;
}
