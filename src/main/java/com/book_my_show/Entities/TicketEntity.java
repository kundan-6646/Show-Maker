package com.book_my_show.Entities;

import javax.persistence.*;

import com.book_my_show.Enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;


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

    @Enumerated(value = EnumType.STRING)
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn
    private UserEntity userEntity;

    //ticket is child w.r.t show
    @ManyToOne
    @JoinColumn
    private ShowEntity showEntity;
}
