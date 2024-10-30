package service;

import ecxeptions.FileServiceException;
import ecxeptions.WrongDataException;
import model.Ecosystem;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesServiceImpl implements FilesService {
    public static final String ANIMALS_BASE_DATA = "baseData/animals.txt";
    public static final String PLANTS_BASE_DATA = "baseData/plants.txt";
    public static final String SAVES_PATH = "saves/";
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
    public void saveEcosystem(Ecosystem ecosystem) throws FileServiceException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(SAVES_PATH + SAVE_FILE_PREFIX + ecosystem.getName() + SAVE_FILE_EXTENSION))) {
            String data = ecosystemService.createEcosystemFullData(ecosystem);
            writer.append(data);
        } catch (IOException e) {
            throw new FileServiceException();
        }
    }

    @Override
    public List<String> getSaveFiles() {
        return Stream.of(new File(SAVES_PATH).listFiles()).
                filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> fileName.startsWith(SAVE_FILE_PREFIX) && fileName.endsWith(SAVE_FILE_EXTENSION))
                .collect(Collectors.toList());
    }

    @Override
    public void loadBaseAnimalsAndPlants(Ecosystem ecosystem) throws WrongDataException {
        try (BufferedReader animalReader = new BufferedReader(new FileReader(ANIMALS_BASE_DATA));
                BufferedReader plantsReader = new BufferedReader(new FileReader(PLANTS_BASE_DATA))) {

            String animalData = animalReader.readLine();
            while (animalData != null && !animalData.isEmpty()){
                buildAndAddAnimal(ecosystem, animalData);
                animalData = animalReader.readLine();
            }

            String plantData = plantsReader.readLine();
            while (plantData != null && !plantData.isEmpty()){
                buildAndAddPlant(ecosystem, plantData);
                plantData = plantsReader.readLine();
            }
        }
        catch (NumberFormatException ex) {
            throw new WrongDataException("Wrong number format in files");
        }
        catch (IOException ex) {
            throw new WrongDataException("Some problems with files");
        }
    }

    private Ecosystem buildEcosystem(String ecosystemData) throws WrongDataException {
        String[] ecosystemDataSplitted = ecosystemData.split(" ");

        if (ecosystemDataSplitted.length != 5)
            throw new WrongDataException("Wrong ecosystem data");

        String name = ecosystemDataSplitted[0];
        float humidity = Float.parseFloat(ecosystemDataSplitted[1]);
        float amountOfWater = Float.parseFloat(ecosystemDataSplitted[2]);
        float sunshine = Float.parseFloat(ecosystemDataSplitted[3]);
        float temperature = Float.parseFloat(ecosystemDataSplitted[4]);

        return ecosystemService.createEcosystem(name, humidity, amountOfWater, sunshine, temperature);
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
        float neededHumidity = Float.parseFloat(plantDataSplitted[2]);
        float neededWater = Float.parseFloat(plantDataSplitted[3]);
        float neededSunshine = Float.parseFloat(plantDataSplitted[4]);
        float normalTemperature = Float.parseFloat(plantDataSplitted[5]);
        float deathCoefficient = Float.parseFloat(plantDataSplitted[6]);
        float bornCoefficient = Float.parseFloat(plantDataSplitted[7]);

        ecosystemService.createAndAddPlant(ecosystem, name, count, neededHumidity, neededWater, neededSunshine, normalTemperature,
                deathCoefficient, bornCoefficient);
    }
}
