package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.util.concurrent.*;

import static org.mockito.Mockito.mock;

/**
 * @author Rhys Macdonald - 300516792
 */
public class FuzzTest {
    private enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }

    // Placeholders
    private final MouseAdapter north = mock(MouseAdapter.class);
    private final MouseAdapter east = mock(MouseAdapter.class);
    private final MouseAdapter south = mock(MouseAdapter.class);
    private final MouseAdapter west = mock(MouseAdapter.class);
    private final KeyAdapter reset = mock(KeyAdapter.class);
    private final List<MouseAdapter> directions = Arrays.asList(north, east, south, west);

    private final int GRID_WIDTH = 21;
    private final int[] grid = new int[GRID_WIDTH * GRID_WIDTH];

    private final int MOVES = 100;
    private final int TIMEOUT = 30;

    @BeforeEach
    void setUp() {



    }

    private MouseAdapter getAdapterFromDirection(Direction d) {
        switch(d) {
            case NORTH: return north;
            case EAST: return east;
            case SOUTH: return south;
            case WEST: return west;
            default: throw new IllegalArgumentException();
        }
    }

    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            sb.append(grid[i]).append(" ");
            if (i % GRID_WIDTH == 20) {
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

    private Map<Direction, Integer> getNeighbours(int row, int col) {
        Map<Direction, Integer> r = new HashMap<>();
        r.put(Direction.NORTH, getGridAt(row-1, col));
        r.put(Direction.EAST, getGridAt(row, col+1));
        r.put(Direction.SOUTH, getGridAt(row+1, col));
        r.put(Direction.WEST, getGridAt(row, col-1));
        return r;
    }

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

    private void performRandomMoves() {
        Random random = new Random();
        for (int i = 0; i < MOVES; i++) {
            MouseAdapter direction = directions.get(random.nextInt(directions.size()));
            direction.mouseClicked(null);
        }
    }

    private void exploreGrid() {
        for (int i = 0; i < 200; i++) {
            explorePath();
            reset.keyPressed(null);
        }
        System.out.println(gridToString());
    }

    private void explorePath() {
        Random random = new Random();

        int currRow = 10;
        int currCol = 10;

        incrementGridAt(currRow, currCol);


        for (int i = 0; i < GRID_WIDTH / 2; i++) {
            List<Direction> directions = getPreferredDirections(currRow, currCol);
            Direction direction = directions.get(random.nextInt(directions.size()));
            MouseAdapter adapter = getAdapterFromDirection(direction);
            adapter.mouseClicked(null);
            if (adapter == north) {
                currRow--;
            } else if (adapter == east) {
                currCol++;
            } else if (adapter == west) {
                currCol--;
            } else if (adapter == south) {
                currRow++;
            }
            incrementGridAt(currRow, currCol);
        }

//        System.out.println(gridToString());
    }

    /**
     * Tests level 1.
     */
    @Test
    void test1() {
        // Load in level 1



        final ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> future = es.submit(this::exploreGrid);
        try {
            future.get(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        es.shutdownNow();

    }

    /**
     * Tests level 2.
     */
    @Test
    void test2() {

    }
}