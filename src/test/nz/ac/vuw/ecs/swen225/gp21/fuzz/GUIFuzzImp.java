package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;

/**
 * Gui implementation that enables {@link FuzzTest} to call directional movements.
 * @author Rhys Macdonald -- 300516792
 */
public class GUIFuzzImp extends GUIImp {
  /**
   * Constructs a GUI with a given file string corresponding to a level XML file.
   * @param file The XML file.
   */
  public GUIFuzzImp(String file) {
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
