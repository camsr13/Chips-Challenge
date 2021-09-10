package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock
 *
 */
public class Location {

	int x;
	int y;

	/**
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {
		// TODO: check x and y aren't negative
		this.x = x;
		this.y = y;
	}

	/**
	 * @param l
	 * @return Copy of location parameter
	 */
	public static Location copy(Location l) {
		return new Location(l.x, l.y);
	}

	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}
}
