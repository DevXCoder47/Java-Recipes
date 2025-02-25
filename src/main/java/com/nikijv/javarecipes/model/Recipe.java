package com.nikijv.javarecipes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String category;
    private String description;
    private String video;
    @Column(columnDefinition = "TEXT[]")
    private List<String> components;
    @ManyToOne
    @JoinColumn(name = "kitchen_id")
    private Kitchen kitchen;
}
