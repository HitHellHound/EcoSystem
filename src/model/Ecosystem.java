package model;

import java.util.List;

public class Ecosystem {
    private final String name;

    private List<Animal> animals;
    private List<Plant> plants;

    private float wetLvl;
    private float amountOfWater;
    private float sunLvl;
    private float temperature;

    public Ecosystem(String name, List<Animal> animals, List<Plant> plants, float wetLvl,
                     float amountOfWater, float sunLvl, float temperature) {
        this.name = name;
        this.animals = animals;
        this.plants = plants;
        this.wetLvl = wetLvl;
        this.amountOfWater = amountOfWater;
        this.sunLvl = sunLvl;
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

    public float getWetLvl() {
        return wetLvl;
    }

    public void setWetLvl(float wetLvl) {
        this.wetLvl = wetLvl;
    }

    public float getAmountOfWater() {
        return amountOfWater;
    }

    public void setAmountOfWater(float amountOfWater) {
        this.amountOfWater = amountOfWater;
    }

    public float getSunLvl() {
        return sunLvl;
    }

    public void setSunLvl(float sunLvl) {
        this.sunLvl = sunLvl;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
