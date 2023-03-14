package com.book_my_show.Convertors;

import com.book_my_show.DTOs.EntryDTOs.ShowEntryDTO;
import com.book_my_show.Entities.ShowEntity;

public class ShowConvertor {
    public static ShowEntity convertDtoToEntity(ShowEntryDTO showEntryDTO) {
        ShowEntity showEntity = ShowEntity.builder()
                .showDate(showEntryDTO.getShowDate())
                .showTime(showEntryDTO.getShowTime())
                .showType(showEntryDTO.getShowType())
                .build();
        return showEntity;
    }
}
