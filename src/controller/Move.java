package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * This command implements the "move" feature for Player objects.
 *
 */
public class Move implements WorldCommand {
  
  private int playerId;
  private int roomId;
  private WorldView view;
  
  /**
   * Construct a move command with player's id and target room id.
   * 
   * @param playerId the id of player to move.
   * @param roomId id of target room.
   * @param view view of the game.
   * @throws IllegalArgumentException if out is null, player id or room id less than 0
   */
  public Move(int playerId, int roomId, WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id canot be negative.");
    }
    this.playerId = playerId;
    this.roomId = roomId;
    this.view = view;
  }
  
  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    // update old room.
    view.updateOldRoom(playerId);
    view.setTextToWindow(m.move(playerId, roomId)); 
    
    // update new room;
    int posInRoom = m.getRoomList().get(roomId).getPlayers().size();
    view.updatePlayerBtn(playerId, m.getRoomList().get(roomId).getUpCol(),
        m.getRoomList().get(roomId).getUpRow(), posInRoom);
      
  }
}
