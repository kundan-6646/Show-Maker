package com.book_my_show.Entities;

import com.book_my_show.Enums.MovieGenre;
import com.book_my_show.Enums.MovieLanguages;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;

    private double rating;
    private int duration;
    private String posterUrl;

    @Enumerated(value = EnumType.STRING)
    private MovieLanguages language;

    //One Movie can have multiple shows
    @OneToMany(mappedBy = "movieEntity", cascade = CascadeType.ALL)
    private List<ShowEntity> shows = new ArrayList<>();
}
