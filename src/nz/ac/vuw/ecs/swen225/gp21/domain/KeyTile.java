package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A tile representing a key that when entered is picked up and leaves the tile
 * a FreeTile
 * 
 * @author Rhys Adcock 300419040
 *
 */
public class KeyTile extends Tile {

	private Game.KeyColour keyColour;

	/**
	 * @param location
	 * @param keyColour
	 */
	public KeyTile(Location location, Game.KeyColour keyColour) {
		super(location, true);
		this.keyColour = keyColour;
	}

	@Override
	public void onPlayerEnter() {
		// TODO: checks
		Game.instance.addKey(keyColour);
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

	@Override
	public String toChar() {
		return "k";
	}

	/**
	 * @return This keys colour
	 */
	public Game.KeyColour getKeyColour() {
		return this.keyColour;
	}

}
