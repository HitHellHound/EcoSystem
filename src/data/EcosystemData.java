package data;

import java.util.List;

public class EcosystemData {
    private String name;

    private List<AnimalData> animals;
    private List<PlantData> plants;

    private float humidity;
    private float amountOfWater;
    private float sunshine;
    private float temperature;

    public EcosystemData(String name, float humidity, float amountOfWater, float sunshine, float temperature) {
        this.name = name;
        this.humidity = humidity;
        this.amountOfWater = amountOfWater;
        this.sunshine = sunshine;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnimalData> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalData> animals) {
        this.animals = animals;
    }

    public List<PlantData> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantData> plants) {
        this.plants = plants;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getAmountOfWater() {
        return amountOfWater;
    }

    public void setAmountOfWater(float amountOfWater) {
        this.amountOfWater = amountOfWater;
    }

    public float getSunshine() {
        return sunshine;
    }

    public void setSunshine(float sunshine) {
        this.sunshine = sunshine;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
