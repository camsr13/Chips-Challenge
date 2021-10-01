package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.ArrayList;
import java.util.HashMap;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game.KeyColour;

public class KeyTests {

	private HashMap<KeyColour, Integer> emptyKeySet(){
		HashMap<KeyColour, Integer> map = new HashMap<KeyColour, Integer>();
		for (KeyColour col : KeyColour.values()){
			map.put(col, 0);
		}
		return map;
	}
	@Test
	public void testKey_01() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 0);
		assert (game.getKeysHeld().get(KeyColour.BLUE) > 0);
	}

	@Test
	public void testKey_02() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.YELLOW);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 0);
		assert (game.getKeysHeld().get(KeyColour.YELLOW) > 0);
	}

	@Test
	public void testKey_03() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (game.getTilemap()[1][0] instanceof FreeTile);
	}

	@Test
	public void testLock_01() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new LockTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 1);
	}

	@Test
	public void testLock_02() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new LockTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		game.addKey(KeyColour.BLUE);
		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 0);
	}

	@Test
	public void testLock_03() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new LockTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);

		game.addKey(KeyColour.BLUE);
		player.move(Game.Direction.UP);
		assert (game.getTilemap()[1][0] instanceof FreeTile);
	}

	@Test
	public void testLock_04() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1), 0);
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new LockTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Integer> keysHeld = emptyKeySet();
		ArrayList<Actor> actors = new ArrayList<>();
		ExitLockTile exitLock = new ExitLockTile(new Location(0, 0));
		Game.instance.setupGame(tilemap, player, keysHeld, 2, 0, exitLock, actors);		game.addKey(KeyColour.YELLOW);

		player.move(Game.Direction.UP);
		assert (player.getLocation().getY() == 1);
	}

}
