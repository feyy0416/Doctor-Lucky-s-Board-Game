package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * Command implements the feature to move the pet to a new room.
 */
public class MovePet implements WorldCommand {

  private int roomId;
  private WorldView view;
  
  /**
   * Construct a move pet command with target room id.
   * 
   * @param roomId - id of the room to move to.
   * @param view - world view.
   * @throws IllegalArgumentException if out is null or room id less than 0.
   */
  public MovePet(int roomId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (roomId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    this.roomId = roomId;
    this.view = view;
  }
  
  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    view.setTextToWindow(m.movePet(roomId));
    view.appendTextToWindow("\nPressed Space Key to Continue...");
  }

}
