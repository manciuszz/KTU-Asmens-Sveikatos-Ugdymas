package app.asu.Models;

public class FoodProductModel {
    private Double calories;
    private Double carbohydrates;
    private Double fat;
    private int id;
    private String name;
    private Double protein;

    public FoodProductModel(String name, Double calories, Double fat, Double protein, Double carbohydrates) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
    }

    public FoodProductModel(int id, String name, Double calories, Double fat, Double protein, Double carbohydrates) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalories() {
        return this.calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getFat() {
        return this.fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getProtein() {
        return this.protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbohydrates() {
        return this.carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
