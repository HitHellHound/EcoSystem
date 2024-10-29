package console;

import ecxeptions.WrongDataException;
import model.Ecosystem;
import service.EcosystemService;
import service.FilesService;

import java.util.Scanner;

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
        System.out.println("Ecosystem: ");
        System.out.println(ecosystem.getName());
        System.out.println(ecosystem);
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
