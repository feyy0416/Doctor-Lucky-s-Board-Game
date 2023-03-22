package world;


/**
 * Target character that moves around the map in an orderly fashion, and
 * the player who kills the target wins the game.
 */
public class TargetCharacter implements Piece {
  
  private final String name;
  private int health;
  private int numRoom;
  private int currentRoomId;
  
  /**
   * Constructs a target character in terms of its name house and total number of rooms.
   * 
   * @param name name of the target character
   * @param health health of the target character
   * @param numRoom total number of rooms in the map
   * @throws IllegalArgumentException if name is null, health or numRoom not greater than 0
   */
  public TargetCharacter(String name, int health, int numRoom) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (health <= 0) {
      throw new IllegalArgumentException("Health cannot be 0 or negative.");
    }
    if (numRoom <= 0) {
      throw new IllegalArgumentException("Number of room has to be positive.");
    }
    this.name = name;
    this.health = health;
    this.numRoom = numRoom;
    currentRoomId = 0;
  }

  @Override
  public void move() {
    if (currentRoomId < numRoom - 1) {
      currentRoomId++;
    } else {
      currentRoomId = 0;
    }
  }

  @Override
  public int getCurrentRoomId() {
    return this.currentRoomId;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public String getName() {
    final String res = this.name;
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
    if (!(o instanceof TargetCharacter)) {
      return false;
    }
    TargetCharacter that = (TargetCharacter) o;
    return (this.name.equals(that.name) && this.health == that.health && this.numRoom
        == that.numRoom);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;   
    result = result * prime + name.hashCode();
    result = result * prime + health;
    result = result * prime + numRoom;
    return result;
  }

  @Override
  public void reduceHealth(int damage) {
    if (damage <= 0) {
      throw new IllegalArgumentException("Damage has to be positive.");
    }
    health = health - damage;
  }


}
