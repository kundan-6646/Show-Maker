package com.book_my_show.Repository;

import com.book_my_show.Entities.MovieEntity;
import com.book_my_show.Entities.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {
    @Query(value = "select * from shows where movie_entity_id=:movieId and show_date=:showDate", nativeQuery = true)
    List<ShowEntity> findShowsOfMovieByDate(int movieId, LocalDate showDate);
}
