package controllertest;

import controller.Features;
import view.WorldView;

/**
 * Mock view for testing SwingController.
 *
 */
public class MockView implements WorldView {
  
  private StringBuilder log;
  private final int uniqueCode;
  
  /**
   * Construct a mock view with log and unique code.
   * 
   * @param log test input
   * @param uniqueCode a unique code
   */
  public MockView(StringBuilder log, int uniqueCode) {
    if (log == null) {
      throw new IllegalArgumentException("log cannot be null.");
    }
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void setFeatures(Features f) {
  }

  @Override
  public void setTextToWindow(String str) {
    log.append("Added text on screen");
  }

  @Override
  public void appendTextToWindow(String str) {
    log.append("Display following text: ").append(str);
  }

  @Override
  public void addPlayerBtn(int id, int posX, int posY, int posInRoom) {
    log.append("view => Player id: ").append(Integer.toString(id));
    log.append(" x and y: ").append(Integer.toString(posX))
    .append(" ").append(Integer.toString(posY));
    log.append(" Position in room: ").append(Integer.toString(posInRoom));
  }

  @Override
  public void updatePlayerBtn(int id, int posX, int posY, int posInRoom) {
    log.append("Player moves to room with id: ").append(Integer.toString(id));
    log.append(" room x and y: ").append(Integer.toString(posX))
    .append(" ").append(Integer.toString(posY));
    log.append(" room player list size : ").append(Integer.toString(posInRoom));
  }

  @Override
  public void updateTargetBtn(int x, int y) {
    log.append("Target moves to room with x and y:");
    log.append(Integer.toString(x)).append(" ").append(Integer.toString(y));
  }

  @Override
  public void setCurrentPlayer(int playerId) {
    log.append("Current turn player's id : ").append(Integer.toString(playerId));
  }

  @Override
  public void onlyShowPlayersInCurrentRoom(int roomId) {
    log.append("Show players in current room.");
  }

  @Override
  public void setAction(String action) {
    log.append(" set action to Action field.");
  }

  @Override
  public void updateOldRoom(int playerId) {
    log.append("update old room layout, player id: ").append(Integer.toString(playerId));
  }

  @Override
  public void closeFrame() {
    log.append("close the game");
  }

}
