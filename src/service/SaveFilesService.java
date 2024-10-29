package service;

import ecxeptions.WrongDataException;
import model.Ecosystem;

public interface SaveFilesService {
    Ecosystem loadEcosystem(String fileName) throws WrongDataException;
    void saveEcosystem(Ecosystem ecosystem);
}
