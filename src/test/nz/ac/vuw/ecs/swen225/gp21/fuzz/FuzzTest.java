package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.awt.Frame;
import java.util.*;
import java.util.concurrent.*;
import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Contains a test for each of the game's two levels.
 * @author Rhys Macdonald - 300516792
 */
public class FuzzTest {

    private Runnable north, east, south, west;

    private final int M = Integer.MAX_VALUE;

    private final int[][] grid1 = new int[][] {
        new int[] {M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, M, M, 0, M, M, M, M, M, 0, M, M, M, M, M, M, M, M, M},
        new int[] {M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        new int[] {M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, M},
        new int[] {M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
    };
    private final int[][] grid2 = new int[][] {
        new int[] {M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, M, 0, M, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, 0, 0, M, 0, 0, 0, 0, M, 0, M, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, M, 0, M, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, M, 0, M, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, M, 0, M, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, M, M, 0, M, M, 0, M, M, 0, M, M, M, M, 0, M, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M},
        new int[] {M, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, M, M, M, M, 0, M, M, M, M, M, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, M, 0, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, 0, M},
        new int[] {M, 0, 0, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, M, 0, M},
        new int[] {M, M, M, M, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, M, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, 0, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, 0, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, M, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M, 0, M, M},
        new int[] {M, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, M, M},
        new int[] {M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M}
    };

    private final int MOVES = 9999999;
    /**
     * The number of seconds the random exploration should run (at the most).
     */
    private final int TIMEOUT = 30;
    private final int MOVE_DELAY = 900;

    /**
     * Converts a specified direction to the Runnable used to move in that direction.
     * @param d The direction.
     * @return The Runnable.
     */
    private Runnable getRunnableFromDirection(Direction d) {
        switch(d) {
            case NORTH: return north;
            case EAST: return east;
            case SOUTH: return south;
            case WEST: return west;
            default: throw new IllegalArgumentException();
        }
    }

    /**
     * Get a specified cell's neighbours, and store them in a map with their corresponding visit numbers.
     * @param grid The grid the cell is from.
     * @param row The row used to specify the cell.
     * @param col The column used to specify the cell.
     * @return The map.
     */
    private Map<Direction, Integer> getNeighbours(int[][] grid, int row, int col) {
        Map<Direction, Integer> r = new HashMap<>();

        if (row > 0) {
            r.put(Direction.NORTH, grid[row-1][col]);
        }
        if (col < grid[0].length - 1) {
            r.put(Direction.EAST, grid[row][col+1]);
        }
        if (col > 0) {
            r.put(Direction.WEST, grid[row][col-1]);
        }
        if (row < grid.length - 1) {
            r.put(Direction.SOUTH, grid[row+1][col]);
        }

        return r;
    }

    /**
     * Finds a specified cell's neighbouring cells with the least visits, converts those into directions and stores the
     * directions in a list.
     * @param grid The grid the cell is from.
     * @param row The row used to specify the cell.
     * @param col The column used to specify the cell.
     * @return The list of directions.
     */
    private List<Direction> getPreferredDirections(int[][] grid, int row, int col) {
        Map<Direction, Integer> neighbours = getNeighbours(grid, row, col);
        List<Direction> preferredDirections = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        for (Map.Entry<Direction, Integer> entry : neighbours.entrySet()) {
            int value = entry.getValue();
            if (entry.getValue() < min) {
                preferredDirections.clear();
                preferredDirections.add(entry.getKey());
                min = value;
            } else if (entry.getValue() == min) {
                preferredDirections.add(entry.getKey());
            }
        }

        return preferredDirections;
    }

    /**
     * Explores a random path, noting on the grid where it has already been.
     * @param grid The grid to explore.
     * @param startRow The row the player starts in.
     * @param startCol The column the player starts in.
     * @throws InterruptedException If the delay between moves in interrupted.
     */
    private void exploreGrid(int[][] grid, int startRow, int startCol) throws InterruptedException {
        Random random = new Random();

        int currRow = startRow;
        int currCol = startCol;

        grid[currRow][currCol] += 1;

        for (int i = 0; i < MOVES; i++) {
            List<Direction> directions = getPreferredDirections(grid, currRow, currCol);
            Direction direction = directions.get(random.nextInt(directions.size()));
            Runnable move = getRunnableFromDirection(direction);

            switch (direction) {
                case NORTH:
                    currRow--;
                    break;
                case EAST:
                    currCol++;
                    break;
                case WEST:
                    currCol--;
                    break;
                case SOUTH:
                    currRow++;
                    break;
            }

            TimeUnit.MILLISECONDS.sleep(MOVE_DELAY);

            grid[currRow][currCol] += 1;

            // Execute move
            move.run();
        }
    }

    /**
     * Executes a given callable until it finishes or the specified amount of time elapses.
     * @param timeout The amount of time.
     * @param unit The unit of time.
     * @param callable The callable.
     */
    private void runWithTimeout(long timeout, TimeUnit unit, Callable<?> callable) {
        final ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> future = es.submit(callable);

        try {
            future.get(timeout, unit);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("Timeout: " + timeout + " elapsed.");
        }
        es.shutdownNow();
    }

    /**
     * Tests a given level by initialising a GUI and performing {@link #exploreGrid} for a maximum of {@link #TIMEOUT}
     * seconds.
     * @param level The level.
     */
    private void testLevel(Level level) {
        // Load level
        GUIImp gui = new GUIImp(level.XMLFile);
        gui.getMainWindow().setVisible(true);

        // Setup directional actions
        north = gui::doNorthMove;
        east = gui::doEastMove;
        south = gui::doSouthMove;
        west = gui::doWestMove;

        // Explore randomly
        runWithTimeout(TIMEOUT, TimeUnit.SECONDS, (Callable<Void>) () -> {
            exploreGrid(level.grid, level.startRow, level.startCol);
            return null;
        });
    }

    /**
     * Removes any existing frames.
     */
    @AfterEach
    void tearDown() {
        for (Frame f : Frame.getFrames()) {
            f.dispose();
        }
    }

    /**
     * Tests level 1.
     */
    @Test
    void test1() {
        Level level = new Level(grid1, 9, 3, "levels/level1.xml");
        testLevel(level);

        System.out.println(level);
    }

    /**
     * Tests level 2.
     */
    @Test
    void test2() {
        Level level = new Level(grid2, 8, 9, "levels/level2.xml");
        testLevel(level);

        System.out.println(level);
    }

    /**
     * Represents a cardinal direction.
     */
    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}