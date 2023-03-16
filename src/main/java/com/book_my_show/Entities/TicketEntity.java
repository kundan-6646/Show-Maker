package com.book_my_show.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String movieName;
    private String theatreName;
    private LocalDate movieDate;
    private LocalTime movieTime;
    private int ticketPrice;
    private String ticketId ;
    private String audiName;
    private String bookedSeats;

    @ManyToOne
    @JoinColumn
    private UserEntity userEntity;

    //ticket is child w.r.t show
    @ManyToOne
    @JoinColumn
    private ShowEntity showEntity;
}
