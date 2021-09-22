package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * TODO: everything
 *
 * @author Rhysa
 *
 */
public class ExitTile extends Tile {

	/**
	 * @param location
	 */
	public ExitTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "e";
	}

}
