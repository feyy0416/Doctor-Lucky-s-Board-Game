package controller;

import world.BuildWorld;

/**
 * This command implements the feature to add a player into the game.
 * @author Zifei Song.
 *
 */
public class AddPlayer implements WorldCommand {
  private String name;
  private int id;
  private boolean isHuman;
  private int bagCapacity;
  private int roomId;
  
  /**
   * Construct a addPlayer command with player's name, id, type(human or computer) 
   * bag size born room id.
   * 
   * @param name name of the player
   * @param id id of the player
   * @param isHuman true if it's human player false if it's controlled by computer
   * @param bagCapacity represents how many items the play can hold
   * @param roomId id of the room where player generates into the world
   * @throws IllegalArgumentException if name is null, id bag size or room id less than 0
   */
  public AddPlayer(String name, int id, boolean isHuman, int bagCapacity, int roomId) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name cannot be null or Empty.");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative.");
    }
    if (bagCapacity < 1) {
      throw new IllegalArgumentException("Bag capacity shoule be positive.");
    }
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id cannot be negative.");
    }
    this.name = name;
    this.id = id;
    this.isHuman = isHuman;
    this.bagCapacity = bagCapacity;
    this.roomId = roomId;
  }

  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    m.addPlayer(name, id, isHuman, bagCapacity, roomId);
  }
}
