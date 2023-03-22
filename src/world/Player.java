package world;

import java.util.List;

/**
 * An interface for HumanPlayer and Bot, interact with Item objects, with methods to 
 * make a player move, pick up an item and display the player's information.
 *
 */
public interface Player {
  
  /**
   * Move the Player from current room to target room whose id is equals to roomId.
   * 
   * @param roomId the id of target room
   */
  void move(int roomId);
  
  /**
   * Return the name of the Player. Players cannot share same names.
   * 
   * @return the name of the Player
   */
  String getName();
  
  /**
   * Return the id of the Player that corresponding to the position in entire player list.
   * 
   * @return the id of player
   */
  int getId();
  
  /**
   * Return the id of room where Player is in.
   * 
   * @return the id of the room where Player is.
   */
  int getCurrentRoomId();
  
  /**
   * Make Player pick up a specific item, and add the item into Player's item list.
   * 
   * @param item item that added into player's item list
   */
  void pickItem(Item item);
  
  /**
   * Return the list of items that Player hold.
   * 
   * @return list of items that Player hold
   */
  List<Item> getItemList();
  
  /**
   * Return a boolean that represents whether Player is human or a computer controller player.
   * True means is human, false means is bot.
   * 
   * @return boolean that represents whether Player is human or a computer controller player
   */
  boolean getIsHuman();

  /**
   * Return how many items Player can carry.
   * 
   * @return the number of item Player can hold
   */
  int getBagCapacity();

  /**
   * Remove the item from player's item list.
   * 
   * @param item the item to remove from player's item list
   * @throws IllegalArgumentException if item is null
   * @throws IllegalArgumentException if item is not in player's item list
   */
  void removeItem(Item item);

}
