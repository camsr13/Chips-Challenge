package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.Image;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
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
	protected int currentDir = Direction.DOWN.ordinal();
	/**
	 * 
	 */
	protected Game game;
	/**
	 * buffered images for scaling and animating
	 */
	protected BufferedImage[] images = {null, null, null, null};
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
	 * Updates the facing direction of the object
	 * @param dir
	 */
	void update(Integer dir) {
		currentDir = dir;
		int width = (int) Math.round( this.getWidth() * scale );
		int height = (int) Math.round( this.getHeight() * scale );
		
		int x = (int) Math.round(this.getBounds().x * scale);
		int y = (int) Math.round(this.getBounds().y * scale);
		this.setBounds(x,y, width, height);
		Image newImage = images[dir].getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
		this.setIcon(new ImageIcon(newImage));
		scale = 1;
	}
	
}