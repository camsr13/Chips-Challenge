package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A tile that stores a message
 *
 * @author Rhysa
 *
 */
public class InfoTile extends Tile {

	private String message;

	/**
	 * @param location
	 * @param message
	 */
	public InfoTile(Location location, String message) {
		super(location, true);
		this.message = message;
	}

	@Override
	public void onPlayerEnter() {

	}

	@Override
	public String toChar() {
		return "i";
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

}
