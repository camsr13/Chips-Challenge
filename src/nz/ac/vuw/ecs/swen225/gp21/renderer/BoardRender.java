package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	
	private List<ActorRender> actors;
	private int scaledTile;
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
	 */
	public BoardRender(Game game) {
		this.game = game;
	}
	
	/**
	 * Depreciated method, to allow main to run without changes
	 * @param game
	 * @param size
	 */
	@Deprecated
	public BoardRender(Game game, int size) {
		this.game = game;
		initaliseBoard(size);
	}
	
	/**
	 * Loads the board components 
	 * @param size
	 */
	public void initaliseBoard(int size) {
		
		scaledTile = (int) Math.floor(getScale(size) * tileSize);
		double initScale = (double) scaledTile/(double)tileSize;
		int chapPos = (int) Math.round(((scaledTile * (boardWidth))/2) - 1.5*scaledTile);
		
		List<Actor> gameActors = game.getActors();
		actors = new ArrayList<ActorRender>();
		if (gameActors != null) {
			for (int i = 0; i < gameActors.size(); i++) {
				actors.add(new ActorRender(gameActors.get(i), tileSize));
			}
		}
		
		chapIcon = new ChapRender(game, initScale, tileSize);
		chapIcon.setBounds(chapPos, chapPos, scaledTile, scaledTile);
		chapIcon.setOpaque(false);

		boardPanel = new BoardPanel(game, tileSize, boardWidth , initScale);
		boardPanel.setVisible(true);
		boardPanel.setBounds(0,0, scaledTile * (boardWidth-2), scaledTile * (boardWidth-2));
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		basePane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
		basePane.add(chapIcon,JLayeredPane.PALETTE_LAYER);
		basePane.setVisible(true);
	}
	

		
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 */
	public void update() {
		updateOnTick();
	}
	
	
	/**
	 * 
	 */
	public void updateOnTick() {
		
		int increment = 0;
		int[] chapMove = chapIcon.getMoved();
		
		//board animation
		int frames = 16;
		if(chapMove != null) {
			for (int i = 0; i < frames; i++) {
				
				increment += tileSize/frames;
				boardPanel.setOffsets(-(increment * chapMove[0]), -(increment * chapMove[1]));
				boardPanel.revalidate();
				boardPanel.repaint();
				
				if (i % (frames/4) == 0) {
					chapIcon.update();
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(62);
				} catch (InterruptedException e) {
					throw new Error("Animation interupted");
				}
			}
			
			boardPanel.setOffsets(0, 0);
			boardPanel.updateChapPos();
		}
		
		boardPanel.revalidate();
		boardPanel.repaint();
		
		
	}
	/**
	 * 
	 */
	private double getScale(int size) {
		return 1.0 * size / (panelSize);
	}
	
	/**
	 * Updates the size of the render
	 * @param size The desired size of the board
	 * @return The actual size the board was set too. Always smaller than the desired size.
	 */
	public int setSize(int size) {
		scaledTile = (int) Math.floor(getScale(size) * tileSize);
		double scale = (double) scaledTile / (double) tileSize;
		basePane.setSize(size, size);
		chapIcon.setScale(scale);
		boardPanel.setScale(scale);
		boardPanel.setBounds(0,0, scaledTile * (boardWidth - 2), scaledTile * (boardWidth - 2));
		return scaledTile * (boardWidth - 2);
	}
	
	/**
	 * Returns the entire render as a layered pane
	 * @return JLayeredPane
	 */
	public JLayeredPane getPane() {
		return basePane;
	}
	
	
}
