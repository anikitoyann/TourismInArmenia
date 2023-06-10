package com.example.tourisminarmenia.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String picName;
    @ManyToOne
    private Region region;


}
