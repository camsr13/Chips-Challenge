package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.image.BufferedImage;
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
	private final String sheetName = "images/chap_sprite_sheet.png";

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 2763313205526639958L;
	
	
	private final int tileSize;
	/**
	 * Creates a JPanel type object which represents the player
	 * @param game
	 * @param scale 
	 * @param tileSize 
	 */
	protected ChapRender(Game game, double scale, int tileSize) {
		this.game = game;
		this.scale = scale;
		this.tileSize = tileSize;
		loadImages();
		//set inital size
		this.setSize(tileSize,tileSize);
		this.setVisible(true);
		update(currentDir);
	}
	
	/**
	 * Updates the scale of chaps sprites
	 * @param scale
	 */
	protected void setScale(double scale) {
		this.scale = scale;
		update(currentDir);
	}
	
	/**
	 * Loads the sprite sheet for chap
	 */
	@Override
	void loadImages() {
		BufferedImage sheet;
		try {
			URL nameUrl = getClass().getResource(sheetName);
			sheet = ImageIO.read(new File(nameUrl.getPath()));
		} catch (IOException e) {
			throw new Error("Unable to load sprite sheet for chap");
		}
		
		int y = 0;
		int x = 0;
		int i = 0;
		while ( i < 16 ) {
			images.add(sheet.getSubimage(x, y,tileSize, tileSize));
			x += tileSize;
			if (x >= sheet.getWidth()) {
				y += tileSize;
				x = 0;
			}
			i++;
		}
		
		
	}



	@Override
	void animate(int dir) {
		// TODO Make chap animate in a direction
		
	}

}
