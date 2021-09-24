package nz.ac.vuw.ecs.swen225.gp21.renderer;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
/**
 * Main rendered class responsible for initialising all sub classes
 * and returning the final assembled render to the app package
 * @author Jac Clarke
 *
 */
public class BoardRender {
	//private Game game;
	private JPanel boardPanel;
	private JLayeredPane basePane = new JLayeredPane();
	private ChapRender chapIcon;
	
	private static final int squareSize = 128;
	private static final int panelHeight = 9;
	private static final int panelWidth = panelHeight;
	
	/**
	 * A Simple enum to keep track of what direction non board objects are facing
	 * @author Jac Clarke
	 *
	 */
	public enum Direction  {
		DOWN,	
		RIGHT,
		UP,
		LEFT
	}
	
	/**
	 * Generates board objects and puts them into the output layered pane
	 * @param game
	 */
	public BoardRender(Game game) {
		//this.game = game;
		//loadImages();
		chapIcon = new ChapRender();
		chapIcon.setBounds(panelHeight/2 * squareSize, panelWidth/2 * squareSize, squareSize, squareSize);
		chapIcon.setOpaque(false);

		boardPanel = new BoardPanel(game);
		boardPanel.setVisible(true);
		boardPanel.setBounds(0,0, panelWidth * squareSize, panelHeight * squareSize);
		
		basePane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
		basePane.add(chapIcon,JLayeredPane.PALETTE_LAYER);
		basePane.setVisible(true);
		
	}
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 * @param dir Direction moved
	 */
	public void update(Direction dir) {
		chapIcon.update(dir.ordinal());
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	
	//public void setScale(int width) {
	//	
	//}
	
	
	/**
	 * Returns the entire render as a layered pane
	 * @return JLayeredPane
	 */
	public JLayeredPane getPane() {
		return basePane;
	}
	
	
}
