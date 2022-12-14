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
    private Deque<Direction> threeMoveHistory = new ArrayDeque<>();

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
    private final int TIMEOUT = 28;
    private final int MOVE_DELAY = 300;

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
    private Map<Direction, Pair> getNeighbours(int[][] grid, int row, int col) {
        Map<Direction, Pair> r = new HashMap<>();

        if (row > 0) {
            r.put(Direction.NORTH, new Pair(row-1,col));
        }
        if (col < grid[0].length - 1) {
            r.put(Direction.EAST, new Pair(row,col+1));
        }
        if (col > 0) {
            r.put(Direction.WEST, new Pair(row,col-1));
        }
        if (row < grid.length - 1) {
            r.put(Direction.SOUTH, new Pair(row+1,col));
        }

        return r;
    }

    /**
     * Finds a specified cell's neighbouring cells with the least visits, converts those into directions and stores the
     * directions in a list.
     * @param level The level.
     * @param row The row used to specify the cell.
     * @param col The column used to specify the cell.
     * @return The list of directions.
     */
    private List<Direction> getPreferredDirections(Level level, int row, int col, Set<Pair> unvisited) {
        int[][] grid = level.grid;

        Map<Direction, Pair> neighbours = getNeighbours(grid, row, col);
        List<Direction> preferredDirections = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        for (Map.Entry<Direction, Pair> entry : neighbours.entrySet()) {
            Pair pair = entry.getValue();
            int value = grid[pair.row][pair.col];

            // TODO: implement repeated move block

            if (threeMoveHistory.size() == 3) {
                Direction d = threeMoveHistory.peek();
                boolean uniformHistory = threeMoveHistory.stream().allMatch(i -> i.equals(d));
                if (uniformHistory && entry.getKey() == d) continue;
            }

            if (level.closedDoors.contains(pair)) {
                if (unvisited.isEmpty()) {
                    level.closedDoors.remove(pair);
                    unvisited = findUnvisited(level, pair.row, pair.col);
                } else {
                    continue;
                }
            }

            if (value < min) {
                preferredDirections.clear();
                preferredDirections.add(entry.getKey());
                min = value;
            } else if (value == min) {
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
            List<Direction> directions = getPreferredDirections(level, currRow, currCol, unvisited);
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

            grid[currRow][currCol] += 1;
            unvisited.remove(new Pair(currRow, currCol));

            // Execute move
            move.run();
            threeMoveHistory.addFirst(direction);
            if (threeMoveHistory.size() > 3) {
                threeMoveHistory.removeLast();
            }
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
        gui.setTimer(0);

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
        Set<Pair> level1doors = new HashSet<>();
        level1doors.add(new Pair(6, 3));
        level1doors.add(new Pair(6, 9));
        level1doors.add(new Pair(3, 12));
        level1doors.add(new Pair(9, 12));
        level1doors.add(new Pair(9, 18));
        Level level = new Level(level1grid, 9, 3, "levels/level1.xml", level1doors);
        testLevel(level);

        System.out.println(level);
    }

    /**
     * Tests level 2.
     */
    @Test
    void test2() {
        Set<Pair> level2doors = new HashSet<>();
        level2doors.add(new Pair(6, 6));
        level2doors.add(new Pair(6, 9));
        level2doors.add(new Pair(6, 12));
        level2doors.add(new Pair(6, 17));
        level2doors.add(new Pair(27, 15));
        Level level = new Level(level2grid, 8, 9, "levels/level2.xml", level2doors);
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