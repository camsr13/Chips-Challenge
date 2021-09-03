package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseAdapter;

import static org.mockito.Mockito.mock;

/**
 * @author Rhys Macdonald - 300516792
 */
public class FuzzTest {
    private MouseAdapter north;
    private MouseAdapter east;
    private MouseAdapter south;
    private MouseAdapter west;

    @BeforeEach
    void setUp() {
        // Placeholders
        north = mock(MouseAdapter.class);
        east = mock(MouseAdapter.class);
        south = mock(MouseAdapter.class);
        west = mock(MouseAdapter.class);
    }

    /**
     * Tests level 1.
     */
    @Test
    void test1() {

    }

    /**
     * Tests level 2.
     */
    @Test
    void test2() {

    }
}