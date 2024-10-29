package model;

import types.DangerLevel;
import types.MealType;

public class Animal {
    private final String name;
    private int count;
    private final DangerLevel dangerLevel;

    private final MealType mealType;
    private final int neededFood;
    private final float neededWater;
    private final float normalTemperature;

    private final float deathCoefficient;
    private final float bornCoefficient;

    public Animal(String name, int count, DangerLevel dangerLevel, MealType mealType, int neededFood, float neededWater,
                  float normalTemperature, float deathCoefficient, float bornCoefficient) {
        this.name = name;
        this.count = count;
        this.dangerLevel = dangerLevel;
        this.mealType = mealType;
        this.neededFood = neededFood;
        this.neededWater = neededWater;
        this.normalTemperature = normalTemperature;
        this.deathCoefficient = deathCoefficient;
        this.bornCoefficient = bornCoefficient;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public MealType getMealType() {
        return mealType;
    }

    public int getNeededFood() {
        return neededFood;
    }

    public float getNeededWater() {
        return neededWater;
    }

    public float getNormalTemperature() {
        return normalTemperature;
    }

    public float getDeathCoefficient() {
        return deathCoefficient;
    }

    public float getBornCoefficient() {
        return bornCoefficient;
    }
}
