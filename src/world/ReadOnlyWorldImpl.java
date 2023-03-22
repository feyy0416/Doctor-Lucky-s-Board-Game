package world;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class ReadOnlyWorldImpl implements ReadOnlyWorld{
  private BuildWorld model;
  
  public ReadOnlyWorldImpl(BuildWorld model) {
    if (model == null) {
      throw new IllegalArgumentException("The model is null.");
    }
    this.model = model;
  }

  @Override
  public List<Player> getPlayerList() {
    return model.getPlayerList();
  }

  @Override
  public List<Space> getRoomList() {
    return model.getRoomList();
  }

  @Override
  public int findRoomId(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid x and y.");
    }   
    return model.findRoomId(x, y);
  }

  @Override
  public Boolean hasItem(int playerId) {
    if (playerId < 0 || playerId > model.getPlayerList().size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    if (model.getPlayerList().get(playerId).getItemList().size() == 0) {
      return false;
    }
    return true;
  }

  @Override
  public BufferedImage getMap() {
    return model.getMap();
  }
  
  @Override
  public String computerAction(int playerId) {
    if (playerId < 0 || playerId > model.getPlayerList().size() - 1) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    return model.computerCommand(playerId);
  }

  @Override
  public int randomNum(int max, Random rand) {
    if (max < 0 || rand == null) {
      throw new IllegalArgumentException("Invalid player id.");
    }
    return model.randomNum(max, rand);
  }

  @Override
  public String getAllRoomsName() {
    StringBuilder res = new StringBuilder();
    for (Space r: model.getRoomList()) {
      res.append(r.getName());
      res.append(", ");
    }
    return res.toString();
  }

}
