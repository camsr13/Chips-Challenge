package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         Player stores the current position and contains the logic that allows the player to move and interact with
 *         the game.
 */
public class Player {

	/**
	 * Players current location
	 */
	Location location;

	int timeFrozen;

	/**
	 * @param location The players location
	 */
	public Player(Location location, int timeFrozen) {
		this.location = location;
		this.timeFrozen = timeFrozen;
	}

	/**
	 * @return Copy of players current location
	 */
	public Location getLocation() {
		return Location.copy(location);
	}

	/**
	 * @return Time player is frozen
	 */
	public int getTimeFrozen() {
		return timeFrozen;
	}

	/**
	 * @param l
	 */
	public void setLocation(Location l) {
		this.location = Location.copy(l);

		if (!Game.instance.getTilemap()[location.getX()][location.getY()].isPlayerPathable()){
			throw new IllegalStateException("Player lcoation cannot be set to a tile that is unpathable.");
		}
	}

	/**
	 * @param d       The direction for player movement
	 */
	public void move(Game.Direction d) {
		if (d == null){
			throw new IllegalArgumentException("Movement direction cannot be null");
		}

		if (timeFrozen > 0) {
			return;
		}
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
		if (x < 0 || y < 0 || x > Game.instance.getTilemap().length || y > Game.instance.getTilemap()[0].length) {
			throw new IllegalStateException("Movement cannot exceed bounds of tile array.");
		} else {
			Tile newTile = Game.instance.getTilemap()[x][y];
			if (newTile.isPlayerPathable()) {
				setLocation(new Location(x, y));
				Game.instance.getTilemap()[x][y].onPlayerEnter();
			}
		}
	}

	public void tick() {
		if (timeFrozen > 0) {
			timeFrozen--;
		}
		// postcondition check
		assert (timeFrozen >= 0);
	}

	public void freeze (int time) {
		if (time < 1) {
			throw new IllegalArgumentException("Time to freeze cannot be less than 1.");
		}
		timeFrozen += time;
	}
}
