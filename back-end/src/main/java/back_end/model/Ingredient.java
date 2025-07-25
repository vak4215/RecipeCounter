package back_end.model;

public class Ingredient {
    private String name;
    private double calories;
    private double servingSize;
    private Unit servingUnit;

    public Ingredient(String name, double calories, double servingSize, Unit servingUnit) {
        this.name = name.toLowerCase();
        this.calories = calories;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public Unit getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(Unit servingUnit) {
        this.servingUnit = servingUnit;
    }

    /*
     * Returns a string representation of the ingredient in CSV format.
     */
    public String toStringCSVFormat() {
        return String.format("%s,%f,%f,%s%n", name, calories,
                    servingSize, servingUnit.getName());
    }

    @Override
    /*
     * Returns a JSON representation of the ingredient.
     */
    public String toString() {
        return String.format("{\"name\":\"%s\",\"calories\":%s,\"servingSize\":%s,\"servingUnit\":\"%s\"}",
            name, calories, servingSize, servingUnit.getName());
    }
}
