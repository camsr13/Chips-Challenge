package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
/**
 * A Modified JLabel base which can be used for objects that aren't part of the board
 * and animate when they move.
 * @author Jac Clarke
 *
 */
abstract class Animatable extends JLabel {
	protected Game game;
	protected ImageIcon[] images = {null, null, null, null};
	protected JLabel imageContainer;
	
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
		this.setIcon(images[dir]);
	}
	
}
