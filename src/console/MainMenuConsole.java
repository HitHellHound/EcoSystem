package console;

import ecxeptions.WrongDataException;
import model.Ecosystem;
import service.EcosystemService;
import service.EcosystemServiceImpl;
import service.FilesService;
import service.FilesServiceImpl;

import java.util.List;
import java.util.Scanner;

public class MainMenuConsole {
    private final EcosystemService ecosystemService;
    private final FilesService filesService;
    private final Scanner console;

    public static void main(String[] args) {
        MainMenuConsole mainMenuConsole = new MainMenuConsole(new EcosystemServiceImpl(),
                new FilesServiceImpl(),
                new Scanner(System.in));
        mainMenuConsole.mainMenu();
    }

    public MainMenuConsole(EcosystemService ecosystemService, FilesService filesService, Scanner console) {
        this.ecosystemService = ecosystemService;
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
                console.next();
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

        EcosystemMenuConsole ecosystemMenu = new EcosystemMenuConsole(ecosystemService, filesService, console);
        if (optionNumber == 1) {
            try {
                choosedEcosystem = ecosystemMenu.createEcosystemMenu();
            } catch (WrongDataException exception) {
                System.out.println("Can't create ecosystem: " + exception.getMessage());
            }
        }

        if (choosedEcosystem != null)
            ecosystemMenu.ecosystemMenu(choosedEcosystem);
    }
}