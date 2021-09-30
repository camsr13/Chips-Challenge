package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         The base class which all tile implementations will extend.
 */
public abstract class Tile {

	/**
	 * This tiles location
	 */
	Location location;
	/**
	 * Whether the player can move onto this tile
	 */
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
	 * @return Whether this tile is pathable for the player
	 */
	public boolean isPlayerPathable() {
		return pathable;
	}

	/**
	 *
	 * @return Whether this tile is pathable for Actors
	 */
	public boolean isActorPathable() { return pathable; }

	/**
	 * For testing purposes
	 * 
	 * @return A char representation of this tile
	 */
	public abstract String toChar();

	/**
	 * Cause behaviour dependent on tile for when a tick occurs while player is standing on this tile
	 */
	public  void onPlayerTick(){}
}
