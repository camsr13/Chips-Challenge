package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock
 *
 *         Game holds the player and tilemap and facilitates logic of gameplay
 *         components
 */
public class Game {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private Tile[][] tilemap;
	private Player player;

	/**
	 * @return The player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return The tilemap
	 */
	public Tile[][] getTilemap() {
		return tilemap;
	}

	/**
	 * @param d The direction for player movement
	 */
	public void inputDirection(Direction d) {
		// TODO: check for nulls
		player.move(d, tilemap);
	}

	/**
	 * Causes a gameplay tick to occur
	 */
	public void tick() {

	}

	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		System.out.println("hello world!");
	}
}
