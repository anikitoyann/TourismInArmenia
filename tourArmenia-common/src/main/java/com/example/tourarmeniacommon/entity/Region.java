package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name is not null or empty")
    private String name;
    @NotEmpty(message = "regional centre is not null or empty")
    private String regionalCenter;

}
