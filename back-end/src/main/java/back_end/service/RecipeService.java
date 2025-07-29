package back_end.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import back_end.model.Ingredient;
import back_end.model.Recipe;
import back_end.model.Unit;

public class RecipeService {
    private final String recipeDataFile = "data/recipes.csv";
    private List<Recipe> recipes;

    private IngredientService ingredientService;

    public RecipeService(IngredientService ingredientService) {
        // Load recipes from the CSV file
        loadRecipes();
        this.ingredientService = ingredientService;
    }

    /*
     * Loads recipes from the CSV file into the recipes list.
     * The recipes list is cleared before loading.
     */
    public void loadRecipes() {
        recipes = new ArrayList<>();

        // Read the CSV file and populate the recipes list
        try (BufferedReader br = Files.newBufferedReader(Paths.get(recipeDataFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Skip the header line
                if (parts[0].equals("name")) {
                    continue;
                }

                // Ensure there are enough parts to create a Recipe
                if (parts.length == 4) {
                    // Parse the recipe data and create an Recipe object to add to the list

                    // description,instructions,notes,imageUrl,servings,total calories,ingredients


                    String name = parts[0].trim();
                    String description = parts[1].trim().replaceAll("\"", "");
                    String instructions = parts[2].trim().replaceAll("\"", "");
                    String notes = parts[3].trim().replaceAll("\"", "");
                    String imageUrl = parts[4].trim();
                    int servings = Integer.parseInt(parts[5].trim());
                    double calories = Double.parseDouble(parts[6].trim());
                    String ingredientsStr = parts[7].trim().replaceAll("\"", "");

                    Map<Ingredient, Unit> ingredientsList = new java.util.HashMap<>();

                    for (String ingredientStr : ingredientsStr.split(",")) {
                        // Split the ingredient string by semicolon to get the ingredient name and quantity
                        String[] ingredientParts = ingredientStr.split(";");
                        Ingredient ingredient = ingredientService.getIngredientByName(ingredientParts[0].trim());

                        String quantityStr = ingredientParts[1].trim();
                        String numberPart = quantityStr.replaceAll("[^0-9.]", "");
                        String unitPart = quantityStr.replaceAll("[0-9.]", "");
                        Unit unit = Unit.valueOf(unitPart.toUpperCase());
                        unit.setAmount(Double.parseDouble(numberPart));

                        ingredientsList.put(ingredient, unit);
                    }

                    recipes.add(new Recipe(name, description, instructions, notes, imageUrl, servings, calories, ingredientsList));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the list of all recipes.
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /*
     * Returns a recipe by its name, ignoring case.
     * If the recipe is not found, returns null.
     */
    public Recipe getRecipeByName(String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equalsIgnoreCase(name.trim())) {
                return recipe;
            }
        }

        // Return null if the recipe is not found
        return null;
    }

    /*
     * Adds a new recipe to the list and updates the CSV file.
     * Throws IllegalArgumentException if the recipe already exists.
     */
    public Recipe addRecipe(Recipe recipe) {
        // Check if the recipe already exists
        if (getRecipeByName(recipe.getName()) != null) {
            throw new IllegalArgumentException("Recipe already exists: " + recipe.getName());
        }

        // Add the new recipe to the list
        recipes.add(recipe);

        // Update the CSV file to include the new recipe
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(recipeDataFile), StandardOpenOption.APPEND)) {
            bw.write(recipe.toStringCSVFormat());
            return recipe;
        } catch (IOException e) {
            throw new RuntimeException("Failed to add recipe: " + recipe.getName(), e);
        }
    }

    public Recipe updateRecipe(String currentName, Recipe updatedRecipe) {
        deleteRecipe(currentName);
        return addRecipe(updatedRecipe);
    }

    /*
     * Currently for use in tests and update requests
     * Deletes a recipe by name and rewrites the CSV file without the removed recipe.
     * TODO : find a better way to handle this
     */
    public void deleteRecipe(String name) {
        Recipe deletedRecipe = getRecipeByName(name);
        
        if (deletedRecipe != null) {
            recipes.remove(deletedRecipe);

            // Rewrite the CSV file without the removed ingredient
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(recipeDataFile))) {
                bw.write("name,description,instructions,notes,imageUrl,servings,total calories,ingredients\n");
                for (Recipe recipe : recipes) {
                    bw.write(recipe.toStringCSVFormat());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Recipe not found: " + name);
        }
    }
}
