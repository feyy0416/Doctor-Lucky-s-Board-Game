package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import world.ReadOnlyWorld;

/**
 * A JButton represents target in the view.
 * 
 * @author admin - Zifei Song, Zesheng Li.
 *
 */
public class TargetButton extends JButton {

  private static final long serialVersionUID = 7601781392159795513L;
  private int posX;
  private int posY;
  private ReadOnlyWorld m;

  /**
   * Constructor target button.
   * 
   * @param posX - the column position in the view.
   * @param posY - the row position in the view.
   * @param m    - the read only model.
   */
  public TargetButton(int posX, int posY, ReadOnlyWorld m) {
    if (posX < 0 || posY < 0 || m == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.posX = posX;
    this.posY = posY;
    this.m = m;
    int dc = getRoomDc(m);
    int dr = getRoomDr(m);
    this.setBounds(dc * 30 - 30, dr * 30 - 30, 30, 30);
    this.setForeground(Color.cyan);
    this.setOpaque(false);
    this.setFocusPainted(false);
    this.setBorderPainted(false);
    this.setVisible(true);
  }

  /**
   * Represents target button move to the given position.
   */
  public void move(int posX, int posY) {
    if (posX < 0 || posY < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.posX = posX;
    this.posY = posY;
    int dc = getRoomDc(m);
    int dr = getRoomDr(m);
    this.setBounds(dc * 30 - 30, dr * 30 - 30, 30, 30);
  }

  /**
   * Get the room id by this target position.
   * 
   * @param m read only model.
   * @return the room id where target is in.
   */
  public int getRoomId(ReadOnlyWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    return m.findRoomId(posX * 30 + 1, posY * 30 + 1);
  }

  /**
   * Get the room down column position where the target is in.
   * 
   * @param m read only model.
   * @return the down column position this room.
   */
  public int getRoomDc(ReadOnlyWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    return m.getRoomList().get(m.findRoomId(posX * 30 + 1, posY * 30 + 1)).getDownCol();
  }

  /**
   * Get the room row column position where the target is in.
   * 
   * @param m read only model.
   * @return the down row position this room.
   */
  public int getRoomDr(ReadOnlyWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    return m.getRoomList().get(m.findRoomId(posX * 30 + 1, posY * 30 + 1)).getDownRow();
  }

  @Override
  public void paintComponent(Graphics g) {
    if (g == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    int radius = 10;
    g.fillOval(getWidth() / 2 - radius, getHeight() / 2 - radius, 20, 20);
    g.setColor(Color.cyan);
  }

}
