package controller;

import world.BuildWorld;


/**
 * An interface for commands with different features.
 *
 */
public interface WorldCommand {
  /**
   * Execute a command by calling the corresponding method in model.
   * 
   * @param m model - represent model of the game.
   */
  void execute(BuildWorld m);
  
}
