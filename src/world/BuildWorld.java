package world;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * An interface for BuildWorld model, with methods corresponding to the commands in controller
 * and return components' information in the model.
 *
 */
public interface BuildWorld {
  
  /**
   * Return the entire list of rooms of the world.
   * 
   * @return the list of rooms of the world
   */
  List<Space> getRoomList();
  
  /**
   * Return the target character of the game.
   * 
   * @return the target character
   */
  Piece getTargetCharacter();
  
  /**
   * Move the player(whose id is playerId) to the target room (whose id is roomId).
   * 
   * @param playerId the id of the player who's going to move
   * @param roomId id of target room
   * @return String that records the move action
   */
  String move(int playerId, int roomId);
  
  /**
   * Make player (whose id is playerId) pick the item (whose id is itemId in the room's item list).
   * 
   * @param playerId the id of player
   * @param itemId the id of the item picked
   * @return String that records the pick item action
   * @throws IllegalArgumentException if player id is negative or exceed range
   * @throws IllegalArgumentException if room does not exist
   * @throws IllegalArgumentException if room is not a neighbor of current room.
   */
  String pickItem(int playerId, int itemId);
  
  /**
   * Add a player into the world, depending on the player's name, ID, type 
   * (whether human player or not), backpack size (which determines the number 
   * of items player can carry), and birth room ID.
   * 
   * @param name the name of the player
   * @param id the id of the player
   * @param isHuman whether player is human controlled or computer controlled
   * @param bagCapacity the number of items you can carry
   * @param roomId birth room ID
   * @throws IllegalArgumentException if the number held of player reach limit.
   * @throws IllegalArgumentException if there is no items in room
   */
  void addPlayer(String name, int id, boolean isHuman, int bagCapacity, int roomId);
  
  /**
   * Move the target to next room in the list.
   * 
   * @return String that records the move target action
   * @throws IllegalArgumentException if name is null or name is taken
   * @throws IllegalArgumentException if bag capacity is negative
   * @throws IllegalArgumentException if room id does not exist
   */
  String moveTarget();

  /**
   * Return the list of the players of the world, list order depends on the order 
   * in which the player joins the world.
   * 
   * @return the list of players
   */
  List<Player> getPlayerList();

  /**
   * Return the limit of the game turns.
   * 
   * @return max number of game turns
   */
  int getMaxTurn();

  /**
   * Generate the graphical representation of the world.
   */
  void generateMap();

  /**
   * Return the player of current game turn that are going to take an action.
   * 
   * @param turn current game turn
   * @return the player of current turn
   */
  Player getCurrentPlayer(int turn);

  /**
   * Return the room of a specific player.
   * 
   * @param player the object that shows the room for
   * @return the room of the player
   */
  Space getLocation(Player player);
  
  /**
   * Return the list of the items in a specific room.
   * 
   * @param id the id of the room that list belongs to
   * @return the item list of the room
   * @throws IllegalArgumentException if player is null
   */
  List<Item> getItemInRoom(int id);
  
  /**
   * Return the neighbor list of a specific room.
   * 
   * @param id the id of room that list belongs to
   * @return the neighbor list of the room
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  List<Space> getNeigList(int id);

  /**
   * Make a player(whose id is playerId) look around, and return the information obtained 
   * from the action including where the player is and which rooms player can see from current room.
   * 
   * @param playerId the id of player who's going to take the action
   * @return String that records the look around action and information obtained
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  String lookAround(int playerId);
  
  /**
   * Display the information of a specific player(whose id is playerId) 
   * including player's name, position and items held.
   * 
   * @param playerId the id of player that information belongs to
   * @return the information of the player
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  String displayPlayerInfo(int playerId);
  
  /**
   * Display the information of the room where a specific player(whose id is playeId) is in
   * including room's name, neighbors, items in room and players in room.
   * 
   * @param playerId the id of the player
   * @return the information of the room
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  String displayRoomInfo(int playerId);
  
  /**
   * Generate a random number from 0 to max (not including max).
   * 
   * @param max the upper limit of the random number
   * @param random a random object used to generate a random number
   * @return a random not from 0 to max (not including max)
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  int randomNum(int max, Random random);
  
  /**
   * Make the player(whose id is playerId) to make an attempt to attack target with item,
   * the index of item is itemId in player's item list. The attack will be successful if 
   * the attack is not seen by others and target is in the same room with player.
   * 
   * @param playerId the id of player that make an attempt to attack target
   * @param itemId the id of item in player's item list
   * @return String that records the poke target action
   * @throws IllegalArgumentException if player id is negative or exceed range
   * @throws IllegalArgumentException if item id is negative or exceed range
   */
  String attackTarget(int playerId, int itemId);
  
  /**
   * Make the player(whose id is playerId) to make an attempt to poke target in eyes,
   * which will deal 1 damage to the target if the attack is not seen by others and 
   * target is in the same room with player.
   * 
   * @param playerId the id of player that make an attempt to poke target in eyes
   * @return String that records the poke target action
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  String pokeTarget(int playerId);
  
  /**
   * Move the pet to the room whose id is roomId.
   * 
   * @param roomId the target room to move the pet to
   * @return String records which room the pet is moved to
   * @throws IllegalArgumentException if room id is negative or exceed range 
   * @throws IllegalArgumentException if pet's current room id is same with target room id
   */
  String movePet(int roomId);
  
  /**
   * Return the remaining health of target character.
   * 
   * @return target's remaining health
   */
  int targetHealth();

  /**
   * Display the items player is carrying, player's id is playerId.
   * 
   * @param playerId the id of the player
   * @return String representing items that player is carrying
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  String displayItemInBag(int playerId);

  /**
   * Return the pet of the game.
   * 
   * @return the pet of the game
   */
  Pet getPet();

  /**
   * Find out if player can be seen by others.
   * 
   * @param player player to be seen
   * @return true if player can be seen, false nobody can see this player
   * @throws IllegalArgumentException if player is null
   */
  boolean canBeSeen(Player player);

  /**
   * Move pet following DfS of spaces in the worlds according to the order of spaces in 
   * world roomList. 
   * 
   * @param startRoomId id of the start room of the path
   * @param numMoves record how many moves are performed on the current path
   * @throws IllegalArgumentException if start room id less than 0 or exceed size
   * @throws IllegalArgumentException if numMoves less than 0
   */
  void movePetFollowDfs(int startRoomId, int numMoves);

  /**
   * Return true if target is killed or game reaches max turn.
   * 
   * @return true if target is killed or game reaches max turn, otherwise false
   */
  boolean isGameOver();

  /**
   * Generate a command for computer controlled player.
   * 
   * @return a string representing the command
   */
  String computerCommand(int playerId);

  /**
   * Return the map in form of bufferedImage.
   * 
   * @return the map in bufferedImage
   */
  BufferedImage getMap();

  /**
   * This method to find room id by x and y.
   * 
   * @param x x coordinate.
   * @param y y coordinate.
   * @return the room id of position (x, y) in the map.
   */
  int findRoomId(int x, int y);

  /**
   * Display all items in the room where player whose id is playerId locates.
   * 
   * @param playerId id of the player
   * @return a String with all items in the room
   * @throws IllegalArgumentException if playerId is invalid
   */
  String displayItemInRoom(int playerId);
  
  /**
   * Return current turn of the game.
   *  
   * @return a integer represents current turn number
   */
  int getTurn();
  
  /**
   * Moves to next turn by adding 1 to current turn number.
   * 
   */
  void nextTurn();
  
  /**
   * Display the game states of the turn, with information of player and the room.
   * 
   * @param playerId id of current turn player
   * @return a String with games states of the turn
   * @throws IllegalArgumentException if playerId is invalid
   */
  String displayGameStates(int playerId);
  
  /**
   * Set the number of turns of the game.
   * 
   * @param max
   */
  void setMaxTurn(int max);
  
  /**
   * Return the path of the file.
   * 
   * @return a String represents the path of the file
   */
  String getFilePath();
  
  /**
   * Return the room where player whose id is playerId locates.
   * 
   * @param playerId id of the player
   * @return a room where player whose id is playerId locates
   * @throws IllegalArgumentException if playerId is invalid
   */
  Space findRoomByPlayerId(int playerId);

}
