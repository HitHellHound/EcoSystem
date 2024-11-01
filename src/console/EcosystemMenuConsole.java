package console;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;
import service.EcosystemService;
import enums.DangerLevel;
import enums.MealType;

import java.util.List;
import java.util.Scanner;

import static console.MenuOptions.*;

public class EcosystemMenuConsole extends AbstractMenu {
    private final String ecosystemName;

    public EcosystemMenuConsole(EcosystemService ecosystemService, Scanner console, ProcessBuilder flushProcess,
                                String ecosystemName) {
        super(ecosystemService, console, flushProcess);
        this.ecosystemName = ecosystemName;
    }

    public void ecosystemMenu() {
        while (true) {
            flushConsole();
            System.out.println("Ecosystem: " + ecosystemName + ". Chose option: ");
            System.out.println(SHORT_STATISTIC + ". Get short statistic");
            System.out.println(FULL_STATISTIC + ". Get full statistic");
            System.out.println(ECOSYSTEM_PARAMS + ". Change ecosystem parameters");
            System.out.println(CHANGE_ENTITIES + ". Change entities");
            System.out.println(ADD_NEW_ENTITY + ". Add new entity");
            System.out.println(EVOLUTION_STEP + ". Make Evolution step");
            System.out.println(PREDICTION + ". Make prediction");
            System.out.println(AUTO_EVOLUTION + ". Start auto evolution");
            System.out.println(EXIT + ". Go back");

            try {
                switch (chooseOption(AUTO_EVOLUTION)) {
                    case EXIT:
                        ecosystemService.closeEcosystem();
                        return;
                    case SHORT_STATISTIC:
                        flushConsole();
                        System.out.println(ecosystemService.getEcosystemShortStatistic(ecosystemName));
                        pressEnterToContinue();
                        break;
                    case FULL_STATISTIC:
                        flushConsole();
                        System.out.println(ecosystemService.getEcosystemFullStatistic(ecosystemName));
                        pressEnterToContinue();
                        break;
                    case ECOSYSTEM_PARAMS:
                        flushConsole();
                        changeEcosystemParams();
                        pressEnterToContinue();
                        break;
                    case CHANGE_ENTITIES:
                        flushConsole();
                        changeEntities();
                        pressEnterToContinue();
                        break;
                    case ADD_NEW_ENTITY:
                        flushConsole();
                        addNewEntity();
                        pressEnterToContinue();
                        break;
                    case EVOLUTION_STEP:
                        flushConsole();
                        doTheEvolution();
                        pressEnterToContinue();
                        break;
                    case PREDICTION:
                        flushConsole();
                        makePrediction();
                        pressEnterToContinue();
                        break;
                    case AUTO_EVOLUTION:
                        autoEvolution();
                        pressEnterToContinue();
                        break;
                }
            } catch (WrongDataException exception) {
                System.out.println("Some problems with ecosystem persistence: " + exception.getMessage());
                System.out.println("Returning to main menu.");
                pressEnterToContinue();
                break;
            }
        }
    }

    private void changeEcosystemParams() throws WrongDataException {
        System.out.println("Ecosystem: " + ecosystemName + ". Chose parameter to change: ");
        System.out.println(HUMIDITY + ". Humidity");
        System.out.println(AMOUNT_OF_WATER + ". Amount of Water");
        System.out.println(SUNSHINE + ". Sunshine");
        System.out.println(TEMPERATURE + ". Temperature");
        System.out.println(EXIT + ". Go back");

        EcosystemData ecosystemData = ecosystemService.getEcosystemParams(ecosystemName);

        switch (chooseOption(TEMPERATURE)) {
            case EXIT:
                return;
            case HUMIDITY:
                System.out.print("Current humidity -- " + ecosystemData.getHumidity()
                        + ". Enter new humidity (from 0,00 to 1,00): ");
                ecosystemData.setHumidity(requireNormalizedValue());
                break;
            case AMOUNT_OF_WATER:
                System.out.print("Current amount of water -- " + ecosystemData.getAmountOfWater()
                        + ". Enter new amount of water(non negative): ");
                ecosystemData.setAmountOfWater(requireNonNegativeFloat());
                break;
            case SUNSHINE:
                System.out.print("Current sunshine -- " + ecosystemData.getSunshine()
                        + ". Enter new sunshine (from 0,00 to 1,00): ");
                ecosystemData.setSunshine(requireNormalizedValue());
                break;
            case TEMPERATURE:
                System.out.print("Current temperature -- " + ecosystemData.getTemperature()
                        + ". Enter new temperature: ");
                ecosystemData.setTemperature(requireFloat());
                break;
        }
        ecosystemService.changeEcosystemParams(ecosystemData);
        System.out.println("Parameter changed.");
    }

    private void changeEntities() {
        System.out.println("What type of entities you want to change?");
        System.out.println(ANIMALS + ". Animals");
        System.out.println(PLANTS + ". Plants");
        System.out.println(EXIT + ". Cancel");

        try {
            switch (chooseOption(PLANTS)) {
                case ANIMALS:
                    flushConsole();
                    changeAnimals();
                    break;
                case PLANTS:
                    flushConsole();
                    changePlants();
                    break;
                default:
                    return;
            }
        } catch (WrongDataException exception) {
            System.out.println("Something went wrong while changing entities: " + exception.getMessage());
        }
    }

    private void changeAnimals() throws WrongDataException {
        System.out.println("Choose animal to change: ");
        List<AnimalData> animals = ecosystemService.getAnimals(ecosystemName);
        for(int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i).getName());
        }
        System.out.println(EXIT + ". Cancel");

        int chosenOption = chooseOption(animals.size() + 1);
        if (chosenOption != EXIT) {
            AnimalData animal = animals.get(chosenOption - 1);

            flushConsole();
            System.out.println("What you want ro do?");
            System.out.println(DELETE + ". Delete " + animal.getName() + " from ecosystem");
            System.out.println(CHANGE_COUNT + ". Change count for " + animal.getName());
            System.out.println(EXIT + ". Cancel");

            switch (chooseOption(CHANGE_COUNT)) {
                case DELETE:
                    flushConsole();
                    System.out.println("Are you sure you want to delete " + animal.getName() + " from ecosystem? (y/n)");
                    if (requireYesOrNo()) {
                        ecosystemService.deleteAnimal(ecosystemName, animal);
                        System.out.println(animal.getName() + " was removed from ecosystem.");
                    }
                    break;
                case CHANGE_COUNT:
                    flushConsole();
                    System.out.println(CHANGE_COUNT + ". Change count for "
                            + animal.getName() + " (current count -- " + animal.getCount() + "):");
                    animal.setCount(requireNonNegativeInt());
                    ecosystemService.changeAnimal(ecosystemName, animal);
                    System.out.println("Count for " + animal.getName() + "was changed!\n");
                    break;
            }
        }
    }

    private void changePlants() throws WrongDataException {
        System.out.println("Choose plant to change: ");
        List<PlantData> plants = ecosystemService.getPlants(ecosystemName);
        for(int i = 0; i < plants.size(); i++) {
            System.out.println((i + 1) + ". " + plants.get(i).getName());
        }
        System.out.println(EXIT + ". Cancel");

        int chosenOption = chooseOption(plants.size() + 1);
        if (chosenOption != EXIT) {
            PlantData plant = plants.get(chosenOption - 1);

            flushConsole();
            System.out.println("What you want ro do?");
            System.out.println(DELETE + ". Delete " + plant.getName() + " from ecosystem");
            System.out.println(CHANGE_COUNT + ". Change count for " + plant.getName());
            System.out.println(EXIT + ". Cancel");

            switch (chooseOption(CHANGE_COUNT)) {
                case DELETE:
                    flushConsole();
                    System.out.println("Are you sure you want to delete " + plant.getName() + " from ecosystem? (y/n)");
                    if (requireYesOrNo()) {
                        ecosystemService.deletePlant(ecosystemName, plant);
                        System.out.println(plant.getName() + " was removed from ecosystem.");
                    }
                    break;
                case CHANGE_COUNT:
                    flushConsole();
                    System.out.println(CHANGE_COUNT + ". Change count for "
                            + plant.getName() + " (current count -- " + plant.getCount() + "):");
                    plant.setCount(requireNonNegativeInt());
                    ecosystemService.changePlant(ecosystemName, plant);
                    System.out.println("Count for " + plant.getName() + "was changed!\n");
                    break;
            }
        }
    }

    private void addNewEntity() {
        System.out.println("What type of entities you want to add?");
        System.out.println(ANIMALS + ". Animals");
        System.out.println(PLANTS + ". Plants");
        System.out.println(EXIT + ". Cancel");

        switch (chooseOption(PLANTS)) {
            case ANIMALS:
                flushConsole();
                addAnimal();
                break;
            case PLANTS:
                flushConsole();
                addPlant();
                break;
        }
    }

    private void addAnimal() {
        System.out.println("Creating new animal!");
        System.out.print("Enter name: ");
        String name = console.next();
        System.out.print("Enter count (non negative): ");
        int count = requireNonNegativeInt();
        System.out.print("Enter danger level (from 1 to 5): ");
        int dangerLvl = requireBounderedInt(1, 5);
        System.out.print("Choose meal type (1.CARNIVOROUS, 2.OMNIVOROUS, 3. HERBIVOROUS): ");
        int mealType = requireBounderedInt(1, 3);
        System.out.print("Enter needed food (non negative): ");
        float neededFood = requireNonNegativeFloat();
        System.out.print("Enter normal temperature: ");
        float temperature = requireFloat();
        System.out.print("Enter contained food (non negative): ");
        float containsFood = requireNonNegativeFloat();

        try {
            ecosystemService.addAnimal(ecosystemName, new AnimalData(name, count, DangerLevel.valueOfInt(dangerLvl),
                    MealType.valueOfInt(mealType), neededFood, temperature, containsFood));
            System.out.println("\nAnimal " + name + " was created!\n");
        } catch (WrongDataException e) {
            System.out.println("\nSomething went wrong while creating animal.\n");
        }
    }

    private void addPlant() {
        System.out.println("Creating new plant!");
        System.out.print("Enter name: ");
        String name = console.next();
        System.out.print("Enter count (non negative): ");
        int count = requireNonNegativeInt();
        System.out.print("Enter needed humidity (from 0,00 to 1,00): ");
        float neededHumidity = requireNormalizedValue();
        System.out.print("Enter needed water (non negative): ");
        float neededWater = requireNonNegativeFloat();
        System.out.print("Enter needed sunshine (from 0,00 to 1,00): ");
        float neededSunshine = requireNormalizedValue();
        System.out.print("Enter normal temperature: ");
        float temperature = requireFloat();
        System.out.print("Enter contained food (non negative): ");
        float containsFood = requireNonNegativeFloat();

        try {
            ecosystemService.addPlant(ecosystemName, new PlantData(name, count, neededHumidity, neededWater,
                    neededSunshine, temperature, containsFood));
            System.out.println("\nPlant " + name + " was created!\n");
        } catch (WrongDataException e) {
            System.out.println("\nSomething went wrong while creating plant.\n");
        }
    }

    private void doTheEvolution() throws WrongDataException {
        EcosystemData ecosystemOriginal = ecosystemService.getEcosystem(ecosystemName);
        EcosystemData ecosystemEvolved = ecosystemService.doTheEvolution(ecosystemOriginal);
        ecosystemService.saveEcosystemStatement(ecosystemEvolved);
        System.out.println("Ecosystem after evolution: ");
        System.out.println(ecosystemService.makeEcosystemChangeStatistic(ecosystemEvolved, ecosystemOriginal));
    }

    private void makePrediction() throws WrongDataException {
        System.out.println("How many steps of Evolution you want to predict? (Max -- 5)");
        int evolutionSteps = requireBounderedInt(0, 5);
        flushConsole();
        EcosystemData ecosystemOriginal = ecosystemService.getEcosystem(ecosystemName);
        EcosystemData ecosystemEvolved = ecosystemOriginal;
        for (int i = 0; i < evolutionSteps; i++) {
            ecosystemEvolved = ecosystemService.doTheEvolution(ecosystemEvolved);
        }
        System.out.println("Ecosystem evolution predict after " + evolutionSteps + " steps: ");
        System.out.println(ecosystemService.makeEcosystemChangeStatistic(ecosystemEvolved, ecosystemOriginal));
    }

    private void autoEvolution() {
        AutoEvolutionThread autoEvolutionThread = new AutoEvolutionThread();
        autoEvolutionThread.start();
        chooseOption(EXIT);
        autoEvolutionThread.interrupt();
        try {
            autoEvolutionThread.join(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Auto evolution don't stop after 10 seconds");
        }
    }

    private class AutoEvolutionThread extends Thread {
        @Override
        public void run() {
            int evolutionCounter = 1;

            while (!isInterrupted()) {
                flushConsole();
                System.out.println("Auto evolution step " + evolutionCounter);
                try {
                    EcosystemData ecosystemOriginal = ecosystemService.getEcosystem(ecosystemName);
                    EcosystemData ecosystemEvolved = ecosystemService.doTheEvolution(ecosystemOriginal);
                    ecosystemService.saveEcosystemStatement(ecosystemEvolved);
                    System.out.println(ecosystemService.getEcosystemFullStatistic(ecosystemName));
                } catch (WrongDataException exception) {
                    System.out.println("Auto evolution unexpectedly stopped: " + exception.getMessage());
                    break;
                }
                System.out.println(EXIT + ". Stop auto evolution");
                evolutionCounter++;
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("Auto evolution stopped after " + (evolutionCounter - 1)  + " evolutions");
        }
    }
}
