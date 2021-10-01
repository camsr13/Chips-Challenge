package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * @author Rhys Adcock 300419040
 *
 *         A tile that will freeze the player when they stand on it.
 */

public class FreezeTile extends Tile {

    /**
     * @param location This tiles location
     */
    public FreezeTile(Location location) {
        super(location, true);
    }

    @Override
    public void onPlayerEnter() {
        Game.instance.getPlayer().freeze(2);
    }

    @Override
    public String toChar() {
        return "f";
    }
}
