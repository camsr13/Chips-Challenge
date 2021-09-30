package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender.Direction;
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
	protected Direction currentDir;
	
	/**
	 * the number of frames in the animatable
	 */
	protected int frames;
	
	private int currentFrame = 0;
	private int framesLeft = 0;
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
	 * Animates movement of the object in the specified direction
	 * @param dir
	 */
	abstract void animate(int dir);
	
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
	 * Updates the facing direction of the object
	 */
	void update() {
		//Location curLocation = getBoardLocation();
		int[] moveMatrix = getMoved();
		if ( moveMatrix != null ) {
			switch (moveMatrix[0]) {
				case(-1):
					currentDir = Direction.LEFT;
					break;
				case(1):
					currentDir = Direction.RIGHT;
					break;
			}
			switch (moveMatrix[1]) {
			case(-1):
				currentDir = Direction.UP;
				break;
			case(1):
				currentDir = Direction.DOWN;
				break;
			}
			currentFrame = 0;
			framesLeft = frames - 1;
			oldLocation = getBoardLocation();
		} else if (framesLeft > 0) {
			currentFrame += 1;
			framesLeft--;
		} else {
			currentFrame = 0;
		}
		System.out.println(currentFrame + " " + framesLeft);
		int width = (int) Math.round( this.getWidth() * scale );
		int height = (int) Math.round( this.getHeight() * scale );
		
		int x = (int) Math.round(this.getBounds().x * scale);
		int y = (int) Math.round(this.getBounds().y * scale);
		int image = currentFrame + (currentDir.ordinal() *  frames);
		System.out.println(currentDir.ordinal() * frames);
		this.setBounds(x,y, width, height);
		Image newImage = images.get(image).getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
		this.setIcon(new ImageIcon(newImage));
		scale = 1;
	}
	
}