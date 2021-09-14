package nz.ac.vuw.ecs.swen225.gp21.render;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;

/**
 * Test file to load the render class independently of the rest of the game for testing
 * @author Jac Clarke
 *
 */
public class RenderTest {
	/**
	 * Creates a new instance of the renderer, and displays it in a frame
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		BoardRender render = new BoardRender(g);
		JFrame frame = new JFrame();
		frame.setSize(1280, 1024);
		frame.setVisible(true);
		frame.add(render.getPanel());
	}
}
