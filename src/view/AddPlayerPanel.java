package view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Represents the panel for user to add player into the game.
 * 
 * @author Zifei Song, Zesheng Li.
 *
 */
public class AddPlayerPanel extends JPanel {

  private static final long serialVersionUID = 8152431565274127120L;
  private static final String NAME_HINT = "Name";
  private static final String TITLE = "Enter player's information";
  private static final String BAG_HINT = "Bag Capacity";
  private static final String BIRTH_ROOM_HINT = "Birth Room";
  private static final String CONTROLLED_HINT = "Is this player controlled by computer?";

  private JLabel title;
  private JLabel nameHint;
  private JTextField nameInput;
  private JLabel bagHint;
  private JTextField bagInput;
  private JLabel birthRoomHint;
  private JTextField birthInput;
  private JCheckBox controlled;
  private JTextArea roomsName;
  private JButton addPlayer;
  private JButton finish;

  /**
   * Constructor of add player panel.
   */
  public AddPlayerPanel() {
    title = new JLabel(TITLE);
    nameHint = new JLabel(NAME_HINT);
    nameInput = new JTextField(10);
    bagHint = new JLabel(BAG_HINT);
    bagInput = new JTextField(10);
    birthRoomHint = new JLabel(BIRTH_ROOM_HINT);
    birthInput = new JTextField(10);
    controlled = new JCheckBox(CONTROLLED_HINT);
    roomsName = new JTextArea();
    roomsName.setLineWrap(true);
    roomsName.setSize(300, 500);
    roomsName.setEditable(false);
    addPlayer = new JButton("Add");
    finish = new JButton("Finish");
    this.initialLayout();
  }

  /**
   * Helper to layout all elements on the panel.
   */
  private void initialLayout() {
    // initial layout type.
    SpringLayout layout = new SpringLayout();
    this.setLayout(layout);

    // Set title.
    title.setFont(new Font("MV Boli", Font.PLAIN, 30));
    this.add(title);

    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER,
        this);
    layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, this);

    // Add name component.
    this.add(nameHint);
    this.add(nameInput);

    // Adjust position.
    layout.putConstraint(SpringLayout.EAST, nameHint, -50, SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, nameHint, 5, SpringLayout.SOUTH, title);

    layout.putConstraint(SpringLayout.WEST, nameInput, 10, SpringLayout.EAST, nameHint);
    layout.putConstraint(SpringLayout.NORTH, nameInput, 5, SpringLayout.SOUTH, title);

    // Add bag component.
    this.add(bagHint);
    this.add(bagInput);

    layout.putConstraint(SpringLayout.EAST, bagHint, -50, SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, bagHint, 5, SpringLayout.SOUTH, nameInput);

    layout.putConstraint(SpringLayout.WEST, bagInput, 10, SpringLayout.EAST, bagHint);
    layout.putConstraint(SpringLayout.NORTH, bagInput, 5, SpringLayout.SOUTH, nameInput);

    // Add birth room component.
    this.add(birthRoomHint);
    this.add(birthInput);

    layout.putConstraint(SpringLayout.EAST, birthRoomHint, -50, SpringLayout.HORIZONTAL_CENTER,
        this);
    layout.putConstraint(SpringLayout.NORTH, birthRoomHint, 5, SpringLayout.SOUTH, bagInput);

    layout.putConstraint(SpringLayout.WEST, birthInput, 10, SpringLayout.EAST, birthRoomHint);
    layout.putConstraint(SpringLayout.NORTH, birthInput, 5, SpringLayout.SOUTH, bagInput);

    // Add computer-controlled check box.
    this.add(controlled);
    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, controlled, 0,
        SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, controlled, 5, SpringLayout.SOUTH, birthInput);

    // Add buttons.
    this.add(addPlayer);
    this.add(finish);

    layout.putConstraint(SpringLayout.EAST, finish, 0, SpringLayout.EAST, controlled);
    layout.putConstraint(SpringLayout.NORTH, finish, 30, SpringLayout.SOUTH, controlled);

    layout.putConstraint(SpringLayout.EAST, addPlayer, 0, SpringLayout.WEST, finish);
    layout.putConstraint(SpringLayout.NORTH, addPlayer, 30, SpringLayout.SOUTH, controlled);

    // Add Text Area to show rooms' name in the world.
    this.add(roomsName);
    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, roomsName, 0,
        SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, roomsName, 10, SpringLayout.SOUTH, addPlayer);
  }

  /**
   * Get input player's name.
   * 
   * @return input of player's name.
   */
  public String getPlayerName() {
    final String name = nameInput.getText();
    return name;
  }

  /**
   * Get input of bag size.
   * 
   * @return the size of player's bag.
   * @throws NumberFormatException if input cannot be convert to integer.
   */
  public int getBagCapacity() throws NumberFormatException {
    final int res = Integer.parseInt(bagInput.getText());
    return res;
  }

  /**
   * Get input of the birth room's name.
   * 
   * @return the name of room where player birth.
   */
  public String getRoomName() {
    final String res = birthInput.getText();
    return res;
  }

  /**
   * Get the check box if the player is controlled by human.
   * 
   * @return true if player is controlled by human.
   */
  public boolean getIsHuman() {
    final boolean res = !controlled.isSelected();
    return res;
  }

  /**
   * Get the Add Button in this panel.
   * 
   * @return add button.
   */
  public JButton getAddButton() {
    return addPlayer;
  }

  /**
   * Get the Finish Button in this panel.
   * 
   * @return finish button.
   */
  public JButton getFinishButton() {
    return finish;
  }

  /**
   * Set the text area to show the given string.
   * @param src - the string we want to display.
   */
  public void setTextArea(String src) {
    if (src == null || "".equals(src)) {
      throw new IllegalArgumentException("Invalid arguments"); 
    }
    this.roomsName.setText(src);
  }

  /**
   * Clear all input contents in text fields.
   */
  public void clearInputTextFields() {
    nameInput.setText("");
    bagInput.setText("");
    birthInput.setText("");
  }

}
