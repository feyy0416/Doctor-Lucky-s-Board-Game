package world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;

/**
 * Build the world of the Kill Doctor Lucky game.
 *
 */
public class BuildWorldModel implements BuildWorld {

  private BufferedImage image;
  private Graphics2D g2d;

  private int mapRow;
  private int mapCol;
  private int numRoom;
  private int numWeapons;
  private String petName;

  private Room tempRoom;
  private List<Room> roomList;
  private List<Player> playerList;

  private String targetName;
  private int targetHealth;
  private Piece targetCharacter;
  private Pet pet;
  private int maxTurn;
  private int turn;
  private String filePath;

  /**
   * Constructs a BuildWorld object and build the world in terms of a String array
   * of arguments that's associated with the path of mansion text file.
   * 
   * @param args command line argument that shows path of mansion.txt and the
   *             number of turns
   * @throws IOException              capture errors
   * @throws IllegalArgumentException if any argument is invalid
   */
  public BuildWorldModel(String[] args) {

    if (args.length < 2) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    FileInputStream f;
    try {
      filePath = args[0];
      f = new FileInputStream(filePath);
      maxTurn = Integer.parseInt(args[1]);
      roomList = new ArrayList<>();
      playerList = new ArrayList<>();

      InputStreamReader reader = new InputStreamReader(f);
      BufferedReader br = new BufferedReader(reader);

      String temp = "";
      temp = br.readLine();
      String[] splitStr = temp.split("\\s+");

      // map row and column
      mapRow = Integer.parseInt(splitStr[0]) * 30;
      mapCol = Integer.parseInt(splitStr[1]) * 30;

      image = new BufferedImage(mapCol, mapRow, BufferedImage.TYPE_INT_ARGB);
      g2d = (Graphics2D) image.getGraphics();
      g2d.setPaint(Color.lightGray);
      g2d.fillRect(0, 0, mapCol, mapRow);

      // doctor health and name
      temp = br.readLine();
      splitStr = temp.split("\\s+");
      targetHealth = Integer.parseInt(splitStr[0]);
      StringBuilder targetNamebd = new StringBuilder();
      for (int m = 1; m < splitStr.length; m++) {
        targetNamebd.append(splitStr[m]);
        if (m != splitStr.length - 1) {
          targetNamebd.append(" ");
        }
      }
      targetName = targetNamebd.toString();

      // pet name
      petName = br.readLine();
      pet = new TargetPet(petName);

      // number of rooms
      numRoom = Integer.parseInt(br.readLine());

      // generate target character
      targetCharacter = new TargetCharacter(targetName, targetHealth, numRoom);

      // room generated
      for (int i = 0; i < numRoom; i++) {
        temp = br.readLine().trim();
        splitStr = temp.split("\\s+");
        int ur = Integer.parseInt(splitStr[0]);
        int uc = Integer.parseInt(splitStr[1]);
        int dr = Integer.parseInt(splitStr[2]);
        int dc = Integer.parseInt(splitStr[3]);

        if (ur > mapRow || dr > mapRow || uc > mapCol || dc > mapCol) {
          throw new IllegalArgumentException("Room cannot exceed the range of map.");
        }

        StringBuilder name = new StringBuilder();
        for (int m = 4; m < splitStr.length; m++) {
          name.append(splitStr[m]);
          if (m != splitStr.length - 1) {
            name.append(" ");
          }
        }
        String nameInString = name.toString();

        // generate room objects
        tempRoom = new Room(nameInString, i, ur, uc, dr, dc);

        g2d = (Graphics2D) image.getGraphics();
        g2d.setPaint(Color.darkGray);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(uc * 30, ur * 30, (dc - uc) * 30, (dr - ur) * 30);
        g2d.setPaint(Color.gray);
        g2d.fillRect(uc * 30, ur * 30, (dc - uc) * 30, (dr - ur) * 30);

        // add text
        Font font = new Font("MV Boli", Font.BOLD, 16);
        g2d.setPaint(Color.white);
        g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawString(nameInString, uc * 30 + 5, ur * 30 + 20);
        roomList.add(tempRoom);

        turn = 1;
      }

      testOverlappedRoom();

      // create map picture
      generateMap();

      // find neighbors
      findNeighbors();

      // read for items
      temp = br.readLine();
      numWeapons = Integer.parseInt(temp);
      for (int i = 0; i < numWeapons; i++) {
        temp = br.readLine();
        splitStr = temp.split("\\s+");
        int roomId = Integer.parseInt(splitStr[0]);
        int power = Integer.parseInt(splitStr[1]);
        StringBuilder weaponNamebd = new StringBuilder();
        for (int j = 2; j < splitStr.length; j++) {
          weaponNamebd.append(splitStr[j]);
          if (j != splitStr.length - 1) {
            weaponNamebd.append(" ");
          }
        }
        Item weaponItem = new Weapon(weaponNamebd.toString(), power);
        roomList.get(roomId).addItem(weaponItem);
      }
      br.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read file content.");
    }
  }

  @Override
  public void generateMap() {
    try {
      ImageIO.write(image, "png", new File("worldBufferedImage.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read file content.");
    }
  }

  private void testOverlappedRoom() {
    for (int i = 0; i < numRoom; i++) {
      for (int j = 0; j < numRoom; j++) {
        if (i != j) {
          Space temp1 = roomList.get(i);
          Space temp2 = roomList.get(j);
          if (isOverlapped(temp1, temp2)) {
            throw new IllegalArgumentException("Rooms cannot overlap with" + "each other.");
          }

        }
      }
    }

  }

  private boolean isOverlapped(Space room1, Space room2) {
    if (room1 == null || room2 == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    int uc1 = room1.getUpCol();
    int uc2 = room2.getUpCol();
    int ur1 = room1.getUpRow();
    int ur2 = room2.getUpRow();
    int dc1 = room1.getDownCol();
    int dc2 = room2.getDownCol();
    int dr1 = room1.getDownRow();
    int dr2 = room2.getDownRow();

    if ((dr1 > ur2 && dc1 > uc2 && ur1 < dr2 && uc1 < dc2)
        || (dr1 == dr2 && dc1 == dc2 && ur1 == ur2 && uc1 == uc2)) {
      return true;
    }
    return false;
  }

  /**
   * provide the list of the room.
   * 
   * @return the list of the room
   */
  @Override
  public List<Space> getRoomList() {
    List<Space> tempList = new ArrayList<>();
    for (int i = 0; i < roomList.size(); i++) {
      tempList.add(roomList.get(i));
    }
    return tempList;
  }

  private void findNeighbors() {
    for (int i = 0; i < roomList.size(); i++) {
      for (int j = 0; j < roomList.size(); j++) {
        if (i == j) {
          continue;
        } else {
          roomList.get(i).isNeighbor(roomList.get(j));
        }
      }
    }
  }

  @Override
  public List<Player> getPlayerList() {
    List<Player> temp = new ArrayList<>();
    for (int i = 0; i < playerList.size(); i++) {
      temp.add(playerList.get(i));
    }
    return temp;
  }

  @Override
  public Piece getTargetCharacter() {
    return targetCharacter;
  }

  @Override
  public Player getCurrentPlayer(int turn) {
    if (turn < 0 || turn > maxTurn) {
      throw new IllegalArgumentException("Turn cannot be negative or greater than max turn.");
    }
    return playerList.get((turn - 1) % playerList.size());
  }

  @Override
  public Space getLocation(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    return roomList.get(player.getCurrentRoomId());
  }

  @Override
  public List<Item> getItemInRoom(int id) {
    if (id < 0 || id > roomList.size() - 1) {
      throw new IllegalArgumentException("Room id does not exist.");
    }
    List<Item> temp = new ArrayList<>();
    for (int i = 0; i < roomList.get(id).getItems().size(); i++) {
      temp.add(roomList.get(id).getItems().get(i));
    }
    return temp;
  }

  @Override
  public List<Space> getNeigList(int id) {
    if (id < 0 || id > roomList.size() - 1) {
      throw new IllegalArgumentException("Room id does not exist.");
    }
    List<Space> temp = new ArrayList<>();
    for (int i = 0; i < roomList.get(id).getNeighbors().size(); i++) {
      temp.add(roomList.get(id).getNeighbors().get(i));
    }
    return temp;
  }

  @Override
  public int findRoomId(int x, int y) {
    if (x < 0 || x > mapCol || y < 0 || y > mapRow) {
      throw new IllegalArgumentException("Room id does not exist.");
    }
    for (Room room : roomList) {
      if (room.getUpCol() * 30 <= x && room.getDownCol() * 30 >= x && room.getUpRow() * 30 <= y
          && room.getDownRow() * 30 >= y) {
        return room.getId();
      }
    }
    throw new IllegalArgumentException("Room id does not exist1.");
  }

  @Override
  public String move(int playerId, int roomId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    if (roomId < 0 || roomId > roomList.size() - 1) {
      throw new IllegalArgumentException("Room id does not exist.");
    }
    Player player = playerList.get(playerId);
    int previousRoomId = player.getCurrentRoomId();
    if (roomList.get(previousRoomId).getNeighbors().contains(roomList.get(roomId))) {
      roomList.get(previousRoomId).removePlayer(player);
      player.move(roomId);
      roomList.get(roomId).addPlayer(player);

      StringBuilder out = new StringBuilder();
      out.append(player.getName());
      out.append(" moves to ");
      out.append(roomList.get(roomId).getName());
      out.append("\n");
      return out.toString();
    } else {
      throw new IllegalArgumentException("Room selected is not a neighbor.");
    }
  }

  @Override
  public String pickItem(int playerId, int itemId) {
    // Validate parameter playerId
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    Space room = roomList.get(player.getCurrentRoomId());
    // Validate parameter itemId
    if (itemId < 0 || itemId >= room.getItems().size()) {
      throw new IllegalArgumentException("Invalid itemId.");
    }
    if (player.getBagCapacity() == player.getItemList().size()) {
      throw new IllegalArgumentException("Player cannot carry more items.");
    }
    if (room.getItems().isEmpty()) {
      throw new IllegalArgumentException("No items in current room.");
    }
    Item item = getItemInRoom(room.getId()).get(itemId);
    room.removeItem(item);
    player.pickItem(item);
    StringBuilder str = new StringBuilder();
    str.append(player.getName());
    str.append(" picks up ").append(item.getName()).append("\n");
    return str.toString();
  }

  @Override
  public void addPlayer(String name, int id, boolean isHuman, int bagCapacity, int roomId) {
    // repeated name
    if (playerList.size() > 0) {
      for (int i = 0; i < playerList.size(); i++) {
        if (playerList.get(i).getName().equals(name)) {
          throw new IllegalArgumentException("Name is taken.");
        }
      }
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (id < playerList.size()) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    if (roomId < 0 || roomId > roomList.size() - 1) {
      throw new IllegalArgumentException("Room id does not exist.");
    }
    if (bagCapacity < 0) {
      throw new IllegalArgumentException("Bag capacity cannot be negative.");
    }

    Player player = new PlayerCharacter(name, id, isHuman, bagCapacity, roomId);
    playerList.add(player);
    roomList.get(roomId).addPlayer(player);
  }

  @Override
  public String moveTarget() {
    StringBuilder str = new StringBuilder();
    targetCharacter.move();
    str.append("Target ").append(getTargetCharacter().getName()).append(" moves to" + " ")
        .append(getRoomList().get(getTargetCharacter().getCurrentRoomId()).getName()).append("\n");
    return str.toString();
  }

  @Override
  public int getMaxTurn() {
    return maxTurn;
  }

  @Override
  public String lookAround(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    Space room = roomList.get(player.getCurrentRoomId());
    StringBuilder str = new StringBuilder();
    String name = player.getName();
    String roomName = room.getName();
    str.append(name).append(" is looking around.").append("\n");
    // location
    str.append("Location: ").append(roomName).append("\n");
    // items in current room
    str.append("Items in ").append(roomName).append(": ");
    StringBuilder sb2 = new StringBuilder();
    for (int j = 0; j < room.getItems().size(); j++) {
      sb2.append(j).append(": ");
      sb2.append(room.getItems().get(j).getName());
      sb2.append("(damage:");
      sb2.append(room.getItems().get(j).getPower());
      sb2.append(")");
      if (j != room.getItems().size() - 1) {
        sb2.append(", ");
      }
    }
    if (sb2.length() == 0) {
      str.append("No items in room.");
    } else {
      str.append(sb2.toString());
    }
    str.append("\n");
    // players in current room
    str.append("Players in ").append(roomName).append(": ");
    if (room.getPlayers().size() == 0) {
      str.append("No players in the room.");
    } else {
      for (int i = 0; i < room.getPlayers().size(); i++) {
        str.append(room.getPlayers().get(i).getName());
        if (i != room.getPlayers().size() - 1) {
          str.append(" ");
        }
      }
    }
    str.append("\n");
    if (pet.getCurrentRoomId() == room.getId()) {
      str.append(petName).append(" is in the ").append(roomName).append("\n");
    }
    // neighbors
    Space neigRoom;
    str.append("Neighboring rooms: ");
    for (int i = 0; i < getNeigList(player.getCurrentRoomId()).size(); i++) {
      str.append("\n");
      neigRoom = getNeigList(player.getCurrentRoomId()).get(i);
      str.append(i);
      str.append(": ");
      str.append(neigRoom.getName());
      str.append("\n");
      if (pet.getCurrentRoomId() == neigRoom.getId()) {
        str.append(" ").append(petName).append(" is in the ").append(neigRoom.getName())
            .append("\n");
        str.append(" The pet blocks player's vision to").append(neigRoom.getName());
        continue;
      }
      // items in neighboring rooms
      str.append("  Items in ").append(neigRoom.getName()).append(": ");
      if (neigRoom.getItems().size() > 0) {
        for (int j = 0; j < neigRoom.getItems().size(); j++) {
          str.append(neigRoom.getItems().get(j).getName());
          str.append("(damage:");
          str.append(neigRoom.getItems().get(j).getPower());
          str.append(")");
          if (j != neigRoom.getItems().size() - 1) {
            str.append(", ");
          }
        }
      } else {
        str.append("No items in the room.");
      }
      str.append("\n");
      // players in neighboring rooms
      str.append("  Players in ").append(neigRoom.getName()).append(": ");
      if (neigRoom.getPlayers().size() == 0) {
        str.append("No players in the room.");
      } else {
        for (int k = 0; k < neigRoom.getPlayers().size(); k++) {
          str.append(neigRoom.getPlayers().get(k).getName());
          if (k != neigRoom.getPlayers().size() - 1) {
            str.append(" ");
          }
        }
      }
    }
    str.append("\n");
    if (getTargetCharacter().getCurrentRoomId() == room.getId()) {
      str.append("Target is in the same room with player.");
    } else {
      str.append("Target is not in the same room with player.");
    }
    str.append("\n");
    return str.toString();
  }

  @Override
  public String displayPlayerInfo(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    StringBuilder out = new StringBuilder();
    out.append("Player name: ");
    out.append(player.getName());
    out.append("\nPlayer's position: ");
    out.append(roomList.get(player.getCurrentRoomId()).getName());
    out.append("\nItems carried: ");
    if (player.getItemList().size() == 0) {
      out.append("This player has no item.");
    } else {
      for (int i = 0; i < player.getItemList().size(); i++) {
        out.append(i).append(": ");
        out.append(player.getItemList().get(i).getName());
        out.append("(damage:");
        out.append(player.getItemList().get(i).getPower());
        out.append(")");
        out.append(" ");
      }
    }
    out.append("\n");

    return out.toString();

  }

  @Override
  public String displayRoomInfo(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    if (pet.getCurrentRoomId() == player.getCurrentRoomId()) {
      StringBuilder sb = new StringBuilder();
      sb.append(getLocation(player).toString());
      sb.append(petName);
      sb.append(" is in the ");
      sb.append(getLocation(player).getName());
      sb.append("\n");
      return sb.toString();
    }
    return getLocation(player).toString();
  }

  @Override
  public int randomNum(int max, Random rand) {
    if (max < 0 || rand == null) {
      throw new IllegalArgumentException("Invalid max or rand.");
    }
    return rand.nextInt(max);

  }

  @Override
  public boolean canBeSeen(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    // return true if there are players in the same room
    if (roomList.get(player.getCurrentRoomId()).getPlayers().size() > 1) {
      return true;
    }
    // return false if there are no players in the same room and pet is in the same
    // room
    if (pet.getCurrentRoomId() == player.getCurrentRoomId()) {
      return false;
    }
    List<Space> neighbors = getNeigList(player.getCurrentRoomId());
    for (int i = 0; i < neighbors.size(); i++) {
      // return true if there are players in neighboring rooms
      if (neighbors.get(i).getPlayers().size() > 0) {
        return true;
      }
    }
    // return false if no players in current room and neighboring room
    return false;
  }

  @Override
  public String attackTarget(int playerId, int itemId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    if (itemId < 0 || itemId >= playerList.get(playerId).getItemList().size()) {
      throw new IllegalArgumentException("Invalid item id.");
    }
    Player player = playerList.get(playerId);
    Item item = playerList.get(playerId).getItemList().get(itemId);
    String playerName = player.getName();
    if (player.getCurrentRoomId() != targetCharacter.getCurrentRoomId()) {
      StringBuilder str = new StringBuilder();
      player.removeItem(item);
      str.append("Attack fails. Target is not in the same room").append("\n");
      return str.toString();
    }
    // attacks fails if player is seen by others
    if (canBeSeen(player)) {
      StringBuilder str = new StringBuilder();
      player.removeItem(item);
      str.append("Attack fails. Player is seen by others").append("\n");
      return str.toString();
    } else {
      int damage = item.getPower();
      // reduce target health
      targetCharacter.reduceHealth(damage);
      // remove item
      player.removeItem(item);
      StringBuilder str = new StringBuilder();
      str.append(playerName).append(" attacks target with ");
      str.append(item.getName()).append("\n");
      str.append("Target remaining health: ").append(targetCharacter.getHealth()).append("\n");
      return str.toString();
    }
  }

  @Override
  public String pokeTarget(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    if (player.getCurrentRoomId() != targetCharacter.getCurrentRoomId()) {
      StringBuilder str = new StringBuilder();
      str.append("Attack fails. Target is not in the same room").append("\n");
      return str.toString();
    }
    if (canBeSeen(player)) {
      StringBuilder str = new StringBuilder();
      str.append("Attack fails. Player is seen by others").append("\n");
      return str.toString();
    } else {
      String playerName = playerList.get(playerId).getName();
      targetCharacter.reduceHealth(1);
      StringBuilder str = new StringBuilder();
      str.append(playerName).append(" pokes target in eyes.").append("\n");
      str.append("Target remaining health: ").append(targetCharacter.getHealth()).append("\n");
      return str.toString();
    }
  }

  @Override
  public String movePet(int roomId) {
    if (roomId < 0 || roomId >= roomList.size()) {
      throw new IllegalArgumentException("Room does not exist.");
    }
//    if (pet.getCurrentRoomId() == roomId) {
//      throw new IllegalArgumentException("Pet cannot move to current room.");
//    }
    pet.move(roomId);
    StringBuilder str = new StringBuilder();
    str.append("Pet is moved to ").append(roomList.get(roomId).getName()).append("\n");
    return str.toString();
  }

  @Override
  public int targetHealth() {
    return targetCharacter.getHealth();
  }

  @Override
  public String displayItemInBag(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player player = playerList.get(playerId);
    List<Item> items = player.getItemList();
    StringBuilder str = new StringBuilder("Pick an item to make attempt on target's life:\n");
    for (int i = 0; i < items.size(); i++) {
      str.append(Integer.toString(i)).append(": ");
      str.append(items.get(i).getName()).append("\n");
    }
    return str.toString();

  }

  @Override
  public Pet getPet() {
    return pet;
  }

  @Override
  public void movePetFollowDfs(int startRoomId, int numMoves) {
    if (startRoomId < 0 || startRoomId >= roomList.size()) {
      throw new IllegalArgumentException("Room does not exist.");
    }
    if (numMoves < 0) {
      throw new IllegalArgumentException("Moves cannot be negative.");
    }
    List<Integer> path = dfsPath(startRoomId);
    path.remove(0);
    movePet(path.get(numMoves % path.size()));

  }

  /**
   * Helper method to return the DFS path starting at Room with id equals to
   * roomId.
   * 
   * @param roomId id of starting room
   * @return an list with DFS path
   * @throws IllegalArgumentException if room id is invalid
   */
  private List<Integer> dfsPath(int roomId) {
    if (roomId < 0 || roomId >= roomList.size()) {
      throw new IllegalArgumentException("Room does not exist.");
    }
    Space room = roomList.get(roomId);
    List<Integer> path = new ArrayList<>();
    Stack<Integer> route = new Stack<>();
    path.add(roomId);
    route.push(roomId);
    while (!route.isEmpty()) {
      int nextRoomId = Integer.MAX_VALUE;
      for (int i = 0; i < room.getNeighbors().size(); i++) {
        int neighborId = room.getNeighbors().get(i).getId();
        if (path.contains(neighborId)) {
          continue;
        }
        if (neighborId < nextRoomId) {
          nextRoomId = neighborId;
        }
      }
      if (nextRoomId != Integer.MAX_VALUE) {
        route.push(nextRoomId);
        path.add(nextRoomId);
        room = roomList.get(nextRoomId);
      } else {
        route.pop();
        if (!route.isEmpty()) {
          path.add(route.peek());
          room = roomList.get(route.peek());
        }
      }
    }
    return path;
  }

  @Override
  public boolean isGameOver() {
    return targetCharacter.getHealth() <= 0 || turn > maxTurn;
  }

  @Override
  public String computerCommand(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    if ((!canBeSeen(playerList.get(playerId))) && (getTargetCharacter()
        .getCurrentRoomId() == playerList.get(playerId).getCurrentRoomId())) {
      if (playerList.get(playerId).getItemList().size() > 0) {
        return "attack";
      } else {
        return "poke";
      }
    } else {
      List<String> commandList = new ArrayList<>();
      commandList.add("look");
      commandList.add("move");
      commandList.add("pick");
      Random random = new Random();
      String cmd = commandList.get(randomNum(commandList.size(), random));
      if ("pick".equals(cmd)
          && roomList.get(playerList.get(playerId).getCurrentRoomId()).getItems().size() == 0) {
        cmd = commandList.get(randomNum(commandList.size() - 1, random));
      }
      return cmd;
    }
  }

  @Override
  public BufferedImage getMap() {
    return image;
  }

  @Override
  public String displayItemInRoom(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Space room = roomList.get(playerList.get(playerId).getCurrentRoomId());
    StringBuilder sb = new StringBuilder(
        "Pick an item below by pressing corresponding number key:\n");
    for (int i = 0; i < room.getItems().size(); i++) {
      sb.append(Integer.toString(i)).append(": ");
      sb.append(room.getItems().get(i).getName()).append("\n");
    }
    if (room.getItems().size() == 0) {
      sb.append("No items in current room.\n");
    }
    return sb.toString();
  }

  @Override
  public int getTurn() {
    final int res = this.turn;
    return res;
  }

  @Override
  public void nextTurn() {
    this.turn++;
  }

  @Override
  public String displayGameStates(int playerId) {
    if (playerId < 0 || playerId >= playerList.size()) {
      throw new IllegalArgumentException("Invalid input.");
    }

    StringBuilder out = new StringBuilder();
    out.append("Turn no.");
    out.append(Integer.toString(turn));
    out.append("\n===================");
    out.append("Player's information: ");
    out.append("\n");
    out.append(displayPlayerInfo(playerId));
    out.append("===================");
    out.append("Room's information: ");
    out.append("\n");
    out.append(displayRoomInfo(playerId));
    out.append("===================");

    return out.toString();
  }

  @Override
  public void setMaxTurn(int max) {
    if (max < 0) {
      throw new IllegalArgumentException("Max cannot be negative.");
    }
    this.maxTurn = max;

  }

  @Override
  public String getFilePath() {
    final String res = this.filePath;
    return res;
  }

  @Override
  public Space findRoomByPlayerId(int playerId) {
    if (playerId < 0 || playerId > playerList.size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    Player p = this.playerList.get(playerId);
    int roomId = p.getCurrentRoomId();
    final Space r = this.roomList.get(roomId);
    return r;
  }

}
