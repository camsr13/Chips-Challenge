package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * An empty tile.
 *
 * @author Rhysa
 *
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
