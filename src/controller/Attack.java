package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * Command implements the feature to make a player attempt to attack the target.
 * 
 * @author Zifei Song.
 */
public class Attack implements WorldCommand {

  private int playerId;
  private int itemId;
  private boolean withItem;
  private WorldView view;

  /**
   * Construct a attack with item command with player's id and item's id.
   * 
   * @param playerId the id of the player
   * @param itemId   the id of the item in player's item list
   * @param view     view of the game
   * @throws IllegalArgumentException if out is null, item id or player id less
   *                                  than 0
   */
  public Attack(int playerId, int itemId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    if (itemId < 0) {
      throw new IllegalArgumentException("Item id canot be negative.");
    }
    this.itemId = itemId;
    this.playerId = playerId;
    this.view = view;
    withItem = true;
  }

  /**
   * Construct a poke target in eyes command with player's id.
   * 
   * @param playerId the id of the player
   * @throws IllegalArgumentException if any argument is invalid
   */
  public Attack(int playerId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    this.playerId = playerId;
    this.view = view;
    itemId = -1;
    withItem = false;
  }

  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    if (withItem) {
      view.setTextToWindow(m.attackTarget(playerId, itemId));
    } else {
      view.setTextToWindow(m.pokeTarget(playerId));
    }
    view.appendTextToWindow("\nPressed Space Key to Continue...");

  }

}
