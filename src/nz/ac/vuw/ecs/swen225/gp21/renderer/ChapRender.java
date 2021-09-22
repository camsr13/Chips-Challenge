package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;

/**
 * An extended JLable which handles the rendering and animation of the players character "Chap".
 * Chap needs to be animated separate from the board so uses Animatable as a template
 * @author Jac Clarke
 *
 */
class ChapRender extends Animatable {
	private final String[] fileNames = {
			"images/Chap.png",
			"images/Chap_R.png",
			"images/Chap_B.png",
			"images/Chap_L.png",
	};

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 2763313205526639958L;
	/**
	 * Creates a JPanel type object which represents the player
	 * @param game
	 */
	protected ChapRender(Game game) {
		this.game = game;
		
		loadImages();
		this.setIcon(images[0]);
		this.setVisible(true);
	}
	
	
	@Override
	void loadImages() {
		for( int i = 0; i < 4; i++ ) {
			images[i] = new ImageIcon(this.getClass().getResource(fileNames[i]));
			if(images[i].getImageLoadStatus() != MediaTracker.COMPLETE) {
				throw new Error("Failed to load the images for Chap");
			}
		}
		
	}



	@Override
	void animate(int dir) {
		// TODO Make chap animate in a direction
		
	}

}
