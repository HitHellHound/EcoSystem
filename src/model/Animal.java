package model;

import enums.DangerLevel;
import enums.MealType;

public class Animal extends Entity {
    private final DangerLevel dangerLevel;
    private final MealType mealType;
    private final float neededFood;
    private final float normalTemperature;

    private final float containsFood;

    public Animal(String name, int count, DangerLevel dangerLevel, MealType mealType, float neededFood,
                  float normalTemperature, float containsFood) {
        super(name, count);
        this.dangerLevel = dangerLevel;
        this.mealType = mealType;
        this.neededFood = neededFood;
        this.normalTemperature = normalTemperature;
        this.containsFood = containsFood;
    }

    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public MealType getMealType() {
        return mealType;
    }

    public float getNeededFood() {
        return neededFood;
    }

    public float getNormalTemperature() {
        return normalTemperature;
    }

    public float getContainsFood() {
        return containsFood;
    }
}
