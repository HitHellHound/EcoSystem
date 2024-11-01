package service;

import dao.EcosystemDAO;
import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;
import enums.DangerLevel;
import enums.MealType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class EcosystemServiceImpl implements EcosystemService {
    private final EcosystemDAO ecosystemDAO;

    private final static float FLOAT_FAULT = 0.0005f;

    public EcosystemServiceImpl(EcosystemDAO ecosystemDAO) {
        this.ecosystemDAO = ecosystemDAO;
    }

    @Override
    public String getEcosystemShortStatistic(String ecosystemName) throws WrongDataException {
        EcosystemData ecosystem = ecosystemDAO.getEcosystem(ecosystemName);
        StringBuilder statistic = new StringBuilder();
        statistic.append(ecosystem.getName())
                .append(":\nHumidity -- ").append(ecosystem.getHumidity())
                .append("\nAmount of Water -- ").append(ecosystem.getAmountOfWater())
                .append("\nSunshine -- ").append(ecosystem.getSunshine())
                .append("\nTemperature -- ").append(ecosystem.getTemperature()).append("\n\n");

        statistic.append("Animals:\n");
        for (AnimalData animal : ecosystem.getAnimals())
            statistic.append(animal.getName()).append(": count -- ").append(animal.getCount()).append("\n");

        statistic.append("\nPlants:\n");
        for (PlantData plant : ecosystem.getPlants())
            statistic.append(plant.getName()).append(": count -- ").append(plant.getCount()).append("\n");

        return statistic.toString();
    }

    @Override
    public String getEcosystemFullStatistic(String ecosystemName) throws WrongDataException {
        EcosystemData ecosystem = ecosystemDAO.getEcosystem(ecosystemName);
        StringBuilder statistic = new StringBuilder();

        statistic.append("+----------------------------------------------------------------------+\n")
                .append("| Ecosystem Name | Humidity | Amount Of Water | Sunshine | Temperature |\n")
                .append("+----------------------------------------------------------------------+\n")
                .append(String.format("| %-14.14s | %8.2f | %15.1f | %8.2f | %+11.1f |%n",
                        ecosystem.getName(), ecosystem.getHumidity(), ecosystem.getAmountOfWater(), ecosystem.getSunshine(), ecosystem.getTemperature()))
                .append("+----------------------------------------------------------------------+\n\n");

        statistic.append("+---------------------------------------------------------------------------------------------+\n")
                .append("|   Animal   |   Count   | Danger Level |  Meal Type  | Need Food | Norm Temp | Contains Food |\n")
                .append("+---------------------------------------------------------------------------------------------+\n");
        for (AnimalData animal : ecosystem.getAnimals())
            statistic.append(String.format("| %-11.11s|%10d | %12s | %11s | %9.1f | %+9.1f | %13.1f |%n",
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
    public String makeEcosystemChangeStatistic(EcosystemData ecosystemChanged, EcosystemData ecosystemOriginal) {
        StringBuilder statistic = new StringBuilder();
        statistic.append(ecosystemChanged.getName())
                .append(":\nHumidity -- ").append(ecosystemChanged.getHumidity())
                .append("\nAmount of Water -- ").append(ecosystemChanged.getAmountOfWater())
                .append(" (").append(ecosystemChanged.getAmountOfWater() - ecosystemOriginal.getAmountOfWater()).append(")")
                .append("\nSunshine -- ").append(ecosystemChanged.getSunshine())
                .append("\nTemperature -- ").append(ecosystemChanged.getTemperature()).append("\n\n");

        statistic.append("Animals:\n");
        for (AnimalData animal : ecosystemChanged.getAnimals()) {
            int countChange = animal.getCount() - ecosystemOriginal.getAnimals().stream()
                    .filter(animalOriginal -> animalOriginal.getName().equals(animal.getName()))
                    .findFirst().get().getCount();
            statistic.append(animal.getName()).append(": count -- ").append(animal.getCount())
                    .append(" (").append(String.format("%+d", countChange)).append(")\n");
        }

        statistic.append("\nPlants:\n");
        for (PlantData plant : ecosystemChanged.getPlants()) {
            int countChange = plant.getCount() - ecosystemOriginal.getPlants().stream()
                    .filter(plantOriginal -> plantOriginal.getName().equals(plant.getName()))
                    .findFirst().get().getCount();
            statistic.append(plant.getName()).append(": count -- ").append(plant.getCount())
                    .append(" (").append(String.format("%+d", countChange)).append(")\n");
        }

        return statistic.toString();
    }

    @Override
    public void saveEcosystemStatement(EcosystemData ecosystemData) throws WrongDataException {
        ecosystemDAO.changeEcosystemParams(ecosystemData);
        for (AnimalData animalData : ecosystemData.getAnimals()) {
            ecosystemDAO.changeAnimal(ecosystemData.getName(), animalData);
        }
        for (PlantData plantData : ecosystemData.getPlants()) {
            ecosystemDAO.changePlant(ecosystemData.getName(), plantData);
        }
    }

    @Override
    public List<String> getExistingEcosystems() {
        return ecosystemDAO.getExistingEcosystems();
    }

    @Override
    public void closeEcosystem() {
        ecosystemDAO.closeEcosystem();
    }

    @Override
    public void createEcosystem(EcosystemData ecosystemData) throws WrongDataException {
        ecosystemDAO.createEcosystem(ecosystemData);
    }

    @Override
    public EcosystemData getEcosystem(String name) throws WrongDataException {
        return ecosystemDAO.getEcosystem(name);
    }

    @Override
    public EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException {
        return ecosystemDAO.getEcosystemParams(ecosystemName);
    }

    @Override
    public void changeEcosystemParams(EcosystemData ecosystemData) throws WrongDataException {
        ecosystemDAO.changeEcosystemParams(ecosystemData);
    }

    @Override
    public List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException {
        return ecosystemDAO.getAnimals(ecosystemName);
    }

    @Override
    public void changeAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        ecosystemDAO.changeAnimal(ecosystemName, animalData);
    }

    @Override
    public void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        ecosystemDAO.addAnimal(ecosystemName, animalData);
    }

    @Override
    public void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        ecosystemDAO.deleteAnimal(ecosystemName, animalData);
    }

    @Override
    public List<PlantData> getPlants(String ecosystemName) throws WrongDataException {
        return ecosystemDAO.getPlants(ecosystemName);
    }

    @Override
    public void changePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        ecosystemDAO.changePlant(ecosystemName, plantData);
    }

    @Override
    public void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        ecosystemDAO.addPlant(ecosystemName, plantData);
    }

    @Override
    public void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        ecosystemDAO.deletePlant(ecosystemName, plantData);
    }

    @Override
    public EcosystemData doTheEvolution(EcosystemData ecosystemOriginal) {
        EcosystemData ecosystem = makeEcosystemDataCopy(ecosystemOriginal);

        Map<PlantData, Float> plantsToCoef = calculatePlantPopulationCoefficients(ecosystem);

        List<AnimalData> carnivorousAnimals = ecosystem.getAnimals().stream()
                .filter(animal -> animal.getMealType().equals(MealType.CARNIVOROUS))
                .collect(Collectors.toList());
        List<AnimalData> omnivorousAnimals = ecosystem.getAnimals().stream()
                .filter(animal -> animal.getMealType().equals(MealType.OMNIVOROUS))
                .collect(Collectors.toList());
        List<AnimalData> herbivorousAnimals = ecosystem.getAnimals().stream()
                .filter(animal -> animal.getMealType().equals(MealType.HERBIVOROUS))
                .collect(Collectors.toList());

        float plantFoodSum = plantsToCoef.entrySet().stream()
                .map(entry -> entry.getKey().getCount() * entry.getValue() * entry.getKey().getContainsFood())
                .reduce(Float::sum)
                .orElse(0.0f);

        float omnivorousNeedFood = omnivorousAnimals.stream()
                .map(animal -> animal.getNeededFood() * animal.getCount())
                .reduce(Float::sum)
                .orElse(0.0f);
        float herbivorousNeedFood = herbivorousAnimals.stream()
                .map(animal -> animal.getNeededFood() * animal.getCount())
                .reduce(Float::sum)
                .orElse(0.0f);

        Map<AnimalData, Float> omnivorousAnimalsToCoef;
        Map<AnimalData, Float> herbivorousAnimalsToCoef;

        float neededPlantFoodSum = (herbivorousNeedFood + omnivorousNeedFood);
        float restPlantFood;
        float omnivorousRestNeedFoodCoef;
        if (neededPlantFoodSum < plantFoodSum) {
            herbivorousAnimalsToCoef = herbivorousAnimals.stream()
                    .collect(Collectors.toMap(Function.identity(),
                            animal -> 1.0f + 0.5f * (plantFoodSum - neededPlantFoodSum) / plantFoodSum));
            omnivorousAnimalsToCoef = omnivorousAnimals.stream()
                    .collect(Collectors.toMap(Function.identity(),
                            animal -> 1.0f + 0.25f * (plantFoodSum - neededPlantFoodSum) / plantFoodSum));
            omnivorousRestNeedFoodCoef = 0;
            restPlantFood = plantFoodSum - neededPlantFoodSum;
        } else {
            herbivorousAnimalsToCoef = herbivorousAnimals.stream()
                    .collect(Collectors.toMap(Function.identity(), animal -> plantFoodSum / neededPlantFoodSum));
            omnivorousAnimalsToCoef = omnivorousAnimals.stream()
                    .collect(Collectors.toMap(Function.identity(), animal -> plantFoodSum / neededPlantFoodSum));
            omnivorousRestNeedFoodCoef = 1 - plantFoodSum / neededPlantFoodSum;
            restPlantFood = 0;
        }

        for (Map.Entry<PlantData, Float> plantToCoef : plantsToCoef.entrySet()) {
            plantToCoef.setValue(plantToCoef.getValue() * (restPlantFood / plantFoodSum));
        }

        Map<DangerLevel, Float> dangerLevelToFoodSum = Arrays.stream(DangerLevel.values())
                .collect(toMap(Function.identity(),
                        dangerLevel -> ecosystem.getAnimals().stream()
                                .filter(animal -> animal.getDangerLevel().equals(dangerLevel))
                                .map(animal -> animal.getContainsFood() * animal.getCount())
                                .reduce(Float::sum)
                                .orElse(0.0f)));

        Map<DangerLevel, Float> dangerLevelToFoodSumForDeath = new HashMap<>(dangerLevelToFoodSum);

        Map<DangerLevel, Float> dangerLevelToNeededFood = Arrays.stream(DangerLevel.values())
                .collect(toMap(Function.identity(), dangerLevel ->
                        ecosystem.getAnimals().stream()
                                .filter(animal -> animal.getDangerLevel().equals(dangerLevel))
                                .map(animal -> {
                                    if (animal.getMealType().equals(MealType.OMNIVOROUS))
                                        return animal.getNeededFood() * animal.getCount() * omnivorousRestNeedFoodCoef;
                                    else
                                        return animal.getNeededFood() * animal.getCount();
                                }).reduce(Float::sum).orElse(0.0f)));

        Map<AnimalData, Float> carnivorousAnimalsToCoef = carnivorousAnimals.stream()
                .collect(Collectors.toMap(Function.identity(),
                        animal -> 1.0f));

        float foodForFifthLvl = dangerLevelToFoodSum.get(DangerLevel.THREE) + dangerLevelToFoodSum.get(DangerLevel.TWO)
                + dangerLevelToFoodSum.get(DangerLevel.ONE);
        if (dangerLevelToNeededFood.get(DangerLevel.FIVE) < foodForFifthLvl) {
            float foodReduceCoef = (foodForFifthLvl - dangerLevelToNeededFood.get(DangerLevel.FIVE)) / foodForFifthLvl;
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.FIVE))
                    entry.setValue(1.0f + 0.5f * foodReduceCoef);
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.FIVE))
                        entry.setValue(1.0f);
                });
            dangerLevelToFoodSum.replace(DangerLevel.THREE, dangerLevelToFoodSum.get(DangerLevel.THREE) * foodReduceCoef);
            dangerLevelToFoodSum.replace(DangerLevel.TWO, dangerLevelToFoodSum.get(DangerLevel.TWO) * foodReduceCoef);
            dangerLevelToFoodSum.replace(DangerLevel.ONE, dangerLevelToFoodSum.get(DangerLevel.ONE) * foodReduceCoef);
        } else {
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.FIVE))
                    entry.setValue(foodForFifthLvl / dangerLevelToNeededFood.get(DangerLevel.FIVE));
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.FIVE))
                        entry.setValue(entry.getValue() +
                                (1.0f - entry.getValue()) * foodForFifthLvl / dangerLevelToNeededFood.get(DangerLevel.FIVE));
                });
            dangerLevelToFoodSum.replace(DangerLevel.THREE, 0.0f);
            dangerLevelToFoodSum.replace(DangerLevel.TWO, 0.0f);
            dangerLevelToFoodSum.replace(DangerLevel.ONE, 0.0f);
        }

        float foodForFourthLvl = dangerLevelToFoodSum.get(DangerLevel.TWO) + dangerLevelToFoodSum.get(DangerLevel.ONE);

        if (dangerLevelToNeededFood.get(DangerLevel.FOUR) < foodForFourthLvl) {
            float foodReduceCoef = (foodForFourthLvl - dangerLevelToNeededFood.get(DangerLevel.FOUR)) / foodForFourthLvl;
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.FOUR))
                    entry.setValue(1.0f + 0.5f * foodReduceCoef);
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.FOUR))
                        entry.setValue(1.0f);
                });
            dangerLevelToFoodSum.replace(DangerLevel.TWO, dangerLevelToFoodSum.get(DangerLevel.TWO) * foodReduceCoef);
            dangerLevelToFoodSum.replace(DangerLevel.ONE, dangerLevelToFoodSum.get(DangerLevel.ONE) * foodReduceCoef);
        } else {
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.FOUR))
                    entry.setValue(foodForFourthLvl / dangerLevelToNeededFood.get(DangerLevel.FOUR));
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.FOUR))
                        entry.setValue(entry.getValue() +
                                (1.0f - entry.getValue()) * foodForFourthLvl / dangerLevelToNeededFood.get(DangerLevel.FOUR));
                });
            dangerLevelToFoodSum.replace(DangerLevel.TWO, 0.0f);
            dangerLevelToFoodSum.replace(DangerLevel.ONE, 0.0f);
        }

        float foodForThirdLvl = dangerLevelToFoodSum.get(DangerLevel.ONE);
        if (dangerLevelToNeededFood.get(DangerLevel.THREE) < foodForThirdLvl) {
            float foodReduceCoef = (foodForThirdLvl - dangerLevelToNeededFood.get(DangerLevel.THREE)) / foodForThirdLvl;
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE))
                    entry.setValue(1.0f + 0.5f * foodReduceCoef);
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE))
                        entry.setValue(1.0f);
                });
            dangerLevelToFoodSum.replace(DangerLevel.ONE, dangerLevelToFoodSum.get(DangerLevel.ONE) * foodReduceCoef);
        } else {
            carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE))
                    entry.setValue(foodForThirdLvl / dangerLevelToNeededFood.get(DangerLevel.THREE));
            });
            if (omnivorousRestNeedFoodCoef > FLOAT_FAULT)
                omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
                    if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE))
                        entry.setValue(entry.getValue() +
                                (1.0f - entry.getValue()) * foodForThirdLvl / dangerLevelToNeededFood.get(DangerLevel.THREE));
                });
            dangerLevelToFoodSum.replace(DangerLevel.ONE, 0.0f);
        }

        carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
            if (entry.getKey().getDangerLevel().equals(DangerLevel.TWO) || entry.getKey().getDangerLevel().equals(DangerLevel.ONE))
                entry.setValue(0.0f);
        });

        carnivorousAnimalsToCoef.entrySet().forEach(entry -> {
            if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.THREE) / dangerLevelToFoodSumForDeath.get(DangerLevel.THREE)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.TWO)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.TWO) / dangerLevelToFoodSumForDeath.get(DangerLevel.TWO)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.ONE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.ONE) / dangerLevelToFoodSumForDeath.get(DangerLevel.ONE)));
            }
        });
        omnivorousAnimalsToCoef.entrySet().forEach(entry -> {
            if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.THREE) / dangerLevelToFoodSumForDeath.get(DangerLevel.THREE)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.TWO)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.TWO) / dangerLevelToFoodSumForDeath.get(DangerLevel.TWO)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.ONE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.ONE) / dangerLevelToFoodSumForDeath.get(DangerLevel.ONE)));
            }
        });
        herbivorousAnimalsToCoef.entrySet().forEach(entry -> {
            if (entry.getKey().getDangerLevel().equals(DangerLevel.THREE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.THREE) / dangerLevelToFoodSumForDeath.get(DangerLevel.THREE)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.TWO)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.TWO) / dangerLevelToFoodSumForDeath.get(DangerLevel.TWO)));
            }
            if (entry.getKey().getDangerLevel().equals(DangerLevel.ONE)) {
                entry.setValue(entry.getValue() *
                        (dangerLevelToFoodSum.get(DangerLevel.ONE) / dangerLevelToFoodSumForDeath.get(DangerLevel.ONE)));
            }
        });



        Map<AnimalData, Float> animalsToCoef = new HashMap<>(carnivorousAnimalsToCoef);
        animalsToCoef.putAll(omnivorousAnimalsToCoef);
        animalsToCoef.putAll(herbivorousAnimalsToCoef);


        for (Map.Entry<AnimalData, Float> animalToCoef : animalsToCoef.entrySet()) {
            float multiplier = 1.0f;

            float temperatureDiff = Math.abs(ecosystem.getTemperature() - animalToCoef.getKey().getNormalTemperature());
            multiplier -= 0.03f * temperatureDiff;
            if (multiplier < 0)
                multiplier = 0;
            animalToCoef.setValue(animalToCoef.getValue() * multiplier);
        }

        for (AnimalData animal : ecosystem.getAnimals()) {
            animal.setCount((int) Math.round(Math.floor(animal.getCount() * animalsToCoef.get(animal))));
        }

        for (PlantData plant : ecosystem.getPlants()) {
            plant.setCount(Math.round(plant.getCount() * plantsToCoef.get(plant)));
        }

        return ecosystem;
    }

    private EcosystemData makeEcosystemDataCopy(EcosystemData ecosystemOriginal) {
        EcosystemData ecosystemCopy = new EcosystemData(ecosystemOriginal.getName(), ecosystemOriginal.getHumidity(),
                ecosystemOriginal.getAmountOfWater(), ecosystemOriginal.getSunshine(), ecosystemOriginal.getTemperature());
        ecosystemCopy.setAnimals(new ArrayList<>());
        ecosystemCopy.setPlants(new ArrayList<>());

        ecosystemOriginal.getAnimals().forEach(animal ->
                ecosystemCopy.getAnimals().add(new AnimalData(animal.getName(), animal.getCount(),
                        animal.getDangerLevel(), animal.getMealType(),
                        animal.getNeededFood(), animal.getNormalTemperature(), animal.getContainsFood())));

        ecosystemOriginal.getPlants().forEach(plant ->
                ecosystemCopy.getPlants().add(new PlantData(plant.getName(), plant.getCount(),
                        plant.getNeededHumidity(), plant.getNeededWater(), plant.getNeededSunshine(),
                        plant.getNormalTemperature(), plant.getContainsFood())));

        return ecosystemCopy;
    }

    private Map<PlantData, Float> calculatePlantPopulationCoefficients(EcosystemData ecosystem) {
        Map<PlantData, Float> plantsToCoef;

        float neededWaterSum = ecosystem.getPlants().stream()
                .map(plant -> plant.getNeededWater() * plant.getCount()).reduce(Float::sum)
                .orElse(0.0f);

        if (neededWaterSum < ecosystem.getAmountOfWater()) {
            plantsToCoef = ecosystem.getPlants().stream()
                    .collect(Collectors.toMap(Function.identity(),
                            plant -> 1.0f + 0.5f * (ecosystem.getAmountOfWater() - neededWaterSum) / ecosystem.getAmountOfWater()));
            ecosystem.setAmountOfWater(ecosystem.getAmountOfWater() - neededWaterSum);
        }
        else {
            plantsToCoef = ecosystem.getPlants().stream()
                    .collect(Collectors.toMap(Function.identity(), plant -> ecosystem.getAmountOfWater() / neededWaterSum));
            ecosystem.setAmountOfWater(0);
        }

        for (Map.Entry<PlantData, Float> plantToCoef : plantsToCoef.entrySet()) {
            float multiplier = 1.0f;

            float humidityDiff = ecosystem.getHumidity() - plantToCoef.getKey().getNeededHumidity();
            float temperatureDiff = Math.abs(ecosystem.getTemperature() - plantToCoef.getKey().getNormalTemperature());
            float sunshineDiff = ecosystem.getSunshine() - plantToCoef.getKey().getNeededSunshine();

            if (humidityDiff >= 0 && sunshineDiff >= 0) {
                multiplier += humidityDiff;
                multiplier += sunshineDiff;
            }
            else if (humidityDiff < 0 && sunshineDiff >= 0) {
                multiplier += humidityDiff;
                multiplier *= ecosystem.getHumidity() / plantToCoef.getKey().getNeededHumidity();
            } else if (humidityDiff >= 0 && sunshineDiff < 0) {
                multiplier += sunshineDiff;
                multiplier *= ecosystem.getSunshine() / plantToCoef.getKey().getNeededSunshine();
            } else {
                multiplier *= ecosystem.getHumidity() / plantToCoef.getKey().getNeededHumidity();
                multiplier *= ecosystem.getSunshine() / plantToCoef.getKey().getNeededSunshine();
            }

            multiplier *= (1 - 0.05f * temperatureDiff);

            if (multiplier < 0)
                multiplier = 0;

            plantToCoef.setValue(plantToCoef.getValue() * multiplier);
        }

        return plantsToCoef;
    }
}
