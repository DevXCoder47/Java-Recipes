package com.nikijv.javarecipes.controller;

import com.nikijv.javarecipes.dto.RecipeDto;
import com.nikijv.javarecipes.model.Dish;
import com.nikijv.javarecipes.model.Kitchen;
import com.nikijv.javarecipes.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DishController {
    List<Dish> dishes = new ArrayList<Dish>(List.of(
        new Dish (1, "D1",
                new Recipe(1, "R1", "C1", "D1", "V1", new ArrayList<>(List.of("Comp11", "Comp12")),
                        new Kitchen(1, "K1", 10))),
            new Dish (2, "D2",
                    new Recipe(2, "R2", "C2", "D2", "V2", new ArrayList<>(List.of("Comp12", "Comp21", "Comp31")),
                            new Kitchen(2, "K2", 12))),
            new Dish (3, "D3",
                    new Recipe(3, "R3", "C3", "D3", "V3", new ArrayList<>(List.of("Comp01", "Comp02")),
                            new Kitchen(3, "K3", 16))),
            new Dish (4, "D4",
                    new Recipe(4, "R4", "C4", "D4", "V4", new ArrayList<>(List.of("Comp44", "Comp45")),
                            new Kitchen(3, "K3", 16))),
            new Dish (5, "D5",
                    new Recipe(5, "R5", "C5", "D5", "V5", new ArrayList<>(List.of("Comp05", "Comp51", "Comp52")),
                            new Kitchen(3, "K3", 16))),
            new Dish (6, "D6",
                    new Recipe(6, "R6", "C6", "D6", "V6", new ArrayList<>(List.of("Comp66", "Comp06")),
                            new Kitchen(2, "K2", 12))),
            new Dish (7, "D7",
                    new Recipe(7, "R7", "C7", "D7", "V7", new ArrayList<>(List.of("Comp77", "Comp71")),
                            new Kitchen(1, "K1", 10))),
            new Dish (8, "D8",
                    new Recipe(8, "R8", "C8", "D8", "V8", new ArrayList<>(List.of("Comp81", "Comp88")),
                            new Kitchen(3, "K3", 16))),
            new Dish (9, "D9",
                    new Recipe(9, "R9", "C9", "D9", "V9", new ArrayList<>(List.of("Comp90", "Comp91")),
                            new Kitchen(2, "K2", 12))),
            new Dish (10, "D10",
                    new Recipe(10, "R10", "C10", "D10", "V10", new ArrayList<>(List.of("Comp010", "Comp001")),
                            new Kitchen(2, "K2", 12)))

    ));
    // Task 1
    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getDishes() {
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/recipe-random")
    public ResponseEntity<Recipe> getRandomRecipe() {
        Random random = new Random();
        int randomId = random.nextInt(1, dishes.size() + 1);
        Optional<Recipe> recipeOpt = dishes.stream()
                .map(Dish::getRecipe)
                .filter(r -> r.getId() == randomId)
                .findFirst();
        if (recipeOpt.isPresent())
            return ResponseEntity.ok(recipeOpt.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipe/by/component/{comp}")
    public ResponseEntity<Recipe> getRecipeByComponent(@PathVariable String comp) {
        Optional<Recipe> recipeOpt = dishes.stream()
                .map(Dish::getRecipe)
                .filter(r -> r.getComponents().contains(comp))
                .findFirst();
        if (recipeOpt.isPresent())
            return ResponseEntity.ok(recipeOpt.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Optional<Recipe> recipeOpt = dishes.stream()
                .map(Dish::getRecipe)
                .filter(r -> r.getId() == id)
                .findFirst();
        if (recipeOpt.isPresent())
            return ResponseEntity.ok(recipeOpt.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipe-short/{id}")
    public ResponseEntity<RecipeDto> getRecipeShort(@PathVariable int id) {
        Optional<Recipe> recipeOpt = dishes.stream()
                .map(Dish::getRecipe)
                .filter(r -> r.getId() == id)
                .findFirst();
        if (recipeOpt.isPresent()) {
            RecipeDto recipeDto = new RecipeDto();
            Recipe recipe = recipeOpt.get();
            recipeDto.setId(recipe.getId());
            recipeDto.setName(recipe.getName());
            recipeDto.setCategory(recipe.getCategory());
            return ResponseEntity.ok(recipeDto);
        }
        return ResponseEntity.notFound().build();
    }

    // Task 2
    @GetMapping("/recipe-categories")
    public ResponseEntity<List<String>> getRecipeCategories() {
        List<String> categories = dishes.stream()
                .map(dish -> dish.getRecipe().getCategory())
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(categories);
    }

    @GetMapping ("/top3/recipe/by/name")
    public ResponseEntity<List<Recipe>> getTop3RecipeByName() {
        List<Recipe> top3Recipes = dishes.stream()
                .map(Dish::getRecipe)
                .sorted(Comparator.comparing(Recipe::getName))
                .limit(3)
                .collect(Collectors.toList());

        return ResponseEntity.ok(top3Recipes);
    }

    @GetMapping ("/random-3-dishes")
    public ResponseEntity<List<Dish>> getRandom3Dishes() {
        List<Dish> shuffledDishes = new ArrayList<>(dishes);
        Collections.shuffle(shuffledDishes);
        return ResponseEntity.ok(shuffledDishes.stream().limit(3).toList());
    }

    @GetMapping ("/random-1-dish")
    public ResponseEntity<Dish> getRandom1Dish() {
        List<Dish> shuffledDishes = new ArrayList<>(dishes);
        Collections.shuffle(shuffledDishes);
        return ResponseEntity.ok(shuffledDishes.getFirst());
    }

    @GetMapping ("/10-specific-dishes/{comp}")
    public ResponseEntity<List<Dish>> tenSpecificDishes(@PathVariable String comp) {
        List<Dish> filteredDishes = dishes.stream()
                .filter(dish -> !dish.getRecipe().getComponents().contains(comp))
                .limit(10)
                .toList();
        return ResponseEntity.ok(filteredDishes);
    }

    // Task 3
    @GetMapping ("/top3/recipe/by/kitchen/{kitch}")
    public ResponseEntity<List<Recipe>> getTop3RecipeByKitchen(@PathVariable String kitch) {
        List<Recipe> top3Recipes = dishes.stream()
                .map(Dish::getRecipe)
                .filter(recipe -> recipe.getKitchen().getName().equals(kitch))
                .limit(3)
                .toList();
        return ResponseEntity.ok(top3Recipes);
    }

    @GetMapping ("/top3/most-popular-kitchen")
    public ResponseEntity<List<Kitchen>> getTop3PopularKitchen() {
        List<Kitchen> top3Kitchens = dishes.stream()
                .map(dish -> dish.getRecipe().getKitchen())
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return ResponseEntity.ok(top3Kitchens);
    }

    @GetMapping ("/top3/categories")
    public ResponseEntity<List<String>> getTop3Categories() {
        List<String> top3Categories = dishes.stream()
                .map(dish -> dish.getRecipe().getCategory())
                .distinct()
                .limit(3)
                .toList();
        return ResponseEntity.ok(top3Categories);
    }

    // Task 4
    @PostMapping("/add-dish")
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) {
        boolean dishIdMatch = false;
        boolean recipeIdMatch = false;
        if(dishes.stream().anyMatch(d -> d.getId() == dish.getId()))
            dishIdMatch = true;
        if(dishes.stream().map(Dish::getRecipe).anyMatch(r -> r.getId() == dish.getRecipe().getId()))
            recipeIdMatch = true;
        if(!dishIdMatch && !recipeIdMatch) {
            dishes.add(dish);
            return ResponseEntity.ok(dish);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping ("/delete-dish/by/id/{id}")
    public ResponseEntity<Dish> deleteDish(@PathVariable int id) {
        if(dishes.stream().anyMatch(d -> d.getId() == id)) {
            Dish dishToDelete = dishes.stream().filter(d -> d.getId() == id).findFirst().get();
            dishes.remove(dishToDelete);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/modify-dish/by/id/{id}")
    public ResponseEntity<Dish> modifyDish(@PathVariable int id, @RequestBody Dish dish) {
        Optional<Dish> existingDish = dishes.stream().filter(d -> d.getId() == id).findFirst();
        if (existingDish.isPresent()) {
            dishes.remove(existingDish.get());
            dishes.add(dish);
            return ResponseEntity.ok(dish);
        }
        return ResponseEntity.notFound().build();
    }
}
//http://localhost:8080/swagger-ui/index.html
