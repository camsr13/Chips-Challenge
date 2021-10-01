package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TreasureTests {

    @Test
    public void test_01() {
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[3][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new TreasureTile(new Location(1, 0));
        ExitLockTile exitLock = new ExitLockTile(new Location(2, 0));
        tilemap[2][0] = exitLock;
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        Game.instance.setupGame(tilemap, player, keysHeld, 1, 0, exitLock, actors);

        player.move(Game.Direction.RIGHT);
        assert player.getLocation().getX() == 1;
        assert Game.instance.getCollectedTreasures() == 1;
        assert tilemap[1][0] instanceof FreeTile;
        assert tilemap[2][0] instanceof FreeTile;
    }

    @Test
    public void test_02() {
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[4][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new TreasureTile(new Location(1, 0));
        tilemap[2][0] = new TreasureTile(new Location(2, 0));
        ExitLockTile exitLock = new ExitLockTile(new Location(3, 0));
        tilemap[3][0] = exitLock;
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        player.move(Game.Direction.RIGHT);
        assert player.getLocation().getX() == 1;
        assert Game.instance.getCollectedTreasures() == 1;
        assert tilemap[1][0] instanceof FreeTile;
        assert tilemap[3][0] instanceof ExitLockTile;

        player.move(Game.Direction.RIGHT);
        assert player.getLocation().getX() == 2;
        assert Game.instance.getCollectedTreasures() == 2;
        assert tilemap[2][0] instanceof FreeTile;
        assert tilemap[3][0] instanceof FreeTile;
    }
}
