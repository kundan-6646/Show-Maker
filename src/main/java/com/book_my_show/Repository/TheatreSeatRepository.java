package com.book_my_show.Repository;

import com.book_my_show.Entities.TheatreSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreSeatRepository extends JpaRepository<TheatreSeatEntity, Integer> {
}
