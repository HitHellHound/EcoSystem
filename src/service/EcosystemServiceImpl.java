package service;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EcosystemServiceImpl implements EcosystemService {
    private final FilesService filesService;

    public EcosystemServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public String createEcosystemShortStatistic(String ecosystemName) throws WrongDataException {
        EcosystemData ecosystem = filesService.getEcosystem(ecosystemName);
        StringBuilder statistic = new StringBuilder();
        statistic.append(ecosystem.getName())
                .append(":\nHumidity -- ").append(ecosystem.getHumidity())
                .append("\nAmount of Water -- ").append(ecosystem.getAmountOfWater())
                .append("\nSunshine --").append(ecosystem.getSunshine())
                .append("\nTemperature").append(ecosystem.getTemperature()).append("\n\n");

        statistic.append("Animals:\n");
        for (AnimalData animal : ecosystem.getAnimals())
            statistic.append(animal.getName()).append(": count -- ").append(animal.getCount()).append("\n");

        statistic.append("\nPlants:\n");
        for (PlantData plant : ecosystem.getPlants())
            statistic.append(plant.getName()).append(": count -- ").append(plant.getCount()).append("\n");

        return statistic.toString();
    }

    @Override
    public String createEcosystemFullStatistic(String ecosystemName) throws WrongDataException {
        EcosystemData ecosystem = filesService.getEcosystem(ecosystemName);
        StringBuilder statistic = new StringBuilder();

        statistic.append("+----------------------------------------------------------------------+\n")
                .append("| Ecosystem Name | Humidity | Amount Of Water | Sunshine | Temperature |\n")
                .append("+----------------------------------------------------------------------+\n")
                .append(String.format("| %-14.14s | %8.2f | %15.2f | %8.2f | %+11.1f |%n",
                        ecosystem.getName(), ecosystem.getHumidity(), ecosystem.getAmountOfWater(), ecosystem.getSunshine(), ecosystem.getTemperature()))
                .append("+----------------------------------------------------------------------+\n\n");

        statistic.append("+---------------------------------------------------------------------------------------------+\n")
                .append("|   Animal   |   Count   | Danger Level |  Meal Type  | Need Food | Norm Temp | Contains Food |\n")
                .append("+---------------------------------------------------------------------------------------------+\n");
        for (AnimalData animal : ecosystem.getAnimals())
            statistic.append(String.format("| %-11.11s|%10d | %12s | %11s | %9f | %+9.1f | %13.1f |%n",
                    animal.getName(), animal.getCount(), animal.getDangerLevel().toString(), animal.getMealType().toString(),
                    animal.getNeededFood(), animal.getNormalTemperature(), animal.getContainsFood()));
        statistic.append("+---------------------------------------------------------------------------------------------+\n\n");

        statistic.append("+-------------------------------------------------------------------------------------------+\n")
                .append("|   Plant   |   Count   | Need Humidity | Need Water | Need Sun | Norm Temp | Contains Food |\n")
                .append("+-------------------------------------------------------------------------------------------+\n");
        for (PlantData plant : ecosystem.getPlants())
            statistic.append(String.format("| %-10.10s|%10d | %13.2f | %10.1f | %8.2f | %+9.1f | %13.1f |%n",
                    plant.getName(), plant.getCount(), plant.getNeededHumidity(), plant.getNeededWater(),
                    plant.getNeededSunshine(), plant.getNormalTemperature(), plant.getContainsFood()));
        statistic.append("+-------------------------------------------------------------------------------------------+");
        return statistic.toString();
    }

    @Override
    public EcosystemData doTheEvolution(EcosystemData ecosystem) {
        Map<PlantData, Float> plantsToCoef;

        float neededWaterSum = ecosystem.getPlants().stream()
                .map(PlantData::getNeededWater).reduce(Float::sum)
                .get();

        if (neededWaterSum < ecosystem.getAmountOfWater()) {
            plantsToCoef = ecosystem.getPlants().stream()
                    .collect(Collectors.toMap(plant -> plant,
                            plant -> 1.0f + 0.05f * (ecosystem.getAmountOfWater() - neededWaterSum) / neededWaterSum));
            ecosystem.setAmountOfWater(ecosystem.getAmountOfWater() - neededWaterSum);
        }
        else {
            plantsToCoef = ecosystem.getPlants().stream()
                    .collect(Collectors.toMap(plant -> plant, plant -> ecosystem.getAmountOfWater() / neededWaterSum));
            ecosystem.setAmountOfWater(0);
        }

        for (Map.Entry<PlantData, Float> plantToCoef : plantsToCoef.entrySet()) {
            float multiplier = 1.0f;

            float humidityDiff = ecosystem.getHumidity() - plantToCoef.getKey().getNeededHumidity();
            if (humidityDiff >= 0)
                multiplier += humidityDiff;
            else
                multiplier *= ecosystem.getHumidity() / plantToCoef.getKey().getNeededHumidity();

            float sunshineDiff = ecosystem.getSunshine() - plantToCoef.getKey().getNeededSunshine();
            if (sunshineDiff >= 0)
                multiplier += sunshineDiff;
            else
                multiplier *= ecosystem.getSunshine() / plantToCoef.getKey().getNeededSunshine();

            float temperatureDiff = Math.abs(ecosystem.getTemperature() - plantToCoef.getKey().getNormalTemperature());
            multiplier -= 0.05f * temperatureDiff;

            plantToCoef.setValue(plantToCoef.getValue() * multiplier);
        }

        float plantFoodSum = plantsToCoef.entrySet().stream()
                .map(entry -> entry.getKey().getCount() * entry.getValue() * entry.getKey().getContainsFood())
                .reduce(Float::sum)
                .get();


        Map<PlantData, Float> plantToWater = ecosystem.getPlants().stream().collect(Collectors.toMap(plant -> plant,
                (PlantData plant) -> plant.getNeededWater() * plant.getCount()));

        return ecosystem;
    }

    @Override
    public List<String> getExistingEcosystems() {
        return filesService.getExistingEcosystems();
    }

    @Override
    public void createEcosystem(EcosystemData ecosystemData) throws WrongDataException {
        filesService.createEcosystem(ecosystemData);
    }

    @Override
    public EcosystemData getEcosystem(String name) throws WrongDataException {
        return filesService.getEcosystem(name);
    }

    @Override
    public EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException {
        return filesService.getEcosystemParams(ecosystemName);
    }

    @Override
    public void changeEcosystemParams(EcosystemData ecosystemData) throws WrongDataException {
        filesService.changeEcosystemParams(ecosystemData);
    }

    @Override
    public List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException {
        return filesService.getAnimals(ecosystemName);
    }

    @Override
    public void changeAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        filesService.changeAnimal(ecosystemName, animalData);
    }

    @Override
    public void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        filesService.addAnimal(ecosystemName, animalData);
    }

    @Override
    public void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        filesService.deleteAnimal(ecosystemName, animalData);
    }

    @Override
    public List<PlantData> getPlants(String ecosystemName) throws WrongDataException {
        return filesService.getPlants(ecosystemName);
    }

    @Override
    public void changePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        filesService.changePlant(ecosystemName, plantData);
    }

    @Override
    public void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        filesService.addPlant(ecosystemName, plantData);
    }

    @Override
    public void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        filesService.deletePlant(ecosystemName, plantData);
    }
}
