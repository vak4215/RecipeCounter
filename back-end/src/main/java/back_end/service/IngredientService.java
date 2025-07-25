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

import org.springframework.stereotype.Service;

import back_end.model.Ingredient;
import back_end.model.Unit;

@Service
public class IngredientService {
    private final String ingredientDataFile = "data/ingredients.csv";
    private List<Ingredient> ingredients;

    public IngredientService() {
        // Load ingredients from the CSV file
        loadIngredients();
    }

    /*
     * Loads ingredients from the CSV file into the ingredients list.
     */
    public void loadIngredients() {
        ingredients = new ArrayList<>();

        // Read the CSV file and populate the ingredients list
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ingredientDataFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Skip the header line
                if (parts[0].equals("ingredient name")) {
                    continue;
                }

                // Ensure there are enough parts to create an Ingredient
                if (parts.length == 4) {
                    // Parse the ingredient data and create an Ingredient object to add to the list
                    String name = parts[0].trim();
                    double calories = Double.parseDouble(parts[1].trim());
                    double servingSize = Double.parseDouble(parts[2].trim());
                    Unit servingUnit = Unit.valueOf(parts[3].trim());

                    ingredients.add(new Ingredient(name, calories, servingSize, servingUnit));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the list of all ingredients.
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /*
     * Returns an ingredient by its name, ignoring case.
     * If the ingredient is not found, returns null.
     */
    public Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name.trim())) {
                return ingredient;
            }
        }

        // Return null if the ingredient is not found
        return null;
    }

    /*
     * Adds a new ingredient to the list and updates the CSV file.
     * Throws IllegalArgumentException if the ingredient already exists.
     */
    public Ingredient addIngredient(Ingredient ingredient) {
        // Check if the ingredient already exists
        if (getIngredientByName(ingredient.getName()) != null) {
            throw new IllegalArgumentException("Ingredient already exists: " + ingredient.getName());
        }

        // Add the new ingredient to the list
        ingredients.add(ingredient);

        // Update the CSV file to include the new ingredient
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(ingredientDataFile), StandardOpenOption.APPEND)) {
            bw.write(ingredient.toStringCSVFormat());
            return ingredient;
        } catch (IOException e) {
            throw new RuntimeException("Failed to add ingredient: " + ingredient.getName(), e);
        }
    }

    public Ingredient updateIngredient(String currentName, Ingredient updatedIngredient) {
        deleteIngredient(currentName);
        return addIngredient(updatedIngredient);
    }

    /*
     * Currently for use in tests and update requests
     * Deletes an ingredient by name and rewrites the CSV file without the removed ingredient.
     * TODO : find a better way to handle this
     */
    public void deleteIngredient(String name) {
        Ingredient deletedIngredient = getIngredientByName(name);
        
        if (deletedIngredient != null) {
            ingredients.remove(deletedIngredient);

            // Rewrite the CSV file without the removed ingredient
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ingredientDataFile))) {
                bw.write("ingredient name,number of calories,serving size,serving unit\n");
                for (Ingredient ingredient : ingredients) {
                    bw.write(ingredient.toStringCSVFormat());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Ingredient not found: " + name);
        }
    }
}
