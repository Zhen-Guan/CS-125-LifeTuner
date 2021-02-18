package com.example.cs125_lifetuner;

public class FoodModel {
    private int id;
    private String foodName;
    private String foodTime;  // we have 'morning', 'afternoon', and 'evening'
    private int foodCalories;

    //Cs
    public FoodModel(int id, String name, String time, int calories) {
        this.id = id;
        this.foodName = name;
        this.foodTime = time;
        this.foodCalories = calories;
    }

    public FoodModel() {
    }

    //to String
    @Override
    public String toString() {
        return foodName + "      Calories:" + foodCalories;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getfoodName() {
        return foodName;
    }

    public void setfoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getfoodTime() {
        return foodTime;
    }

    public void setfoodTime(String foodTime) {
        this.foodTime = foodTime;
    }

    public int getfoodCalories() {
        return foodCalories;
    }

    public void setfoodCalories(int foodCalories) {
        this.foodCalories = foodCalories;
    }
}
