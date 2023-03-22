package world;

/**
 * An interface for target character that represents the target of the game, with methods 
 * to return the target information and move the target between spaces.
 *
 */
public interface Piece {
  
  /**
   * Move the target to next Space, order of movement corresponding 
   * to the entire Space list of the world.
   */
  void move();
   
  /**
   * Return the current health of the target.
   * 
   * @return current health of target
   */
  int getHealth();
  
  /**
   * Return the name of the target.
   * 
   * @return the name of target
   */
  String getName();

  /**
   * Return the id of the room where target is in.
   * 
   * @return the id of the room where target is
   */
  int getCurrentRoomId();

  /**
   * Reduce target's health, the amount of reduced health is equal to damage.
   * 
   * @param damage the amount of reduced health
   */
  void reduceHealth(int damage);
}
