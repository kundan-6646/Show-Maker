package com.book_my_show.Convertors;

import com.book_my_show.DTOs.EntryDTOs.TheatreEntryDTO;
import com.book_my_show.Entities.TheatreEntity;

public class TheatreConvertor {
    public static TheatreEntity convertDtoToEntity(TheatreEntryDTO theatreEntryDTO) {
        TheatreEntity theatreEntity = TheatreEntity.builder()
                .name(theatreEntryDTO.getName())
                .location(theatreEntryDTO.getLocation()).build();

        return theatreEntity;
    }
}
