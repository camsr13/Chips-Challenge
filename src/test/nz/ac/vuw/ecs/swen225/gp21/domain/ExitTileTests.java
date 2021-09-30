package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ExitTileTests {

    @Test
    public void test_01() {
        Game game = new Game();
        Player player = new Player(new Location(0, 0), 0);
        Tile[][] tilemap = new Tile[2][1];
        tilemap[0][0] = new FreeTile(new Location(0, 0));
        tilemap[1][0] = new ExitTile(new Location(1, 0));
        game.setupGame(tilemap, player, null, 0, 0, null, new ArrayList<Actor>());
        player.move(Game.Direction.RIGHT);
        assert player.getLocation().getX() == 1;
        assert Game.instance.isLevelComplete();
    }
}
