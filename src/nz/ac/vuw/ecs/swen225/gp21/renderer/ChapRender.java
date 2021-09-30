package nz.ac.vuw.ecs.swen225.gp21.renderer;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.renderer.BoardRender.Direction;

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
	private final int tileSize;
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
		super.currentDir = Direction.DOWN;
		this.tileSize = tileSize;
		loadImages();
		//set initial size
		this.setSize(tileSize,tileSize);
		this.setVisible(true);
		update();
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
	Location getBoardLocation() {
		// TODO Auto-generated method stub
		return game.getPlayer().getLocation();
	}

}
