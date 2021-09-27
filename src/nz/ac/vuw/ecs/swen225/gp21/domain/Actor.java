package nz.ac.vuw.ecs.swen225.gp21.domain;

public abstract class Actor {

	/**
	 * Actors current location
	 */
	Location location;

	/**
	 * @param location The Actors location
	 */
	public Actor(Location location) {
		this.location = location;
	}

	public abstract void tick();

	public abstract void onPlayerEnter();
}
