package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import world.ReadOnlyWorld;

/**
 * Represents the world panel in the view. For detail, this panel is the main
 * panel that player can interact with the game.
 * 
 * @author Zifei Song, Zesheng Li.
 *
 */
public class WorldPanel extends JPanel {

  private static final long serialVersionUID = -8741230079664176369L;
  private JTextArea textWindow;
  private BufferedImage map;
  private JLabel worldMap;
  private List<PlayerButton> playerBtnList;

  /**
   * Constructor of the world panel.
   * 
   * @param model the read only model.
   */
  public WorldPanel(ReadOnlyWorld model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.setLayout(null);
    textWindow = new JTextArea("Pressed Enter to Start the Game!\n");
    textWindow.setLineWrap(true);
    textWindow.setBounds(20, 50, 250, 650);
    textWindow.setBackground(Color.LIGHT_GRAY);
    textWindow.setForeground(Color.darkGray);
    textWindow.setEditable(false);
    textWindow.setBorder(javax.swing.BorderFactory.createLineBorder(Color.white));
    textWindow.setFont(new Font("MV Boli", Font.BOLD, 16));
    this.add(textWindow);
    this.setBackground(Color.gray);
    map = model.getMap();
    worldMap = new JLabel();
    ImageIcon imageIcon = new ImageIcon(map);
    worldMap.setIcon(imageIcon);
    JScrollPane js = new JScrollPane(worldMap);
    js.setBounds(300, 50, 850, 650);
    this.add(js);

    // initial player list.
    playerBtnList = new ArrayList<PlayerButton>();
  }

  /**
   * Get JTextArea in this panel.
   * 
   * @return JTextArea to show information.
   */
  public JTextArea getTextWindow() {
    return textWindow;
  }

  /**
   * Get the JLabel represented the world map.
   * 
   * @return the JLabel represented the world map.
   */
  public JLabel getWorldMap() {
    return worldMap;
  }

  /**
   * Get the list of player buttons in the panel.
   * 
   * @return the copy of list of player button.
   */
  public List<PlayerButton> getPlayerBtnList() {
    List<PlayerButton> copy = new ArrayList<>();
    for (PlayerButton p : this.playerBtnList) {
      copy.add(p);
    }
    return copy;
  }

  /**
   * Add player button to the panel and refresh view.
   * 
   * @param id        - player id.
   * @param posX      - the column of player position.
   * @param posY      - the row of player position.
   * @param posInRoom - the player index in the room.
   */
  public void addPlayer(int id, int posX, int posY, int posInRoom) {
    if (id < 0 || posX < 0 || posY < 0 || posInRoom < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    playerBtnList.add(new PlayerButton(id, posX, posY, posInRoom));
    updatePlayerView();
  }

  /**
   * Only show the current turn player and all players in the same room on the
   * view.
   * 
   * @param roomId current room id.
   * @param model read only model.
   */
  public void onlyShowPlayersInCurrentRoom(int roomId, ReadOnlyWorld model) {
    if (roomId < 0 || model == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    for (PlayerButton pbt : playerBtnList) {
      if (pbt.getRoomId(model) == roomId) {
        pbt.makeVisible();
      } else {
        pbt.makeInvisible();
      }
    }
  }

  /**
   * helper Method to display player on the map
   */
  private void updatePlayerView() {
    for (PlayerButton player : playerBtnList) {
      worldMap.add(player);
    }
  }

  /**
   * Refresh this button to focus on key event.
   */
  public void refresh() {
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

}
