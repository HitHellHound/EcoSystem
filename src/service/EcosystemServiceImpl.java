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
    @Override
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

    @Override
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

    @Override
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

    @Override
    public String createEcosystemShortStatistic(Ecosystem ecosystem) {
        StringBuilder statistic = new StringBuilder();
        statistic.append(ecosystem.getName())
                .append(":\nHumidity -- ").append(ecosystem.getHumidity())
                .append("\nAmount of Water -- ").append(ecosystem.getAmountOfWater())
                .append("\nSunshine --").append(ecosystem.getSunshine())
                .append("\nTemperature").append(ecosystem.getTemperature()).append("\n\n");

        statistic.append("Animals:\n");
        for (Animal animal : ecosystem.getAnimals())
            statistic.append(animal.getName()).append(": count -- ").append(animal.getCount()).append("\n");

        statistic.append("\nPlants:\n");
        for (Plant plant : ecosystem.getPlants())
            statistic.append(plant.getName()).append(": count -- ").append(plant.getCount()).append("\n");

        return statistic.toString();
    }

    @Override
    public String createEcosystemFullStatistic(Ecosystem ecosystem) {
        StringBuilder statistic = new StringBuilder();

        statistic.append("+----------------------------------------------------------------------+\n")
                .append("| Ecosystem Name | Humidity | Amount Of Water | Sunshine | Temperature |\n")
                .append("+----------------------------------------------------------------------+\n")
                .append(String.format("| %-14.14s | %8.2f | %15.2f | %8.2f | %+11.1f |%n",
                        ecosystem.getName(), ecosystem.getHumidity(), ecosystem.getAmountOfWater(), ecosystem.getSunshine(), ecosystem.getTemperature()))
                .append("+----------------------------------------------------------------------+\n\n");

        statistic.append("+-----------------------------------------------------------------------------------------------------------------+\n")
                .append("|   Animal   |   Count   | Danger Level |  Meal Type  | Need Food | Need Water | Norm Temp | DeathCoef | BornCoef |\n")
                .append("+-----------------------------------------------------------------------------------------------------------------+\n");
        for (Animal animal : ecosystem.getAnimals())
            statistic.append(String.format("| %-11.11s|%10d | %12s | %11s | %9d | %10.1f | %+9.1f | %9.2f | %8.2f |%n",
                    animal.getName(), animal.getCount(), animal.getDangerLevel().toString(), animal.getMealType().toString(),
                    animal.getNeededFood(), animal.getNeededWater(), animal.getNormalTemperature(),
                    animal.getDeathCoefficient(), animal.getBornCoefficient()));
        statistic.append("+-----------------------------------------------------------------------------------------------------------------+\n\n");

        statistic.append("+--------------------------------------------------------------------------------------------------+\n")
                .append("|   Plant   |   Count   | Need Humidity | Need Water | Need Sun | Norm Temp | DeathCoef | BornCoef |\n")
                .append("+--------------------------------------------------------------------------------------------------+\n");
        for (Plant plant : ecosystem.getPlants())
            statistic.append(String.format("| %-10.10s|%10d | %13.2f | %10.1f | %8.2f | %+9.1f | %9.2f | %8.2f |%n",
                    plant.getName(), plant.getCount(), plant.getNeededHumidity(), plant.getNeededWater(),
                    plant.getNeededSunshine(), plant.getNormalTemperature(), plant.getDeathCoefficient(),
                    plant.getBornCoefficient()));
        statistic.append("+--------------------------------------------------------------------------------------------------+");
        return statistic.toString();
    }

    @Override
    public String createEcosystemFullData(Ecosystem ecosystem) {
        StringBuilder data = new StringBuilder();
        data.append(ecosystem.getName()).append(" ")
                .append(ecosystem.getHumidity()).append(" ")
                .append(ecosystem.getAmountOfWater()).append(" ")
                .append(ecosystem.getSunshine()).append(" ")
                .append(ecosystem.getTemperature()).append("\n\n");

        for (Animal animal : ecosystem.getAnimals())
            data.append(animal.getName()).append(" ")
                    .append(animal.getCount()).append(" ")
                    .append(animal.getDangerLevel().getDngLvlInt()).append(" ")
                    .append(animal.getMealType().getMealTypeInt()).append(" ")
                    .append(animal.getNeededFood()).append(" ")
                    .append(animal.getNeededWater()).append(" ")
                    .append(animal.getNormalTemperature()).append(" ")
                    .append(animal.getDeathCoefficient()).append(" ")
                    .append(animal.getBornCoefficient()).append("\n");
        data.append("\n");

        for (Plant plant : ecosystem.getPlants())
            data.append(plant.getName()).append(" ")
                    .append(plant.getCount()).append(" ")
                    .append(plant.getNeededHumidity()).append(" ")
                    .append(plant.getNeededWater()).append(" ")
                    .append(plant.getNeededSunshine()).append(" ")
                    .append(plant.getNormalTemperature()).append(" ")
                    .append(plant.getDeathCoefficient()).append(" ")
                    .append(plant.getBornCoefficient()).append("\n");

        return data.toString();
    }
}
