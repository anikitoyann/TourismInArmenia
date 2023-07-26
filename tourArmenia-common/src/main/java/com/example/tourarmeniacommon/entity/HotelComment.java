package com.example.tourarmeniacommon.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hotel_comment")
public class HotelComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    @ManyToOne
    private Item item;
    @ManyToOne
    private User user;
}
