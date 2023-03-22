package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * This command implements the feature to move the target character.
 *
 */
public class MoveTarget implements WorldCommand {
  
  private WorldView view;
  
  /**
   * Construct a move target command to move the target character.
   * 
   * @param out output
   * @throws IllegalArgumentException if out is null
   */
  public MoveTarget(WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    this.view = view;
  }

  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    view.setTextToWindow(m.moveTarget());
  }

}
