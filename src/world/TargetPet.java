package world;

/**
 * This class represents target's pet in game, with feature to hide the information
 * of located room.
 *
 */
public class TargetPet implements Pet {
  
  private String name;
  private int currentRoomId;
  
  /**
   * Construct a pet with name. 
   * 
   * @param name name of the pet
   * @throws IllegalArgumentException if name is null
   */
  public TargetPet(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
    //always born at space 0
    currentRoomId = 0;
  }

  @Override
  public void move(int roomId) {
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id cannot be negative.");
    }
    currentRoomId = roomId;
  }

  @Override
  public String getName() {
    final String res = this.name;
    return res;
  }

  @Override
  public int getCurrentRoomId() {
    final int res = this.currentRoomId;
    return res;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof TargetPet)) {
      return false;
    }
    TargetPet that = (TargetPet) o;
    return (this.name.equals(that.name));
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;   
    result = result * prime + name.hashCode();
    return result;
  }

}
