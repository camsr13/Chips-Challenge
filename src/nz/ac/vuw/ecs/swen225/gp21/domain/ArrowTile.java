package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         ArrowTiles will move the player in their stored direction when a tick occurs while the player is standing on them.
 */

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
        super(l, true);
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
        Game.instance.getPlayer().move(getDirection());
    }

    /**
     *
     * @return this tiles direction
     */
    public Game.Direction getDirection() {
        return direction;
    }
}
