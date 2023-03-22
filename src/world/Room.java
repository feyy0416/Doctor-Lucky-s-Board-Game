package world;

import java.util.ArrayList;
import java.util.List;


/**
 * Room that allow player and target character to move around.
 */
public class Room implements Space {
  
  private final String name;
  private final int upCol;
  private final int downCol;
  private final int upRow;
  private final int downRow;
  private final int id;
  private List<Item> items;
  private List<Space> neighbors;
  private List<Player> players;
  
  /**
   * Constructs a Space in terms of its name and coordinates of the two vertices.
   * 
   * @param name the name of the Room
   * @param id  the id of the room
   * @param upRow the row of top left vertex
   * @param upCol the column of top left vertex
   * @param downRow the row of top right vertex
   * @param downCol the column of top right vertex
   * @throws IllegalArgumentException if any argument is invalid
   */
  public Room(String name, int id, int upRow, int upCol, int downRow, int downCol) {
    if (upRow < 0 || upCol < 0 || downCol < 0 || downRow < 0) {
      throw new IllegalArgumentException("Column and row cannot be negative.");
    }
     
    if (upCol >= downCol) {
      throw new IllegalArgumentException("Top vertex column cannot be greater than or equal"
          + " to bottom vertex column.");
    }
    if (upRow >= downRow) {
      throw new IllegalArgumentException("Top vertex column cannot be greater than or equal"
          + " to bottom vertex column.");
    }
    if (name == null) {
      throw new IllegalArgumentException("Room name cannot be null.");
    }
    this.name = name;
    this.id = id;
    this.upRow = upRow;
    this.upCol = upCol;
    this.downRow = downRow;
    this.downCol = downCol;
    items = new ArrayList<>();
    neighbors = new ArrayList<>();
    players = new ArrayList<>();
  }
  
  @Override
  public void isNeighbor(Space space) {
    if (space == null) {
      throw new IllegalArgumentException("Space cannot be null.");
    }
    int ur = space.getUpRow();
    int uc = space.getUpCol();
    int dr = space.getDownRow();
    int dc = space.getDownCol();
    if (upRow == ur || downRow == dr) {
      if (upCol == dc || downCol == uc) {
        neighbors.add((Room) space);
      }
    } else if (upRow == dr || downRow == ur) {
      if ((uc <= upCol && dc > upCol) || (uc < downCol && dc >= downCol)
          || (uc <= upCol && dc >= downCol) || (uc >= upCol && dc <= downCol)) {
        neighbors.add((Room) space);
      }
    } else if (upCol == uc || downCol == dc) {
      if (upRow == dr || downRow == ur) {
        neighbors.add((Room) space);
      }
    } else if (downCol == uc || upCol == dc) {
      if ((ur <= upRow && dr > upRow) || (ur < downRow && dr >= downRow)
          || (ur <= upRow && dr >= downRow) || (ur >= upRow && dr <= downRow)) {
        neighbors.add((Room) space);
      }
    }
  }
  
  @Override
  public String getName() {
    final String res = this.name;
    return res;
  }

  @Override
  public void addItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    items.add(item);
  }
  
  @Override
  public void removeItem(Item item) {
    if (items == null) {
      throw new IllegalArgumentException("List is empty.");
    }
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).equals(item)) {
        items.remove(i);
      }
    }
  }
  
  @Override
  public List<Item> getItems() {
    if (items == null) {
      throw new IllegalArgumentException("List is empty.");
    }
    List<Item> temp = new ArrayList<>();
    for (int i = 0; i < items.size(); i++) {
      temp.add(items.get(i));
    }
    return temp;
  }
  
  @Override
  public List<Player> getPlayers() {
    if (players == null) {
      throw new IllegalArgumentException("List is empty.");
    }
    List<Player> temp = new ArrayList<>();
    for (int i = 0; i < players.size(); i++) {
      temp.add(players.get(i));
    }
    return temp;
  }
  
  @Override
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null."); 
    }
    players.add(player); 
  }

  @Override
  public void removePlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null."); 
    }
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).equals(player)) {
        players.remove(i);
      }
    }
  }

  @Override
  public int getUpRow() {
    return upRow;
  }

  @Override
  public int getUpCol() {
    return upCol;
  }

  @Override
  public int getDownRow() {
    return downRow;
  }

  @Override
  public int getDownCol() {
    return downCol;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public List<Space> getNeighbors() {
    List<Space> temp = new ArrayList<>();
    for (int i = 0; i < neighbors.size(); i++) {
      temp.add(neighbors.get(i));
    }
    return temp;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < neighbors.size(); i++) {
      sb.append(Integer.toString(i));
      sb.append(": ");
      sb.append(neighbors.get(i).getName());
      if (i != neighbors.size() - 1) {
        sb.append(", ");
      }
    }
    StringBuilder sb2 = new StringBuilder();
    for (int j = 0; j < items.size(); j++) {
      sb2.append(j).append(": ");
      sb2.append(items.get(j).getName());
      sb2.append("(damage:");
      sb2.append(items.get(j).getPower());
      sb2.append(")");
      if (j != items.size() - 1) {
        sb2.append(", ");
      }
    }
    StringBuilder str = new StringBuilder();
    str.append("Room's name: ");
    str.append(name);
    str.append("\n");
    str.append("Neighbors: ");
    str.append(sb.toString());
    str.append("\n");
    str.append("Items in room: ");
    if (sb2.length() == 0) {
      str.append("There is no items in this room.");
    } else {
      str.append(sb2.toString());
    }
    str.append("\n");
    str.append("Players in room: ");
    if (players.size() == 0) {
      str.append("There is no players in this room.");
    } else {
      for (int i = 0; i < players.size(); i++) {
        str.append(players.get(i).getName());
        if (i != players.size() - 1) {
          str.append(" ");
        }
      }
    }
    str.append("\n");
    return str.toString();
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Room)) {
      return false;
    }
    Room that = (Room) o;
    return (this.name.equals(that.name) && this.id == that.id && this.upCol
        == that.upCol && this.upRow == that.upRow && this.downCol == that.downCol
        && this.downRow == that.downRow);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;   
    result = result * prime + name.hashCode();
    result = result * prime + id;
    result = result * prime + upCol;
    result = result * prime + upRow;
    result = result * prime + downCol;
    result = result * prime + downRow;
    return result;
  }

}
