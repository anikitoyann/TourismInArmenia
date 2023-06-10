package com.example.tourisminarmenia.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
    private String phoneNumber;
}
