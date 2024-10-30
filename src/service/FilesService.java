package service;

import ecxeptions.FileServiceException;
import ecxeptions.WrongDataException;
import model.Ecosystem;

import java.util.List;

public interface FilesService {
    Ecosystem loadEcosystem(String fileName) throws WrongDataException;
    void saveEcosystem(Ecosystem ecosystem) throws FileServiceException;
    List<String> getSaveFiles();
    void loadBaseAnimalsAndPlants(Ecosystem ecosystem) throws WrongDataException;
}
