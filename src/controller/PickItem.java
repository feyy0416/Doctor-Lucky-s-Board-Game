package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * This command implements the "pick an item" feature for Player objects.
 */
public class PickItem implements WorldCommand {
  
  private int playerId;
  private int itemId;
  private WorldView view;

  /**
   * Construct a pick item command with player id.
   * 
   * @param playerId the id of player to pick the item.
   * @param itemId the id of item in room's item list.
   * @param view view of the game.
   * @throws IllegalArgumentException if any argument is invalid.
   */
  public PickItem(int playerId, int itemId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (itemId < 0) {
      throw new IllegalArgumentException("Item id cannot be negative.");
    }
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id cannot be negative.");
    }
    this.playerId = playerId;
    this.view = view;
    this.itemId = itemId;
  }
  
  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    // catch if bag is full.
    try {
      view.setTextToWindow(m.pickItem(playerId, itemId));
    } catch (IllegalArgumentException err) {
      view.setTextToWindow("Bag is full.");
    }
    view.appendTextToWindow("\nPressed Space Key to Continue...");
  }

}
