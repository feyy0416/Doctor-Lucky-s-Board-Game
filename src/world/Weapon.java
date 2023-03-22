package world;

/**
 * Weapons in the game Kill Doctor Lucky that allow player to attack target character.
 *
 */
public class Weapon implements Item {

  private final String name;
  private final int power;
  
  /**
   * Constructs a weapon in terms of its name house and how much damage it can deal.
   * 
   * @param name name of the weapon
   * @param power how much damage it can deal
   * @throws IllegalArgumentException if any argument is invalid
   */
  public Weapon(String name, int power) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (power < 0) {
      throw new IllegalArgumentException("Power cannot be negative.");
    }
    this.name = name;
    this.power = power;
  }
  
  @Override
  public String getName() {
    final String res = this.name;
    return res;
  }

  @Override
  public int getPower() {
    final int res = this.power;
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
    if (!(o instanceof Weapon)) {
      return false;
    }
    Weapon that = (Weapon) o;
    return (this.name.equals(that.name) && this.power == that.power);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;   
    result = result * prime + name.hashCode();
    result = result * prime + power;
    return result;
  }
}
