package com.example.tourarmeniacommon.dto;

import com.example.tourarmeniacommon.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private UserType userType;
    private String phoneNumber;
}