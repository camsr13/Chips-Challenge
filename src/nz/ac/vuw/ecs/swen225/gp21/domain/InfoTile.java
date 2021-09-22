package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * TODO: everything
 *
 * @author Rhysa
 *
 */
public class InfoTile extends Tile {

	/**
	 * @param location
	 */
	public InfoTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "i";
	}

}
