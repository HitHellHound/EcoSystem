package service;

import ecxeptions.WrongDataException;
import model.Ecosystem;

public interface EcosystemService {
    Ecosystem createEcosystem(String name, float wetLvl,
                                     float amountOfWater, float sunLvl, float temperature) throws WrongDataException;

    void createAndAddAnimal(Ecosystem ecosystem, String name, int count, int dangerLevel, int mealType,
                                   int neededFood, float neededWater, float normalTemperature,
                                   float deathCoefficient, float bornCoefficient)  throws WrongDataException;

    void createAndAddPlant(Ecosystem ecosystem, String name, int count, float neededWet, float neededWater,
                                  float neededSun, float normalTemperature,
                                  float deathCoefficient, float bornCoefficient)  throws WrongDataException;
}
