package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name can not be null or empty")
    private String name;
    private String description;
    private String picName;
    @ManyToOne
    @NotNull(message = "Region is not null")
    private Region region;
    @Enumerated(value = EnumType.STRING)
    private Type type;


}
