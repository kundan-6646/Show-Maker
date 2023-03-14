package com.book_my_show.Services;

import com.book_my_show.Convertors.UserConvertor;
import com.book_my_show.DTOs.EntryDTOs.UserEntryDTO;
import com.book_my_show.Entities.UserEntity;
import com.book_my_show.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public String addUser(UserEntryDTO userEntryDTO) throws Exception{
        UserEntity userEntity= UserConvertor.convertDtoToEntity(userEntryDTO);
        userRepository.save(userEntity);
        return "User added Successfully!";
    }
}
