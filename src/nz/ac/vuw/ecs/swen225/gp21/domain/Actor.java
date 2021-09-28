package nz.ac.vuw.ecs.swen225.gp21.domain;

public abstract class Actor {

	/**
	 * Actors current location
	 */
	Location location;
	boolean shouldRemove = false;

	/**
	 * @param location The Actors location
	 */
	public Actor(Location location) {
		this.location = location;
	}

	public abstract void tick();

	public abstract void onPlayerEnter();

	/**
	 * @param l
	 */
	public  void setLocation(Location l) {
		this.location = Location.copy(l);
	};
}
