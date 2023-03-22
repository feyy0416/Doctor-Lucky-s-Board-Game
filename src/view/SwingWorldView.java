package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import controller.Features;
import world.BuildWorld;
import world.ReadOnlyWorld;
import world.ReadOnlyWorldImpl;

/**
 * A Java Swing view to represents a view to display Kill Doctor Lucky game
 * world. Users can interact with game by the given features and get feedback on
 * view.
 * 
 * @author Zifei Song.
 *
 */
public class SwingWorldView extends JFrame implements WorldView {
  private static final long serialVersionUID = -8673007700382839632L;
  private WelcomePanel welcomePanel;
  private AddPlayerPanel addPlayerPanel;
  private WorldPanel worldPanel;
  private JMenuBar worldMenu;
  // read only world.
  private ReadOnlyWorld readModel;
  // menu elements
  private JMenu option;
  private JMenuItem exit;
  private JMenuItem startNewWorld;
  private JMenuItem startCurrentWorld;
  // world text area
  private JTextArea textWindow;
  // world map
  private JLabel worldMap;
  private TargetButton target;
  // how many players in the game.
  private int numPlayer;
  // current turn player.
  private int curPlayerId;
  // mouse position.
  private int xCoord;
  private int yCoord;
  // action message.
  private String action;

  /**
   * Constructor of Swing View.
   * 
   * @param m - the model which using for initialing read only model.
   */
  public SwingWorldView(BuildWorld m) {
    super("Doctor Lucky Game");
    this.xCoord = -1;
    this.yCoord = -1;
    this.readModel = new ReadOnlyWorldImpl(m);
    welcomePanel = new WelcomePanel();
    worldPanel = new WorldPanel(this.readModel);
    addPlayerPanel = new AddPlayerPanel();
    textWindow = worldPanel.getTextWindow();
    worldMap = worldPanel.getWorldMap();
    worldPanel.refresh();
    // add target
    target = new TargetButton(m.getRoomList().get(0).getUpCol(), m.getRoomList().get(0).getUpRow(),
        new ReadOnlyWorldImpl(m));
    // store how many players in the world.
    this.numPlayer = 0;
    curPlayerId = 0;
    action = "start";
    initialSetting();
  }

  /**
   * Helper to initial all setting in the view.
   */
  private void initialSetting() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // size
    this.setSize(1200, 800);
    // initial menu bar.
    this.initialJmenu();
    this.add(welcomePanel);
    // display
    this.setVisible(true);
    // displayWorldPanel();
    this.switchPanel("welcome");

    worldMap.add(target);
  }

  /**
   * Helper to initial JMenu in the view.
   */
  private void initialJmenu() {
    this.setJMenuBar(createMenuBar());
  }

  /**
   * Helper to initial JMenuBar in the view.
   * 
   * @return JMenuBar in the view.
   */
  private JMenuBar createMenuBar() {
    worldMenu = new JMenuBar();
    option = new JMenu("Options");

    startNewWorld = new JMenuItem("Open New World");
    startCurrentWorld = new JMenuItem("Current World");
    exit = new JMenuItem("Quit");

    worldMenu.add(option);
    option.add(startNewWorld);
    option.add(startCurrentWorld);
    option.add(exit);
    return worldMenu;
  }

  /**
   * Helper to do browse file action if user want to start the new world.
   * 
   * @param f - Features in the view.
   */
  private void browseFiles(Features f) {
    if (f == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    final JFileChooser fileChooser = new JFileChooser();

    int returnVal = fileChooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      int max = askMaxNumOfTurn();

      String path = fileChooser.getSelectedFile().getPath();
      // handle error file.
      try {
        f.startNewWorld(path, max);
      } catch (Exception err) {
        JOptionPane.showMessageDialog(null, "The given file is not valid file.");
        f.exitProgram();
      }
    }
  }

  /**
   * Helper to ask player what is the maximum turn.
   * 
   * @return the maximun turn.
   */
  private int askMaxNumOfTurn() {
    String maxTurn = JOptionPane.showInputDialog("Please enter the maximum turn.");
    int res;
    while (true) {
      // check if the given String can be converted to int.
      try {
        res = Integer.parseInt(maxTurn);
        if (res > 0) {
          break;
        } else {
          throw new IllegalArgumentException("The given input is not positive.");
        }
      } catch (Exception e) {
        maxTurn = JOptionPane.showInputDialog("Please give a positive number.");
      }
    }
    return res;
  }

  @Override
  public void addPlayerBtn(int id, int posX, int posY, int posInRoom) {
    if (id < 0 || posX < 0 || posY < 0 || posInRoom < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    worldPanel.addPlayer(id, posX, posY, posInRoom);
  }

  @Override
  public void updatePlayerBtn(int id, int posX, int posY, int posInRoom) {
    if (id < 0 || posX < 0 || posY < 0 || posInRoom < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    PlayerButton playerButton = worldPanel.getPlayerBtnList().get(id);
    playerButton.move(posX, posY, posInRoom);
    worldMap.revalidate();
    worldMap.repaint();

  }

  @Override
  public void setTextToWindow(String str) {
    if (str == null || "".equals(str)) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.textWindow.setText(str);
  }

  @Override
  public void setFeatures(Features f) {
    if (f == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    
    startNewWorld.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browseFiles(f);
      }
    });

    startCurrentWorld.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int max = askMaxNumOfTurn();
        f.startCurrentWorld(max);
      }
    });

    exit.addActionListener(l -> f.exitProgram());

    worldMap.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (action == "wait") {
          xCoord = e.getX();
          yCoord = e.getY();
          try {
            int roomId = readModel.findRoomId(xCoord, yCoord);
            textWindow.setText(
                String.format("Room selected: %s.", readModel.getRoomList().get(roomId).getName()));
          } catch (IllegalArgumentException error) {
            textWindow.setText("No room at this space.");
          }
        }
      }
    });

    // add click listener to welcome panel, move to next panel.
    welcomePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        switchPanel("add player");
      }
    });

    // add button action to add player panel.
    JButton addPlayerButton = addPlayerPanel.getAddButton();
    JButton finishButton = addPlayerPanel.getFinishButton();

    addPlayerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String playerName = addPlayerPanel.getPlayerName();
          boolean isHuman = addPlayerPanel.getIsHuman();
          int roomId = convertToRoomId(addPlayerPanel.getRoomName());
          int bagSize = addPlayerPanel.getBagCapacity();
          f.addPlayer(playerName, numPlayer, isHuman, bagSize, roomId);
          numPlayer++;
          // if add successful, show prompt and clear text field.
          JOptionPane.showMessageDialog(null,
              String.format("Add Player %s successful!", playerName));
          addPlayerPanel.clearInputTextFields();
        } catch (IllegalArgumentException err) {
          StringBuilder hint = new StringBuilder();
          hint.append("Please check the input values are valid.\n");
          hint.append("This error cause by the following reasons:\n");
          hint.append("Player's name exist.\n");
          hint.append("Bag size is not a positive number\n");
          hint.append("The given room name is not in the world.");
          JOptionPane.showMessageDialog(null, hint.toString());
        }
      }
    });

    finishButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switchPanel("world");
        // show game start info.
        setTextToWindow("Pressed Eneter to Start the Game.");
        for (PlayerButton btn : worldPanel.getPlayerBtnList()) {
          btn.setEnabled(true);
          btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              if (!"start".equals(action)) {
                f.displayPlayerInfo(btn.getId());
              }
              worldPanel.refresh();
            }
          });
        }
      }
    });

    // add listener to world panel.
    worldPanel.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 32) {
          if ("look".equals(action) || "move pet".equals(action) || "attack".equals(action)
              || "attackDone".equals(action) || "itemDone".equals(action)
              || "computerDone".equals(action)) {
            action = "wait";
            f.nextTurn();
          }
        }

        if (action == "wait") {
          // test look around
          if (e.getKeyChar() == 'l') {
            f.lookAround(curPlayerId);
            action = "look";
          }
          // pick an item
          if (e.getKeyChar() == 'i') {
            action = "item";
            f.displayItemInRoom(curPlayerId);
          }
          // attack target
          if (e.getKeyChar() == 'a') {
            action = "attack";
            if (!readModel.hasItem(curPlayerId)) {
              f.poke(curPlayerId);
            } else {
              f.displayItemInBag(curPlayerId);
            }
          }
          // move pet
          if (e.getKeyChar() == 'p') {
            try {
              f.movePet(readModel.getRoomList().get(readModel.findRoomId(xCoord, yCoord)).getId());
              action = "move pet";
            } catch (IllegalArgumentException err) {
              JOptionPane.showMessageDialog(null, "Select position is not a room.");
            }
          }
          // move
          if (e.getKeyChar() == 'm') {
            try {
              f.move(curPlayerId, readModel.getRoomList().get(readModel.findRoomId(xCoord, yCoord)).getId());
              xCoord = -1;
              yCoord = -1;
              f.nextTurn();
            } catch (IllegalArgumentException err) {
              JOptionPane.showMessageDialog(null, "Select position is not a neighbor.");
            }
          }
        }

        if ("item".equals(action) || "attack".equals(action)) {
          if (e.getKeyChar() == '0') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 0);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 0);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '1') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 1);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 1);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '2') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 2);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 2);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '3') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 3);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 3);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '4') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 4);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 4);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '5') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 5);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 5);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '6') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 6);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 6);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '7') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 7);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 7);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '8') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 8);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 8);
              action = "itemDone";
            }
          }
          if (e.getKeyChar() == '9') {
            if ("attack".equals(action)) {
              f.attack(curPlayerId, 9);
              action = "attackDone";
            } else if ("item".equals(action)) {
              f.pick(curPlayerId, 9);
              action = "itemDone";
            }
          }
        }
        // enter.
        if (e.getKeyCode() == 10 && "start".equals(action)) {
          action = "wait";
          f.turnStart(1);
        }
      }
    });

  }

  /**
   * Helper method to switch between panels
   * 
   * @param panelName target panel name
   */
  private void switchPanel(String panelName) {
    if (panelName == null || "".equals(panelName)) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.getContentPane().removeAll();
    switch (panelName) {
      case "welcome":
        this.displayWelcomePanel();
        break;
      case "add player":
        this.displayAddPlayerPanel();
        break;
      case "world":
        this.displayWorldPanel();
        break;
      default:
        break;
    }
  }

  /**
   * Helper method to display welcome panel
   */
  private void displayWelcomePanel() {
    this.add(welcomePanel);
    this.validate();
  }

  /**
   * Helper method to display add player panel
   */
  private void displayAddPlayerPanel() {
    String hint = String.format("The following rooms in the world.\n%s", readModel.getAllRoomsName());
    addPlayerPanel.setTextArea(hint);
    this.add(addPlayerPanel);
    this.validate();
  }

  /**
   * Helper method to display world panel
   */
  private void displayWorldPanel() {
    add(worldPanel);
    validate();

    // refresh key event.
    worldPanel.refresh();
  }

  /**
   * Helper method to get room id in the map by the given room name;
   * 
   * @param represents room name.
   */
  private int convertToRoomId(String roomName) {
    if ("".equals(roomName) || roomName == null) {
      throw new IllegalArgumentException("Invalid input.");
    }

    for (int i = 0; i < readModel.getRoomList().size(); i++) {
      if (readModel.getRoomList().get(i).getName().equals(roomName)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void updateTargetBtn(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    target.move(x, y);
    worldMap.revalidate();
    worldMap.repaint();
  }

  @Override
  public void setCurrentPlayer(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.curPlayerId = playerId;
  }

  @Override
  public void onlyShowPlayersInCurrentRoom(int roomId) {
    if (roomId < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    worldPanel.onlyShowPlayersInCurrentRoom(roomId, readModel);
  }

  @Override
  public void setAction(String action) {
    if (action == null || "".equals(action)) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.action = action;
  }

  @Override
  public void updateOldRoom(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    for (PlayerButton pbt : worldPanel.getPlayerBtnList()) {
      if (pbt.getId() != playerId) {
        PlayerButton movePbt = worldPanel.getPlayerBtnList().get(playerId);
        if (pbt.getPosX() == movePbt.getPosX() && pbt.getPosY() == movePbt.getPosY()
            && pbt.getPosInRoom() > movePbt.getPosInRoom()) {
          pbt.setPosInRoom(pbt.getPosInRoom() - 1);
        }
      }
    }
    worldMap.revalidate();
    worldMap.repaint();
  }

  @Override
  public void closeFrame() {
    this.dispose();
  }

  @Override
  public void appendTextToWindow(String str) {
    if (str == null || "".equals(str)) {
      throw new IllegalArgumentException("Invalid input.");
    }
    this.textWindow.append(str);
  }

}
