package service;

import ecxeptions.WrongDataException;
import model.Animal;
import model.Ecosystem;
import model.Plant;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesServiceImpl implements FilesService {
    public static final String BASE_DATA_PATH = "BaseData/";
    public static final String SAVES_PATH = "Saves/";
    public static final String SAVE_FILE_PREFIX = "save_";
    public static final String SAVE_FILE_EXTENSION = ".txt";

    public EcosystemService ecosystemService = new EcosystemServiceImpl();

    @Override
    public Ecosystem loadEcosystem(String fileName) throws WrongDataException {
        Ecosystem ecosystem = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVES_PATH + fileName))) {
            String ecosystemData = reader.readLine();
            ecosystem = buildEcosystem(ecosystemData);

            reader.readLine();

            String animalData = reader.readLine();
            while (animalData != null && !animalData.isEmpty()){
                buildAndAddAnimal(ecosystem, animalData);
                animalData = reader.readLine();
            }

            String plantData = reader.readLine();
            while (plantData != null && !plantData.isEmpty()){
                buildAndAddPlant(ecosystem, plantData);
                plantData = reader.readLine();
            }
        }
        catch (NumberFormatException ex) {
            throw new WrongDataException("Wrong number format in file");
        }
        catch (IOException ex) {
            throw new WrongDataException("Some problems with file");
        }
        return ecosystem;
    }

    @Override
    public void saveEcosystem(Ecosystem ecosystem) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(SAVES_PATH + SAVE_FILE_PREFIX + ecosystem.getName() + SAVE_FILE_EXTENSION))) {
            StringBuilder data = createEcosystemData(ecosystem);
            writer.append(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getSaveFiles() {
        return Stream.of(new File(SAVES_PATH).listFiles()).
                filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> fileName.startsWith(SAVE_FILE_PREFIX) && fileName.endsWith(SAVE_FILE_EXTENSION))
                .collect(Collectors.toList());
    }

    private Ecosystem buildEcosystem(String ecosystemData) throws WrongDataException {
        String[] ecosystemDataSplitted = ecosystemData.split(" ");

        if (ecosystemDataSplitted.length != 5)
            throw new WrongDataException("Wrong ecosystem data");

        String name = ecosystemDataSplitted[0];
        float wetLvl = Float.parseFloat(ecosystemDataSplitted[1]);
        float amountOfWater = Float.parseFloat(ecosystemDataSplitted[2]);
        float sunLvl = Float.parseFloat(ecosystemDataSplitted[3]);
        float temperature = Float.parseFloat(ecosystemDataSplitted[4]);

        return ecosystemService.createEcosystem(name, wetLvl, amountOfWater, sunLvl, temperature);
    }

    private void buildAndAddAnimal(Ecosystem ecosystem, String animalData) throws WrongDataException {
        String[] animalDataSplitted = animalData.split(" ");

        if (animalDataSplitted.length != 9)
            throw new WrongDataException("Wrong animal data");

        String name = animalDataSplitted[0];
        int count = Integer.parseInt(animalDataSplitted[1]);
        int dangerLvl = Integer.parseInt(animalDataSplitted[2]);
        int mealType = Integer.parseInt(animalDataSplitted[3]);
        int neededFood = Integer.parseInt(animalDataSplitted[4]);
        float neededWater = Float.parseFloat(animalDataSplitted[5]);
        float normalTemperature = Float.parseFloat(animalDataSplitted[6]);
        float deathCoefficient = Float.parseFloat(animalDataSplitted[7]);
        float bornCoefficient = Float.parseFloat(animalDataSplitted[8]);

        ecosystemService.createAndAddAnimal(ecosystem, name, count, dangerLvl, mealType,
                neededFood, neededWater, normalTemperature, deathCoefficient, bornCoefficient);
    }

    private void buildAndAddPlant(Ecosystem ecosystem, String plantData) throws WrongDataException {
        String[] plantDataSplitted = plantData.split(" ");

        if (plantDataSplitted.length != 8)
            throw new WrongDataException("Wrong plant data");

        String name = plantDataSplitted[0];
        int count = Integer.parseInt(plantDataSplitted[1]);
        float neededWet = Float.parseFloat(plantDataSplitted[2]);
        float neededWater = Float.parseFloat(plantDataSplitted[3]);
        float neededSun = Float.parseFloat(plantDataSplitted[4]);
        float normalTemperature = Float.parseFloat(plantDataSplitted[5]);
        float deathCoefficient = Float.parseFloat(plantDataSplitted[6]);
        float bornCoefficient = Float.parseFloat(plantDataSplitted[7]);

        ecosystemService.createAndAddPlant(ecosystem, name, count, neededWet, neededWater, neededSun, normalTemperature,
                deathCoefficient, bornCoefficient);
    }

    private StringBuilder createEcosystemData(Ecosystem ecosystem) {
        StringBuilder data = new StringBuilder();
        data.append(ecosystem.getName()).append(" ")
                .append(ecosystem.getWetLvl()).append(" ")
                .append(ecosystem.getAmountOfWater()).append(" ")
                .append(ecosystem.getSunLvl()).append(" ")
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
                    .append(plant.getNeededWet()).append(" ")
                    .append(plant.getNeededWater()).append(" ")
                    .append(plant.getNeededSun()).append(" ")
                    .append(plant.getNormalTemperature()).append(" ")
                    .append(plant.getDeathCoefficient()).append(" ")
                    .append(plant.getBornCoefficient()).append("\n");

        return data;
    }
}
