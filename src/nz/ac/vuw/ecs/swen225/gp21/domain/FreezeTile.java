package nz.ac.vuw.ecs.swen225.gp21.domain;

public class FreezeTile extends Tile {

    /**
     * @param location This tiles location
     */
    public FreezeTile(Location location) {
        super(location, true);
    }

    @Override
    public void onPlayerEnter() {
        Game.instance.getPlayer().freeze(1);
    }

    @Override
    public String toChar() {
        return "f";
    }
}
