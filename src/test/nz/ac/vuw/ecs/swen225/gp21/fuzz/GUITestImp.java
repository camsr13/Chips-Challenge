package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;

/**
 * Gui implementation without dialog
 */
public class GUITestImp extends GUIImp {
  public GUITestImp(String file) {
    super(file);
  }

  @Override
  protected void doWestMove() {
    super.doWestMove();
  }

  @Override
  protected void doEastMove() {
    super.doEastMove();
  }

  @Override
  protected void doSouthMove() {
    super.doSouthMove();
  }

  @Override
  protected void doNorthMove() {
    super.doNorthMove();
  }
}
