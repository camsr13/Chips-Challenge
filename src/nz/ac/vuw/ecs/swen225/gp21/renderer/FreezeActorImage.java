package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.List;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * ActorImage that renders a Freeze actor onto the board of the game
 * @author Jac Clarke
 */
class FreezeActorImage extends ActorImage {
	private FreezeActor actor;
	private int tileSize;
	private static final String sheetName = "images/Freeze_Actor_Sheet.png";
	private static final int frames = 4;
	private static List<BufferedImage> freezeImages = null;
	private Game.Direction currentDir =  Game.Direction.DOWN;
	
	/**
	 * Draws the image for the Freeze Actor onto the board
	 * @param actor the actor to be rendered
	 * @param tileSize the tile size for the board
	 */
	protected FreezeActorImage(FreezeActor actor, int tileSize) {
		super(actor, tileSize, frames);
		this.actor = actor;
		this.tileSize = tileSize;
		
		if (freezeImages == null) {
			freezeImages = super.loadImages(sheetName);
		}
		super.setImageByDir();
	}
	/**
	 * Gets the sprites of a freeze actor
	 */
	@Override
	protected List<BufferedImage> getImages(){
		return freezeImages;
	}
	/**
	 * Updates the actors location after a tick
	 */
	protected void upDateLocation() {
		
	}
	/**
	 * Draws the freeze actor on the board using graphics
	 */
	@Override 
	void drawActor(Graphics2D g, Location chapPos, int[] offsets,JPanel board) {
				Location loc = actor.getLocation();
				int x = loc.getX() - chapPos.getX() ;
				int y = loc.getY() - chapPos.getY() ;
				int relX = x  +4;
				int relY = y +4;
				if(x > -6 && x < 6 && y > -6 && y < 6) {
					g.drawImage(image, relX * tileSize + offsets[0], relY* tileSize + offsets[1] ,board);
				}
			
		}
	/**
	 * Sets the direction the freeze actor is facing
	 * @param currentDir
	 */
	protected void setDirection(Game.Direction currentDir) {
		this.currentDir = currentDir;
	}
	/**
	 * Gets the current direction of the freeze actor
	 */
	@Override
	protected int getCurDirection() {
		return currentDir.ordinal();
	}
}
