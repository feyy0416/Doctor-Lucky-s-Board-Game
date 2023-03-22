package world;

/**
 * An interface for weapons that represents items can be found by players in the 
 * world, with methods to return the item informations.
 *
 */
public interface Item {

  /**
   * Return the name of the Item.
   * 
   * @return the name of the Item
   */
  String getName();
  
  /**
   * Return how many damage the Item can deal.
   * 
   * @return the damage of the Item
   */
  int getPower();
  
}
