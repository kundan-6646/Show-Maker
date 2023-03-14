package com.book_my_show.Services;

import com.book_my_show.Convertors.MovieConvertor;
import com.book_my_show.DTOs.EntryDTOs.MovieEntryDTO;
import com.book_my_show.Entities.MovieEntity;
import com.book_my_show.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public String addMovie(MovieEntryDTO movieEntryDTO) {
        MovieEntity movieEntity = MovieConvertor.convertDtoToEntity(movieEntryDTO);
        movieRepository.save(movieEntity);
        return "Movie Added SuccessFully!";
    }

}
