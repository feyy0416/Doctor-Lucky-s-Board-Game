package controller;

import view.SwingWorldView;
import view.WorldView;
import world.BuildWorld;
import world.BuildWorldModel;

/**
 * Driver class for Doctor Lucky game to show how it works.
 *
 * @author Zifei Song.
 */
public class Driver {
  /**
   * Driver program to show how game works.
   * 
   * @param args command line arguments represents world file path.
   */
  public static void main(String[] args) {
    BuildWorld model = new BuildWorldModel(args);
    WorldView view = new SwingWorldView(model);
    WorldController controller = new SwingController(model, view);
    controller.setView(view);
  }
}
