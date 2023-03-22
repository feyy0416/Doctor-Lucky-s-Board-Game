package world;

import java.util.List;

/**
 * An interface for room that represents spaces in the world, with methods 
 * to return the space informations and interact with Item objects.
 */
public interface Space {

  /**
   * Return the name of the Space.
   * 
   * @return name of the Space
   */
  String getName();
  
  /**
   * Return the list of items in this Space.
   * 
   * @return list of items in this Space
   */
  List<Item> getItems();
  
  /**
   * Return the row of upper left corner of the Space.
   * 
   * @return row of upper left corner
   */
  int getUpRow();
  
  /**
   * Return the column of upper left corner of the Space.
   * 
   * @return column of upper left corner
   */
  int getUpCol();
  
  /**
   * Return the row of lower left corner of the Space.
   * 
   * @return row of lower left corner
   */
  int getDownRow();
  
  /**
   * Return the column of lower left corner of the Space.
   * 
   * @return column of lower left corner
   */
  int getDownCol();
  
  /**
   * return the unique id of the room, corresponding to the position of
   *  this room in the entire room list.
   * 
   * @return id of the room
   */
  int getId();
  
  /**
   * Return the list of neighbors of this Space.
   * 
   * @return list of neighbors
   */
  List<Space> getNeighbors();
  
  /**
   * Test whether two Space are neighbor, if yes, add the space into current Space neighbor list,
   * if no, do nothing.
   * 
   * @param space The object Space that will be tested to 
   *        see if it is a neighbor of the present Space
   */
  void isNeighbor(Space space);

  /**
   * Add an item to the end of Space's item list.
   * 
   * @param item Item that added into the list
   */
  void addItem(Item item);

  /**
   * Remove a specific item from the item list of the room.
   * 
   * @param item Item that removed from the list
   */
  void removeItem(Item item);

  /**
   * Return the list of players in the Space.
   * 
   * @return list of players in the Space
   */
  List<Player> getPlayers();
  
  /**
   * Add a player to the end of player list of the Space.
   * 
   * @param player player that added into the list
   */
  void addPlayer(Player player);
  
  /**
   * Removed a specific player from the player list of the Space.
   * 
   * @param player player that removed from the list
   */
  void removePlayer(Player player);
   

}
