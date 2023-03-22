package controllertest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import world.BuildWorld;
import world.Item;
import world.Pet;
import world.Piece;
import world.Player;
import world.PlayerCharacter;
import world.Room;
import world.Space;
import world.TargetCharacter;
import world.Weapon;

/**
 * Mock model that used to test SwingController class.
 *
 */
public class MockModel1 implements BuildWorld {
  
  private StringBuilder log;
  private final int uniqueCode;
  private int maxTurn;
  private StringBuffer out;
  
  /**
   * Mock model that is used to test controller.
   * 
   * @param log test input
   * @param uniqueCode a unique code
   * @param maxTurn max turn of game
   * @param out output
   * @throws IllegalArgumentException if log is null or maxTurn is negative
   */
  public MockModel1(StringBuilder log, int uniqueCode, int maxTurn, StringBuffer out) {
    if (log == null) {
      throw new IllegalArgumentException("log cannot be null.");
    }
    if (maxTurn < 0) {
      throw new IllegalArgumentException("max turn has to be positive.");
    }
    this.log = log;
    this.uniqueCode = uniqueCode;
    this.maxTurn = maxTurn;
    this.out = out;
  }

  @Override
  public List<Space> getRoomList() {
    List<Space> list = new ArrayList<>();
    list.add(new Room("B", 0, 0, 0, 1, 1));
    return list;
  }

  @Override
  public Piece getTargetCharacter() {
    Piece target = new TargetCharacter("T", 100, 20);
    return target;
  }

  @Override
  public String move(int playerId, int roomId) {
    log.append("Player id: ").append(playerId).append(" ");
    log.append("Target room id: ").append(roomId);
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public String pickItem(int playerId, int itemId) {
    log.append("Player's id ").append(playerId);
    log.append(" Item's id: ").append(itemId);
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public void addPlayer(String name, int id, boolean isHuman, int bagCapacity, int roomId) {
    log.append("mode => Play name: ").append(name);
    log.append(" id: ").append(Integer.toString(id));
    if (isHuman) {
      log.append(" type : human").append(name);
    } else {
      log.append(" type : computer").append(name);
    }
    log.append(" bag size:").append(Integer.toString(bagCapacity));
    log.append(" Birth room id: ").append(Integer.toString(roomId));
  }

  @Override
  public String moveTarget() {
    log.append("target moves to next room. ");
    return "";
  }

  @Override
  public List<Player> getPlayerList() {
    List<Player> list = new ArrayList<>();
    list.add(new PlayerCharacter("A", 0, true, 0, 0));
    return list;
  }

  @Override
  public int getMaxTurn() {
    return maxTurn;
  }

  @Override
  public void generateMap() {
    log.append("map is created. ");
  }

  @Override
  public Player getCurrentPlayer(int turn) {
    Player player = new PlayerCharacter("A", 0, true, 5, 0);
    return player;
  }

  @Override
  public Space getLocation(Player player) {
    Space space = new Room("B", 0, 0, 0, 1, 1);
    return space;
  }

  @Override
  public List<Item> getItemInRoom(int id) {
    Item knife = new Weapon("knife", 2);
    Item shoes = new Weapon("shoes", 2);
    List<Item> list = new ArrayList<>();
    list.add(knife);
    list.add(shoes);
    return list;
    
  }

  @Override
  public List<Space> getNeigList(int id) {
    List<Space> list = new ArrayList<>();
    list.add(new Room("B", 0, 0, 0, 1, 1));
    list.add(new Room("B", 1, 1, 1, 2, 2));
    
    return list;
  }

  @Override
  public String lookAround(int playerId) {
    log.append("Player is looking around. ");
    log.append(" Player id: ").append(Integer.toString(playerId));
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public String displayPlayerInfo(int playerId) {
    log.append("Display player's info. ");
    return "";
  }

  @Override
  public String displayRoomInfo(int playerId) {
    log.append("Display room's info. ");
    return "";
  }

  @Override
  public int randomNum(int max, Random rand) {
    return 0;
  }

  @Override
  public String attackTarget(int playerId, int itemId) {
    log.append("Player trys to attack target. ");
    log.append(" Player id: ").append(Integer.toString(playerId));
    log.append(" Item id: ").append(Integer.toString(itemId));
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public String pokeTarget(int playerId) {
    log.append("Player poke target in eyes. ");
    log.append(" Player id: ").append(Integer.toString(playerId));
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public String movePet(int roomId) {
    log.append("Player moves the pet. ");
    out.append(Integer.toString(uniqueCode));
    return Integer.toString(uniqueCode);
  }

  @Override
  public int targetHealth() {
    log.append(" check target health ");
    return 20;
  }

  @Override
  public String displayItemInBag(int playerId) {
    return null;
  }

  @Override
  public Pet getPet() {
    return null;
  }

  @Override
  public boolean canBeSeen(Player player) {
    return false;
  }

  @Override
  public void movePetFollowDfs(int startRoomId, int numMoves) {
    return;  
  }

  @Override
  public boolean isGameOver() {
    log.append(" Check if game is over ");
    return false;
  }

  @Override
  public String computerCommand(int playerId) {
    return null;
  }

  @Override
  public BufferedImage getMap() {
    return null;
  }

  @Override
  public int findRoomId(int x, int y) {
    return 0;
  }

  @Override
  public String displayItemInRoom(int playerId) {
    return null;
  }

  @Override
  public int getTurn() {
    return 0;
  }

  @Override
  public void nextTurn() {
    log.append("Game goes to next turn");
  }

  @Override
  public String displayGameStates(int playerId) {
    log.append("Display games states for player: ").append(Integer.toString(playerId));
    log.append(Integer.toBinaryString(uniqueCode));
    return Integer.toBinaryString(uniqueCode);
  }

  @Override
  public void setMaxTurn(int max) {

  }

  @Override
  public String getFilePath() {
    return null;
  }

  @Override
  public Space findRoomByPlayerId(int playerId) {
    return new Room("a", 0, 0, 1, 1, 5);
  }

}
