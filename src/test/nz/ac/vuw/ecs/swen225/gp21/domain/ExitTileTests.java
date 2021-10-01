package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ExitTileTests {

    @Test
    public void test_01() {
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[2][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new ExitTile(new Location(1, 0));
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        player.move(Game.Direction.RIGHT);
        assert player.getLocation().getX() == 1;
        assert Game.instance.isLevelComplete();
    }
}
