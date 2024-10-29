package model;

public class Plant {
    private final String name;
    private int count;

    private final float neededHumidity;
    private final float neededWater;
    private final float neededSunshine;
    private final float normalTemperature;

    private final float deathCoefficient;
    private final float bornCoefficient;

    public Plant(String name, int count, float neededHumidity, float neededWater, float neededSunshine, float normalTemperature,
                 float deathCoefficient, float bornCoefficient) {
        this.name = name;
        this.count = count;
        this.neededHumidity = neededHumidity;
        this.neededWater = neededWater;
        this.neededSunshine = neededSunshine;
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

    public float getNeededHumidity() {
        return neededHumidity;
    }

    public float getNeededWater() {
        return neededWater;
    }

    public float getNeededSunshine() {
        return neededSunshine;
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
