package com.book_my_show.Convertors;

import com.book_my_show.DTOs.EntryDTOs.UserEntryDTO;
import com.book_my_show.Entities.UserEntity;

public class UserConvertor {

    public static UserEntity convertDtoToEntity(UserEntryDTO userEntryDTO) {
            //Set all attributes in one Go
            UserEntity userEntity = UserEntity.builder().age(userEntryDTO.getAge()).
                    name(userEntryDTO.getName())
                    .email(userEntryDTO.getEmail()).mobile(userEntryDTO.getMobile())
                    .address(userEntryDTO.getAddress())
                    .build();
            return userEntity;
    }
}
