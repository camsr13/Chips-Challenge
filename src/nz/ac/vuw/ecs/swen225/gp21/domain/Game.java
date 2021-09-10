package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.List;

/**
 * @author Rhys Adcock
 *
 *         Game holds the player and tilemap and facilitates logic of gameplay
 *         components
 */
public class Game {

	public Game() {

	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private Tile[][] tilemap;
	private Player player;
	private List<Observer> observers;

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

	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	/**
	 * setups up the initial state of the game
	 * 
	 * @param tilemap
	 * @param player
	 */
	public void setupGame(Tile[][] tilemap, Player player) {
		// TODO: null checks
		this.tilemap = tilemap;
		this.player = player;
	}

	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		System.out.println("hello world!");
	}
}
