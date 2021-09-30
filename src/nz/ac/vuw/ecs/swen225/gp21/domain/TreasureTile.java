package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         Treasures can be picked up.
 */
public class TreasureTile extends Tile {

	/**
	 * @param location
	 */
	public TreasureTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {
		Game.instance.collectTreasure();
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

	@Override
	public String toChar() {
		return "t";
	}

}
