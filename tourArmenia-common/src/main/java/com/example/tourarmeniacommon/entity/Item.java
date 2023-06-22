package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String picName;
    @ManyToOne
    private Region region;
    @Enumerated(value = EnumType.STRING)
    private Type type;


}
