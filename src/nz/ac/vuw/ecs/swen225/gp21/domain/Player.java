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
		// TODO: check tile is valid
	}

	/**
	 * @param d       The direction for player movement
	 */
	public void move(Game.Direction d) {
		if (timeFrozen > 0){
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
		if (x < 0 || y < 0) {
			// TODO: bad movement case
		} else {
			Tile newTile = Game.instance.getTilemap()[x][y];
			if (newTile.isPlayerPathable()) {
				setLocation(new Location(x, y));
				Game.instance.getTilemap()[x][y].onPlayerEnter();
			}
		}
	}

	public void tick(){
		if (timeFrozen > 0){
			timeFrozen--;
		}
	}

	public void freeze (int time){
		timeFrozen += time;
	}
}
