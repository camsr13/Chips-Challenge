package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         An empty tile that the player can move through.
 */
public class FreeTile extends Tile {

	/**
	 * @param location
	 */
	public FreeTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "-";
	}

}
