# EcoSystem

A console application for managing a virtual ecosystem in which users can add different species of plants and animals. The application models interactions between species, takes into food chains and various resources. All data is stored in .txt files.

## Install

Install java(11+ version) to your PC and download repository. 

## Usage
To open application run run.bat on Windows and run.sh on Linux.

After launch, the first menu that will appear is the ecosystem selection menu. It allows you to load an existing ecosystem or create a new one. Existing ecosystems are stored in ecosystemsData folder. Example of ecosystem selection menu:
```
1. Create new Ecosystem
2. NewEcosystem
3. OldEcosystem
0. Close application
```
If you choose one of existing ecosystem or create new one then appears Ecosystem menu. Example:
```
Ecosystem: NewEcosystem. Chose option: 
1. Get short statistic
2. Get full statistic
3. Change ecosystem parameters
4. Change entities
5. Add new entity
6. Make Evolution step
7. Make prediction
8. Start auto evolution
0. Go back
```
There you can operate with ecosystem:
1. Get short statistic -- gives short variant of ecosystem state information.
2. Get full statistic -- gives full information about ecosystem int tables.
3. Change ecosystem parameters -- opens menu where you can change ecosystem parameters(such as humidity, sunshine ect.).
4. Change entities -- there you can choose what entity(animal or plant) you want to change.
5. Add new entity -- in that opened menu you add new animal or plant to ecosystem.
6. Make Evolution step -- does calculations based on the state of the ecosystem and shows changes in ecosystem.
7. Make prediction -- with this option you can get a prediction of the state of the ecosystem after several evolutions(max 5).
8. Start auto evolution -- starts process of auto evolution of ecosystem that you can only stop. It changes ecosystem parameters in small range and make evolution step. After that shows ecosystem full statistic. Logs of this process saves in logs folder.

## Maintainers
[@HitHellHound(Игнат Чурило)](https://github.com/HitHellHound)
