package dao;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;
import enums.DangerLevel;
import enums.MealType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesEcosystemDAOImpl implements EcosystemDAO {
    public static final String ECOSYSTEM_DATA_PATH = "ecosystemsData/";
    public static final String ECOSYSTEM_FILE_PREFIX = "ecosystem_";
    public static final String ANIMALS_FILE_PREFIX = "animals_";
    public static final String PLANTS_FILE_PREFIX = "plants_";
    public static final String FILE_EXTENSION = ".txt";

    private final static float FLOAT_FAULT = 0.0005f;

    static {
        File saveDir = new File(ECOSYSTEM_DATA_PATH);
        if (!saveDir.exists()) {
            new File(ECOSYSTEM_DATA_PATH).mkdirs();
        }
    }

    @Override
    public void createEcosystem(EcosystemData ecosystem) throws WrongDataException {
        saveFullEcosystem(ecosystem);
    }

    @Override
    public EcosystemData getFullEcosystem(String ecosystemName) throws WrongDataException {
        return loadFullEcosystem(ecosystemName);
    }

    @Override
    public EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException {
        return loadEcosystemParams(ecosystemName);
    }

    @Override
    public List<String> getExistingEcosystems() {
        File[] files = new File(ECOSYSTEM_DATA_PATH).listFiles();
        return Stream.of(files).
                filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> fileName.startsWith(ECOSYSTEM_FILE_PREFIX) && fileName.endsWith(FILE_EXTENSION))
                .map(fileName -> fileName.subSequence(ECOSYSTEM_FILE_PREFIX.length(),
                        fileName.length() - FILE_EXTENSION.length()).toString())
                .collect(Collectors.toList());
    }

    @Override
    public void updateEcosystemParams(EcosystemData ecosystemData) throws WrongDataException {
        saveEcosystemParams(ecosystemData);
    }

    @Override
    public List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException {
        return loadAnimals(ecosystemName);
    }

    @Override
    public void updateAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        List<AnimalData> animalDataList = getAnimals(ecosystemName);
        AnimalData animalToUpdate = animalDataList.stream()
                .filter(animal -> animal.getName().equals(animalData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + animalData.getName() + " animal"));
        animalToUpdate.setCount(animalData.getCount());
        saveAnimals(animalDataList, ecosystemName);
    }

    @Override
    public void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        List<AnimalData> animalDataList = getAnimals(ecosystemName);
        animalDataList.add(animalData);
        saveAnimals(animalDataList, ecosystemName);
    }

    @Override
    public void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException {
        List<AnimalData> animalDataList = getAnimals(ecosystemName);
        AnimalData animalToDelete = animalDataList.stream()
                .filter(animal -> animal.getName().equals(animalData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + animalData.getName() + " animal"));
        animalDataList.remove(animalToDelete);
        saveAnimals(animalDataList, ecosystemName);
    }

    @Override
    public List<PlantData> getPlants(String ecosystemName) throws WrongDataException {
        return loadPlants(ecosystemName);
    }

    @Override
    public void updatePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        List<PlantData> plantDataList = getPlants(ecosystemName);
        PlantData plantToUpdate = plantDataList.stream()
                .filter(plant -> plant.getName().equals(plantData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + plantData.getName() + " plant"));
        plantToUpdate.setCount(plantData.getCount());
        savePlants(plantDataList, ecosystemName);
    }

    @Override
    public void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        List<PlantData> plantDataList = getPlants(ecosystemName);
        plantDataList.add(plantData);
        savePlants(plantDataList, ecosystemName);
    }

    @Override
    public void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException {
        List<PlantData> plantDataList = getPlants(ecosystemName);
        PlantData plantToDelete = plantDataList.stream()
                .filter(plant -> plant.getName().equals(plantData.getName()))
                .findFirst()
                .orElseThrow(() -> new WrongDataException("Can't find " + plantData.getName() + " plant"));
        plantDataList.remove(plantToDelete);
        savePlants(plantDataList, ecosystemName);
    }


    private EcosystemData loadFullEcosystem(String ecosystemName) throws WrongDataException{
        EcosystemData ecosystem = loadEcosystemParams(ecosystemName);
        ecosystem.setAnimals(loadAnimals(ecosystemName));
        ecosystem.setPlants(loadPlants(ecosystemName));
        return ecosystem;
    }

    private EcosystemData loadEcosystemParams(String ecosystemName) throws WrongDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ECOSYSTEM_DATA_PATH + ECOSYSTEM_FILE_PREFIX
                + ecosystemName + FILE_EXTENSION))) {
            return buildEcosystemParamsFromString(reader.readLine());
        } catch (NumberFormatException exception) {
            throw new WrongDataException("Wrong number format in file");
        } catch (IOException exception) {
            throw new WrongDataException("Some problems with " + ecosystemName + " file");
        }
    }

    private List<AnimalData> loadAnimals(String ecosystemName) throws WrongDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ECOSYSTEM_DATA_PATH + ANIMALS_FILE_PREFIX
                + ecosystemName + FILE_EXTENSION))) {
            List<AnimalData> animalsData = new ArrayList<>();

            String animalString = reader.readLine();
            while (animalString != null && !animalString.isEmpty()) {
                animalsData.add(buildAnimalFromString(animalString));
                animalString = reader.readLine();
            }

            return animalsData;
        } catch (NumberFormatException exception) {
            throw new WrongDataException("Wrong number format in file");
        } catch (IOException exception) {
            throw new WrongDataException("Some problems with " + ecosystemName + " file");
        }
    }

    private List<PlantData> loadPlants(String ecosystemName) throws WrongDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ECOSYSTEM_DATA_PATH + PLANTS_FILE_PREFIX
                + ecosystemName + FILE_EXTENSION))) {
            List<PlantData> plantsData = new ArrayList<>();

            String plantString = reader.readLine();
            while (plantString != null && !plantString.isEmpty()) {
                plantsData.add(buildPlantFromString(plantString));
                plantString = reader.readLine();
            }

            return plantsData;
        } catch (NumberFormatException exception) {
            throw new WrongDataException("Wrong number format in file");
        } catch (IOException exception) {
            throw new WrongDataException("Some problems with " + ecosystemName + " file");
        }
    }

    private EcosystemData buildEcosystemParamsFromString(String ecosystemData) throws WrongDataException {
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
        ecosystem.setAnimals(new ArrayList<>());
        ecosystem.setPlants(new ArrayList<>());

        validateEcosystemParams(ecosystem);

        return ecosystem;
    }

    private AnimalData buildAnimalFromString(String animalData) throws WrongDataException {
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

        return animal;
    }

    private PlantData buildPlantFromString(String plantData) throws WrongDataException {
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

        return plant;
    }

    private void saveFullEcosystem(EcosystemData ecosystemData) throws WrongDataException {
        saveEcosystemParams(ecosystemData);
        saveAnimals(ecosystemData.getAnimals(), ecosystemData.getName());
        savePlants(ecosystemData.getPlants(), ecosystemData.getName());
    }

    private void saveEcosystemParams(EcosystemData ecosystemData) throws WrongDataException {
        validateEcosystemParams(ecosystemData);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ECOSYSTEM_DATA_PATH + ECOSYSTEM_FILE_PREFIX + ecosystemData.getName() + FILE_EXTENSION))) {
            String data = createEcosystemFileData(ecosystemData);
            writer.append(data);
        } catch (IOException e) {
            throw new WrongDataException("Can't create or work with ecosystem file for " + ecosystemData.getName());
        }
    }

    private void saveAnimals(List<AnimalData> animalsData, String ecosystemName) throws WrongDataException {
        for (AnimalData animal : animalsData)
            validateAnimal(animal);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ECOSYSTEM_DATA_PATH + ANIMALS_FILE_PREFIX + ecosystemName + FILE_EXTENSION))) {
            String data = createAnimalsFileData(animalsData);
            writer.append(data);
        } catch (IOException e) {
            throw new WrongDataException("Can't create or work with animals file for " + ecosystemName);
        }
    }

    private void savePlants(List<PlantData> plantsData, String ecosystemName) throws WrongDataException {
        for (PlantData plant : plantsData)
            validatePlant(plant);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ECOSYSTEM_DATA_PATH + PLANTS_FILE_PREFIX + ecosystemName + FILE_EXTENSION))) {
            String data = createPlantsFileData(plantsData);
            writer.append(data);
        } catch (IOException e) {
            throw new WrongDataException("Can't create or work with plants file for " + ecosystemName);
        }
    }

    private String createEcosystemFileData(EcosystemData ecosystem) {
        StringBuilder data = new StringBuilder();

        data.append(ecosystem.getName()).append(" ")
                .append(ecosystem.getHumidity()).append(" ")
                .append(ecosystem.getAmountOfWater()).append(" ")
                .append(ecosystem.getSunshine()).append(" ")
                .append(ecosystem.getTemperature());

        return data.toString();
    }

    private String createAnimalsFileData(List<AnimalData> animals) {
        StringBuilder data = new StringBuilder();

        for (AnimalData animal : animals)
            data.append(animal.getName()).append(" ")
                    .append(animal.getCount()).append(" ")
                    .append(animal.getDangerLevel().getDngLvlInt()).append(" ")
                    .append(animal.getMealType().getMealTypeInt()).append(" ")
                    .append(animal.getNeededFood()).append(" ")
                    .append(animal.getNormalTemperature()).append(" ")
                    .append(animal.getContainsFood()).append("\n");

        return data.toString();
    }

    private String createPlantsFileData(List<PlantData> plants) {
        StringBuilder data = new StringBuilder();

        for (PlantData plant : plants)
            data.append(plant.getName()).append(" ")
                    .append(plant.getCount()).append(" ")
                    .append(plant.getNeededHumidity()).append(" ")
                    .append(plant.getNeededWater()).append(" ")
                    .append(plant.getNeededSunshine()).append(" ")
                    .append(plant.getNormalTemperature()).append(" ")
                    .append(plant.getContainsFood()).append("\n");

        return data.toString();
    }

    private void validateFullEcosystem(EcosystemData ecosystem) throws WrongDataException {
        validateEcosystemParams(ecosystem);
        for (AnimalData animal : ecosystem.getAnimals())
            validateAnimal(animal);
        for (PlantData plant : ecosystem.getPlants())
            validatePlant(plant);
    }

    private void validateEcosystemParams(EcosystemData ecosystem) throws WrongDataException {
        if (ecosystem.getHumidity() < 0.00 || ecosystem.getHumidity() > 1.00 + FLOAT_FAULT
                || ecosystem.getAmountOfWater() < 0
                || ecosystem.getSunshine() < 0.00 || ecosystem.getSunshine() > 1.00 + FLOAT_FAULT) {
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
                || plant.getNeededHumidity() < 0.00 || plant.getNeededHumidity() > 1.00 + FLOAT_FAULT
                || plant.getNeededWater() < 0
                || plant.getNeededSunshine() < 0.00 || plant.getNeededSunshine() > 1.00 + FLOAT_FAULT
                || plant.getContainsFood() < 0.00) {
            throw new WrongDataException("Some parameters in plant " + plant.getName() + " out of bounds");
        }
    }
}
