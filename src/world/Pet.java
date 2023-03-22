package world;

/**
 * An interface for CharacterPet class that represents target chharacter's pet in Doctor 
 * Lucky game, pet can be moved by player with a function to hide the information of the 
 * space where pet is located.
 *
 */
public interface Pet {

  /**
   * Change pet's current room id to roomId.
   * 
   * @param roomId target room id to move the pet to
   * @throws IllegalArgumentException if room id is negative
   */
  void move(int roomId);
  
  /**
   * Return the name of the pet.
   * 
   * @return String represents pet's name
   */
  String getName();
  
  /**
   * Return the room id where pet locates.
   * 
   * @return the room id where pet locates.
   */
  int getCurrentRoomId();
  
}
