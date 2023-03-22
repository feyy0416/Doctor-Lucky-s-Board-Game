# Doctor-Lucky-s-Board-Game

### About/Overview

Develop a Doctor Lucky likely board game with using MVC design pattern.



### List of Features

- Create a graphical representation of the world map in the form of a BufferedImage.<br>
- Display information about a specified space in the world.<br>
- Add a human-controlled player to the game.<br>
- Add a computer-controlled player to the game.<br>
- Move a player to a neighbor room.<br>
- Allow a player to pick up an item.<br>
- Allow a player to look around to get informations of current and neighboring rooms.<br>
- Display a description of a specific player.<br>
- Limit the maximum number of turns allowed (see information on the Driver class below for how to specify this number).<br>
- Move the target character around the world according to the list of spaces. Target starts in space 0 and will move to space 1 next round even they are not next to each other.<br>
- Allow players to make an attempt on target's life.<br>
- Allow players to move pet to another room in the world.<br>
- Game ends when target is killed or max turn reached, if target is killed, winner's name will be displayed.<br>



### How to Run

To run the jar file, passing an argument with the path of mansion.txt file in res/ dirctory and followed by total number of turns of the game. To be specific, on cmd prompt, cd to the res/ folder, then input command "java -jar milestone4-the-view.jar mansion.txt 200"(200 is the max turn, it can be any positive integer) to run the jar file.
-example: "java -jar milestone4-the-view.jar mansion.txt 200"



### How to Use the Program

When game starts, user will be brought to Welcome page, after clicking on the screen, user will go to addPlayer page. User can add players (human or computer controlled) by entering player's information. After adding all players, user will be brought to game page by clicking on finish button. In game page, user can control their character to perform various actions. To be specific, player can move to a neighboring room by selecting the room and pressed 'm' key, also player can pick an item in room by pressing 'i' button. A list of item will be displayed for player to choose their desired item. Moreover, player can attack the target by pressing 'a' buttom. Items carrying will be displayed for player, play can choose to use which item to make an attempt on target's life. Notice that the attack will only be successful when 1: player is in the same room with target, 2: there are no other players can see current player. To collect information around, player can get informations of current and neighboring rooms by pressing 'l' key to look around. Notice that target's pet can block player's sight towards the room where pet locates. Players are free to move the pet strategically. At last, the game will end if 1: Target is killed, 2: game reaches max turn. Have fun! 

The menu bar on the top of the panel provide following three features to users:<br>
1, start a new world by with a new world specification<br>
2, start a new game with the current world specification<br>
3, quit the game


### Design/Model Changes

- Move the method that generates command for computer controlled player from controller to model.<br>
- Add method to locate the room with x coordinate and y coordinate.<br>
- Add a readOnlyModel for view to get read only datas.<br>



### Assumptions

- Player's bag capacity (maxium items can carry) cannot be greater than 10<br>
- Number of items in a room cannot be greater than 10<br>
- Picking an item in a room that has no item is counted as a turn<br>


### Limitations

If there are too many players in a room, the player's graphics display may overflow out of the room.


### Citations

We don't use any resource online.

