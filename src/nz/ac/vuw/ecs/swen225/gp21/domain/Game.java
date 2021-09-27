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

	/**
	 * Singleton pattern
	 */
	public static Game instance;

	private Tile[][] tilemap;
	private Player player;
	private List<Observer> observers;
	private HashMap<KeyColour, Integer> keysHeld;
	private int totalTreasures;
	private int collectedTreasures;
	private ExitLockTile exitLock;
	private List<Actor> actors;

	public int getTotalTreasures() {
		return totalTreasures;
	}

	public int getCollectedTreasures() {
		return collectedTreasures;
	}

	public ExitLockTile getExitLock() {
		return exitLock;
	}

	/**
	 * @author Rhysa
	 *
	 */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	/**
	 * @author Rhysa
	 *
	 */
	public enum KeyColour {
		BLUE, YELLOW
	}

	/**
	 * Sets singleton instance to this
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
	 * @return The tilemap array
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
	 * @param keysHeld
	 */
	public void setupGame(Tile[][] tilemap, Player player, HashMap<KeyColour, Integer> keysHeld, int totalTreasure,
			int collectedTreasures, ExitLockTile exitLock, List<Actor> actors) {
		// TODO: null checks
		this.tilemap = tilemap;
		this.player = player;
		this.keysHeld = keysHeld;
		this.totalTreasures = totalTreasure;
		this.collectedTreasures = collectedTreasures;
		this.exitLock = exitLock;
		this.actors = actors;
	}

	/**
	 * 
	 * @return The keys already collected.
	 */
	public HashMap<KeyColour, Integer> getKeysHeld() {
		return keysHeld;

	}

	/**
	 * Add a key of the specified colour to the heldKeys list.
	 * 
	 * @param colour
	 */
	public void addKey(KeyColour colour) {
		// TODO: checks
		keysHeld.put(colour, keysHeld.get(colour) + 1);
	}

	public void removeKey(KeyColour colour) {
		// TODO: checks
		keysHeld.put(colour, keysHeld.get(colour) - 1);
	}

	public void collectTreasure() {
		collectedTreasures++;
		// TODO: pre and post condition checks
		if (collectedTreasures == totalTreasures) {
			exitLock.removeTile();
		}
	}
}
