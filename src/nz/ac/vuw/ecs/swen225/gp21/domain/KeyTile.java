package nz.ac.vuw.ecs.swen225.gp21.domain;

public class KeyTile extends Tile {

	private Game.KeyColour keyColour;

	public KeyTile(Location location, Game.KeyColour keyColour) {
		super(location, true);
		this.keyColour = keyColour;
	}

	@Override
	public void onPlayerEnter() {
		Game.instance.addKey(keyColour);
		FreeTile newTile = new FreeTile(Location.copy(location));
		Game.instance.setTile(newTile);
	}

	@Override
	public String toChar() {
		return "k";
	}

}
