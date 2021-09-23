package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains a test for each of the game's two levels.
 * @author Rhys Macdonald - 300516792
 */
public class FuzzTest {

    private Runnable north, east, south, west;

    private final int GRID_WIDTH = 21;
    private final int[] grid = new int[GRID_WIDTH * GRID_WIDTH];


//    private final int MOVES = 100;
    /**
     * The number of seconds the random exploration should run (at the most).
     */
    private final int TIMEOUT = 30;
    private final int MOVE_DELAY = 50;

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
     * Converts the grid into a human-readable string with the visit numbers of each cell.
     * @return The string.
     */
    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            sb.append(grid[i]).append(" ");
            if (i % GRID_WIDTH == GRID_WIDTH-1) {
                sb.append("\n");
            }
        }
        return String.valueOf(sb);
    }

    private int getGridAt(int row, int col) {
        return grid[row * GRID_WIDTH + col];
    }

    private void setGridAt(int row, int col, int value) {
        grid[row * GRID_WIDTH + col] = value;
    }

    private void incrementGridAt(int row, int col) {
        setGridAt(row, col, getGridAt(row, col) + 1);
    }

    /**
     * Get a specified cell's neighbours, and store them in a map with their corresponding visit numbers.
     * @param row The row used to specify the cell.
     * @param col The column used to specify the cell.
     * @return The map.
     */
    private Map<Direction, Integer> getNeighbours(int row, int col) {
        Map<Direction, Integer> r = new HashMap<>();

        if (row > 0) {
            r.put(Direction.NORTH, getGridAt(row-1, col));
        }
        if (col < GRID_WIDTH - 1) {
            r.put(Direction.EAST, getGridAt(row, col+1));
        }
        if (col > 0) {
            r.put(Direction.WEST, getGridAt(row, col-1));
        }
        if (row < GRID_WIDTH - 1) {
            r.put(Direction.SOUTH, getGridAt(row+1, col));
        }




        return r;
    }

    /**
     * Finds a specified cell's neighbouring cells with the least visits, converts those into directions and stores the
     * directions in a list.
     * @param row The row used to specify the cell.
     * @param col The column used to specify the cell.
     * @return The list of directions.
     */
    private List<Direction> getPreferredDirections(int row, int col) {
        Map<Direction, Integer> neighbours = getNeighbours(row, col);
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

//    private void performRandomMoves() {
//        Random random = new Random();
//        for (int i = 0; i < MOVES; i++) {
//            MouseAdapter direction = directions.get(random.nextInt(directions.size()));
//            direction.mouseClicked(null);
//        }
//    }

    /**
     * Explores a set number of random paths from the start, resetting the level after each path.
     */
    private void exploreGrid() throws InterruptedException {
//        for (int i = 0; i < 200; i++) {
            explorePath();
//            reset.keyPressed(null);
//        }
    }

    /**
     * Explores a random path, noting on the grid where it has already been.
     */
    private void explorePath() throws InterruptedException {
        Random random = new Random();

        int currRow = GRID_WIDTH / 2;
        int currCol = GRID_WIDTH / 2;

        incrementGridAt(currRow, currCol);


        for (int i = 0; i < 200; i++) {
            List<Direction> directions = getPreferredDirections(currRow, currCol);
            Direction direction = directions.get(random.nextInt(directions.size()));
            Runnable runnable = getRunnableFromDirection(direction);

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

            incrementGridAt(currRow, currCol);

            System.out.println(direction); // SOUTH
            System.out.println("(" + currRow + ", " +  currCol + ")"); // (20, 16)

            // Execute move
            TimeUnit.MILLISECONDS.sleep(MOVE_DELAY);
            runnable.run();
        }

        System.out.println(gridToString());
    }

    @BeforeEach
    void setUp() {



    }

//    @AfterEach
//    void tearDown() {
//        while (true) {
//
//        }
//    }

    /**
     * Tests level 1. Starts by traversing random paths from the start, then purposely tries to break the level using
     * hardcoded paths.
     */
    @Test
    void test1() {
        // Load in level 1
        GUITestImp gui = new GUITestImp();
        gui.getMainWindow().setVisible(true);

//        // Setup directional actions
        north = gui::doNorthMove;
        east = gui::doEastMove;
        south = gui::doSouthMove;
        west = gui::doWestMove;

        // This code block runs exploreGrid() until either it finishes, or a certain time limit is reached.
        final ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> future = es.submit((Callable<Void>) () -> {
            exploreGrid();
            return null;
        });
        try {
            future.get(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("Timeout: " + TIMEOUT + "s elapsed.");
        }
        es.shutdownNow();

    }

    /**
     * Tests level 2. Starts by traversing random paths from the start, then purposely tries to break the level using
     * hardcoded paths.
     */
    @Test
    void test2() {

    }

    /**
     * Represents a cardinal direction.
     */
    private enum Direction {
      NORTH, SOUTH, EAST, WEST
    }
}