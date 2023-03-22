package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.Item;
import world.Player;
import world.PlayerCharacter;
import world.Room;
import world.Space;
import world.Weapon;

/**
 * Class for testing the Space class.
 *
 */
public class SpaceTest {
  
  private Space bedroom;
  
  /**
   * This method is providing short hand way of creating instances of a new Space object.
   * This method will make the implementation more flexible.
   * 
   * @param name name of the room
   * @param id id of the room
   * @param ur upper left row
   * @param uc upper left column
   * @param dr down right row 
   * @param dc down right column
   * @return new Room object
   */
  protected Space rm(String name, int id, int ur, int uc, int dr, int dc) {
    return new Room(name, id, ur, uc, dr, dc);
  }
  
  @Before
  public void setUp() {
    bedroom = new Room("bedroom", 1, 5, 7, 10, 15);
  }

  @Test
  public void testGetRoomId() {
    assertEquals(1, bedroom.getId());
  }
  
  @Test
  public void testGetRoomName() {
    assertEquals("bedroom", bedroom.getName());
  }
  
  @Test
  public void testGetUpRow() {
    assertEquals(5, bedroom.getUpRow());
  }
  
  @Test
  public void testGetUpCol() {
    assertEquals(7, bedroom.getUpCol());
  }
  
  @Test
  public void testGetDownRow() {
    assertEquals(10, bedroom.getDownRow());
  }
  
  @Test
  public void testGetDownCol() {
    assertEquals(15, bedroom.getDownCol());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpRowLessThanZero() {
    rm("Living room", 1, -1, 0, 5, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpColLessThanZero() {
    rm("Living room", 1, 0, -1, 5, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomDownRowLessThanZero() {
    rm("Living room", 1, 1, 0, -5, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomDownColLessThanZero() {
    rm("Living room", 1, 1, 0, 5, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpRowGreaterThanDownRow() {
    rm("Living room", 10, 5, 0, 1, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpColGreaterThanDownCol() {
    rm("Living room", 10, 5, 5, 10, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpRowEqualsToDownRow() {
    rm("Living room", 10, 5, 0, 5, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomUpColEqualsToDownCol() {
    rm("Living room", 10, 5, 5, 10, 5);
  }
  
  @Test
  public void testEquals() {
    assertTrue(rm("bedroom", 1, 5, 7, 10, 15).equals(bedroom));
  }
  
  @Test
  public void testHashCode() {
    assertEquals(rm("bedroom", 1, 5, 7, 10, 15).hashCode(), bedroom.hashCode());
  }
  
  @Test
  public void testAddItem() {
    Item item = new Weapon("Knife", 4);
    bedroom.addItem(item);
    assertTrue(bedroom.getItems().contains(item));
  }
  
  @Test
  public void testRemoveItem() {
    Item item = new Weapon("Knife", 4);
    bedroom.addItem(item);
    assertTrue(bedroom.getItems().contains(item));
    bedroom.removeItem(item);
    assertFalse(bedroom.getItems().contains(item));
  }
  
  @Test
  public void testGetItemList() {
    Item item1 = new Weapon("Knife", 4);
    Item item2 = new Weapon("gun", 4);
    bedroom.addItem(item1);
    bedroom.addItem(item2);
    List<Item> expected = new ArrayList<>();
    expected.add(item1);
    expected.add(item2);
    assertEquals(expected, bedroom.getItems());
  }
  
  @Test
  public void testAddPlayer() {
    Player player = new PlayerCharacter("A", 0, false, 1, 0);
    bedroom.addPlayer(player);
    assertTrue(bedroom.getPlayers().contains(player));
  }
  
  @Test
  public void testRemovePlayer() {
    Player player = new PlayerCharacter("A", 0, false, 1, 0);
    bedroom.addPlayer(player);
    assertTrue(bedroom.getPlayers().contains(player));
    bedroom.removePlayer(player);
    assertFalse(bedroom.getPlayers().contains(player));
  }
  
  @Test
  public void testGetPlayerList() {
    Player player1 = new PlayerCharacter("A", 0, false, 1, 0);
    Player player2 = new PlayerCharacter("B", 1, true, 1, 0);
    bedroom.addPlayer(player1);
    bedroom.addPlayer(player2);
    List<Player> expected = new ArrayList<>();
    expected.add(player1);
    expected.add(player2);
    assertEquals(expected, bedroom.getPlayers());
  }
  
  @Test
  public void testIsNeighbor() {
    Space bathroom = new Room("Bathroom", 2, 0, 0, 5, 8);
    assertTrue(bedroom.getNeighbors().isEmpty());
    
    bedroom.isNeighbor(bathroom);
    assertTrue(bedroom.getNeighbors().contains(bathroom));
    
    Space playroom = new Room("playroom", 3, 5, 15, 7, 20);
    bedroom.isNeighbor(playroom);
    assertTrue(bedroom.getNeighbors().contains(playroom));
    assertEquals(2, bedroom.getNeighbors().size());
    
    Space livingroom = new Room("Livingroom", 3, 20, 20, 30, 30);
    bedroom.isNeighbor(livingroom);
    assertFalse(bedroom.getNeighbors().contains(livingroom));
  }
  
  @Test
  public void testGetNeighbors() {
    Space bathroom = new Room("Bathroom", 2, 0, 0, 5, 8);
    List<Space> expected = new ArrayList<>();
    expected.add(bathroom);
    bedroom.isNeighbor(bathroom);
    assertEquals(expected, bedroom.getNeighbors());
  }
  
  @Test
  public void testToString() {
    //room with no item and neighbor
    Space bathroom = new Room("Bathroom", 2, 0, 0, 5, 8);
    assertEquals("Room's name: Bathroom\n"
        + "Neighbors: \n"
        + "Items in room: There is no items in this room.\n"
        + "Players in room: There is no players in this room.\n", bathroom.toString());
    
    //room with one item
    Item item = new Weapon("knife", 1);
    bathroom.addItem(item);
    assertEquals("Room's name: Bathroom\n"
        + "Neighbors: \n"
        + "Items in room: 0: knife(damage:1)\n"
        + "Players in room: There is no players in this room.\n", bathroom.toString());
    
    //room with one neighbor
    bathroom.isNeighbor(bedroom);
    assertEquals("Room's name: Bathroom\n"
        + "Neighbors: 0: bedroom\n"
        + "Items in room: 0: knife(damage:1)\n"
        + "Players in room: There is no players in this room.\n", bathroom.toString());
  }
  
  
}
