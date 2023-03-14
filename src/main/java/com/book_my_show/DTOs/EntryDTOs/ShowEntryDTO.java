package com.book_my_show.DTOs.EntryDTOs;

import com.book_my_show.Enums.ShowType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowEntryDTO {
    private LocalDate showDate;
    private LocalTime showTime;
    private ShowType showType;
    private int movieId;
    private int theatreId;
    private int classicSeatPrice;
    private int premiumSeatPrice;
}
