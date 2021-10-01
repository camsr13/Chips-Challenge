package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class FreezeActorTests {

    @Test
    public void movementTest_01(){
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[5][1];
        for (int i =1; i < 4; i++){
            tilemap[i][0] = new FreeTile(new Location(i, 0));
        }
        tilemap[0][0] = new WallTile(new Location(0, 0));
        tilemap[4][0] = new WallTile(new Location(4, 0));
        FreezeActor actor = new FreezeActor(new Location(2, 0), Game.Direction.RIGHT);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(actor);
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);        Game.instance.tick();

        assert (actor.getLocation().getX() == 3);
        Game.instance.tick();
        assert (actor.getLocation().getX() == 3);
        assert (((FreezeActor) actor).currentDirection == Game.Direction.LEFT);
        Game.instance.tick();
        assert (actor.getLocation().getX() == 2);
    }

    @Test
    public void movementTest_02(){
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[1][5];
        for (int i =1; i < 4; i++){
            tilemap[0][i] = new FreeTile(new Location(0, i));
        }
        tilemap[0][0] = new WallTile(new Location(0, 0));
        tilemap[0][4] = new WallTile(new Location(0, 4));
        FreezeActor actor = new FreezeActor(new Location(0, 2), Game.Direction.DOWN);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(actor);
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        Game.instance.tick();
        assert (actor.getLocation().getY() == 3);
        Game.instance.tick();
        assert (actor.getLocation().getY() == 3);
        assert (((FreezeActor) actor).currentDirection == Game.Direction.UP);
        Game.instance.tick();
        assert (actor.getLocation().getY() == 2);
    }

    @Test
    public void freezeTest(){
        Game game = new Game();
        Player player = new Player(new Location(2, 0), 0);
        Tile[][] tilemap = new Tile[5][1];
        for (int i =1; i < 4; i++){
            tilemap[i][0] = new FreeTile(new Location(i, 0));
        }
        tilemap[0][0] = new WallTile(new Location(0, 0));
        tilemap[4][0] = new WallTile(new Location(0, 0));
        FreezeActor actor = new FreezeActor(new Location(1, 0), Game.Direction.RIGHT);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(actor);
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
        Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

        assert player.getTimeFrozen() == 0;
        Game.instance.tick();
        assert player.getTimeFrozen() > 0;
        assert Game.instance.getActors().isEmpty();
    }

}
