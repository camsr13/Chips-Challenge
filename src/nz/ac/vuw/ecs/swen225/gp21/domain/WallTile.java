package nz.ac.vuw.ecs.swen225.gp21.domain;

public class WallTile extends Tile {

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
