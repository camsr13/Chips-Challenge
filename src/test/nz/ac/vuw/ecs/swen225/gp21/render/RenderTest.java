package test.nz.ac.vuw.ecs.swen225.gp21.render;

import javax.swing.JFrame;


import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game.KeyColour;
import nz.ac.vuw.ecs.swen225.gp21.domain.KeyTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.LockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Test/demo file to load the render class independently of the rest of the game for testing
 * @author Jac Clarke
 *
 */
public class RenderTest {
	private static Tile[][] board = {
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null),  new KeyTile(null, Game.KeyColour.BLUE), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new LockTile(null, Game.KeyColour.YELLOW), new FreeTile(null), new KeyTile(null, Game.KeyColour.BLUE), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new LockTile(null, Game.KeyColour.BLUE), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new ExitLockTile(null), new ExitTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)}
	};
	/**
	 * Creates a new instance of the renderer, and displays it in a frame
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Game g = new Game();
		Player p = new Player(new Location(6,6), 0);
		FreezeActor actor = new FreezeActor(new Location(2,2), Game.Direction.UP);
		List<Actor> actors = new ArrayList<Actor>();
		actors.add(actor);
		g.setupGame(board, p, new HashMap<KeyColour, Integer>(), 0, 0, new ExitLockTile(null), actors);
		
		BoardRender render = new BoardRender(g);
		render.initaliseBoard(600);
		JFrame frame = new JFrame();
		frame.setSize(1280, 1024);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(render.getPane());
		
		g.inputDirection(Game.Direction.LEFT);
		render.updateChap();
		actor.setLocation(new Location(2,3));
		render.updateOnTick();
		
	}
}
