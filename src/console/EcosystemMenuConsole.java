package console;

import ecxeptions.FileServiceException;
import ecxeptions.WrongDataException;
import model.Ecosystem;
import model.Entity;
import service.EcosystemService;
import service.FilesService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static console.MenuOptions.*;

public class EcosystemMenuConsole {
    private final EcosystemService ecosystemService;
    private final FilesService filesService;
    private final Scanner console;

    public EcosystemMenuConsole(EcosystemService ecosystemService, FilesService filesService, Scanner console) {
        this.ecosystemService = ecosystemService;
        this.filesService = filesService;
        this.console = console;
    }

    public Ecosystem createEcosystemMenu() throws WrongDataException {
        System.out.println("Creating new Ecosystem!");
        System.out.print("Enter name: ");
        String name = console.next();
        System.out.print("Enter humidity (from 0,00 to 1,00): ");
        float humidity = requireNormalizedValue();
        System.out.print("Enter amount of water(non negative): ");
        float amountOfWater = requireNonNegativeFloat();
        System.out.print("Enter sunshine (from 0,00 to 1,00): ");
        float sunshine = requireNormalizedValue();
        System.out.print("Enter temperature: ");
        float temperature = requireFloat();

        Ecosystem ecosystem = ecosystemService.createEcosystem(name, humidity, amountOfWater, sunshine, temperature);

        System.out.println("Do you need to add base data? (y/n)");
        if (requireYesOrNo())
            filesService.loadBaseAnimalsAndPlants(ecosystem);

        System.out.println("Ecosystem created!");

        return ecosystem;
    }

    public void ecosystemMenu(Ecosystem ecosystem) {
        while (true) {
            System.out.println("Ecosystem: " + ecosystem.getName() + ". Chose option: ");
            System.out.println(SHORT_STATISTIC + ". Get short statistic");
            System.out.println(FULL_STATISTIC + ". Get full statistic");
            System.out.println(ECOSYSTEM_PARAMS + ". Change ecosystem parameters");
            System.out.println(CHANGE_ENTITIES + ". Change entities");
            System.out.println(ADD_NEW_ENTITY + ". Add new entity");
            System.out.println(SAVE + ". Save ecosystem");
            System.out.println(EXIT + ". Go back");

            switch (chooseOption(SAVE)) {
                case EXIT:
                    return;
                case SHORT_STATISTIC:
                    System.out.println(ecosystemService.createEcosystemShortStatistic(ecosystem));
                    pressEnterToContinue();
                    break;
                case FULL_STATISTIC:
                    System.out.println(ecosystemService.createEcosystemFullStatistic(ecosystem));
                    pressEnterToContinue();
                    break;
                case ECOSYSTEM_PARAMS:
                    changeEcosystemParamsMenu(ecosystem);
                    break;
                case CHANGE_ENTITIES:
                    changeEntities(ecosystem);
                    break;
                case ADD_NEW_ENTITY:
                    addNewEntity(ecosystem);
                    break;
                case SAVE:
                    try {
                        filesService.saveEcosystem(ecosystem);
                        System.out.println("Successfully saved!");
                    } catch (FileServiceException e) {
                        System.out.println("Something went wrong while saving. Try again later...\n");
                    }
            }
        }
    }

    private void changeEcosystemParamsMenu(Ecosystem ecosystem) {
        System.out.println("Ecosystem: " + ecosystem.getName() + ". Chose parameter to change: ");
        System.out.println(HUMIDITY + ". Humidity");
        System.out.println(AMOUNT_OF_WATER + ". Amount of Water");
        System.out.println(SUNSHINE + ". Sunshine");
        System.out.println(TEMPERATURE + ". Temperature");
        System.out.println(EXIT + ". Go back");

        switch (chooseOption(TEMPERATURE)) {
            case EXIT:
                return;
            case HUMIDITY:
                System.out.print("Enter new humidity (from 0,00 to 1,00): ");
                ecosystem.setHumidity(requireNormalizedValue());
                break;
            case AMOUNT_OF_WATER:
                System.out.print("Enter new amount of water(non negative): ");
                ecosystem.setAmountOfWater(requireNonNegativeFloat());
                break;
            case SUNSHINE:
                System.out.print("Enter new sunshine (from 0,00 to 1,00): ");
                ecosystem.setSunshine(requireNormalizedValue());
                break;
            case TEMPERATURE:
                System.out.print("Enter new temperature: ");
                ecosystem.setTemperature(requireFloat());
                break;
        }
        System.out.println("Parameter changed.\n");
    }

    private void changeEntities(Ecosystem ecosystem){
        System.out.println("What type of entities you want to change?");
        System.out.println(ANIMALS + ". Animals");
        System.out.println(PLANTS + ". Plants");
        System.out.println(EXIT + ". Cancel");

        List<? extends Entity> entities;

        switch (chooseOption(PLANTS)) {
            case ANIMALS:
                entities = ecosystem.getAnimals();
                System.out.println("Choose animal to change: ");
                break;
            case PLANTS:
                entities = ecosystem.getPlants();
                System.out.println("Choose plant to change: ");
                break;
            default:
                return;
        }

        for(int i = 0; i < entities.size(); i++) {
            System.out.println((i + 1) + ". " + entities.get(i).getName());
        }
        System.out.println(EXIT + ". Cancel");

        int chosenOption = chooseOption(entities.size() + 1);

        if (chosenOption != 0) {
            Entity entity = entities.get(chosenOption - 1);

            System.out.println("What you want ro do?");
            System.out.println(DELETE + ". Delete " + entity.getName() + " from ecosystem");
            System.out.println(CHANGE_COUNT + ". Change count for " + entity.getName());
            System.out.println(EXIT + ". Cancel");

            switch (chooseOption(CHANGE_COUNT)) {
                case DELETE:
                    System.out.println("Are you sure you want to delete " + entity.getName() + " from ecosystem? (y/n)");
                    if (requireYesOrNo()) {
                        entities.remove(entity);
                        System.out.println(entity.getName() + " was removed from ecosystem.");
                    }
                    break;
                case CHANGE_COUNT:
                    System.out.println(CHANGE_COUNT + ". Change count for " + entity.getName() + ":");
                    entity.setCount(requireNonNegativeInt());
                    System.out.println("Count for " + entity.getName() + "was changed!\n");
                    break;
            }
        }
    }

    private void addNewEntity(Ecosystem ecosystem) {
        System.out.println("What type of entities you want to add?");
        System.out.println(ANIMALS + ". Animals");
        System.out.println(PLANTS + ". Plants");
        System.out.println(EXIT + ". Cancel");

        switch (chooseOption(PLANTS)) {
            case ANIMALS:
                addAnimal(ecosystem);
                break;
            case PLANTS:
                addPlant(ecosystem);
                break;
        }
    }

    private void addAnimal(Ecosystem ecosystem) {
        System.out.println("Creating new animal!");
        System.out.print("Enter name: ");
        String name = console.next();
        System.out.print("Enter count (non negative): ");
        int count = requireNonNegativeInt();
        System.out.print("Enter danger level (from 1 to 5): ");
        int dangerLvl = requireBounderedInt(1, 5);
        System.out.print("Choose meal type (1.CARNIVOROUS, 2.OMNIVOROUS, 3. HERBIVOROUS)");
        int mealType = requireBounderedInt(1, 3);
        System.out.print("Enter needed food (non negative): ");
        int neededFood = requireNonNegativeInt();
        System.out.print("Enter needed water (non negative): ");
        float neededWater = requireNonNegativeFloat();
        System.out.print("Enter normal temperature: ");
        float temperature = requireFloat();
        System.out.print("Enter death coefficient (from 0,00 to 1,00): ");
        float deathCoefficient = requireNormalizedValue();
        System.out.print("Enter born coefficient (from 0,00 to 1,00): ");
        float bornCoefficient = requireNormalizedValue();

        try {
            ecosystemService.createAndAddAnimal(ecosystem, name, count, dangerLvl, mealType, neededFood, neededWater,
                    temperature, deathCoefficient, bornCoefficient);
            System.out.println("\nAnimal " + name + " was created!\n");
        } catch (WrongDataException e) {
            System.out.println("\nSomething went wrong while creating animal.\n");
        }
    }

    private void addPlant(Ecosystem ecosystem) {
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
        System.out.print("Enter death coefficient (from 0,00 to 1,00): ");
        float deathCoefficient = requireNormalizedValue();
        System.out.print("Enter born coefficient (from 0,00 to 1,00): ");
        float bornCoefficient = requireNormalizedValue();

        try {
            ecosystemService.createAndAddPlant(ecosystem, name, count, neededHumidity, neededWater, neededSunshine,
                    temperature, deathCoefficient, bornCoefficient);
            System.out.println("\nPlant " + name + " was created!\n");
        } catch (WrongDataException e) {
            System.out.println("\nSomething went wrong while creating plant.\n");
        }
    }

    private int chooseOption(int lastOption) {
        int optionNumber;
        while (true) {
            if (console.hasNextInt()) {
                optionNumber = console.nextInt();
                if (optionNumber > lastOption || optionNumber < 0) {
                    System.out.println("Incorrect option number!");
                    continue;
                }
            } else {
                System.out.println("Enter an option number!");
                console.next();
                continue;
            }
            break;
        }
        return optionNumber;
    }

    private void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
            String garbage = console.nextLine();
            if (garbage != null && !garbage.isEmpty())
                console.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int requireNonNegativeInt() {
        int i;
        while(true) {
            if(console.hasNextInt()) {
                i = console.nextInt();
                if (i >= 0)
                    return i;
                else
                    System.out.println("Enter positive integer!");
            } else {
                System.out.println("Enter correct integer!");
                console.next();
            }
        }
    }

    private int requireBounderedInt(int min, int max) {
        int i;
        while(true) {
            if(console.hasNextInt()) {
                i = console.nextInt();
                if (i >= min && i <= max)
                    return i;
                else
                    System.out.println("Enter number between" + min + " and " + max + "!");
            } else {
                System.out.println("Enter correct integer!");
                console.next();
            }
        }
    }


    private float requireFloat() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                return console.nextFloat();
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    private float requireNonNegativeFloat() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                f = console.nextFloat();
                if (f >= 0.00)
                    return f;
                else
                    System.out.println("Enter positive float!");
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    private float requireNormalizedValue() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                f = console.nextFloat();
                if (f >= 0.00 && f <= 1.00)
                    return f;
                else
                    System.out.println("Enter number between 0,00 and 1,00!");
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    private boolean requireYesOrNo() {
        while (true) {
            String answer = console.next();
            if (answer.toLowerCase().strip().equals("y"))
                return true;
            if (answer.toLowerCase().strip().equals("n"))
                return false;
            System.out.println("Enter 'y' for YES and 'n' for NO");
        }
    }
}
