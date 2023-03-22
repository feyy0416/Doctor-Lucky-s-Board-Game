package controller;

import view.WorldView;
import world.BuildWorld;

/**
 * This command implements the "create map" feature.
 *
 */
public class GenerateMap implements WorldCommand {
  
  private WorldView view;
  
  /**
   * Construct a command to create a graphical representation of the map.
   * 
   * @param view world view
   * @throws IllegalArgumentException if out is null
   */
  public GenerateMap(WorldView view) {
    if (view == null) {
      throw new IllegalArgumentException("View can't be null.");
    }
    this.view = view;
  }
  

  @Override
  public void execute(BuildWorld m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    m.generateMap();
    view.setTextToWindow("Graphical representation of the world map is created.\n");
  }

}
