package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrowTileTests {

    @Test
    public void test_01(){
        Game game = new Game();
        Player player = new Player(new Location(1, 0), 0);
        Tile[][] tilemap = new Tile[3][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new ArrowTile(new Location(1, 0), Game.Direction.RIGHT);
        tilemap[2][0] = new ArrowTile(new Location(2, 0), Game.Direction.LEFT);
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        Game.instance.tick();
        assert (player.getLocation().getX() == 2);
        Game.instance.tick();
        assert (player.getLocation().getX() == 1);
    }

    @Test
    public void test_02(){
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[2][2];
        tilemap[0][0] = new ArrowTile(new Location(0, 0), Game.Direction.RIGHT);
        tilemap[1][0] = new ArrowTile(new Location(1, 0), Game.Direction.DOWN);
        tilemap[1][1] = new ArrowTile(new Location(1, 1), Game.Direction.LEFT);
        tilemap[0][1] = new ArrowTile(new Location(0, 1), Game.Direction.UP);
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        Game.instance.tick();
        assert (player.getLocation().getX() == 1);
        assert (player.getLocation().getY() == 0);
        Game.instance.tick();

        assert (player.getLocation().getX() == 1);
        assert (player.getLocation().getY() == 1);
        Game.instance.tick();

        assert (player.getLocation().getX() == 0);
        assert (player.getLocation().getY() == 1);
        Game.instance.tick();

        assert (player.getLocation().getX() == 0);
        assert (player.getLocation().getY() == 0);
    }
}
