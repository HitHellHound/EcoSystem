package service;

import ecxeptions.WrongDataException;
import model.Animal;
import model.Ecosystem;
import model.Plant;
import types.DangerLevel;
import types.MealType;

import java.util.ArrayList;
import java.util.List;

public class EcosystemServiceImpl implements EcosystemService {

    public Ecosystem createEcosystem(String name, float wetLvl,
                                     float amountOfWater, float sunLvl, float temperature) throws WrongDataException {
        if (wetLvl < 1.00 && wetLvl >= 0.00
                && amountOfWater > 0
                && sunLvl >= 0.00 && sunLvl <= 1.00){

            List<Animal> animals = new ArrayList<>();
            List<Plant> plants = new ArrayList<>();

            return new Ecosystem(name, animals, plants, wetLvl, amountOfWater, sunLvl, temperature);
        } else {
            throw new WrongDataException();
        }
    }

    public void createAndAddAnimal(Ecosystem ecosystem, String name, int count, int dangerLevel, int mealType,
                                   int neededFood, float neededWater, float normalTemperature,
                                   float deathCoefficient, float bornCoefficient)  throws WrongDataException {
        if (count >= 0 && neededFood >= 0 && neededWater >= 0 && deathCoefficient > 0.00 && deathCoefficient < 1.00
                && bornCoefficient > 0.00 && bornCoefficient < 1.00) {
            Animal animal = new Animal(name, count, DangerLevel.valueOfInt(dangerLevel), MealType.valueOfInt(mealType),
                    neededFood, neededWater, normalTemperature, deathCoefficient, bornCoefficient);
            ecosystem.getAnimals().add(animal);
        } else {
            throw new WrongDataException();
        }
    }

    public void createAndAddPlant(Ecosystem ecosystem, String name, int count, float neededWet, float neededWater,
                                  float neededSun, float normalTemperature,
                                  float deathCoefficient, float bornCoefficient)  throws WrongDataException {
        if (count >= 0 && neededWet >= 0.00 && neededWet < 1.00 && neededWater > 0
                && neededSun > 0.00 && neededSun < 1.00 && deathCoefficient > 0.00 && deathCoefficient < 1.00
                && bornCoefficient > 0.00 && bornCoefficient < 1.00) {
            Plant plant = new Plant(name, count, neededWet, neededWater, neededSun, normalTemperature,
                    deathCoefficient, bornCoefficient);
            ecosystem.getPlants().add(plant);
        } else {
            throw new WrongDataException();
        }
    }
}
