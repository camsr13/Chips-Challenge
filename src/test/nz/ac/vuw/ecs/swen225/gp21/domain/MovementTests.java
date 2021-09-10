package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;

public class MovementTests {
	@Test
	public void testValid_01() {
		Game game = new Game();
		Player player = new Player(new Location(1, 1));
		Tile[][] tilemap = new Tile[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tilemap[x][y] = new WallTile(new Location(x, y));
			}
		}
		tilemap[1][1] = new FreeTile(new Location(1, 1));
		player.move(Game.Direction.UP, tilemap);
		System.out.println(player.getLocation().getY());
		assert (player.getLocation().getY() == 1);
	}
}
