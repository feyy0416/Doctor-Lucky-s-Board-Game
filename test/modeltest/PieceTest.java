package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.Piece;
import world.TargetCharacter;

/**
 * Class for testing the Piece class.
 *
 */
public class PieceTest {
  
  private Piece target;
  
  /**
   * This method is providing short-hand way of creating instances of a new Piece object.
   * This method will make the implementation more flexible.
   * 
   * @param name name of the target
   * @param health how many health target has
   * @param numRoom how many rooms map has
   * @return new TargetCharacter object
   */
  protected Piece tg(String name, int health, int numRoom) {
    return new TargetCharacter(name, health, numRoom);
  }
  
  @Before
  public void setUp() {

    target = new TargetCharacter("Doctor lucky", 100, 25);
  }

  @Test(expected = IllegalArgumentException.class)
  public void targetCharacterWithNullName() {
    tg(null, 100, 25);
  }
  
  @Test
  public void testGetTargetCharacterName() {
    assertEquals("Doctor lucky", target.getName());
  }
  
  @Test
  public void testGetTargetCharacterHealth() {
    assertEquals(100, target.getHealth());
  }
  
  @Test
  public void testTargertMoveAndCurrentRoomId() {
    assertEquals(0, target.getCurrentRoomId());
    target.move();
    assertEquals(1, target.getCurrentRoomId());
    target.move();
    target.move();
    target.move();
    target.move();
    target.move();
    assertEquals(6, target.getCurrentRoomId());
    
    //test if target move back to the first room of the list.
    //move target to the last room of the list.
    for (int i = 6; i < 25; i++) {
      target.move();
    }
    assertEquals(0, target.getCurrentRoomId());
    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void targetCharacterNegativeHealth() {
    tg("Doctor lucky", -100, 25);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void targetCharacterZeroHealth() {
    tg("Doctor lucky", 0, 25);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void targetCharacterNegativeNumRoom() {
    tg("Doctor lucky", 100, -25);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void targetCharacterZeroNumRoom() {
    tg("Doctor lucky", 100, 0);
  }
  
  @Test
  public void testEquals() {
    assertTrue(tg("Doctor lucky", 100, 25).equals(target));
  }
  
  @Test
  public void testHashCode() {
    assertEquals(tg("Doctor lucky", 100, 25).hashCode(), target.hashCode());
  }
  
}
