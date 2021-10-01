package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.util.Set;

/**
 * Represents a level of the game.
 * @author Rhys Macdonald -- 300516792
 */
public class Level {
  /**
   * A grid corresponding to tiles in the level, each integer representing the number of visits to that tile, except in
   * the case of wall tiles, where the maximum integer value is used.
   */
  public final int[][] grid;
  /**
   * The row the player starts in.
   */
  public final int startRow;
  /**
   * The column the player starts in.
   */
  public final int startCol;
  /**
   * The XML file the level is created from.
   */
  public final String XMLFile;

  public final Set<Pair> closedDoors;

  /**
   * @param grid See {@link #grid}.
   * @param startRow See {@link #startRow}.
   * @param startCol See {@link #startCol}.
   * @param XMLFile See {@link #XMLFile}.
   */
  public Level(int[][] grid, int startRow, int startCol, String XMLFile, Set<Pair> closedDoors) {
    this.grid = grid;
    this.startRow = startRow;
    this.startCol = startCol;
    this.XMLFile = XMLFile;
    this.closedDoors = closedDoors;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int[] row : this.grid) {
      for (int i = 0; i < row.length; i++) {
        if (row[i] == Integer.MAX_VALUE) {
          sb.append("X");
        } else {
          sb.append(row[i]);
        }
        sb.append(" ");
        if (i == row.length - 1) {
          sb.append("\n");
        }
      }
    }
    return String.valueOf(sb);
  }
}
