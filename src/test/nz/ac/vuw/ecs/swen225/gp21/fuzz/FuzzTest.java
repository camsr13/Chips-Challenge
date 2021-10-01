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

    private final int[][] level1grid = new int[][] {
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
    private final Set<Pair> level1doors = Set.of(new Pair(3, 6), new Pair(9, 6),
        new Pair(12, 3), new Pair(12, 9), new Pair(18, 9));

    private final int[][] level2grid = new int[][] {
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
    private final int TIMEOUT = 600;
    private final int MOVE_DELAY = 1000;

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

    private Set<Pair> findUnvisited(Level level, int startRow, int startCol) {
        int[][] grid = level.grid;

        Set<Pair> unvisited = new HashSet<>();
        Deque<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(startRow, startCol));

        while(!queue.isEmpty()) {
            Pair currPair = queue.pollFirst();
            int currRow = currPair.row;
            int currCol = currPair.col;
            if (grid[currRow][currCol] == 0 && !unvisited.contains(currPair) && !level.closedDoors.contains(currPair)) {
                unvisited.add(currPair);
                if (currCol > 0) {
                    queue.addLast(new Pair(currRow, currCol-1)); // west
                }
                if (currCol < grid[0].length - 1) {
                    queue.addLast(new Pair(currRow, currCol+1)); // east
                }
                if (currRow > 0) {
                    queue.addLast(new Pair(currRow - 1, currCol)); // north
                }
                if (currRow < grid.length - 1) {
                    queue.addLast(new Pair(currRow + 1, currCol)); // south
                }
            }
        }

        return unvisited;
    }

    /**
     * Explores a level, noting on a grid where it has already been.
     * @param level The level to explore.
     * @throws InterruptedException If the delay between moves in interrupted.
     */
    private void exploreLevel(Level level) throws InterruptedException {
        int[][] grid = level.grid;
        int startRow = level.startRow;
        int startCol = level.startCol;
        Set<Pair> unvisited = findUnvisited(level, startRow, startCol);

        Random random = new Random();

        int currRow = startRow;
        int currCol = startCol;

        grid[currRow][currCol] += 1;
        unvisited.remove(new Pair(currRow, currCol));

        for (int i = 0; i < MOVES; i++) {
            List<Direction> directions = getPreferredDirections(grid, currRow, currCol);
            Direction direction = directions.get(random.nextInt(directions.size()));
            Runnable move = getRunnableFromDirection(direction);

            int nextRow = currRow;
            int nextCol = currCol;

            switch (direction) {
                case NORTH:
                    nextRow--;
//                    currRow--;
                    break;
                case EAST:
                    nextCol++;
//                    currCol++;
                    break;
                case WEST:
                    nextCol--;
//                    currCol--;
                    break;
                case SOUTH:
                    nextRow++;
//                    currRow++;
                    break;
            }

            // TODO:
            Pair nextPair = new Pair(nextRow, nextCol);
            if (level.closedDoors.contains(nextPair)) {
                if (unvisited.isEmpty()) {
                    level.closedDoors.remove(nextPair);
                    unvisited = findUnvisited(level, nextRow, nextCol);
                } else {
                    continue;
                }
            }
            currRow = nextRow;
            currCol = nextCol;

            grid[currRow][currCol] += 1;
            unvisited.remove(new Pair(currRow, currCol));

            // Execute move
            move.run();
            System.out.println(direction);

            TimeUnit.MILLISECONDS.sleep(MOVE_DELAY);
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
     * Tests a given level by initialising a GUI and performing {@link #exploreLevel} for a maximum of {@link #TIMEOUT}
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
            exploreLevel(level);
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
        Level level = new Level(level1grid, 9, 3, "levels/level1.xml", level1doors);
        testLevel(level);

        System.out.println(level);
    }

    /**
     * Tests level 2.
     */
    @Test
    void test2() {
//        Level level = new Level(level2grid, 8, 9, "levels/level2.xml");
//        testLevel(level);
//
//        System.out.println(level);
    }

    /**
     * Represents a cardinal direction.
     */
    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}