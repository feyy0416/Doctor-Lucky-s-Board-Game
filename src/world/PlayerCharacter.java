package world;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the players controller by users, with methods to move, pick up
 * an item in the room and displayer the information of a player.
 *
 */
public class PlayerCharacter implements Player {
  
  private String name;
  private int id;
  private int currentRoomId;
  private List<Item> itemsInBag;
  private boolean isHuman;
  private int bagCapacity;
  
  /**
   * Constructs a human player in terms of its name and id.
   * @param name name of the player
   * @param id id of the player
   * @param isHuman true if the player is controlled by human, false it's controlled 
   *        by computer.
   * @param bagCapacity represents how many items the player can hold
   * @param currentRoomId the id of the room where player is locating
   * @throws IllegalArgumentException if any argument is invalid
   */
  public PlayerCharacter(String name, int id, boolean isHuman, int bagCapacity, int currentRoomId) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    } 
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative.");
    }
    if (bagCapacity < 0) {
      throw new IllegalArgumentException("Bag capacity cannot be negative.");
    }
    if (currentRoomId < 0) {
      throw new IllegalArgumentException("Room id cannot be negative.");
    }
    this.name = name;
    this.id = id;
    this.isHuman = isHuman;
    this.bagCapacity = bagCapacity;
    this.currentRoomId = currentRoomId;
    itemsInBag = new ArrayList<>();
  }

  @Override
  public void move(int roomId) {
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id cannot be negative.");
    }
    currentRoomId = roomId;

  }
  
  @Override
  public int getBagCapacity() {
    return bagCapacity;
  }

  @Override
  public String getName() {
    final String res = this.name;
    return res;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public int getCurrentRoomId() {
    return currentRoomId;
  }

  //sort player's item list according to damage in descending order
  private void sortItemList(List<Item> items) {
    if (items == null) {
      throw new IllegalArgumentException("Item list cannot be null.");
    }
    items.sort((o1, o2) -> Integer.compare(o2.getPower(), o1.getPower()));
  }
  
  @Override
  public void pickItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (itemsInBag.size() == bagCapacity) {
      throw new IllegalArgumentException("The number of items held by "
          + "player have reached the maximum.");
    }
    itemsInBag.add(item);
    sortItemList(itemsInBag);

  }
  
  @Override
  public void removeItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (!itemsInBag.contains(item)) {
      throw new IllegalArgumentException("Play does not have the item.");
    }
    for (int i = 0; i < itemsInBag.size(); i++) {
      if (item.equals(itemsInBag.get(i))) {
        itemsInBag.remove(i);
        return;
      }
    }
    
  }

  @Override
  public List<Item> getItemList() {
    List<Item> temp = new ArrayList<>();
    for (int i = 0; i < itemsInBag.size(); i++) {
      temp.add(itemsInBag.get(i));
    }
    return temp;
  }
  
  @Override
  public boolean getIsHuman() {
    return isHuman;
  }
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof PlayerCharacter)) {
      return false;
    }
    PlayerCharacter that = (PlayerCharacter) o;
    return (this.name.equals(that.name) && this.id == that.id 
        && this.isHuman == that.isHuman && this.bagCapacity == that.bagCapacity 
        && this.currentRoomId == that.currentRoomId);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;   
    result = result * prime + name.hashCode();
    result = result * prime + id;
    result = result * prime + (isHuman ? 0 : 1);
    result = result * prime + bagCapacity;
    result = result * prime + currentRoomId;
    return result;
  }



}
