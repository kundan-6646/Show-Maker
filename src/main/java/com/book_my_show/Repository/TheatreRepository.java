package com.book_my_show.Repository;

import com.book_my_show.Entities.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<TheatreEntity, Integer> {
}
