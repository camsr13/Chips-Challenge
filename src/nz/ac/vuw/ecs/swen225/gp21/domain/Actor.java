
package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         Actor is an abstract class which enemies that can more around the tilemap and interact with the player will extend.
 */

public abstract class Actor {

	/** Actors current location.
	 */
	private Location location;
	/**
	 * Whether this actor should be removed by game.
	 */
	boolean shouldRemove = false;

	/**
	 * @param l The Actors location
	 */
	public Actor(final Location l) {
		this.location = Location.copy(l);
	}

	/**
	 * Behaiviour of actor when a gameplay tick occurs.
	 */
	public abstract void tick();

	/**
	 * Behaiviour of actor when a player enters its tile.
	 */
	public abstract void onPlayerEnter();

	/**
	 * @param l
	 */
	public  void setLocation(final Location l) {
		this.location = Location.copy(l);
	};

	/**
	 * @return Copy of actors current location
	 */
	public Location getLocation() {
		return Location.copy(location);
	}
}
