package com.book_my_show.DTOs.EntryDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreEntryDTO {
    private String name;
    private String location;
    private int premiumSeatsCount;
    private int classicSeatsCount;
}
