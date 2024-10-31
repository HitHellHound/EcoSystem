package data;

import types.DangerLevel;
import types.MealType;

public class AnimalData extends EntityData {
    private DangerLevel dangerLevel;
    private MealType mealType;
    private float neededFood;
    private float normalTemperature;
    private float containsFood;

    public AnimalData(String name, int count, DangerLevel dangerLevel, MealType mealType, float neededFood, float normalTemperature, float containsFood) {
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

    public void setDangerLevel(DangerLevel dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public float getNeededFood() {
        return neededFood;
    }

    public void setNeededFood(float neededFood) {
        this.neededFood = neededFood;
    }

    public float getNormalTemperature() {
        return normalTemperature;
    }

    public void setNormalTemperature(float normalTemperature) {
        this.normalTemperature = normalTemperature;
    }

    public float getContainsFood() {
        return containsFood;
    }

    public void setContainsFood(float containsFood) {
        this.containsFood = containsFood;
    }
}
