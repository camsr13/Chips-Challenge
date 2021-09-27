package nz.ac.vuw.ecs.swen225.gp21.domain;

public class FreezeActor extends Actor {

	public Game.Direction currentDirection;

	public FreezeActor(Location location, Game.Direction currentDirection) {
		super(location);
		this.currentDirection = currentDirection;
	}

	@Override
	public void tick() {
		move();
		// TODO: check if touching player

	}

	private void move(){
		int x = -1;
		int y = -1;
		if (currentDirection == Game.Direction.UP) {
			x = location.getX();
			y = location.getY() - 1;
		} else if (currentDirection == Game.Direction.DOWN) {
			x = location.getX();
			y = location.getY() + 1;
		} else if (currentDirection == Game.Direction.LEFT) {
			x = location.getX() - 1;
			y = location.getY();
		} else if (currentDirection == Game.Direction.RIGHT) {
			x = location.getX() + 1;
			y = location.getY();
		}
		if (x < 0 || y < 0) {
			// TODO: bad movement case
		} else {
			Tile newTile = Game.instance.getTilemap()[x][y];
			if (newTile.isActorPathable()){
				setLocation(new Location(x, y));
			}
		}
	}

	@Override
	public void onPlayerEnter() {
		freezePlayer();
	}

	private void freezePlayer(){
		this.shouldRemove = true;
		Game.instance.getPlayer().freeze(3);
	}
}