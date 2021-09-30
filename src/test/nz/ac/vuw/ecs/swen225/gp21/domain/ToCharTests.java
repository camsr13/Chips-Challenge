package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

public class ToCharTests {

    @Test
    public void test_01() {
        assert (new ArrowTile(new Location(0, 0), Game.Direction.UP)).toChar() == "a";
        assert (new ExitLockTile(new Location(0, 0))).toChar() == "E";
        assert (new ExitTile(new Location(0, 0))).toChar() == "e";
        assert (new FreeTile(new Location(0, 0))).toChar() == "-";
        assert (new FreeTile(new Location(0, 0))).toChar() == "-";
        assert (new InfoTile(new Location(0, 0), "Hello world.")).toChar() == "i";
        assert (new KeyTile(new Location(0, 0), Game.KeyColour.BLUE)).toChar() == "k";
        assert (new TreasureTile(new Location(0, 0))).toChar() == "t";
        assert (new WallTile(new Location(0, 0))).toChar() == "#";
    }

    @Test
    public void test_02() {
        assert (new InfoTile(new Location(0, 0), "Hello world.")).getMessage() == "Hello world.";
    }
}
