package model;

import java.util.List;

@Deprecated
public class Ecosystem {
    private final String name;

    private List<Animal> animals;
    private List<Plant> plants;

    private float humidity;
    private float amountOfWater;
    private float sunshine;
    private float temperature;

    public Ecosystem(String name, List<Animal> animals, List<Plant> plants, float humidity,
                     float amountOfWater, float sunshine, float temperature) {
        this.name = name;
        this.animals = animals;
        this.plants = plants;
        this.humidity = humidity;
        this.amountOfWater = amountOfWater;
        this.sunshine = sunshine;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
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
