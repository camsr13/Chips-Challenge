package nz.ac.vuw.ecs.swen225.gp21.domain;

public class FreeTile extends Tile {

	public FreeTile(Location location) {
		super(location, true);
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "-";
	}

}
