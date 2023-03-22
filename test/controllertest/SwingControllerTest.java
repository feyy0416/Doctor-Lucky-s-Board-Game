package controllertest;

import static org.junit.Assert.assertEquals;

import controller.SwingController;
import org.junit.Test;
import world.BuildWorld;
import view.WorldView;


/**
 * Class test for the SwingController class.
 *
 */
public class SwingControllerTest {


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    BuildWorld model = null;
    new SwingController(model, new MockView(new StringBuilder(), 11));    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidView() {
    WorldView view = null;
    new SwingController(new MockModel1(new StringBuilder(), 22, 0, 
        new StringBuffer()), view);    
  }
  
  @Test
  public void testAddPlayerWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100031, 0, out);
    WorldView view = new MockView(log2, 100002);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.addPlayer("a", 0, false, 5, 0);
    assertEquals("mode => Play name: a id: 0 type : "
        + "computera bag size:5 Birth room id: 0", log1.toString());
    assertEquals("view => Player id: 0 x and y: 0 0 Position in room: 0", log2.toString());
    
  }
  
  @Test
  public void testDisplayPlayerInfoWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100039, 0, out);
    WorldView view = new MockView(log2, 1003004);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.displayPlayerInfo(0);;
    assertEquals("Display player's info. ", log1.toString());
    assertEquals("Added text on screen", log2.toString());
    
  }
  
  @Test
  public void testMoveWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100036, 0, out);
    WorldView view = new MockView(log2, 1008004);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.move(0, 0);;
    assertEquals("Player id: 0 Target room id: 0", log1.toString());
    assertEquals("update old room layout, player id: 0Added text on screenPlayer "
        + "moves to room with id: 0 room x and y: 0 0 room player list size : 0", log2.toString());
    assertEquals("100036", out.toString());
    
  }
  
  @Test
  public void testPickWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100035, 0, out);
    WorldView view = new MockView(log2, 1700004);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.pick(0, 0);
    assertEquals("Player's id 0 Item's id: 0", log1.toString());
    assertEquals("Added text on screenDisplay following text: \n"
        + "Pressed Space Key to Continue...", log2.toString());
    assertEquals("100035", out.toString());
    
  }
  
  @Test
  public void testMoveTargetWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100043, 0, out);
    WorldView view = new MockView(log2, 1000604);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.moveTarget();
    assertEquals("target moves to next room. ", log1.toString());
    assertEquals("Target moves to room with x and y:0 0", log2.toString());
    
  }
  
  @Test
  public void testlookAroundWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100022, 0, out);
    WorldView view = new MockView(log2, 100004);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.lookAround(0);
    assertEquals("Player is looking around.  Player id: 0", log1.toString());
    assertEquals("Added text on screenDisplay following text: \n"
        + "Pressed Space Key to Continue...", log2.toString());
    assertEquals("100022", out.toString());
    
  }
  
  @Test
  public void testAttackWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 1090033, 0, out);
    WorldView view = new MockView(log2, 1009044);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.attack(0, 0);
    assertEquals("Player trys to attack target.  Player id: 0 Item id: 0", log1.toString());
    assertEquals("Added text on screenDisplay following text: \n"
        + "Pressed Space Key to Continue...", log2.toString());
    assertEquals("1090033", out.toString());
    
  }
  
  @Test
  public void testMovePetWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 1060223, 0, out);
    WorldView view = new MockView(log2, 1006044);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.movePet(0);
    assertEquals("Player moves the pet. Player moves the pet. ", log1.toString());
    assertEquals("Added text on screenDisplay following text: \n"
        + "Pressed Space Key to Continue...", log2.toString());
    assertEquals("10602231060223", out.toString());
    
  }
  
  @Test
  public void testStartNewWorldWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 10072223, 0, out);
    WorldView view = new MockView(log2, 10018044);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.startNewWorld("res/mansion.txt", 15);
    assertEquals("close the game", log2.toString());
    
  }
  
  @Test
  public void testStartCurrentWorldWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 100243, 0, out);
    WorldView view = new MockView(log2, 100544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.startNewWorld("res/mansion.txt", 15);
    assertEquals("close the game", log2.toString());
    
  }
  
  @Test
  public void testPokeWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 1020243, 0, out);
    WorldView view = new MockView(log2, 1007544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.poke(0);
    assertEquals("Player poke target in eyes.  Player id: 0", log1.toString());
    assertEquals("Added text on screenDisplay following text: \n"
        + "Pressed Space Key to Continue...", log2.toString());
    assertEquals("1020243", out.toString());
    
  }
  
  @Test
  public void testDisplayItemInRoomWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 10120243, 0, out);
    WorldView view = new MockView(log2, 10107544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.displayItemInRoom(0);
    assertEquals("Added text on screen set action to Action field.", log2.toString());
    
  }
  
  @Test
  public void testDisplayItemInBagWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 10420243, 0, out);
    WorldView view = new MockView(log2, 14007544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.displayItemInBag(0);
    assertEquals("Added text on screen", log2.toString());
    
  }
  
  @Test
  public void testNextTurnWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 10520243, 0, out);
    WorldView view = new MockView(log2, 10507544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.nextTurn();
    assertEquals("Target moves to room with x and y:0 0Current turn player's id : "
        + "0Added text on screenDisplay following text: Actions Hint:\n"
        + "Move: click the room you want to move to, and press \"m\" key.\n"
        + "Attack: press \"a\" key and use digit keys to choose item for attacking.\n"
        + "Look Around: press \"l\" key to look\n"
        + "Move Pet: click the room you want to move pet to. Press \"p\" key to move pet.\n"
        + "Show players in current room.", log2.toString());

    
  }
  
  @Test
  public void testShowGameStateWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 10202543, 0, out);
    WorldView view = new MockView(log2, 10075544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.showGameState(0);;
    assertEquals("Display games states for player: "
        + "0100110111010110110101111", log1.toString());
    assertEquals("Added text on screen", log2.toString());
    
  }
  
  @Test
  public void testTurnStartWithMock() {
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuffer out = new StringBuffer();
    BuildWorld model = new MockModel1(log1, 1020243, 0, out);
    WorldView view = new MockView(log2, 1007544);
    SwingController controller = new SwingController(model, view);
    controller.setView(view);
    controller.turnStart(5);;
    assertEquals("Display games states for player: 011111001000101010011", log1.toString());
    assertEquals("Current turn player's id : 0"
        + "Added text on screenDisplay following text: Actions Hint:\n"
        + "Move: click the room you want to move to, and press \"m\" key.\n"
        + "Attack: press \"a\" key and use digit keys to choose item for attacking.\n"
        + "Look Around: press \"l\" key to look\n"
        + "Move Pet: click the room you want to move pet to. Press \"p\" key to move pet.\n"
        + "Show players in current room.", log2.toString());
    
  }
}
