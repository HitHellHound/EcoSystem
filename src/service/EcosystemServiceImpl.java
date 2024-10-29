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

    public Ecosystem createEcosystem(String name, float humidity,
                                     float amountOfWater, float sunshine, float temperature) throws WrongDataException {
        if (humidity < 1.00 && humidity >= 0.00
                && amountOfWater > 0
                && sunshine >= 0.00 && sunshine <= 1.00){

            List<Animal> animals = new ArrayList<>();
            List<Plant> plants = new ArrayList<>();

            return new Ecosystem(name, animals, plants, humidity, amountOfWater, sunshine, temperature);
        } else {
            throw new WrongDataException("Can't create ecosystem " + name);
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
            throw new WrongDataException("Can't create animal " + name);
        }
    }

    public void createAndAddPlant(Ecosystem ecosystem, String name, int count, float neededHumidity, float neededWater,
                                  float neededSunshine, float normalTemperature,
                                  float deathCoefficient, float bornCoefficient)  throws WrongDataException {
        if (count >= 0 && neededHumidity >= 0.00 && neededHumidity < 1.00 && neededWater > 0
                && neededSunshine > 0.00 && neededSunshine < 1.00 && deathCoefficient > 0.00 && deathCoefficient < 1.00
                && bornCoefficient > 0.00 && bornCoefficient < 1.00) {
            Plant plant = new Plant(name, count, neededHumidity, neededWater, neededSunshine, normalTemperature,
                    deathCoefficient, bornCoefficient);
            ecosystem.getPlants().add(plant);
        } else {
            throw new WrongDataException("Can't create plant " + name);
        }
    }
}
