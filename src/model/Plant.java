package model;

import types.MealType;

public class Plant {
    private final String name;
    private int count;

    private final float neededWet;
    private final float neededWater;
    private final float neededSun;
    private final float normalTemperature;

    private final double deathCoefficient;
    private final double bornCoefficient;

    public Plant(String name, int count, float neededWet, float neededWater, float neededSun, float normalTemperature,
                 double deathCoefficient, double bornCoefficient) {
        this.name = name;
        this.count = count;
        this.neededWet = neededWet;
        this.neededWater = neededWater;
        this.neededSun = neededSun;
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

    public float getNeededWet() {
        return neededWet;
    }

    public float getNeededWater() {
        return neededWater;
    }

    public float getNeededSun() {
        return neededSun;
    }

    public float getNormalTemperature() {
        return normalTemperature;
    }

    public double getDeathCoefficient() {
        return deathCoefficient;
    }

    public double getBornCoefficient() {
        return bornCoefficient;
    }
}
