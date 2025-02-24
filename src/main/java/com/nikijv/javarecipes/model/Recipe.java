package com.nikijv.javarecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("recipes")
public class Recipe {
    @Id
    private int id;
    private String name;
    private String category;
    private String description;
    private String video;
    @Column("components")
    private List<String> components;
    @Column("kitchen_id")
    private Kitchen kitchen;
}
