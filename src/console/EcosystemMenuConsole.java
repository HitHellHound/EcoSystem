package console;

import ecxeptions.FileServiceException;
import ecxeptions.WrongDataException;
import model.Ecosystem;
import service.EcosystemService;
import service.FilesService;

import java.io.IOException;
import java.util.Scanner;

public class EcosystemMenuConsole {
    private final static int NUMBER_OF_OPTIONS_IN_ECOSYS_MENU = 6;

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
        System.out.print("Enter humidity (from 0.00 to 1.00): ");
        float humidity = requireNormalizedValue();
        System.out.print("Enter amount of water(non negative): ");
        float amountOfWater = requireNonNegativeFloat();
        System.out.print("Enter sunshine (from 0.00 to 1.00): ");
        float sunshine = requireNormalizedValue();
        System.out.print("Enter temperature: ");
        float temperature = requireFloat();

        Ecosystem ecosystem = ecosystemService.createEcosystem(name, humidity, amountOfWater, sunshine, temperature);

        System.out.println("Do you need to add base data? (y/n)");
        if (requireYesOrNo())
            filesService.loadBaseAnimalsAndPlants(ecosystem);

        return ecosystem;
    }

    public void ecosystemMenu(Ecosystem ecosystem) {
        while (true) {
            System.out.println("Ecosystem: " + ecosystem.getName() + ". Chose option: ");
            System.out.println("1. Get short statistic");
            System.out.println("2. Get full statistic");
            System.out.println("3. Change ecosystem parameters");
            System.out.println("4. Change animals");
            System.out.println("5. Change plants");
            System.out.println(NUMBER_OF_OPTIONS_IN_ECOSYS_MENU + ". Save ecosystem");
            System.out.println("0. Go back");

            int optionNumber;
            while (true) {
                if (console.hasNextInt()) {
                    optionNumber = console.nextInt();
                    if (optionNumber > NUMBER_OF_OPTIONS_IN_ECOSYS_MENU || optionNumber < 0) {
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

            switch (optionNumber) {
                case 0:
                    return;
                case 1:
                    System.out.println(ecosystemService.createEcosystemShortStatistic(ecosystem));
                    pressEnterToContinue();
                    break;
                case 2:
                    System.out.println(ecosystemService.createEcosystemFullStatistic(ecosystem));
                    pressEnterToContinue();
                    break;
                case 6:
                    try {
                        filesService.saveEcosystem(ecosystem);
                        System.out.println("Successfully saved!");
                    } catch (FileServiceException e) {
                        System.out.println("Something went wrong while saving. Try again later...");
                    }
            }
        }
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
                    System.out.println("Enter number between 0.00 and 1.00!");
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
