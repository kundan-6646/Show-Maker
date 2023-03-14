package com.book_my_show.Convertors;

import com.book_my_show.DTOs.EntryDTOs.MovieEntryDTO;
import com.book_my_show.Entities.MovieEntity;


public class MovieConvertor {
    public static MovieEntity convertDtoToEntity(MovieEntryDTO movieEntryDTO) {
        //Set all attributes in one Go
        MovieEntity movieEntity = MovieEntity.builder().name(movieEntryDTO.getName())
                .genre(movieEntryDTO.getGenre())
                .rating(movieEntryDTO.getRating())
                .duration(movieEntryDTO.getDuration())
                .language(movieEntryDTO.getLanguage()).build();

        return movieEntity;
    }
}
