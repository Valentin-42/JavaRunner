## Java Runner project

This video game is my first coing project using Java.
The player has to stay alive by avoiding obsctacles. The longer he survives the better score he has.
Implemented voice analysis to control player's action. Do click on rules & commands at the start menu. 

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Files structures 

- App.java : the main file -> Run it to play !
- GameXXX  : are game-scene interfaces.
- AudioXXX : refers to all audio management class.
- FFT.java and Complex.java are two classes containing most of the mathematical methods.
- FFT.txt  : contains Fast-Fourier Transform result in a python table shape. 
- SaveFile.txt : contains players scores. This file is load by the Leaderboard scene to display ranks. 

## Going further 

1. Sort saved scores displayed on the leaderboard (quicksort should be ok).
2. Reduce voice analysis complexity (may be recording at any time would work).
3. Optimize code and complete comments.
