package controller;

/**
 * Represents the features that can be used to interact between controller and
 * view.
 * 
 * @author Zifei Song, Zesheng Li.
 *
 */
public interface Features {

  /**
   * Close Jframe and exit the program.
   */
  void exitProgram();

  /**
   * Add player into the world. It should both add the player into model and show
   * player button on view screen.
   * 
   * @param name    - name of the player.
   * @param id      - id of the player.
   * @param isHuman - is this player controlled by human.
   * @param bagSize - the size of bag.
   * @param roomId  - the birth room id.
   */
  void addPlayer(String name, int id, boolean isHuman, int bagSize, int roomId);

  /**
   * Display player information by the given player id.
   * 
   * @param playerId - id of the player who we want to display.
   */
  void displayPlayerInfo(int playerId);

  /**
   * Move player whose id given to the given neighbors based on the room index in
   * neighbors list.
   * 
   * @param playerId  - player's id in the world.
   * @param roomIndex - room index in the list of neighbors.
   */
  void move(int playerId, int roomIndex);

  /**
   * Player whose id given pick up item by the given item index in the room.
   * 
   * @param playerId - player's id in the world.
   * @param itemId   - item id in the player's current room.
   */
  void pick(int playerId, int itemId);

  /**
   * Target move to the next room.
   */
  void moveTarget();

  /**
   * Handle look around action. Player whose id given is doing this action.
   * 
   * @param playerId - player's id in the world.
   */
  void lookAround(int playerId);

  /**
   * Handle attack action of player whose id given. Player will use the given item
   * to do attack action.
   * 
   * @param playerId - player's id in the world.
   * @param itemId   - the id of item used by the player.
   */
  void attack(int playerId, int itemId);

  /**
   * Handle the poke eyes attack action of player whose id given.
   * 
   * @param playerId - player's id in the world.
   */
  void poke(int playerId);

  /**
   * Handle to start a new world by the given path of file and the given number of
   * maximum turn.
   * 
   * @param path - file path.
   * @param max  - maximum turn.
   */
  void startNewWorld(String path, int max);

  /**
   * Handle to start a game by the default world by the given number of maximum
   * turn.
   * 
   * @param max max number of the turns
   */
  void startCurrentWorld(int max);

  /**
   * Represents all operations when each turn start. Such as show number of the
   * turn, the current player information and current room information.
   * 
   * @param turn - current turn number.
   */
  void turnStart(int turn);

  /**
   * Handle move pet action to the room which id is given.
   * 
   * @param roomId - room id in the world.
   */
  void movePet(int roomId);

  /**
   * Display all items in the player's current room on view text area.
   * 
   * @param playerId - the current turn player's id.
   */
  void displayItemInRoom(int playerId);

  /**
   * Display all items in the player's bag.
   * 
   * @param playerId - the current turn player's id.
   */
  void displayItemInBag(int playerId);

  /**
   * Display current player information, room information, and if the pet in the
   * same room with the player.
   * 
   * @param playerId - the current turn player's id.
   */
  void showGameState(int playerId);

  /**
   * Handle next turn action. And check if the game is over.
   */
  void nextTurn();
}
