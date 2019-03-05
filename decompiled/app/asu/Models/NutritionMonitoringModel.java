package app.asu.Models;

public class NutritionMonitoringModel {
    private String date;
    private FoodProductModel foodProductModel;
    private int time;

    public NutritionMonitoringModel(String date, int time, FoodProductModel foodProductModel) {
        this.date = date;
        this.time = time;
        this.foodProductModel = foodProductModel;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public FoodProductModel getFoodProductModel() {
        return this.foodProductModel;
    }

    public void setFoodProductModel(FoodProductModel foodProductModel) {
        this.foodProductModel = foodProductModel;
    }
}
