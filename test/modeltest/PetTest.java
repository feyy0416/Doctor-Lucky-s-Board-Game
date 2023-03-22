package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.Pet;
import world.TargetPet;

/**
 * Class for testing the Player class.
 *
 */
public class PetTest {

  private Pet cat;
  
  @Before
  public void setUp() {
    cat = new TargetPet("cat");
  }

  @Test
  public void testGetName() {
    assertEquals("cat", cat.getName());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNullName() {
    Pet dog = new TargetPet(null);
  }
  
  @Test
  public void testStartAtSpace0() {
    assertEquals(0, cat.getCurrentRoomId());
  }
  
  @Test
  public void testMovePet() {
    //born in space 0
    assertEquals(0, cat.getCurrentRoomId());
    //move to space 15
    cat.move(15);
    assertEquals(15, cat.getCurrentRoomId());
    //move to space 5
    cat.move(5);
    assertEquals(5, cat.getCurrentRoomId());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveNegativeRoomId() {
    cat.move(-1);
  }
  
  @Test
  public void testEquals() {
    assertTrue(new TargetPet("cat").equals(cat));
  }
  
  @Test
  public void testHashCode() {
    assertEquals(new TargetPet("cat").hashCode(), cat.hashCode());
  }
  
}
