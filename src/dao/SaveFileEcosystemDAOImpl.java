package dao;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;
import model.Animal;
import model.Ecosystem;
import model.Plant;
import enums.DangerLevel;
import enums.MealType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveFileEcosystemDAOImpl implements EcosystemDAO {
    public static final String SAVES_PATH = "saves/";
    public static final String SAVE_FILE_PREFIX = "save_";
    public static final String SAVE_FILE_EXTENSION = ".txt";

    private Ecosystem loadedEcosystem;

    @Override
    public void createEcosystem(EcosystemData ecosystemData) throws WrongDataException {
        validateEcosystemParams(ecosystemData);
        loadedEcosystem = new Ecosystem(ecosystemData.getName(), new ArrayList<>(), new ArrayList<>(),
                ecosystemData.getHumidity(), ecosystemData.getAmountOfWater(), ecosystemData.getSunshine(),
                ecosystemData.getTemperature());
        saveEcosystem();
    }

    @Override
    public EcosystemData getEcosystem(String ecosystemName) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        return ctreateEcosystemData();
    }

    @Override
    public EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        return new EcosystemData(loadedEcosystem.getName(), loadedEcosystem.getHumidity(),
                loadedEcosystem.getAmountOfWater(), loadedEcosystem.getSunshine(), loadedEcosystem.getTemperature());
    }

    @Override
    public List<String> getExistingEcosystems() {
        File[] files = new File(SAVES_PATH).listFiles();
        if (files == null) {
            new File(SAVES_PATH).mkdirs();
            files = new File(SAVES_PATH).listFiles();
        }
        return Stream.of(files).
                filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> fileName.startsWith(SAVE_FILE_PREFIX) && fileName.endsWith(SAVE_FILE_EXTENSION))
                .map(fileName -> fileName.subSequence(SaveFileEcosystemDAOImpl.SAVE_FILE_PREFIX.length(),
                        fileName.length() - SaveFileEcosystemDAOImpl.SAVE_FILE_EXTENSION.length()).toString())
                .collect(Collectors.toList());
    }

    @Override
    public void changeEcosystemParams(EcosystemData ecosystemData) throws WrongDataException {
        validateEcosystemParams(ecosystemData);
        if (!isEcosystemLoaded(ecosystemData.getName())) {
            loadEcosystem(ecosystemData.getName());
        }
        loadedEcosystem.setHumidity(ecosystemData.getHumidity());
        loadedEcosystem.setAmountOfWater(ecosystemData.getAmountOfWater());
        loadedEcosystem.setSunshine(ecosystemData.getSunshine());
        loadedEcosystem.setTemperature(ecosystemData.getTemperature());
        saveEcosystem();
    }

    @Override
    public List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }
        return createAnimalsData();
    }

    @Override
    public void changeAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        validateAnimal(animalData);
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        Animal animalToChange = loadedEcosystem.getAnimals().stream()
                .filter(animal -> animal.getName().equals(animalData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + animalData.getName()));
        animalToChange.setCount(animalData.getCount());
        saveEcosystem();
    }

    @Override
    public void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        validateAnimal(animalData);
        loadedEcosystem.getAnimals().add(new Animal(animalData.getName(), animalData.getCount(),
                animalData.getDangerLevel(), animalData.getMealType(), animalData.getNeededFood(),
                animalData.getNormalTemperature(), animalData.getContainsFood()));
        saveEcosystem();
    }

    @Override
    public void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        Animal animalToDelete = loadedEcosystem.getAnimals().stream()
                .filter(animal -> animal.getName().equals(animalData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + animalData.getName()));
        loadedEcosystem.getAnimals().remove(animalToDelete);
        saveEcosystem();
    }

    @Override
    public List<PlantData> getPlants(String ecosystemName) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }
        return createPlantsData();
    }

    @Override
    public void changePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        validatePlant(plantData);
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        Plant plantToChange = loadedEcosystem.getPlants().stream()
                .filter(plant -> plant.getName().equals(plantData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + plantData.getName()));
        plantToChange.setCount(plantData.getCount());
        saveEcosystem();
    }

    @Override
    public void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        validatePlant(plantData);
        loadedEcosystem.getPlants().add(new Plant(plantData.getName(), plantData.getCount(), plantData.getNeededHumidity(),
                plantData.getNeededWater(), plantData.getNeededSunshine(),
                plantData.getNormalTemperature(), plantData.getContainsFood()));
        saveEcosystem();
    }

    @Override
    public void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        if (!isEcosystemLoaded(ecosystemName)) {
            loadEcosystem(ecosystemName);
        }

        Plant plantToDelete = loadedEcosystem.getPlants().stream()
                .filter(plant -> plant.getName().equals(plantData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + plantData.getName()));
        loadedEcosystem.getPlants().remove(plantToDelete);
        saveEcosystem();
    }

    private boolean isEcosystemLoaded(String ecosystemName) {
        return loadedEcosystem != null && loadedEcosystem.getName().equals(ecosystemName);
    }

    private void loadEcosystem(String ecosystemName) throws WrongDataException{
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVES_PATH + SAVE_FILE_PREFIX
                + ecosystemName + SAVE_FILE_EXTENSION))) {
            String ecosystemData = reader.readLine();
            Ecosystem ecosystem = buildEcosystemFromString(ecosystemData);

            reader.readLine();

            String animalData = reader.readLine();
            while (animalData != null && !animalData.isEmpty()) {
                ecosystem.getAnimals().add(buildAnimalFromString(animalData));
                animalData = reader.readLine();
            }

            String plantData = reader.readLine();
            while (plantData != null && !plantData.isEmpty()) {
                ecosystem.getPlants().add(buildPlantFromString(plantData));
                plantData = reader.readLine();
            }

            loadedEcosystem = ecosystem;
        } catch (NumberFormatException ex) {
            throw new WrongDataException("Wrong number format in file");
        } catch (IOException ex) {
            throw new WrongDataException("Some problems with " + ecosystemName + " file");
        }
    }

    private void saveEcosystem() throws WrongDataException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(SAVES_PATH + SAVE_FILE_PREFIX + loadedEcosystem.getName() + SAVE_FILE_EXTENSION))) {
            String data = createEcosystemSaveData();
            writer.append(data);
        } catch (IOException e) {
            throw new WrongDataException("Can't create save file for " + loadedEcosystem.getName());
        }
    }

    private EcosystemData ctreateEcosystemData() {
        EcosystemData ecosystemData = new EcosystemData(loadedEcosystem.getName(), loadedEcosystem.getHumidity(),
                loadedEcosystem.getAmountOfWater(), loadedEcosystem.getSunshine(), loadedEcosystem.getTemperature());

        ecosystemData.setAnimals(createAnimalsData());
        ecosystemData.setPlants(createPlantsData());

        return ecosystemData;
    }

    private List<AnimalData> createAnimalsData() {
        return loadedEcosystem.getAnimals().stream()
                .map(animal -> new AnimalData(animal.getName(), animal.getCount(), animal.getDangerLevel(),
                        animal.getMealType(), animal.getNeededFood(), animal.getNormalTemperature(), animal.getContainsFood()))
                .collect(Collectors.toList());
    }

    private List<PlantData> createPlantsData() {
        return loadedEcosystem.getPlants().stream()
                .map(plant -> new PlantData(plant.getName(), plant.getCount(), plant.getNeededHumidity(), plant.getNeededWater(),
                        plant.getNeededSunshine(), plant.getNormalTemperature(), plant.getContainsFood()))
                .collect(Collectors.toList());
    }

    private String createEcosystemSaveData() {
        StringBuilder data = new StringBuilder();
        data.append(loadedEcosystem.getName()).append(" ")
                .append(loadedEcosystem.getHumidity()).append(" ")
                .append(loadedEcosystem.getAmountOfWater()).append(" ")
                .append(loadedEcosystem.getSunshine()).append(" ")
                .append(loadedEcosystem.getTemperature()).append("\n\n");

        for (Animal animal : loadedEcosystem.getAnimals())
            data.append(animal.getName()).append(" ")
                    .append(animal.getCount()).append(" ")
                    .append(animal.getDangerLevel().getDngLvlInt()).append(" ")
                    .append(animal.getMealType().getMealTypeInt()).append(" ")
                    .append(animal.getNeededFood()).append(" ")
                    .append(animal.getNormalTemperature()).append(" ")
                    .append(animal.getContainsFood()).append("\n");
        data.append("\n");

        for (Plant plant : loadedEcosystem.getPlants())
            data.append(plant.getName()).append(" ")
                    .append(plant.getCount()).append(" ")
                    .append(plant.getNeededHumidity()).append(" ")
                    .append(plant.getNeededWater()).append(" ")
                    .append(plant.getNeededSunshine()).append(" ")
                    .append(plant.getNormalTemperature()).append(" ")
                    .append(plant.getContainsFood()).append("\n");

        return data.toString();
    }

    private Ecosystem buildEcosystemFromString(String ecosystemData) throws WrongDataException {
        String[] ecosystemDataSplitted = ecosystemData.split(" ");

        if (ecosystemDataSplitted.length != 5)
            throw new WrongDataException("Wrong ecosystem data");

        String name = ecosystemDataSplitted[0];
        float humidity = Float.parseFloat(ecosystemDataSplitted[1]);
        float amountOfWater = Float.parseFloat(ecosystemDataSplitted[2]);
        float sunshine = Float.parseFloat(ecosystemDataSplitted[3]);
        float temperature = Float.parseFloat(ecosystemDataSplitted[4]);

        EcosystemData ecosystem = new EcosystemData(name, humidity, amountOfWater,
                sunshine, temperature);
        validateEcosystemParams(ecosystem);

        return new Ecosystem(name, new ArrayList<>(), new ArrayList<>(), humidity, amountOfWater, sunshine, temperature);
    }

    private Animal buildAnimalFromString(String animalData) throws WrongDataException {
        String[] animalDataSplitted = animalData.split(" ");

        if (animalDataSplitted.length != 7)
            throw new WrongDataException("Wrong animal data");

        String name = animalDataSplitted[0];
        int count = Integer.parseInt(animalDataSplitted[1]);
        int dangerLvl = Integer.parseInt(animalDataSplitted[2]);
        int mealType = Integer.parseInt(animalDataSplitted[3]);
        float neededFood = Float.parseFloat(animalDataSplitted[4]);
        float normalTemperature = Float.parseFloat(animalDataSplitted[5]);
        float containsFood = Float.parseFloat(animalDataSplitted[6]);

        AnimalData animal = new AnimalData(name, count, DangerLevel.valueOfInt(dangerLvl), MealType.valueOfInt(mealType),
                neededFood, normalTemperature, containsFood);

        validateAnimal(animal);

        return new Animal(name, count, DangerLevel.valueOfInt(dangerLvl), MealType.valueOfInt(mealType),
                neededFood, normalTemperature, containsFood);
    }

    private Plant buildPlantFromString(String plantData) throws WrongDataException {
        String[] plantDataSplitted = plantData.split(" ");

        if (plantDataSplitted.length != 7)
            throw new WrongDataException("Wrong plant data");

        String name = plantDataSplitted[0];
        int count = Integer.parseInt(plantDataSplitted[1]);
        float neededHumidity = Float.parseFloat(plantDataSplitted[2]);
        float neededWater = Float.parseFloat(plantDataSplitted[3]);
        float neededSunshine = Float.parseFloat(plantDataSplitted[4]);
        float normalTemperature = Float.parseFloat(plantDataSplitted[5]);
        float containsFood = Float.parseFloat(plantDataSplitted[6]);

        PlantData plant = new PlantData(name, count, neededHumidity, neededWater, neededSunshine, normalTemperature,
                containsFood);

        validatePlant(plant);

        return new Plant(name, count, neededHumidity, neededWater, neededSunshine, normalTemperature,
                containsFood);
    }

    private void validateEcosystemParams(EcosystemData ecosystem) throws WrongDataException {
        if (ecosystem.getHumidity() < 0.00 || ecosystem.getHumidity() > 1.00
                || ecosystem.getAmountOfWater() < 0
                || ecosystem.getSunshine() < 0.00 || ecosystem.getSunshine() > 1.00) {
            throw new WrongDataException("Some parameters in ecosystem " + ecosystem.getName() + " out of bounds");
        }
    }

    private void validateAnimal(AnimalData animal) throws WrongDataException {
        if (animal.getCount() < 0
                || animal.getNeededFood() < 0
                || animal.getContainsFood() < 0.00) {
            throw new WrongDataException("Some parameters in animal " + animal.getName() + " out of bounds");
        }
    }

    private void validatePlant(PlantData plant) throws WrongDataException {
        if (plant.getCount() < 0
                || plant.getNeededHumidity() < 0.00 || plant.getNeededHumidity() > 1.00
                || plant.getNeededWater() < 0
                || plant.getNeededSunshine() < 0.00 || plant.getNeededSunshine() > 1.00
                || plant.getContainsFood() < 0.00) {
            throw new WrongDataException("Some parameters in plant " + plant.getName() + " out of bounds");
        }
    }
}
