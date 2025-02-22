package com.nikijv.javarecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private int id;
    private String name;
    private String category;
    private String description;
    private String video;
    private List<String> components;
    private Kitchen kitchen;
}
