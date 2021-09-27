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
	private BoardPanel boardPanel;
	private JLayeredPane basePane = new JLayeredPane();
	private ChapRender chapIcon;
	
	/**
	 * Defines the image size
	 */
	static final int squareSize = 128;
	private static final int panelHeight = 9;
	private static final int panelWidth = panelHeight;
	
	/**
	 * A Simple enum to keep track of what direction non board objects are facing
	 * @author Jac Clarke
	 *
	 */
	public enum Direction  {
		/**
		 * Player facing down
		 */
		DOWN,	
		/**
		 * Player facing right
		 */
		RIGHT,
		/**
		 * Player facing up
		 */
		UP,
		/**
		 * Player facing left
		 */
		LEFT
	}
	
	/**
	 * Generates board objects and puts them into the output layered pane
	 * @param game
	 * @param size  
	 */
	public BoardRender(Game game, int size) {
		
		double initScale = getScale(size);
		int scaledSquare = (int) Math.round(initScale*squareSize);
		
		chapIcon = new ChapRender(game, initScale);
		chapIcon.setBounds(6 * scaledSquare/2, 6 * scaledSquare/2, scaledSquare, scaledSquare);
		chapIcon.setOpaque(false);

		boardPanel = new BoardPanel(game, initScale);
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
	/**
	 * 
	 */
	private double getScale(int size) {
		return 1.0 * size / (squareSize * panelWidth);
	}
	
	/**
	 * Updates the size of the render
	 * @param scale
	 */
	public void setSize(int size) {
		double scale = getScale(size);
		
		basePane.setSize(size, size);
		chapIcon.setScale(scale);
		boardPanel.setScale(scale);
	}
	
	
	/**
	 * Returns the entire render as a layered pane
	 * @return JLayeredPane
	 */
	public JLayeredPane getPane() {
		return basePane;
	}
	
	
}
