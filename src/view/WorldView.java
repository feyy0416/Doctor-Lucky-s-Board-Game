package view;

import controller.Features;

/**
 * This interface represents the world view.
 * 
 * @author Zifei Song.
 *
 */
public interface WorldView {

  /**
   * This method adds click and key listeners to components. All add listener
   * methods are helper functions for setFeatures method.
   */
  void setFeatures(Features f);

  /**
   * Set the text field to display the given string.
   * 
   * @param str - the string we want to show.
   */
  void setTextToWindow(String str);

  /**
   * Append the given string to the text field to display.
   * 
   * @param str - the string we want to show.
   */
  void appendTextToWindow(String str);

  /**
   * Add player button into the view by the given parameters.
   * 
   * @param id        - player id.
   * @param posX      - the column position of player.
   * @param posY      - the row position of player.
   * @param posInRoom - the player index in the room.
   */
  void addPlayerBtn(int id, int posX, int posY, int posInRoom);

  /**
   * Update player button display on view.
   * 
   * @param id        - player id.
   * @param posX      - the column position of player.
   * @param posY      - the row position of player.
   * @param posInRoom - the player index in the room.
   */
  void updatePlayerBtn(int id, int posX, int posY, int posInRoom);

  /**
   * Update target button display on view.
   * 
   * @param x - the column position of target.
   * @param y - the row position of target.
   */
  void updateTargetBtn(int x, int y);

  /**
   * Set the current player by the given player id.
   * 
   * @param playerId - the id of player whom we want to set as current turn.
   */
  void setCurrentPlayer(int playerId);

  /**
   * Show all players in the given room id.
   * 
   * @param roomId the target room id.
   */
  void onlyShowPlayersInCurrentRoom(int roomId);

  /**
   * Set the action command by the given action.
   * 
   * @param action - the action command.
   */
  void setAction(String action);

  /**
   * Update old room when player move outside.
   * 
   * @param playerId - the target player.
   */
  void updateOldRoom(int playerId);

  /**
   * Close JFrame when we quit the game.
   */
  void closeFrame();

}
