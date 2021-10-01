package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class FreezeTileTests {

    @Test
    public void test_01(){
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[3][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new FreezeTile(new Location(1, 0));
        tilemap[2][0] = new FreeTile(new Location(2, 0));
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ArrayList<Actor> actors = new ArrayList<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        Game.instance.getPlayer().move(Game.Direction.RIGHT);
        assert(Game.instance.getPlayer().getTimeFrozen() == 1);
        Game.instance.getPlayer().move(Game.Direction.RIGHT);
        assert(Game.instance.getPlayer().getLocation().getX() == 1);
        Game.instance.tick();
        assert(Game.instance.getPlayer().getTimeFrozen() == 0);
        Game.instance.getPlayer().move(Game.Direction.RIGHT);
        assert(Game.instance.getPlayer().getLocation().getX() == 2);
    }
}
