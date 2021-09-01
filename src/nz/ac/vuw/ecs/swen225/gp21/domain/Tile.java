package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock
 *
 */
public abstract class Tile {

	Location location;
	boolean pathable;

	/**
	 * @param location This tiles location
	 * @param pathable Whether this tile is pathable
	 */
	public Tile(Location location, boolean pathable) {
		this.location = location;
		this.pathable = pathable;
	}

	/**
	 * @return Copy of this tiles location
	 */
	public Location getLocation() {
		return Location.copy(location);
	}

	/**
	 * Cause behaviour dependent on tile for when the player enters this tile
	 */
	public abstract void onPlayerEnter();

	/**
	 * @return Whether this tile is pathable
	 */
	public boolean isPathable() {
		return pathable;
	}
}
