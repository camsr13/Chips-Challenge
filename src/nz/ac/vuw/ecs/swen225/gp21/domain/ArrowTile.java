package nz.ac.vuw.ecs.swen225.gp21.domain;

public class ArrowTile extends Tile {

    /**
     * The direction this tile will push the player.
     */
    private Game.Direction direction;

    /**
     * @param l This tiles location
     * @param d This tiles direction
     */
    public ArrowTile(Location l, Game.Direction d) {
        super(location, true);
        this.direction = d;
    }

    @Override
    public void onPlayerEnter() {

    }

    @Override
    public String toChar() {
        return "a";
    }

    @Override
    public void onPlayerTick() {
        Game.instance.getPlayer().move(direction);
    }

    /**
     *
     * @return this tiles direction
     */
    public Game.Direction getDirection() {
        return direction;
    }
}
