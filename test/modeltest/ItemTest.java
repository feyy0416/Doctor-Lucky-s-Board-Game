package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.Item;
import world.Weapon;

/**
 * Class for testing the Item class.
 *
 */
public class ItemTest {
  
  private Item knife;
  
  @Before
  public void setUp() {
    knife = new Weapon("knife", 4);
  }
  
  @Test
  public void testGetItemName() {
    assertEquals("knife", knife.getName());
  }
  
  @Test
  public void testGetItemPower() {
    assertEquals(4, knife.getPower());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testWeaponDamageLessThanZero() {
    Item gun = new Weapon("gun", -1);
  }
  
  @Test
  public void testEquals() {
    Item knife2 = new Weapon("knife", 4);
    assertTrue(knife2.equals(knife));
  }
  
  @Test
  public void testHashCode() {
    Item knife2 = new Weapon("knife", 4);
    assertEquals(knife2.hashCode(), knife.hashCode());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testWeaponWithNullName() {
    Item gun = new Weapon(null, 1);
  }
}
