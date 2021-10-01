package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class MovementTests {
	@Test
	public void testValid_01() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new WallTile(new Location(x, y));
			}
		}
		tilemap[1][1] = new FreeTile(new Location(1, 1));
		HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 1);
	}

	@Test
	public void testValid_02() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 0);
	}
}
