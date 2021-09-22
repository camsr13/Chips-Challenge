package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.HashMap;
import java.util.List;

/**
 * @author Rhys Adcock
 *
 *         Game holds the player and tilemap and facilitates logic of gameplay
 *         components
 */
public class Game {

	public static Game instance;

	private Tile[][] tilemap;
	private Player player;
	private List<Observer> observers;
	private HashMap<KeyColour, Boolean> keysHeld;

	/**
	 * @author Rhysa
	 *
	 */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public enum KeyColour {
		BLUE, YELLOW
	}

	/**
	 * Singleton pattern.
	 */
	public Game() {
		instance = this;
	}

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
	 * Overwrites the tile in tilemap using given tiles location.
	 * 
	 * @param tile
	 */
	public void setTile(Tile tile) {
		tilemap[tile.getLocation().getX()][tile.getLocation().getY()] = tile;
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

	/**
	 * Add an observer to the list of observers
	 * 
	 * @param obs
	 */
	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	/**
	 * setups up the initial state of the game
	 * 
	 * @param tilemap
	 * @param player
	 */
	public void setupGame(Tile[][] tilemap, Player player, HashMap<KeyColour, Boolean> keysHeld) {
		// TODO: null checks
		this.tilemap = tilemap;
		this.player = player;
		this.keysHeld = keysHeld;
	}

	/**
	 * 
	 * @return The keys already collected.
	 */
	public HashMap<KeyColour, Boolean> getKeysHeld() {
		return keysHeld;

	}

	/**
	 * Add a key of the specified colour to the heldKeys list.
	 * 
	 * @param colour
	 */
	public void addKey(KeyColour colour) {
		keysHeld.put(colour, true);
	}
}
