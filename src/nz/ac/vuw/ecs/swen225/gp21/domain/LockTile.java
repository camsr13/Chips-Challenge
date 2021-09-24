package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A tile that cannot be entered until the player has the appropriate key
 * 
 * @author Rhysa
 *
 */
public class LockTile extends Tile {
	private Game.KeyColour keyColour;

	/**
	 * @param location
	 * @param keyColour
	 */
	public LockTile(Location location, Game.KeyColour keyColour) {
		super(location, false);
		this.keyColour = keyColour;
	}

	@Override
	public boolean isPathable() {
		if (Game.instance.getKeysHeld().get(keyColour) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onPlayerEnter() {
		// TODO: check player holds correct key
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

	@Override
	public String toChar() {
		return "L";
	}

	/**
	 * @return This locks keyColour
	 */
	public Game.KeyColour getKeyColour() {
		return this.keyColour;
	}

}
