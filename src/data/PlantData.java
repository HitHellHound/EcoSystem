package data;

public class PlantData extends EntityData {
    private float neededHumidity;
    private float neededWater;
    private float neededSunshine;
    private float normalTemperature;
    private float containsFood;

    public PlantData(String name, int count, float neededHumidity, float neededWater, float neededSunshine,
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

    public void setNeededHumidity(float neededHumidity) {
        this.neededHumidity = neededHumidity;
    }

    public float getNeededWater() {
        return neededWater;
    }

    public void setNeededWater(float neededWater) {
        this.neededWater = neededWater;
    }

    public float getNeededSunshine() {
        return neededSunshine;
    }

    public void setNeededSunshine(float neededSunshine) {
        this.neededSunshine = neededSunshine;
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
