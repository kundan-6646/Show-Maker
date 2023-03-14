package com.book_my_show.DTOs.EntryDTOs;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntryDTO {
    private int age;
    private String name;
    private String address;
    private String mobile;
    private String email;
}
