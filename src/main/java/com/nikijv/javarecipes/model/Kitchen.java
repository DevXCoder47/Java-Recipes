package com.nikijv.javarecipes.model;

import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("kitchens")
public class Kitchen {
    @Id
    private int id;
    private String name;
    private int popularity;
}
