package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * TODO: everything
 *
 * @author Rhysa
 *
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
