package com.nikijv.javarecipes.repository;

import com.nikijv.javarecipes.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Query(value = """
        SELECT d.* FROM dishes d
        INNER JOIN recipes r ON d.recipe_id = r.id
        INNER JOIN kitchens k ON r.kitchen_id = k.id
        """, nativeQuery = true)
    List<Dish> findAllDishes();
}
