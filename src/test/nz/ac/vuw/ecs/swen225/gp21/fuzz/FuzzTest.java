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


    private final int MOVES = 9999999;
    /**
     * The number of seconds the random exploration should run (at the most).
     */
    private final int TIMEOUT = 60;
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

    private Runnable getRandomDirectionRunnable(Random random) {
        Direction[] directions = Direction.values();

        Direction direction = directions[random.nextInt(directions.length)];
        return getRunnableFromDirection(direction);
    }

    /**
     * Simple algorithm to play random moves.
     */
    private void performRandomMoves() {
        Random random = new Random();

        for (int i = 0; i < MOVES; i++) {
            getRandomDirectionRunnable(random).run();
        }
    }

    /**
     * Explores a random path, noting on the grid where it has already been.
     */
    private void exploreGrid() {
        Random random = new Random();

        int currRow = GRID_WIDTH / 2;
        int currCol = GRID_WIDTH / 2;

        incrementGridAt(currRow, currCol);


        for (int i = 0; i < MOVES; i++) {
            List<Direction> directions = getPreferredDirections(currRow, currCol);
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

            incrementGridAt(currRow, currCol);

            // Execute move
            move.run();
        }
    }

    /**
     * Move in a given list of directions.
     * @param directions The list of directions.
     * @param delay The time delay (ms) between each move.
     * @throws InterruptedException If this method is stopped during a delay.
     */
    private void exploreDefinedPath(List<Direction> directions, long delay) throws InterruptedException {
        for (Direction d : directions) {
            getRunnableFromDirection(d).run();
            if (delay > 0) {
                TimeUnit.MILLISECONDS.sleep(delay);
            }
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
     * Tests level 1. Purposely tries to break the level using a predetermined path, then traverses randomly.
     */
    @Test
    void test1() {
        // Load level 1
        GUITestImp gui = new GUITestImp();
        gui.getMainWindow().setVisible(true);

        // Setup directional actions
        north = gui::doNorthMove;
        east = gui::doEastMove;
        south = gui::doSouthMove;
        west = gui::doWestMove;

        // Explore a predetermined path
        String path = "N N N S S W W W E E E E E S S S W W N N N N N N S N E E E E E E S N W W W W E E N N W W E E E E S S S S S S S E E E E E E W W W W W W N N N N N N E E E E E E E E N N W W W W S S W W W W S S S S S S E E E E E E S S E E N N E E";
        try {
            exploreDefinedPath(Direction.getDirectionsFromSequence(path), MOVE_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Explore randomly
        runWithTimeout(TIMEOUT, TimeUnit.SECONDS, (Callable<Void>) () -> {
            exploreGrid();
            return null;
        });
    }

    /**
     * Tests level 2. Purposely tries to break the level using a predetermined path, then traverses randomly.
     */
    @Test
    void test2() {

    }

    /**
     * Represents a cardinal direction.
     */
    private enum Direction {
        NORTH, SOUTH, EAST, WEST;

        /**
         * Converts a given string (of the form "N E S W" etc.) to a list of directions.
         * @param seq The string.
         * @return The list of directions.
         * @throws IllegalArgumentException If the string isn't of the correct form.
         */
        private static List<Direction> getDirectionsFromSequence(String seq) throws IllegalArgumentException {
            List<Direction> r = new ArrayList<>();
            Scanner sc = new Scanner(seq);

            while (sc.hasNext()) {
                switch(sc.next()) {
                    case "N":
                        r.add(NORTH);
                        break;
                    case "E":
                        r.add(EAST);
                        break;
                    case "S":
                        r.add(SOUTH);
                        break;
                    case "W":
                        r.add(WEST);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            return r;
        }
    }
}