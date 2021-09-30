package test.nz.ac.vuw.ecs.swen225.gp21.render;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.KeyTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.LockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender.Direction;

import java.awt.Color;
import java.util.concurrent.TimeUnit;/**
 * Test file to load the render class independently of the rest of the game for testing
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
		Player p = new Player(new Location(4,4), 0);
		g.setupGame(board, p, null, 0, 0, null, null);
		BoardRender render = new BoardRender(g);
		render.initaliseBoard(500);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 405, 405);
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		render.getPane().add(panel, JLayeredPane.MODAL_LAYER);
		JFrame frame = new JFrame();
		frame.setSize(1280, 1024);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(render.getPane());
		
		TimeUnit.SECONDS.sleep(1);
		p.setLocation(new Location(4,5));
		render.update(Direction.LEFT);
		TimeUnit.SECONDS.sleep(1);
		p.setLocation(new Location(3,5));
		render.update(Direction.LEFT);
		TimeUnit.SECONDS.sleep(1);
		p.setLocation(new Location(3,4));
		render.update(Direction.UP);
		TimeUnit.SECONDS.sleep(1);
		p.setLocation(new Location(4,4));
		render.update(Direction.RIGHT);
		
	}
}
