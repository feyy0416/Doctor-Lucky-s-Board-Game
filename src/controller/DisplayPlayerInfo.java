package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * Command implements the feature to display current room information.
 * 
 * @author Zifei Song.
 */
public class DisplayPlayerInfo implements WorldCommand {
  private int playerId;
  private WorldView view;

  /**
   * Construct a display player information command with player's id.
   * 
   * @param playerId id of player of the turn
   * @param view view of the game
   * @throws IllegalArgumentException out is null or player id less than 0
   */
  public DisplayPlayerInfo(int playerId, WorldView view) {
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
      throw new IllegalArgumentException("Model can't be null.");
    }
    view.setTextToWindow(m.displayPlayerInfo(playerId));
  }
}
