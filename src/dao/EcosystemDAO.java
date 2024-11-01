package dao;

import data.AnimalData;
import data.EcosystemData;
import data.PlantData;
import ecxeption.WrongDataException;

import java.util.List;

public interface EcosystemDAO {
    void createEcosystem(EcosystemData ecosystemData) throws WrongDataException;
    EcosystemData getFullEcosystem(String ecosystemName) throws WrongDataException;
    EcosystemData getEcosystemParams(String ecosystemName) throws WrongDataException;
    List<String> getExistingEcosystems();
    void updateEcosystemParams(EcosystemData ecosystemData) throws WrongDataException;

    List<AnimalData> getAnimals(String ecosystemName) throws WrongDataException;
    void updateAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;
    void addAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;
    void deleteAnimal(String ecosystemName, AnimalData animalData) throws WrongDataException;

    List<PlantData> getPlants(String ecosystemName) throws WrongDataException;
    void updatePlant(String ecosystemName, PlantData plantData) throws WrongDataException;
    void addPlant(String ecosystemName, PlantData plantData) throws WrongDataException;
    void deletePlant(String ecosystemName, PlantData plantData) throws WrongDataException;
}
