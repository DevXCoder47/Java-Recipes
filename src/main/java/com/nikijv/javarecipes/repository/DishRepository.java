package com.nikijv.javarecipes.repository;

import com.nikijv.javarecipes.model.Dish;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Query("""
            select* from dishes
            inner join recipes on dishes.recipe_id = recipes.id
            inner join kitchens on recipes.kitchen_id = kitchens.id;""")
    List<Dish> findAllDishes();
}
