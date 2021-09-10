package nz.ac.vuw.ecs.swen225.gp21.domain;

public class LockTile extends Tile {
	private Game.KeyColour keyColour;

	public LockTile(Location location, Game.KeyColour keyColour) {
		super(location, false);
		this.keyColour = keyColour;
	}

	@Override
	public boolean isPathable() {
		if (Game.instance.getKeysHeld().containsKey(keyColour) && Game.instance.getKeysHeld().get(keyColour)) {
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

}
