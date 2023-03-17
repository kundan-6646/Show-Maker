package com.book_my_show.Entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "theatreEntity", cascade = CascadeType.ALL)
    private List<TheatreSeatEntity> theatreSeats = new ArrayList<>();

    //Theatre can have multiple shows
    @OneToMany(mappedBy = "theatreEntity", cascade = CascadeType.ALL)
    private List<ShowEntity> shows = new ArrayList<>();
}
