package nz.ac.vuw.ecs.swen225.gp21.domain;

public class ArrowTile extends Tile{

    private Game.Direction direction;

    /**
     * @param location This tiles location
     */
    public ArrowTile(Location location, Game.Direction direction) {
        super(location, true);
        this.direction = direction;
    }

    @Override
    public void onPlayerEnter() {

    }

    @Override
    public String toChar() {
        return "a";
    }

    @Override
    public void onPlayerTick(){
        Game.instance.getPlayer().move(direction);
    }

    public Game.Direction getDirection(){
        return direction;
    }
}
