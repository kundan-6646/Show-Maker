package com.book_my_show.Services;

import com.book_my_show.Convertors.TheatreConvertor;
import com.book_my_show.DTOs.EntryDTOs.TheatreEntryDTO;
import com.book_my_show.Entities.TheatreEntity;
import com.book_my_show.Entities.TheatreSeatEntity;
import com.book_my_show.Enums.SeatType;
import com.book_my_show.Repository.TheatreRepository;
import com.book_my_show.Repository.TheatreSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService {

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    TheatreSeatRepository theatreSeatRepository;

    public String addTheatre(TheatreEntryDTO theatreEntryDTO) {
        TheatreEntity theatreEntity = TheatreConvertor.convertDtoToEntity(theatreEntryDTO);
        List<TheatreSeatEntity> theatreSeats = createTheatreSeats(theatreEntryDTO, theatreEntity);
        theatreEntity.setTheatreSeats(theatreSeats);

        theatreRepository.save(theatreEntity);
        return "Theatre Added Successfully";
    }

    private List<TheatreSeatEntity> createTheatreSeats(TheatreEntryDTO theatreEntryDTO, TheatreEntity theatreEntity) {
        List<TheatreSeatEntity> theatreSeats = new ArrayList<>();
        int premiumSeats = theatreEntryDTO.getPremiumSeatsCount();
        int classicSeats = theatreEntryDTO.getClassicSeatsCount();

        //Creating classic seats
        for(int i = 1; i <= classicSeats; i++) {
            TheatreSeatEntity theatreSeatEntity = TheatreSeatEntity.builder()
                    .seatNumber(i + "C")
                    .seatType(SeatType.Classic)
                    .theatreEntity(theatreEntity)
                    .build();
            theatreSeats.add(theatreSeatEntity);
        }

        //Creating Premium seats
        for(int i = 1; i <= premiumSeats; i++) {
            TheatreSeatEntity theatreSeatEntity = TheatreSeatEntity.builder()
                    .seatNumber(i + "P")
                    .seatType(SeatType.Premium)
                    .theatreEntity(theatreEntity)
                    .build();
            theatreSeats.add(theatreSeatEntity);
        }

        return theatreSeats;
    }
}
