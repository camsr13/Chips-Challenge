package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * A Modified JLabel base which can be used for objects that aren't part of the board
 * and animate when they move.
 * @author Jac Clarke
 *
 */
abstract class Animatable extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	protected Game.Direction currentDir;
	
	/**
	 * the number of frames in the animatable
	 */
	protected int frames;
	
	private int currentFrame = 0;
	protected int framesLeft = 0;
	
	protected int tileSize;
	protected int tileScaled;
	/**
	 * 
	 */
	protected Location oldLocation;
	/**
	 * 
	 */
	protected Game game;
	/**
	 * buffered images for scaling and animating
	 */
	protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	/**
	 * Label that displays the image
	 */
	protected JLabel imageContainer;
	
	/**
	 * Scales the animatable
	 */
	double scale = 1.0;
	
	
	
	/**
	 * Loads the images for the animated object
	 */
	abstract void loadImages();
	
	/**
	 * Gets the location of the assigned object from the board
	 * @return Location 
	 */
	abstract Location getBoardLocation();
	
	/**
	 * Sees if the animatable has moved
	 * @return Direction of movement
	 */
	protected int[] getMoved() {
		int[] dir = null;
		Location curLocation = getBoardLocation();
		if (!oldLocation.equals(curLocation)) {
			//figure out direction moved
			int dy = curLocation.getY() - oldLocation.getY();
			int dx = curLocation .getX() - oldLocation.getX();
			dir = new int[] {dx, dy};
		}
		
		return dir;
	}
	
	
	/**
	 * Updates the scale of the sprites
	 * @param scale
	 */
	protected void setScale(double scale) {
		this.scale = scale;
		this.tileScaled =(int) (scale * tileSize);
		if(this.getWidth() > 0) {
			update();
		}
	}
	
	/**
	 * Sets the current frame of the JLabel
	 * @param next determines if to move to the next frame in the sequence
	 */
	protected void setFrame(boolean next) {
		if(next) {
			currentFrame = (currentFrame+1) % 4;
		}
		int width = (int) Math.round( this.getWidth() * scale );
		int height = (int) Math.round( this.getHeight() * scale );
		
		int x = (int) Math.round(this.getBounds().x * scale);
		int y = (int) Math.round(this.getBounds().y * scale);
		int image = currentFrame + (currentDir.ordinal() *  frames);
		this.setBounds(x,y, width, height);
		Image newImage = images.get(image).getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
		this.setIcon(new ImageIcon(newImage));
		scale = 1;
	}
	
	/**
	 * Converts a int table containing a x and y value into a Direction
	 * @param matrix matrix to be converted {x,y}
	 * @return Direction corresponding with the matrix
	 */
	protected static Game.Direction matrixToDir(int[] matrix){
		Game.Direction dir = null;
		switch (matrix[0]) {
		case(-1):
			dir = Game.Direction.LEFT;
			break;
		case(1):
			dir = Game.Direction.RIGHT;
			break;
		}
		switch (matrix[1]) {
		case(-1):
			dir = Game.Direction.UP;
			break;
		case(1):
			dir = Game.Direction.DOWN;
			break;
		}
		return dir;
	}
	
	void refreshChapPost() {
		oldLocation = getBoardLocation();
	}
	/**
	 * Updates the facing direction of the object
	 */
	void update() {
		boolean next = false;
		int[] moveMatrix = getMoved();
		
		if ( moveMatrix != null ) {
			currentDir = matrixToDir(moveMatrix);
			
			framesLeft = 4;
		}
		if(framesLeft == 0) {
			
		} else {
			framesLeft--;
			next = true;
		}
		setFrame(next);
	}
	
}