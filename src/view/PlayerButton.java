package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import world.ReadOnlyWorld;

/**
 * A JButton to represent a player in the view.
 * @author Zifei Song, Zesheng Li.
 *
 */
public class PlayerButton extends JButton {

  private static final long serialVersionUID = -4036840404388735311L;
  
  // id corresponding to player id.
  private int id;
  private int posX;
  private int posY;
  private int posInRoom;

  /**
   * Constructor of player button.
   * 
   * @param id - the player id.
   * @param posX - the column position in the world panel.
   * @param posY - the row position in the world panel.
   * @param posInRoom - player index in the room.
   */
  public PlayerButton(int id, int posX, int posY, int posInRoom) {
    if (id < 0 || posX < 0 || posY < 0 || posInRoom < 0) {
      throw new IllegalArgumentException("Invalid arguments"); 
    }
    this.id = id;
    this.posInRoom = posInRoom;
    this.posX = posX;
    this.posY = posY;
    this.setBounds(posX * 30 + (posInRoom -1)  * 20, posY * 30 + 30, 20, 20);
    this.setForeground(Color.pink);
    this.setOpaque(false);
    this.setFocusPainted(false);
    this.setBorderPainted(false);
    this.setVisible(true);
  }

  /**
   * Get the player id.
   * @return player id.
   */
  public int getId() {
    return this.id;
  }

  /**
   * Get the column position of player in the view.
   * @return the column position of player in the view.
   */
  public int getPosX() {
    return this.posX;
  }

  /**
   * Get the row position of player in the view.
   * @return the row position of player in the view.
   */
  public int getPosY() {
    return this.posY;
  }

  /**
   * Get the index of player in the room.
   * @return the index of player in the room.
   */
  public int getPosInRoom() {
    return this.posInRoom;
  }

  /**
   * Set the index of player in the room.
   * @param pos - the index of player in the room.
   */
  public void setPosInRoom(int pos) {
    if (pos < 0) {
      throw new IllegalArgumentException("Position cannot be negitive.");
    }
    this.posInRoom = pos;
    refresh();
  }

  /**
   * Get the room id in the model.
   * 
   * @param m - the read only model.
   * @return the room id in the model.
   */
  public int getRoomId(ReadOnlyWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    return m.findRoomId(posX * 30 + 1, posY * 30 + 1);
  }

  /**
   * Make this button is visible.
   */
  public void makeInvisible() {
    this.setVisible(false);
  }

  /**
   * Make this button is invisible.
   */
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Change the button position to handle move action.
   *
   * @param posX - the column position player need to be moved.
   * @param posY - the row position player need to be moved.
   * @param posInRoom - the index of room that player in the new room.
   */
  public void move(int posX, int posY, int posInRoom) {
    if (posX < 0 || posY < 0 || posInRoom < 0) {
      throw new IllegalArgumentException("Position cannot be negitive.");
    }
    this.posX = posX;
    this.posY = posY;
    this.posInRoom = posInRoom;
    refresh();
  }

  @Override
  public void paintComponent(Graphics g) {
    if (g == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    int radius = 10;
    g.fillOval(getWidth() / 2 - radius, getHeight() / 2 - radius, 20, 20);
    g.setColor(Color.PINK);
  }
  
  /**
   * Refresh this button for updating on view.
   */
  public void refresh() {
    this.setBounds(posX * 30 + (posInRoom -1) * 20, posY * 30 + 30, 20, 20);
  }

}
