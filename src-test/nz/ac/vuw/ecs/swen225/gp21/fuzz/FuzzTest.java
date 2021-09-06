package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseAdapter;
import java.util.Random;
import java.util.concurrent.*;

import static org.mockito.Mockito.mock;

/**
 * @author Rhys Macdonald - 300516792
 */
public class FuzzTest {
    // Placeholders
    private final MouseAdapter north = mock(MouseAdapter.class);
    private final MouseAdapter east = mock(MouseAdapter.class);
    private final MouseAdapter south = mock(MouseAdapter.class);
    private final MouseAdapter west = mock(MouseAdapter.class);
    private final MouseAdapter[] directions = new MouseAdapter[]{north, east, south, west};

    private final int MOVES = 100;
    private final int TIMEOUT = 30;

    @BeforeEach
    void setUp() {



    }

    private void performRandomMoves() {
        Random random = new Random();
        for (int i = 0; i < MOVES; i++) {
            MouseAdapter direction = directions[random.nextInt(directions.length)];
            direction.mouseClicked(null);
        }
    }

    /**
     * Tests level 1.
     */
    @Test
    void test1() {
        // Load in level 1



        final ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> future = es.submit(this::performRandomMoves);
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