package service;

import ecxeptions.WrongDataException;
import model.Ecosystem;

import java.util.List;

public interface FilesService {
    Ecosystem loadEcosystem(String fileName) throws WrongDataException;
    void saveEcosystem(Ecosystem ecosystem);
    List<String> getSaveFiles();
}
