package model;

public class Plant {
    private final String name;
    private int count;

    private final float neededWet;
    private final float neededWater;
    private final float neededSun;
    private final float normalTemperature;

    private final float deathCoefficient;
    private final float bornCoefficient;

    public Plant(String name, int count, float neededWet, float neededWater, float neededSun, float normalTemperature,
                 float deathCoefficient, float bornCoefficient) {
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

    public float getDeathCoefficient() {
        return deathCoefficient;
    }

    public float getBornCoefficient() {
        return bornCoefficient;
    }
}
