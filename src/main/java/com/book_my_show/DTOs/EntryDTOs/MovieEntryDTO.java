package com.book_my_show.DTOs.EntryDTOs;

import com.book_my_show.Enums.MovieGenre;
import com.book_my_show.Enums.MovieLanguages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntryDTO {
    private String name;
    private MovieGenre genre;
    private double rating;
    private int duration;
    private MovieLanguages language;
}
