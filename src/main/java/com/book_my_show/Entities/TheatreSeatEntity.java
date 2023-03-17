package com.book_my_show.Entities;

import com.book_my_show.Enums.SeatType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theatreSeats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seatNumber;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn
    private TheatreEntity theatreEntity;
}
