package console;

import dao.FilesEcosystemDAOImpl;
import data.EcosystemData;
import ecxeption.WrongDataException;
import service.EcosystemService;
import service.EcosystemServiceImpl;
import dao.SaveFileEcosystemDAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenuConsole extends AbstractMenu{
    public static void main(String[] args) {
        ProcessBuilder flushProcess;
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows"))
            flushProcess = new ProcessBuilder("cmd", "/c", "cls").inheritIO();
        else if (os.startsWith("Linux"))
            flushProcess = new ProcessBuilder("clear").inheritIO();
        else
            flushProcess = null;

        MainMenuConsole mainMenuConsole = new MainMenuConsole(new EcosystemServiceImpl(new FilesEcosystemDAOImpl()),
                new Scanner(System.in), flushProcess);

        mainMenuConsole.mainMenu();
    }

    public MainMenuConsole(EcosystemService ecosystemService, Scanner console, ProcessBuilder flushProcess) {
        super(ecosystemService, console, flushProcess);
    }

    public void mainMenu() {
        while (true) {
            List<String> existingEcosystems = ecosystemService.getExistingEcosystems();
            flushConsole();
            System.out.println("Create new Ecosystem or choose existing one:");
            System.out.println("1. Create new Ecosystem");
            for (int i = 0; i < existingEcosystems.size(); i++)
                System.out.println((i + 2) + ". " + existingEcosystems.get(i));
            System.out.println("0. Close application");

            int optionNumber = chooseOption(existingEcosystems.size() + 2);
            EcosystemData choosedEcosystem = null;

            if (optionNumber == 0)
                return;
            else if (optionNumber == 1) {
                try {
                    choosedEcosystem = createEcosystemMenu();
                } catch (WrongDataException exception) {
                    System.out.println("Can't create ecosystem: " + exception.getMessage());
                }
                pressEnterToContinue();
            } else {
                try {
                    choosedEcosystem = ecosystemService.getEcosystem(existingEcosystems.get(optionNumber - 2));
                } catch (WrongDataException exception) {
                    System.out.println("Can't load Ecosystem: " + exception.getMessage() + "\n" +
                            "Try another one");
                    pressEnterToContinue();
                }
            }

            if (choosedEcosystem != null) {
                EcosystemMenuConsole ecosystemMenu = new EcosystemMenuConsole(ecosystemService, console, flushProcess,
                        choosedEcosystem.getName());
                ecosystemMenu.ecosystemMenu();
            }
        }
    }

    private EcosystemData createEcosystemMenu() throws WrongDataException {
        flushConsole();
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

        EcosystemData ecosystemData = new EcosystemData(name, humidity, amountOfWater, sunshine, temperature);
        ecosystemData.setAnimals(new ArrayList<>());
        ecosystemData.setPlants(new ArrayList<>());

        ecosystemService.createEcosystem(ecosystemData);

        System.out.println("Ecosystem created!");

        return ecosystemData;
    }
}