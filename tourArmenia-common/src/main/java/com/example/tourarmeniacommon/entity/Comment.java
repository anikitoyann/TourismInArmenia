package com.example.tourarmeniacommon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tour_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    @NotNull(message = "Tour isn't null")
    @ManyToOne
    private TourPackage tour;
    private Date date;
    @ManyToOne
//    @NotNull(message = "User isn't null")
    private User user;



}
