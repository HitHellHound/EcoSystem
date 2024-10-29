import ecxeptions.WrongDataException;
import model.Ecosystem;
import service.SaveFilesService;
import service.SaveFilesServiceImpl;
import types.DangerLevel;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws WrongDataException {
        SaveFilesService filesService = new SaveFilesServiceImpl();
        Ecosystem ecosystem = filesService.loadEcosystem("save_firstEcosyst_10.txt");
        System.out.println(ecosystem);
        filesService.saveEcosystem(ecosystem);
    }
}