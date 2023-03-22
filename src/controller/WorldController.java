package controller;

import view.WorldView;

/**
 * Represents a Controller to Doctor Lucky game: handle user interaction with
 * view by executing them using the model; convey actions outcomes to the view
 * showing to user.
 *
 */
public interface WorldController {
  /**
   * Set the new view into the controller and reset all features in the view.
   * 
   * @param v the view we want to set.
   */
  void setView(WorldView view);
}
