package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * Command implements the feature for player to look around the space.
 */
public class LookAround implements WorldCommand {

  private int playerId;
  private WorldView view;
  
  /**
   * Construct a look around command with player's id.
   * 
   * @param playerId if of player of the turn.
   * @param view view of the game.
   * @throws IllegalArgumentException if out is null or player id less than 0.
   */
  public LookAround(int playerId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    this.playerId = playerId;
    this.view = view;
  }
  
  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    view.setTextToWindow(m.lookAround(playerId));
    view.appendTextToWindow("\nPressed Space Key to Continue...");
  }

}
