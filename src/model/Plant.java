package model;

@Deprecated
public class Plant extends Entity {
    private final float neededHumidity;
    private final float neededWater;
    private final float neededSunshine;
    private final float normalTemperature;

    private final float containsFood;

    public Plant(String name, int count, float neededHumidity, float neededWater, float neededSunshine,
                 float normalTemperature, float containsFood) {
        super(name, count);
        this.neededHumidity = neededHumidity;
        this.neededWater = neededWater;
        this.neededSunshine = neededSunshine;
        this.normalTemperature = normalTemperature;
        this.containsFood = containsFood;
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

    public float getContainsFood() {
        return containsFood;
    }
}
