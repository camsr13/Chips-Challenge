package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.util.Objects;

/**
 * Represents a cell of a level grid.
 * @author Rhys Macdonald -- 300516792
 */
public class Pair {
  public final int row;
  public final int col;

  public Pair(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pair pair = (Pair) o;
    return row == pair.row && col == pair.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
