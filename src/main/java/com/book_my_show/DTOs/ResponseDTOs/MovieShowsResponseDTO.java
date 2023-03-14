package com.book_my_show.DTOs.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieShowsResponseDTO {
    private String movieName;
    private String theatreName;
    private String theatreLocation;
    private LocalDate showDate;
    private LocalTime showTime;
}
