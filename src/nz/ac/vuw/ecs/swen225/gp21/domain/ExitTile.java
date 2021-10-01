package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         When a player stands on an exit tile the game level will be marked as complete.
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
		Game.instance.notifyObserversLevelEnded();
	}

	@Override
	public String toChar() {
		return "e";
	}

}
