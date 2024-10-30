package service;

import ecxeptions.WrongDataException;
import model.Ecosystem;

public interface EcosystemService {
    Ecosystem createEcosystem(String name, float humidity,
                                     float amountOfWater, float sunshine, float temperature) throws WrongDataException;

    void createAndAddAnimal(Ecosystem ecosystem, String name, int count, int dangerLevel, int mealType,
                                   int neededFood, float neededWater, float normalTemperature,
                                   float deathCoefficient, float bornCoefficient)  throws WrongDataException;

    void createAndAddPlant(Ecosystem ecosystem, String name, int count, float neededHumidity, float neededWater,
                                  float neededSunshine, float normalTemperature,
                                  float deathCoefficient, float bornCoefficient)  throws WrongDataException;

    String createEcosystemShortStatistic(Ecosystem ecosystem);

    String createEcosystemFullStatistic(Ecosystem ecosystem);

    String createEcosystemFullData(Ecosystem ecosystem);
}
