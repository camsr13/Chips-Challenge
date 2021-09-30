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
	protected double scale = 1.0;
	
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
	 * @return
	 */
	abstract Location getBoardLocation();
	
	/**
	 * Sees if the animatable has moved
	 * @return Direction of movement
	 */
	public int[] getMoved() {
		int[] dir = null;
		Location curLocation = getBoardLocation();
		if (!oldLocation.equals(curLocation)) {
			//figure out direction moved
			int dy = oldLocation.getY() - curLocation.getY();
			int dx = oldLocation .getX() - curLocation.getX();
			dir = new int[] {dx, dy};
		}
		oldLocation = curLocation;
		return dir;
	}
	
	/**
	 * Updates the facing direction of the object
	 */
	void update() {
		//Location curLocation = getBoardLocation();
		
		int width = (int) Math.round( this.getWidth() * scale );
		int height = (int) Math.round( this.getHeight() * scale );
		
		int x = (int) Math.round(this.getBounds().x * scale);
		int y = (int) Math.round(this.getBounds().y * scale);
		this.setBounds(x,y, width, height);
		Image newImage = images.get(0).getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
		this.setIcon(new ImageIcon(newImage));
		scale = 1;
	}
	
}