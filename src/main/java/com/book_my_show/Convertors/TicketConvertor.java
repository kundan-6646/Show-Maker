package com.book_my_show.Convertors;

import com.book_my_show.Entities.ShowEntity;
import com.book_my_show.Entities.TicketEntity;
import com.book_my_show.Enums.TicketStatus;

import java.util.UUID;

public class TicketConvertor {

    public static TicketEntity convertEntryDtoToEntity(ShowEntity showEntity) {
        TicketEntity ticket = TicketEntity.builder()
                .showEntity(showEntity)
                .movieDate(showEntity.getShowDate())
                .movieTime(showEntity.getShowTime())
                .movieName(showEntity.getMovieEntity().getName())
                .theatreName(showEntity.getTheatreEntity().getName())
                .ticketId(UUID.randomUUID().toString())
                .audiName("3-B")
                .ticketStatus(TicketStatus.BOOKED)
                .build();
        return ticket;
    }
}
