package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         FreezeActor moves in its current direction until it encounters a tile it cannot path through and turns
 *         around. If it encounters a player it will freeze them.
 */

public class FreezeActor extends Actor {

	/**
	 * This FreezeActors current direction for movement.
	 */
	public Game.Direction currentDirection;

	/**
	 *
	 * @param location
	 * @param currentDirection
	 */
	public FreezeActor(Location location, Game.Direction currentDirection) {
		super(location);
		this.currentDirection = currentDirection;
	}

	@Override
	public void tick() {
		move();
		if (Game.instance.getPlayer().getLocation().equals(getLocation())) {
			freezePlayer();
		}
	}

	private void move() {
		int x = -1;
		int y = -1;
		if (currentDirection == Game.Direction.UP) {
			x = getLocation().getX();
			y = getLocation().getY() - 1;
		} else if (currentDirection == Game.Direction.DOWN) {
			x = getLocation().getX();
			y = getLocation().getY() + 1;
		} else if (currentDirection == Game.Direction.LEFT) {
			x = getLocation().getX() - 1;
			y = getLocation().getY();
		} else if (currentDirection == Game.Direction.RIGHT) {
			x = getLocation().getX() + 1;
			y = getLocation().getY();
		}
		if (x < 0 || y < 0 || x > Game.instance.getTilemap().length || y > Game.instance.getTilemap()[0].length) {
			throw new IllegalStateException("Movement cannot exceed bounds of tile array.");
		} else {
			Tile newTile = Game.instance.getTilemap()[x][y];
			if (newTile.isActorPathable()) {
				setLocation(new Location(x, y));
			}
			else {
				// Reverse direction
				if (currentDirection == Game.Direction.UP) currentDirection = Game.Direction.DOWN;
				if (currentDirection == Game.Direction.DOWN) currentDirection = Game.Direction.UP;
				if (currentDirection == Game.Direction.LEFT) currentDirection = Game.Direction.RIGHT;
				if (currentDirection == Game.Direction.RIGHT) currentDirection = Game.Direction.LEFT;
			}
		}
	}

	@Override
	public void onPlayerEnter() {
		freezePlayer();
	}

	private void freezePlayer() {
		this.shouldRemove = true;
		Game.instance.getPlayer().freeze(3);
	}
}