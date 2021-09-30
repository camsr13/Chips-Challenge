package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * This is a tile that acts as a WallTile until all treasures have been collected.
 *
 * @author Rhys Adcock 300419040
 *
 */
public class ExitLockTile extends Tile {

	/**
	 * @param location
	 */
	public ExitLockTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "E";
	}

	/**
	 * Replaces this tile current location in the game instance tilemap with a FreeTile.
	 */
	public void removeTile() {
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

}
