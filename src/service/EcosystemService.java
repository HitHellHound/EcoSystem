package service;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;

import java.util.List;

public interface EcosystemService {
    void createEcosystem(EcosystemData ecosystemData) throws WrongDataException;
    EcosystemData getEcosystem(String name) throws WrongDataException;
    EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException;
    List<String> getExistingEcosystems();
    void saveEcosystemStatement(EcosystemData ecosystemData) throws WrongDataException;
    void changeEcosystemParams(EcosystemData ecosystemData) throws WrongDataException;
    void closeEcosystem();

    List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException;
    void changeAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;
    void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;
    void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;

    List<PlantData> getPlants(String ecosystemName) throws WrongDataException;
    void changePlant(String ecosystemName, PlantData plantData) throws WrongDataException;
    void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException;
    void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException;

    String getEcosystemShortStatistic(String ecosystemName) throws WrongDataException;
    String getEcosystemFullStatistic(String ecosystemName) throws WrongDataException;
    String makeEcosystemChangeStatistic(EcosystemData ecosystemChanged, EcosystemData ecosystemOriginal);
    EcosystemData doTheEvolution(EcosystemData ecosystem);
}
