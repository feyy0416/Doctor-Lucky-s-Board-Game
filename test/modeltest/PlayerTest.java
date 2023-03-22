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
import world.Weapon;



/**
 * Class for testing the Player class.
 *
 */
public class PlayerTest {

  private Player player;
  
  @Before
  public void setUp() {
    player = new PlayerCharacter("Amy", 0, true, 3, 0);
  }
  
  /**
   * This method is providing short hand way of creating instances of a new Player object.
   * This method will make the implementation more flexible.
   * 
   * @param name name of the player
   * @param id player's id
   * @param isHuman player is human controlled or not
   * @param items how many items player can carry
   * @param roomId birth room id
   * @return new PlayerCharacter object
   */
  protected Player pl(String name, int id, boolean isHuman, int items, int roomId) {
    return new PlayerCharacter(name, id, isHuman, items, roomId);
  }
  

  @Test
  public void testMove() { 
    player.move(1);
    assertEquals(1, player.getCurrentRoomId());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeRoomId() {
    player.move(-1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerWithNullName() {
    pl(null, 0, true, 3, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerWithNegId() {
    pl("T", -1, true, 3, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerWithNegBagSize() {
    pl("T", 0, true, -3, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerWithNegRoomId() {
    pl("T", 0, true, 3, -10);
  }
  
  @Test
  public void testGetName() {
    String expected = "Amy";
    assertEquals(expected, player.getName());
  }
  
  @Test
  public void testGetId() {
    assertEquals(0, player.getId());
  }
  
  @Test
  public void testGetIsHuman() {
    assertEquals(true, player.getIsHuman());
  }
  
  @Test
  public void testGetBagCapacity() {
    assertEquals(3, player.getBagCapacity());
  }
  
  @Test
  public void testGetRoomId() {
    assertEquals(0, player.getCurrentRoomId());
  }
  
  @Test
  public void testPickItem() {
    Item item = new Weapon("knife", 4);
    player.pickItem(item);
    assertTrue(player.getItemList().contains(item));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickItemWhenBagIsFull() {
    player.pickItem(new Weapon("knife", 4)); 
    player.pickItem(new Weapon("gun", 2)); 
    player.pickItem(new Weapon("spoon", 1)); 
    
    //pick item when bag is full
    player.pickItem(new Weapon("torch", 4)); 
  }

  @Test
  public void testGetItemList() {
    player.pickItem(new Weapon("knife", 4)); 
    player.pickItem(new Weapon("gun", 2)); 
    player.pickItem(new Weapon("spoon", 1)); 
    
    List<Item> expected = new ArrayList<>();
    expected.add(new Weapon("knife", 4));
    expected.add(new Weapon("gun", 2));
    expected.add(new Weapon("spoon", 1));
    
    assertEquals(expected, player.getItemList());
    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    pl(null, 1, false, 5, 5);
  }
  
  @Test
  public void testEquals() {
    Player temp = pl("Amy", 0, true, 3, 0);
    assertTrue(player.equals(temp));
    temp = pl("Amy", 0, false, 3, 0);
    assertFalse(player.equals(temp));
    temp = pl("Bob", 0, true, 3, 0);
    assertFalse(player.equals(temp));
    temp = pl("Amy", 1, true, 3, 0);
    assertFalse(player.equals(temp));
    temp = pl("Amy", 0, true, 2, 0);
    assertFalse(player.equals(temp));
    temp = pl("Amy", 0, true, 3, 1);
    assertFalse(player.equals(temp));
  }
  
  @Test
  public void testHashCode() {
    Player temp = pl("Amy", 0, true, 3, 0);
    assertEquals(temp.hashCode(), player.hashCode());
  }
  
  @Test
  public void testSortedItemList() {
    Player temp = pl("Amy", 0, true, 3, 0);
    Item pen = new Weapon("pen", 1);
    Item knife = new Weapon("knife", 4);
    Item fork = new Weapon("fork", 3);
    temp.pickItem(pen);
    temp.pickItem(knife);
    temp.pickItem(fork);
    List<Item> list = new ArrayList<>();
    list.add(knife);
    list.add(fork);
    list.add(pen);
    assertEquals(list, temp.getItemList());
    
  }
  
  @Test
  public void testFirstItemAlwaysDealMostDamage() {
    Player temp = pl("Bob", 0, false, 3, 0);
    Item pen = new Weapon("pen", 1);
    Item knife = new Weapon("knife", 5);
    Item fork = new Weapon("fork", 2);
    temp.pickItem(pen);
    temp.pickItem(knife);
    temp.pickItem(fork);
    List<Item> list = new ArrayList<>();
    list.add(knife);
    list.add(fork);
    list.add(pen);
    assertEquals(5, temp.getItemList().get(0).getPower());
    
  }
  
}
