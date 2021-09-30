package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * Handles the rendering of the non player enemies and their movements
 * Behaves like a modifies JLabel component
 * @author Jac Clarke
 *
 */
class ActorRender extends Animatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6775023084165233366L;
	private Actor actor;
	private static final String sheetName = "images/Freeze_Actor_Sheet.png";
	private int tileSize;
	
	/**
	 * Renders the individual actors on top of the board pane.
	 * Each actor checks for updates individually
	 * @param actor
	 */
	protected ActorRender(Actor actor, int tileSize) {
		this.actor = actor;
		this.tileSize = tileSize;
	}

	@Override
	void loadImages() {
		BufferedImage sheet;
		try {
		URL sheetURL = getClass().getResource(sheetName);
			sheet = ImageIO.read(new File(sheetURL.getPath()));
		} catch (IOException e) {
			throw new Error("Unable to load sprite sheet for freeze actor");
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
		return actor.getLocation();
	}
	
	/**
	 * Thread that allows the animation to be run separate of other tasks
	 * @author Jac Clarke
	 */
	protected class animate extends Thread {
		public void run() {
			
		}
	}

}
