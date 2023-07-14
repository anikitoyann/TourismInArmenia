package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "user")
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
