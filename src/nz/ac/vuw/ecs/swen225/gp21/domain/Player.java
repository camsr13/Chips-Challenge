package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock
 *
 */
public class Player {

	/**
	 * Players current location
	 */
	Location location;

	/**
	 * @param location The players location
	 */
	public Player(Location location) {
		this.location = location;
	}

	/**
	 * @return Copy of players current location
	 */
	public Location getLocation() {
		return Location.copy(location);
	}

	/**
	 * @param l
	 */
	public void setLocation(Location l) {
		this.location = Location.copy(l);
		// TODO: check tile is valid
	}

	/**
	 * @param d       The direction for player movement
	 * @param tilemap The tilemap in which the player is moving
	 */
	public void move(Game.Direction d, Tile[][] tilemap) {
		int x = -1;
		int y = -1;
		if (d == Game.Direction.UP) {
			x = location.getX();
			y = location.getY() - 1;
		} else if (d == Game.Direction.DOWN) {
			x = location.getX();
			y = location.getY() + 1;
		} else if (d == Game.Direction.LEFT) {
			x = location.getX() - 1;
			y = location.getY();
		} else if (d == Game.Direction.RIGHT) {
			x = location.getX() + 1;
			y = location.getY();
		}
		if (x < 0 || y < 0) {
			// TODO: bad movement case
		} else {
			Tile newTile = tilemap[x][y];
			if (newTile.isPathable()) {
				setLocation(new Location(x, y));
				tilemap[x][y].onPlayerEnter();
			}
		}
	}
}
