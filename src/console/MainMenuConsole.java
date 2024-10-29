package console;

import ecxeptions.WrongDataException;
import model.Ecosystem;
import service.FilesService;
import service.FilesServiceImpl;

import java.util.List;
import java.util.Scanner;

public class MainMenuConsole {
    private final FilesService filesService;
    private final Scanner console;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        FilesService filesService = new FilesServiceImpl();
        MainMenuConsole mainMenuConsole = new MainMenuConsole(filesService, console);
        mainMenuConsole.mainMenu();
    }

    public MainMenuConsole(FilesService filesService, Scanner console) {
        this.filesService = filesService;
        this.console = console;
    }

    public void mainMenu() {
        List<String> saveFiles = filesService.getSaveFiles();

        System.out.println("Choose save or create new Ecosystem:");
        int optionCounter = 1;
        System.out.println(optionCounter + ". Create new Ecosystem");
        for (String saveFile : saveFiles){
            optionCounter++;
            System.out.println(optionCounter + ". " + saveFile.subSequence(FilesServiceImpl.SAVE_FILE_PREFIX.length(),
                    saveFile.length() - FilesServiceImpl.SAVE_FILE_EXTENSION.length()));
        }
        System.out.println("0. Close application");

        int optionNumber = 0;
        Ecosystem choosedEcosystem = null;
        while (true) {
            if (console.hasNextInt()){
                optionNumber = console.nextInt();
                if (optionNumber > saveFiles.size() + 1 || optionNumber < 0) {
                    System.out.println("Incorrect option number!");
                    continue;
                }
            } else {
                System.out.println("Enter an option number!");
                console.nextLine();
            }

            if (optionNumber <= 1)
                break;
            else {
                try {
                    choosedEcosystem = filesService.loadEcosystem(saveFiles.get(optionNumber - 2));
                    break;
                } catch (WrongDataException exception) {
                    System.out.println("Can't load this save: " + exception.getMessage() + "\n" +
                            "Try another one");
                }
            }
        }

        if (optionNumber == 0)
            return;

        EcosystemMenuConsole ecosystemMenu = new EcosystemMenuConsole(console);
        if (optionNumber == 1)
            choosedEcosystem = ecosystemMenu.createEcosystemMenu();
        if (choosedEcosystem != null)
            ecosystemMenu.ecosystemMenu(choosedEcosystem);
    }
}