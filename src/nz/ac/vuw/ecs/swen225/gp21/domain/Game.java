package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rhys Adcock 300419040
 *
 *         Game holds the player and tilemap and facilitates logic of gameplay
 *         components.
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
	private boolean levelComplete = false;

	/**
	 *
	 * @param levelComplete Set state of current level completion.
	 */
	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	/**
	 *
	 * @return Whether the current level has been completed.
	 */
	public boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 *
	 * @return Total number of treasures in a level.
	 */
	public int getTotalTreasures() {
		return totalTreasures;
	}

	/**
	 *
	 * @return Number of treausre collected.
	 */
	public int getCollectedTreasures() {
		return collectedTreasures;
	}

	/**
	 *
	 * @return The ExitLock for this level.
	 */
	public ExitLockTile getExitLock() {
		return exitLock;
	}

	/**
	 *
	 * @return List of actors.
	 */
	public List<Actor> getActors() {
		return actors;
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
		BLUE, YELLOW, RED, GREEN
	}

	/**
	 * Sets singleton instance to this.
	 */
	public Game() {
		instance = this;
		observers = new ArrayList<Observer>();
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
	 * @param tile
	 */
	public void setTile(Tile tile) {
		tilemap[tile.getLocation().getX()][tile.getLocation().getY()] = tile;
	}

	/**
	 * @param d The direction for player movement
	 */
	public void inputDirection(Direction d) {
		if (d == null){
			throw new IllegalArgumentException("Direction cannot be null.");
		}
		player.move(d);
	}

	/**
	 * Causes a gameplay tick to occur.
	 */
	public void tick() {
		player.tick();
		Tile playerTile = tilemap[player.getLocation().getX()][player.getLocation().getY()];
		playerTile.onPlayerTick();
		ArrayList<Actor> toRemove = new ArrayList<Actor>();
		for (Actor a : actors) {
			a.tick();
			if (a.shouldRemove) {
				toRemove.add(a);
			}
		}
		for (Actor a : toRemove) {
			actors.remove(a);
		}
	}

	/**
	 * Add an observer to the list of observers.
	 * @param obs
	 */
	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	/**
	 * setups up the initial state of the game.
	 * @param tilemap
	 * @param player
	 * @param keysHeld
	 * @param totalTreasure
	 * @param collectedTreasures
	 * @param exitLock
	 * @param  actors
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
	 * @return The keys already collected.
	 */
	public HashMap<KeyColour, Integer> getKeysHeld() {
		return keysHeld;
	}

	/**
	 * Add a key of the specified colour to the heldKeys list.
	 * @param colour
	 */
	public void addKey(KeyColour colour) {
		keysHeld.put(colour, keysHeld.get(colour) + 1);
	}

	/**
	 * Remove a key of the specified colour from the heldKeys list.
	 * @param colour
	 */
	public void removeKey(KeyColour colour) {
		if (keysHeld.get(colour) < 1) {
			throw new IllegalArgumentException("Cannot remove a key when number held is less than 1.");
		}
		keysHeld.put(colour, keysHeld.get(colour) - 1);
		// postcondition check
		assert (keysHeld.get(colour) >= 0);
	}

	/**
	 * Adds a treausre to the collectedTreasure count and removes the exitLock if all have been collected.
	 */
	public void collectTreasure() {
		collectedTreasures++;
		if (collectedTreasures == totalTreasures) {
			exitLock.removeTile();
		}
		if (collectedTreasures > totalTreasures) {
			throw new IllegalStateException("Collected treasure count cannot exceed total treasures");
		}
	}

	/**
	 * Notify observers that a infoTile has been stepped on.
	 * @param message
	 */
	public void notifyObserversInfoTile(String message){
		for (Observer o : observers) {
			o.enterInfoTile(message);
		}
	}

	/**
	 * Notify observers that the current level has ended.
	 */
	public void notifyObserversLevelEnded() {
		Game.instance.setLevelComplete(true);
		for (Observer o : observers) {
			o.levelEnd();
		}
	}
}
