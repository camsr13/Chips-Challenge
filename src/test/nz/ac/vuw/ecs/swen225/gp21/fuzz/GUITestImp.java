package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;

/**
 * Gui implementation without dialog
 */
public class GUITestImp extends GUIImp {

  @Override
  protected void startPopUp() {
    this.currFile = "level1.xml";
    loadGame();
  }

  @Override
  public void doWestMove() {
    super.doWestMove();
  }

  @Override
  public void doEastMove() {
    super.doEastMove();
  }

  @Override
  public void doSouthMove() {
    super.doSouthMove();
  }

  @Override
  public void doNorthMove() {
    super.doNorthMove();
  }
}
