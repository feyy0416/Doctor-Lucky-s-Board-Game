package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import world.BuildWorld;
import world.BuildWorldModel;
import world.Item;
import world.Pet;
import world.Piece;
import world.Player;
import world.PlayerCharacter;
import world.Room;
import world.Space;
import world.TargetCharacter;
import world.TargetPet;
import world.Weapon;

/**
 * Class for testing the BuildWorld class.
 *
 */
public class BuildWorldModelTest {
  
  private BuildWorld bwm;
  private BuildWorld testWorld;

  /**
   * set up a buildWorld object.
   */
  @Before
  public void setUp() {
    try {
      String[] arguments = new String[] {"res/mansion.txt", "15"};
      bwm = new BuildWorldModel(arguments);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Fail to read file.");
    }
  }
  
  @Test
  public void testGetTargetCharacter() {
    Piece expectedTarget = new TargetCharacter("Doctor Lucky", 20, 20);
    assertEquals(expectedTarget, bwm.getTargetCharacter());
  }
  
  @Test
  public void testGetRoomList() {
    List<Room> list = new ArrayList<>();
    list.add(new Room("Bathroom", 0, 0, 0, 4, 6));
    list.add(new Room("Bedroom", 1, 2, 6, 6, 12));
    list.add(new Room("Sun room", 2, 4, 12, 8, 18));
    list.add(new Room("Dinning room", 3, 8, 9, 14, 18));
    list.add(new Room("Kitchen", 4, 14, 13, 17, 18));
    list.add(new Room("Baby's room", 5, 6, 18, 10, 24));
    list.add(new Room("Doll room", 6, 8, 24, 12, 30));
    list.add(new Room("Living room", 7, 12, 18, 17, 26));
    list.add(new Room("Foyer", 8, 12, 26, 17, 30));
    list.add(new Room("Library", 9, 17, 9, 21, 18));
    list.add(new Room("Gym", 10, 4, 0, 12, 4));
    list.add(new Room("Tennis court", 11, 8, 4, 12, 9));
    list.add(new Room("Play room", 12, 12, 0, 16, 9));
    list.add(new Room("Movie theater", 13, 16, 0, 21, 9));
    list.add(new Room("Secret room", 14, 21, 0, 25, 4));
    list.add(new Room("Guest bathroom", 15, 25, 0, 29, 6));
    list.add(new Room("Guest bedroom", 16, 23, 6, 27, 12));
    list.add(new Room("Office", 17, 21, 12, 25, 18));
    list.add(new Room("Laundry room", 18, 19, 18, 23, 24));
    list.add(new Room("Cat room", 19, 17, 24, 21, 30));    
    assertEquals(list, bwm.getRoomList());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testOverlappedRoom() {

    String[] arg = new String[] {"res/overlappedRoom.txt", "10"};
    testWorld = new BuildWorldModel(arg);

  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomExceedWorld() {

    String[] arg = new String[] {"res/roomExceedWorld.txt", "10"};
    testWorld = new BuildWorldModel(arg);

  }
  
  @Test
  public void testGetMaxTurn() {
    assertEquals(15, bwm.getMaxTurn());
  }
  
  @Test
  public void testMoveTarget() {
    bwm.moveTarget();
    String expectedRoom = bwm.getRoomList().get(1).getName();
    String actualRoom = bwm.getRoomList().get(bwm.getTargetCharacter().getCurrentRoomId(
        )).getName();
    assertEquals(expectedRoom, actualRoom);
    String str = bwm.moveTarget();
    assertEquals("Target Doctor Lucky moves to Sun room\n", str);
    expectedRoom = bwm.getRoomList().get(2).getName();
    actualRoom = bwm.getRoomList().get(bwm.getTargetCharacter().getCurrentRoomId()).getName();
    assertEquals(expectedRoom, actualRoom);
  }
  
  @Test
  public void testAddPlayer() {
    bwm.addPlayer("a", 0, true, 5, 0);
    bwm.addPlayer("b", 1, false, 3, 0);
    assertEquals(new PlayerCharacter("a", 0, true, 5, 0), bwm.getPlayerList().get(0));
    assertEquals(new PlayerCharacter("b", 1, false, 3, 0), bwm.getPlayerList().get(1));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDuplicatePlayerName() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    bwm.addPlayer("Bob", 1, false, 3, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerRoomIdOutOfRange() {
    bwm.addPlayer("Bob", 0, true, 5, 35);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNegativePlayerRoomId() {
    bwm.addPlayer("Bob", 1, false, 3, 35);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNegativePlayerBagCapacity() {
    bwm.addPlayer("Bob", 1, false, -3, 5);
  }
  
  @Test
  public void testPickItem() {
    
    //test the item is added to player's bag
    //let player picks up the first item in the item list of the room 
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player a = bwm.getPlayerList().get(0);
    Item item = bwm.getItemInRoom(0).get(0);
    String str = bwm.pickItem(0, 0);
    
    assertEquals("Bob picks up Shower nozzle\n", str);
    assertEquals(a.getItemList().get(0), item);
    
    //test if the item is removed from room
    assertFalse(bwm.getRoomList().get(0).getItems().contains(item));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickItemWhenBagIsFull() {
    //player bag size is 1, make player pick two items 
    bwm.addPlayer("Bob", 0, true, 1, 0);
    //player picks up number 0 item in room's item list
    bwm.pickItem(0, 0);
    bwm.move(0, 1);
    //player tries to pick up number 0 item in room's item list
    bwm.pickItem(0, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickItemWhenNoItemsInRoom() {
    //player bag size is 1, make player pick two items 
    bwm.addPlayer("Bob", 0, true, 1, 0);
    //player picks the only item in space 0
    bwm.pickItem(0, 0);
    //no item in space and call pickItem again
    bwm.pickItem(0, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickItemThatDoesNotExistInRoom() {
    bwm.addPlayer("Bob", 0, true, 1, 0);
    //player is in space 0 that only contains 1 item.
    //try to pick item with id 5 in the item list of the room which does not exist.
    bwm.pickItem(0, 5);

  }
  
  @Test
  public void testMovePlayer() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player a = bwm.getPlayerList().get(0);
    //move player from room 0 to room 1, room 0 and room 1 are next to each other
    String str = bwm.move(0, 1);
    assertEquals(1, a.getCurrentRoomId());
    assertEquals("Bob moves to Bedroom\n", str);
    
    //test if player is added to new room
    assertTrue(bwm.getRoomList().get(1).getPlayers().contains(a));
    
    //test if player is removed from previous room
    assertFalse(bwm.getRoomList().get(0).getPlayers().contains(a));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    
    //try to move player to a room that is not a neighbor of current room
    bwm.move(0, 3);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNegativeMove() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    
    //try to move player to a room with negative id
    bwm.move(0, -3);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveExceedSize() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    
    //try to move player to a room that does not exist.
    bwm.move(0, 100);
  }
  
  
  @Test
  public void testGetNeigList() {
    List<Space> neigList = bwm.getNeigList(0);
    List<Space> expected =  new ArrayList<>();
    expected.add(new Room("Bedroom", 1, 2, 6, 6, 12));
    expected.add(new Room("Gym", 10, 4, 0, 12, 4));   
    assertEquals(expected, neigList);
       
  }
  
  @Test
  public void testGetItemInRoom() {
    List<Item> itemInRoom = bwm.getItemInRoom(0); 
    List<Item> expected =  new ArrayList<>();
    expected.add(new Weapon("Shower nozzle", 2));
    assertEquals(expected, itemInRoom);
  }
  
  @Test
  public void testGetPlayerLocation() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    //player generated at space 0 bathroom
    assertEquals(new Room("Bathroom", 0, 0, 0, 4, 6), bwm.getLocation(bob));
    
    bwm.move(0, 10);
    
  }
  
  @Test
  public void testGetThisTurnPlayer() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    bwm.addPlayer("Amy", 1, true, 3, 1);
    Player amy = bwm.getPlayerList().get(1);
    
    //check first turn
    assertEquals(bob, bwm.getCurrentPlayer(1));
    
    //check second turn
    assertEquals(amy, bwm.getCurrentPlayer(2));
    
    //check third turn, if control goes back to the first player
    assertEquals(bob, bwm.getCurrentPlayer(3));
  }
  
  @Test
  public void testLookAroundWithItems() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Bathroom\n"
        + "Items in Bathroom: 0: Shower nozzle(damage:2)\n"
        + "Players in Bathroom: Bob\n"
        + "Pikachu is in the Bathroom\n"
        + "Neighboring rooms: \n"
        + "0: Bedroom\n"
        + "  Items in Bedroom: Table lamp(damage:2)\n"
        + "  Players in Bedroom: No players in the room.\n"
        + "1: Gym\n"
        + "  Items in Gym: Basketball(damage:2)\n"
        + "  Players in Gym: No players in the room.\n"
        + "Target is in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithNoItems() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithItemsInNeighboringRoom() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithNoItemsInNeighboringRoom() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithPlayers() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    bwm.addPlayer("Amy", 1, true, 5, 6);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob Amy\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithoutOthers() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithPlayerInNeighboringRoom() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    bwm.addPlayer("Amy", 1, true, 5, 5);
    bwm.addPlayer("John", 2, true, 5, 5);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: Amy John\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithTarget() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Bathroom\n"
        + "Items in Bathroom: 0: Shower nozzle(damage:2)\n"
        + "Players in Bathroom: Bob\n"
        + "Pikachu is in the Bathroom\n"
        + "Neighboring rooms: \n"
        + "0: Bedroom\n"
        + "  Items in Bedroom: Table lamp(damage:2)\n"
        + "  Players in Bedroom: No players in the room.\n"
        + "1: Gym\n"
        + "  Items in Gym: Basketball(damage:2)\n"
        + "  Players in Gym: No players in the room.\n"
        + "Target is in the same room with player.\n", str);
  }
  
  @Test
  public void testLookAroundWithNoTarget() {
    bwm.addPlayer("Bob", 0, true, 5, 6);
    //target is in space 0
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Doll room\n"
        + "Items in Doll room: No items in room.\n"
        + "Players in Doll room: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Baby's room\n"
        + "  Items in Baby's room: Pram(damage:3)\n"
        + "  Players in Baby's room: No players in the room.\n"
        + "1: Living room\n"
        + "  Items in Living room: No items in the room.\n"
        + "  Players in Living room: No players in the room.\n"
        + "2: Foyer\n"
        + "  Items in Foyer: Umbrella(damage:2), High-heeled shoes(damage:2)\n"
        + "  Players in Foyer: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvlidLookAroundNegId() {
    bwm.lookAround(-1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvlidLookAroundNegIdExceedSize() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    bwm.lookAround(2);
  }
  
  @Test
  public void testGetTarget() {
    Piece expected = new TargetCharacter("Doctor Lucky", 20, 20);
    assertEquals(expected, bwm.getTargetCharacter());
  }
  
  @Test
  public void testGetPlayerList() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    bwm.addPlayer("Amy", 1, true, 3, 1);
    Player amy = bwm.getPlayerList().get(1);
    
    List<Player> expected = new ArrayList<>();
    expected.add(bob);
    expected.add(amy);
    assertEquals(expected, bwm.getPlayerList());
    
    
  }
  
  @Test
  public void testDisplayPlayerInfo() {
    //player with no item
    bwm.addPlayer("Bob", 0, true, 5, 15);
    Player player = bwm.getPlayerList().get(0);
    String str = bwm.displayPlayerInfo(0);
    assertEquals("Player name: Bob\n"
        + "Player's position: Guest bathroom\n"
        + "Items carried: This player has no item.\n", str);
    
    //player with one item
    player.pickItem(new Weapon("knfie", 5));
    str = bwm.displayPlayerInfo(0);
    assertEquals("Player name: Bob\n"
        + "Player's position: Guest bathroom\n"
        + "Items carried: 0: knfie(damage:5) \n", str);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDisplayPlayerInfo() {
    bwm.displayPlayerInfo(-1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDisplayPlayerInfoExceedSize() {
    bwm.addPlayer("Bob", 0, true, 5, 15);
    bwm.displayPlayerInfo(2);
  }
  
  @Test
  public void testDisplayRoomInfo() {
    bwm.addPlayer("Bob", 0, true, 5, 15);
    String str = bwm.displayRoomInfo(0);
    assertEquals("Room's name: Guest bathroom\n"
        + "Neighbors: 0: Secret room, 1: Guest bedroom\n"
        + "Items in room: There is no items in this room.\n"
        + "Players in room: Bob\n", str);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDisplayRoomInfoNegId() {
    bwm.displayRoomInfo(-1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDisplayRoomExceedSize() {
    bwm.addPlayer("Bob", 0, true, 5, 15);
    bwm.displayRoomInfo(2);
  }
  
  @Test
  public void testRandomNum() {
    Random random = new Random(10);
    assertEquals(3, bwm.randomNum(10, random));
    random = new Random(15);
    assertEquals(1, bwm.randomNum(10, random));
  }
  
  //Tests added for milestone 3
  @Test
  public void testLookAroundWithPetInNeighboringRoom() {
    //name, player id, is human or not, number of items can carry, born space id
    //pet born in space 0, which is a neighboring room of space 1
    bwm.addPlayer("Bob", 0, true, 5, 1);
    String str = bwm.lookAround(0);
    assertEquals("Bob is looking around.\n"
        + "Location: Bedroom\n"
        + "Items in Bedroom: 0: Table lamp(damage:2)\n"
        + "Players in Bedroom: Bob\n"
        + "Neighboring rooms: \n"
        + "0: Bathroom\n"
        //Pet Pikachu hides the information of space 1
        + " Pikachu is in the Bathroom\n"
        + " The pet blocks player's vision toBathroom\n"
        + "1: Sun room\n"
        + "  Items in Sun room: Flower pot(damage:2)\n"
        + "  Players in Sun room: No players in the room.\n"
        + "Target is not in the same room with player.\n", str);
  }
  
  @Test
  public void testGetTargetPosition() {
    int roomId = bwm.getTargetCharacter().getCurrentRoomId();
    //start at space 0
    assertEquals(0, roomId);
    bwm.moveTarget();
    //move to space 1 after calling moveTarget()
    roomId = bwm.getTargetCharacter().getCurrentRoomId();
    assertEquals(1, roomId);
  }
  
  @Test
  public void testGetPet() {
    Pet pet = new TargetPet("Pikachu");
    assertEquals(pet, bwm.getPet());
  }
  
  @Test
  public void testValidAttackWithItem() {
    //name, player id, is human or not, number of items can carry, born space id
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    Item knife = new Weapon("knife", 4);
    assertEquals(20, bwm.targetHealth());
    //make bob pick up item knife
    bob.pickItem(knife);
    //first 0 is player's id
    //second 0 is item id in player item list (first item in list)
    String str = bwm.attackTarget(0, 0);
    assertEquals("Bob attacks target with knife\n"
        + "Target remaining health: 16\n", str);
    assertEquals(16, bwm.targetHealth());
  }
  
  @Test
  public void testRemoveItemAfterSuccessfulAttackWithItem() {
    //name, player id, is human or not, number of items can carry, born space id
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    Item knife = new Weapon("knife", 4);
    assertEquals(20, bwm.targetHealth());
    //make bob pick up item knife
    bob.pickItem(knife);
    //first 0 is player's id
    //second 0 is item id in player item list (first item in list)
    bwm.attackTarget(0, 0);
    //player's item list should not contain the knife anymore
    assertFalse(bob.getItemList().contains(knife));
  }
  
  @Test
  public void testRemoveItemAfterAttackWithItemBeSeenByOthers() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 1);
    //move pet to a room that is not space 0
    bwm.movePet(10);
    Item knife = new Weapon("knife", 4);
    assertEquals(20, bwm.targetHealth());
    Player bob = bwm.getPlayerList().get(0);
    //make bob pick up item knife
    bob.pickItem(knife);
    //first 0 is player's id
    //second 0 is item id in player item list (first item in list)
    bwm.attackTarget(0, 0);
    //attack fails, item removed 
    assertEquals(20, bwm.targetHealth());
    assertFalse(bob.getItemList().contains(knife));
  }
  
  @Test
  public void testRemoveItemAfterAttackWithItemInDifferentRoomWithTarget() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 5 while target is in space 0
    bwm.addPlayer("Bob", 0, true, 5, 5);
    Player bob = bwm.getPlayerList().get(0);
    //move pet to a room that is not space 0
    Item knife = new Weapon("knife", 4);
    assertEquals(20, bwm.targetHealth());
    //make bob pick up item knife
    bob.pickItem(knife);
    //first 0 is player's id
    //second 0 is item id in player item list (first item in list)
    bwm.attackTarget(0, 0);
    //attack fails, item removed 
    assertEquals(20, bwm.targetHealth());
    assertFalse(bob.getItemList().contains(knife));
  }
  
  @Test
  public void testAttackWithNoItemBeSeenByOthers() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 1);
    //move pet to a room that is not space 0
    bwm.movePet(10);
    assertEquals(20, bwm.targetHealth());
    //0 is player's id, which is player Bob
    String str = bwm.pokeTarget(0);
    assertEquals("Attack fails. Player is seen by others\n", str);
    //attack fails, target health should not be reduced
    assertEquals(20, bwm.targetHealth());
  }
  
  @Test
  public void testAttackWithNoItemInDifferentRoomWithTarget() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 5 while target is in space 0
    bwm.addPlayer("Bob", 0, true, 5, 5);
    assertEquals(20, bwm.targetHealth());
    //0 is player's id
    String str = bwm.pokeTarget(0);
    assertEquals("Attack fails. Target is not in the same room\n", str);
    //attack fails, target health should not be reduced
    assertEquals(20, bwm.targetHealth());
  }
  
  @Test
  public void testValidAttackWithNoItem() {
    //name, player id, is human or not, number of items can carry, born space id
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    assertEquals(20, bwm.targetHealth());
    //0 is player's id
    String str = bwm.pokeTarget(0);
    assertEquals("Bob pokes target in eyes.\n"
        + "Target remaining health: 19\n", str);
    assertEquals(19, bwm.targetHealth());
  }
  
  @Test
  public void testSuceesfulAttackWithItemWithPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0, which is also the space pet born in
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 1);
    //pet is in space 0
    assertEquals(0, bwm.getPet().getCurrentRoomId());
    Item knife = new Weapon("knife", 4);
    assertEquals(20, bwm.targetHealth());
    Player bob = bwm.getPlayerList().get(0);
    //make bob pick up item knife
    bob.pickItem(knife);
    //first 0 is player's id
    //second 0 is item id in player item list (first item in list)
    String str = bwm.attackTarget(0, 0);
    assertEquals("Bob attacks target with knife\n"
        + "Target remaining health: 16\n", str);
    //attack successful
    assertEquals(16, bwm.targetHealth());
    //item removed
    assertFalse(bob.getItemList().contains(knife));
  }
  
  @Test
  public void testAttackFailsBeSeenByOthersInSameRoom() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 0);
    //move pet to a room that is not space 0
    bwm.movePet(10);
    assertEquals(20, bwm.targetHealth());
    //0 is player's id, which is player Bob
    String str = bwm.pokeTarget(0);
    assertEquals("Attack fails. Player is seen by others\n", str);
    //attack fails, target health should not be reduced
    assertEquals(20, bwm.targetHealth());
  }
  
  @Test
  public void testAttackFailedBeSeenByOthersInNeighboringRoomWithPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 1);
    //move pet to space 1
    bwm.movePet(1);
    assertEquals(20, bwm.targetHealth());
    //0 is player's id, which is player Bob
    String str = bwm.pokeTarget(0);
    assertEquals("Attack fails. Player is seen by others\n", str);
    //attack fails, target health should not be reduced
    assertEquals(20, bwm.targetHealth());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAttackNegativePlayerId() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    bwm.attackTarget(-1, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAttackPlayerIdExceedList() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //player list size = 1, 2 exceed player list
    bwm.attackTarget(2, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAttackNegativeItemId() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    bwm.attackTarget(0, -1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAttackItemIdExceedList() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //player list size = 1, 2 exceed player list
    bwm.attackTarget(0, 2);
  }
  
  @Test
  public void testMoveStartAtSpace0() {
    assertEquals(0, bwm.getPet().getCurrentRoomId());
  }
  
  @Test
  public void testMovePet() {
    String str = bwm.movePet(5);
    assertEquals(5, bwm.getPet().getCurrentRoomId());
    assertEquals("Pet is moved to Baby's room\n", str);
    str = bwm.movePet(10);
    assertEquals(10, bwm.getPet().getCurrentRoomId());
    assertEquals("Pet is moved to Gym\n", str);
  }
  
  
  @Test(expected = IllegalArgumentException.class)
  public void testMovePetNegativeRoomId() {
    bwm.movePet(-5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testMovePetExceedRoomList() {
    bwm.movePet(35);
  }
  
  @Test
  public void testDisplayItemInBag() {
    bwm.addPlayer("Bob", 0, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    Item knife = new Weapon("knife", 4);
    bob.pickItem(knife);
    String str = bwm.displayItemInBag(0);
    assertEquals("Pick an item to make attempt on target's life:\n"
        + "0: knife\n", str);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDisplayItemInbagWithNegativeId() {
    bwm.displayItemInBag(-1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDisplayItemInbagWithIdExceedList() {
    bwm.displayItemInBag(100);
  }
  
  @Test
  public void testDisplayPetInfoIfPlayerInSameRoom() {
    //add a player in space 0
    bwm.addPlayer("a", 0, true, 5, 0);
    String info = bwm.displayRoomInfo(0);
    assertEquals("Room's name: Bathroom\n"
        + "Neighbors: 0: Bedroom, 1: Gym\n"
        + "Items in room: 0: Shower nozzle(damage:2)\n"
        + "Players in room: a\n"
        //Pet info is included
        + "Pikachu is in the Bathroom\n", info);
  }
  
  @Test
  public void testCanbeSeenInSameRoomWithoutPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 0);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 0);
    Player bob = bwm.getPlayerList().get(0);
    Player john = bwm.getPlayerList().get(1);
    //bob and john are in same room
    assertTrue(bwm.canBeSeen(bob));
    assertTrue(bwm.canBeSeen(john));
  }
  
  @Test
  public void testCanbeSeenInSameRoomWithPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 1);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 1);
    Player bob = bwm.getPlayerList().get(0);
    Player john = bwm.getPlayerList().get(1);
    bwm.movePet(1);
    //bob and john are in same room
    assertTrue(bwm.canBeSeen(bob));
    assertTrue(bwm.canBeSeen(john));
  }
  
  @Test
  public void testCanbeSeenInNeighboringRoomWithoutPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 1);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 2);
    Player bob = bwm.getPlayerList().get(0);
    Player john = bwm.getPlayerList().get(1);
    //bob in space 1, john in space 2, space 1 and 2 are neighbors
    assertTrue(bwm.canBeSeen(bob));
    assertTrue(bwm.canBeSeen(john));
  }
  
  @Test
  public void testCanbeSeenInNeighboringRoomWithPet() {
    //name, player id, is human or not, number of items can carry, born space id
    //Bob is born in space 0
    bwm.addPlayer("Bob", 0, true, 5, 1);
    //John is born in the neighboring room of Bob
    bwm.addPlayer("John", 1, true, 5, 2);
    Player bob = bwm.getPlayerList().get(0);
    Player john = bwm.getPlayerList().get(1);
    //move pet to space 1
    bwm.movePet(1);
    //bob in space 1, john in space 2, space 1 and 2 are neighbors
    //bob cannot be seen by john since pet is in space 1
    //john can still be seen by bob
    assertFalse(bwm.canBeSeen(bob));
    assertTrue(bwm.canBeSeen(john));
  }
  
  @Test
  public void testPetsMoveFollowDfs() {
    //DFS path: 0, 1, 2, 3, 4, 7, 6, 5, 6, 8, 19, 18, 9, 13, 12, 10, 11, 10, 12, 13, 14, 
    //15, 16, 17, 16, 15, 14, 13, 9, 18, 19, 8, 6, 7, 4, 3, 2, 1, 0
    Pet pet = bwm.getPet();
    assertEquals(0, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 0);
    assertEquals(1, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 1);
    assertEquals(2, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 2);
    assertEquals(3, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 3);
    assertEquals(4, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 4);
    assertEquals(7, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 5);
    assertEquals(6, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 6);
    assertEquals(5, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 7);
    assertEquals(6, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 8);
    assertEquals(8, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 9);
    assertEquals(19, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 10);
    assertEquals(18, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 11);
    assertEquals(9, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 12);
    assertEquals(13, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 13);
    assertEquals(12, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 14);
    assertEquals(10, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 15);
    assertEquals(11, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 16);
    assertEquals(10, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 17);
    assertEquals(12, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 18);
    assertEquals(13, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 19);
    assertEquals(14, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 20);
    assertEquals(15, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 21);
    assertEquals(16, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 22);
    assertEquals(17, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 23);
    assertEquals(16, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 24);
    assertEquals(15, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 25);
    assertEquals(14, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 26);
    assertEquals(13, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 27);
    assertEquals(9, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 28);
    assertEquals(18, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 29);
    assertEquals(19, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 30);
    assertEquals(8, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 31);
    assertEquals(6, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 32);
    assertEquals(7, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 33);
    assertEquals(4, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 34);
    assertEquals(3, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 35);
    assertEquals(2, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 36);
    assertEquals(1, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 37);
    assertEquals(0, pet.getCurrentRoomId());
       
  }
  
  @Test
  public void testMovePetsRestartDfs() {
    Pet pet = bwm.getPet();
    assertEquals(0, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 0);
    assertEquals(1, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(0, 1);
    assertEquals(2, pet.getCurrentRoomId());
    //move pet to space 15
    bwm.movePet(15);
    assertEquals(15, pet.getCurrentRoomId());
    //restart dfs
    bwm.movePetFollowDfs(15, 0);
    assertEquals(14, pet.getCurrentRoomId());
    bwm.movePetFollowDfs(15, 1);
    assertEquals(13, pet.getCurrentRoomId());
  }
}
