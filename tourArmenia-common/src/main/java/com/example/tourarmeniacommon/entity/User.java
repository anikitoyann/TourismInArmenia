package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name is not null or empty")
    private String name;
    @NotEmpty(message = "surname is not null or empty")
    private String surname;
    @Email
    private String email;
    @NotEmpty(message = "address can not be null")
    private String address;
   // @Size(min = 8, max = 20)
    @NotNull(message = "Password must be 8 to 20 characters long")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
   // @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a 10-digit number.")
    private String phoneNumber;
}
