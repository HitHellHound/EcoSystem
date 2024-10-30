package model;

public class Plant extends Entity {
    private final float neededHumidity;
    private final float neededWater;
    private final float neededSunshine;
    private final float normalTemperature;

    private final float deathCoefficient;
    private final float bornCoefficient;

    public Plant(String name, int count, float neededHumidity, float neededWater, float neededSunshine, float normalTemperature,
                 float deathCoefficient, float bornCoefficient) {
        super(name, count);
        this.neededHumidity = neededHumidity;
        this.neededWater = neededWater;
        this.neededSunshine = neededSunshine;
        this.normalTemperature = normalTemperature;
        this.deathCoefficient = deathCoefficient;
        this.bornCoefficient = bornCoefficient;
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
