package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

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
	
	private static final int chapFrames = 4;
	/**
	 * Creates a JPanel type object which represents the player
	 * @param game
	 * @param scale 
	 * @param tileSize 
	 */
	protected ChapRender(Game game, double scale, int tileSize) {
		super.frames = chapFrames;
		super.game = game;
		super.scale = scale;
		super.oldLocation = game.getPlayer().getLocation();
		super.currentDir = Game.Direction.DOWN;
		super.tileSize = tileSize;
		loadImages();
		setScale(scale);
		//set initial size
		this.setSize(tileScaled,tileScaled);
		this.setVisible(true);
		update();
	}
	
	@Override
	void setBounds(){
		
	}
	
	/**
	 * Loads the sprite sheet for chap
	 */
	@Override
	void loadImages() {
		BufferedImage sheet;
		try {
			URL nameUrl = this.getClass().getResource(sheetName);
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
	Location getBoardLocation() {
		// TODO Auto-generated method stub
		return game.getPlayer().getLocation();
	}

}
