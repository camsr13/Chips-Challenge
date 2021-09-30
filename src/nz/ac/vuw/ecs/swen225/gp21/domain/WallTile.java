package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A tile that represents a wall that cannot be walked through
 * 
 * @author Rhys Adcock 300419040
 *
 */
public class WallTile extends Tile {

	/**
	 * @param location
	 */
	public WallTile(Location location) {
		super(location, false);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "#";
	}

}
