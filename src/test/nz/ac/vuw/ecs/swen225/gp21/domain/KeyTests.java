package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game.KeyColour;
import nz.ac.vuw.ecs.swen225.gp21.domain.KeyTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

public class KeyTests {
	@Test
	public void testValid_01() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1));
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Boolean> keyHeld = new HashMap<KeyColour, Boolean>();
		game.setupGame(tilemap, player, keyHeld);

		player.move(Game.Direction.UP, tilemap);
		assert (player.getLocation().getY() == 0);
		assert (game.getKeysHeld().get(KeyColour.BLUE));
	}

	@Test
	public void testValid_02() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1));
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		HashMap<KeyColour, Boolean> keyHeld = new HashMap<KeyColour, Boolean>();
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.BLUE);
		game.setupGame(tilemap, player, keyHeld);

		player.move(Game.Direction.UP, tilemap);
		assert (game.getKeysHeld().get(KeyColour.BLUE));
	}

	@Test
	public void testValid_03() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1));
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new FreeTile(new Location(x, y));
			}
		}
		tilemap[1][0] = new KeyTile(new Location(1, 0), KeyColour.BLUE);
		HashMap<KeyColour, Boolean> keyHeld = new HashMap<KeyColour, Boolean>();
		game.setupGame(tilemap, player, keyHeld);

		player.move(Game.Direction.UP, tilemap);
		assert (game.getTilemap()[1][0] instanceof FreeTile);
	}
}