package com.nikijv.javarecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kitchen {
    private int id;
    private String name;
    private int popularity;
}
