package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
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
	private boolean isAnimating = false;
	
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
	 * @return The actual size
	 */
	public int initaliseBoard(int size) {
		
		scaledTile = (int) Math.floor(getScale(size) * tileSize);
		double initScale = (double) scaledTile/(double)tileSize;
		int chapPos = (int) Math.round(((scaledTile * (boardWidth))/2) - 1.5*scaledTile);
		
		List<Actor> gameActors = game.getActors();
		actors = new ArrayList<ActorRender>();
		if (gameActors != null) {
			for (int i = 0; i < gameActors.size(); i++) {
				ActorRender newActor = new ActorRender(game, (FreezeActor) gameActors.get(i), initScale, tileSize );
				actors.add(newActor);
				basePane.add(newActor, JLayeredPane.PALETTE_LAYER);
			}
		}
		System.out.println(scaledTile);
		chapIcon = new ChapRender(game, initScale, tileSize);
		chapIcon.setBounds(chapPos, chapPos, scaledTile, scaledTile);
		chapIcon.setOpaque(false);

		boardPanel = new BoardPanel(game, tileSize, boardWidth , initScale);
		boardPanel.setVisible(true);
		boardPanel.setBounds(0,0, scaledTile * (boardWidth-2), scaledTile * (boardWidth-2));
		
		basePane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
		basePane.add(chapIcon,JLayeredPane.MODAL_LAYER);
		basePane.setPreferredSize(new Dimension(scaledTile * 9,scaledTile * 9));
		basePane.setVisible(true);
		return scaledTile * boardWidth;
	}
	

	/**
	 * Inverts a game Direction
	 * @param dir Direction to be inverted
	 * @return Inverted game direction
	 */
	protected static Game.Direction invDir(Game.Direction dir){
		Game.Direction invDir = null;
		switch(dir) {
		case UP:
			invDir = Game.Direction.DOWN;
			break;
		case DOWN:
			invDir = Game.Direction.UP;
			break;
		case LEFT:
			invDir = Game.Direction.RIGHT;
			break;
		case RIGHT:
			invDir = Game.Direction.LEFT;
			break;
		}
		return invDir;
	}
	
	
	/**
	 * Updates and animates chaps position
	 */
	public void updateChap() {
		if (!isAnimating) {
			int[] chapMove = chapIcon.getMoved();
		
			//board animation
			if(chapMove != null) {
				isAnimating = true;
				
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					int totalFrames = 16;
					int frames = 0;
					int increment = 0;
					int[] chapMove = chapIcon.getMoved();
					@Override
					public void run() {
							
						increment += tileSize/totalFrames;
						boardPanel.setOffsets(-(increment * chapMove[0]), -(increment * chapMove[1]));
						boardPanel.revalidate();
						boardPanel.repaint();
							
							
						if (frames % (totalFrames/4) == 0) {
							chapIcon.update();
						}
							
						frames++;
						if(frames == totalFrames) {
							this.cancel();
							isAnimating = false;
							chapIcon.refreshLocation();
							boardPanel.setOffsets(0, 0);
							boardPanel.updateChapPos();
						}
					}
					
				}, 14, 14);
			
			
			}
		}
		
		boardPanel.revalidate();
		boardPanel.repaint();
		
	}
	
	
	/**
	 * Updates actors which move on ticks
	 */
	public void updateOnTick() {
		updateChap();
	}
	/**
	 * Sets the scale of the board
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
		basePane.setPreferredSize(new Dimension(scaledTile * 9,scaledTile * 9));
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
