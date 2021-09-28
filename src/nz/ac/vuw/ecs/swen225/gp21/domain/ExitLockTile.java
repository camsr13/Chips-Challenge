package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * TODO: everything
 *
 * @author Rhysa
 *
 */
public class ExitLockTile extends Tile {

	/**
	 * @param location
	 */
	public ExitLockTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "E";
	}

	public void removeTile() {
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

}
