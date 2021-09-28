package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

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
	 * @param scale 
	 */
	protected ChapRender(Game game, double scale) {
		this.game = game;
		this.scale = scale;
		loadImages();
		//set inital size
		this.setSize((int) Math.round(images[0].getWidth()),(int) Math.round(images[0].getHeight()));
		this.setVisible(true);
		update(currentDir);
	}
	
	/**
	 * @param scale
	 */
	protected void setScale(double scale) {
		this.scale =  scale;
		update(currentDir);
	}
	
	
	@Override
	void loadImages() {
		for( int i = 0; i < 4; i++ ) {
			URL nameUrl = getClass().getResource(fileNames[i]);
			try {
				images[i] = ImageIO.read(new File(nameUrl.getPath()));
			} catch (IOException e) {
				throw new Error("Unable to load images for chap");
			}
		}
		
	}



	@Override
	void animate(int dir) {
		// TODO Make chap animate in a direction
		
	}

}
