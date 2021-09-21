package test.nz.ac.vuw.ecs.swen225.gp21.render;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender;

/**
 * Test file to load the render class independently of the rest of the game for testing
 * @author Jac Clarke
 *
 */
public class RenderTest {
	private static Tile[][] board = {
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(new Location(2, 4)), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new FreeTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)},
			{new WallTile(null),new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null), new WallTile(null)}
	};
	/**
	 * Creates a new instance of the renderer, and displays it in a frame
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		Player p = new Player(new Location(1,1));
		g.setupGame(board, p, null);
		BoardRender render = new BoardRender(g);
		JFrame frame = new JFrame();
		frame.setSize(1280, 1024);
		frame.setVisible(true);
		frame.add(render.getPanel());
		//p.setLocation(new Location(5,5));
		//render.update();
		frame.revalidate();
		frame.repaint();
	}
}
