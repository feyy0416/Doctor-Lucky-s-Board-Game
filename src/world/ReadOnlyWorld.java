package world;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public interface ReadOnlyWorld {
  
  /**
   * Get read only player list in the model.
   * 
   * @return a read only player list
   */
  List<Player> getPlayerList();
  
  /**
   * Get read only room list in the model.
   * 
   * @return a read only room list
   */
  List<Space> getRoomList();
  
  /**
   * Get room id by room's x and y coordinate.
   * 
   * @param x x coordinate
   * @param y y coordinate
   * @return target room id
   * @throws IllegalArgumentException if x and y are negative.
   */
  int findRoomId(int x, int y);
  
  /**
   * Return true if player whose id is playerId has any item, otherwise false.
   * 
   * @param playerId id of the player
   * @return true if player has item, false if player has no item
   * @throws IllegalArgumentException if player id is invalid
   */
  Boolean hasItem(int playerId);
  
  /**
   * Get the map in bufferedImage format from model.
   * 
   * @return the bufferedImage map
   */
  BufferedImage getMap();
  
  /**
   * Generate a command for computer controlled player.
   * 
   * @param playerId id of the player
   * @return a String represents the command action
   * @throws IllegalArgumentException if player id is invalid
   */
  String computerAction(int playerId);
  
  /**
   * Generate a random number from 0 to max (not including max).
   * 
   * @param max the upper limit of the random number
   * @param random a random object used to generate a random number
   * @return a random not from 0 to max (not including max)
   * @throws IllegalArgumentException if player id is negative or exceed range
   */
  int randomNum(int max, Random rand);
  
  /**
   * Return a String with names of all rooms.
   * 
   * @return a String with all names of rooms.
   */
  String getAllRoomsName();
}
