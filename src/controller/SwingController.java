package controller;

import java.util.Random;
import view.SwingWorldView;
import view.WorldView;
import world.BuildWorld;
import world.BuildWorldModel;

/**
 * Controller built with command design pattern that takes user's inputs and
 * give responses to the user by executing commands using the command classes
 * and model.
 *
 */
public class SwingController implements WorldController, Features {

  private BuildWorld model;
  private WorldView view;

  /**
   * Construct a controller with model and view.
   * 
   */
  public SwingController(BuildWorld model, WorldView view) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void setView(WorldView v) {
    if (v == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    view = v;
    // give the feature call backs to the view
    view.setFeatures(this);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
    view.closeFrame();
  }

  @Override
  public void addPlayer(String name, int id, boolean isHuman, int bagSize, int roomId) {
    if ("".equals(name) || name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (id < 0) {
      throw new IllegalArgumentException("ID is nagative.");
    }
    if (bagSize < 0) {
      throw new IllegalArgumentException("Bag size is nagative.");
    }
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id is nagative.");
    }
    model.addPlayer(name, id, isHuman, bagSize, roomId);
    int posX = model.getRoomList().get(roomId).getUpCol();
    int posY = model.getRoomList().get(roomId).getUpRow();
    int posInRoom = model.getRoomList().get(roomId).getPlayers().size();
    view.addPlayerBtn(id, posX, posY, posInRoom);
  }

  @Override
  public void displayPlayerInfo(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    WorldCommand cmd = new DisplayPlayerInfo(playerId, view);
    cmd.execute(model);
  }

  @Override
  public void move(int playerId, int roomIndex) {
    if (playerId < 0 || roomIndex < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    WorldCommand cmd = new Move(playerId, roomIndex, view);
    cmd.execute(model);
  }

  @Override
  public void pick(int playerId, int itemId) {
    if (playerId < 0 || itemId < 0) {
      throw new IllegalArgumentException("Invalid player or item id.");
    }
    WorldCommand cmd = new PickItem(playerId, itemId, view);
    cmd.execute(model);
  }

  @Override
  public void moveTarget() {
    model.moveTarget();
    int x = model.getRoomList().get(model.getTargetCharacter().getCurrentRoomId()).getUpCol();
    int y = model.getRoomList().get(model.getTargetCharacter().getCurrentRoomId()).getUpRow();
    view.updateTargetBtn(x, y);
  }

  @Override
  public void lookAround(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    WorldCommand cmd = new LookAround(playerId, view);
    cmd.execute(model);
  }

  @Override
  public void attack(int playerId, int itemId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id canot be negative.");
    }
    if (itemId < 0) {
      throw new IllegalArgumentException("Item id canot be negative.");
    }
    WorldCommand cmd = new Attack(playerId, itemId, view);
    cmd.execute(model);
  }

  @Override
  public void movePet(int roomId) {
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id is nagative.");
    }
    model.movePet(roomId);
    WorldCommand cmd = new MovePet(roomId, view);
    cmd.execute(model);
  }

  @Override
  public void startNewWorld(String path, int max) {
    if ("".equals(path) || path == null || max <= 0) {
      throw new IllegalArgumentException("Invalid input.");
    }

    view.closeFrame();
    String[] args = { path, Integer.toString(max) };

    model = new BuildWorldModel(args);
    view = new SwingWorldView(model);
    this.setView(view);
  }

  @Override
  public void startCurrentWorld(int max) {
    if (max < 0) {
      throw new IllegalArgumentException("Invalid input.");
    }
    view.closeFrame();
    String path = model.getFilePath();
    String[] args = { path, Integer.toString(max) };
    model = new BuildWorldModel(args);
    view = new SwingWorldView(model);
    this.setView(view);
  }

  @Override
  public void poke(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id cannot be negative.");
    }
    WorldCommand cmd = new Attack(playerId, view);
    cmd.execute(model);
  }

  @Override
  public void displayItemInRoom(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id cannot be negative.");
    }
    String str = model.displayItemInRoom(playerId);
    view.setTextToWindow(str);
    // if no item in the room. Set action as no item.
    if (model.findRoomByPlayerId(playerId).getItems().isEmpty()) {
      view.setAction("itemDone");
    }
  }

  @Override
  public void displayItemInBag(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id cannot be negative.");
    }
    String str = model.displayItemInBag(playerId);
    view.setTextToWindow(str);

  }

  @Override
  public void nextTurn() {
    model.nextTurn();
    int turn = model.getTurn();
    // check if game over.
    if (model.isGameOver()) {
      if (model.targetHealth() <= 0) {
        String winner = model.getCurrentPlayer(turn - 1).getName();
        view.setTextToWindow(String.format("Game over! Winner is %s.", winner));
      } else {
        view.setTextToWindow("Game over! Target ran away!");
      }
      view.setAction("end");
    } else {
      this.moveTarget();
      this.turnStart(turn);
    }
  }

  @Override
  public void showGameState(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id is nagative.");
    }
    String hint = String.format("Game State:\n%s", model.displayGameStates(playerId));
    view.setTextToWindow(hint);
  }

  private void doComputerTurn(int playerId) {
    if (playerId < 0) {
      throw new IllegalArgumentException("Player id is nagative.");
    }
    String cmd = model.computerCommand(playerId);

    if ("attack".equals(cmd)) {
      attack(playerId, 0); // The first item is the most damage item.
    }
    if ("poke".equals(cmd)) {
      poke(playerId);
    }
    if ("look".equals(cmd)) {
      lookAround(playerId);
    }
    if ("move".equals(cmd)) {
      Random random = new Random();
      int roomId = model.getPlayerList().get(playerId).getCurrentRoomId();
      int targetRoomId = model.getRoomList().get(roomId).getNeighbors()
          .get(model.randomNum(model.getRoomList().get(roomId).getNeighbors().size(), random))
          .getId();
      move(playerId, targetRoomId);
    }
    if ("pick".equals(cmd)) {
      pick(playerId, 0);
    }
    view.setAction("computerDone");
  }

  @Override
  public void turnStart(int turn) {
    if (turn < 0) {
      throw new IllegalArgumentException("Turn is nagative.");
    }
    int playerId = model.getCurrentPlayer(turn).getId();
    view.setCurrentPlayer(playerId);
    
    if (!model.getPlayerList().get(playerId).getIsHuman()) {
      this.doComputerTurn(playerId);
      view.appendTextToWindow(String.format("\nThis is Turn: No.%d", turn));
    } else {
      // human turn
      // show game state.
      this.showGameState(playerId);
      view.appendTextToWindow(optionsHint());
    }
    // hide other players.
    int roomId = model.getPlayerList().get(playerId).getCurrentRoomId();
    view.onlyShowPlayersInCurrentRoom(roomId);
    
    
  }

  /**
   * Helper to show what operations offer for user to interact with the world.
   * 
   * @return a String formatting with user's operations.
   */
  private String optionsHint() {
    StringBuilder res = new StringBuilder();
    res.append("Actions Hint:\n");
    res.append("Move: click the room you want to move to, and press \"m\" key.\n");
    res.append("Attack: press \"a\" key and use digit keys to choose item for attacking.\n");
    res.append("Look Around: press \"l\" key to look\n");
    res.append("Move Pet: click the room you want to move pet to. Press \"p\" key to move pet.\n");
    return res.toString();
  }
}
