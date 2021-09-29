package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
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
	private Game game;
	
	/**
	 * Size of the tiles in the image file (pixels)
	 */
	private static final int tileSize = 64;
	/**
	 * desired board width to be rendered, including offscreen tiles for moves
	 */
	private static final int boardWidth = 11;
	private int panelSize = boardWidth * tileSize;
	
	private List<Actor> actors;
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
	public BoardRender(Game game) {
		this.game = game;
	}
	
	
	/**
	 * Loads the components re
	 * @param size
	 */
	public void initaliseBoard(int size) {
		double initScale = getScale(size);
		int scaledTile = (int) Math.round(initScale * tileSize);
		int chapPos = (int) Math.round(scaledTile * boardWidth/2 - (1.5 * scaledTile));
		
		actors = game.getActors();
		
		chapIcon = new ChapRender(game, initScale, tileSize);
		chapIcon.setBounds(chapPos, chapPos, scaledTile, scaledTile);
		chapIcon.setOpaque(false);

		boardPanel = new BoardPanel(game, tileSize, boardWidth , initScale);
		boardPanel.setVisible(true);
		boardPanel.setBounds(0,0, scaledTile * boardWidth, scaledTile * boardWidth);
		
		basePane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
		basePane.add(chapIcon,JLayeredPane.PALETTE_LAYER);
		basePane.setVisible(true);
	}
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 * @param dir Direction moved
	 */
	@Deprecated
	public void update(Direction dir) {
		chapIcon.update();
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	
	
	/**
	 * 
	 */
	public void updateOnTick() {
		chapIcon.update();
	}
	/**
	 * 
	 */
	private double getScale(int size) {
		return 1.0 * size / (panelSize);
	}
	
	/**
	 * Updates the size of the render
	 * @param size
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
