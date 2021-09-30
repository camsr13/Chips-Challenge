package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
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
	
	private static final int animationFrames = 4;
	
	
	/**
	 * Renders the individual actors on top of the board pane.
	 * Each actor checks for updates individually
	 * @param game 
	 * @param actor
	 * @param scale 
	 * @param tileSize 
	 */
	protected ActorRender(Game game, FreezeActor actor, double scale, int tileSize) {
		super.frames = animationFrames;
		super.game = game;
		this.actor = actor;
		this.tileSize = tileSize;
		super.currentDir = actor.currentDirection;
		super.oldLocation = actor.getLocation();
		loadImages();
		setScale(scale);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		this.setBounds(actor.getLocation().getX()*tileScaled,actor.getLocation().getY()*tileScaled,tileScaled, tileScaled);
		this.setSize(tileScaled, tileScaled);
		this.setVisible(true);
		update();
	}

	@Override
	void loadImages() {
		BufferedImage sheet;
		try {
		URL sheetURL = this.getClass().getResource(sheetName);
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
	 * Updates the position and plays the animation for the actor
	 */
	void animateSprite() {
		if(getMoved() != null) { 
			update();
			Animate gif = new Animate();
			gif.start();
			
		}
	}
	/**
	 * Offsets the sprite in an x y direction. Used to move the actor sprite about
	 * @param frameRate
	 * @param dir
	 */
	protected void offsetSprite(int frameRate, Game.Direction dir) {
		int x = this.getX();
		int y= this.getY();
		switch (dir) {
		case DOWN:
			y += tileScaled/frameRate;
			break;
		case UP:
			y -= tileScaled/frameRate;
			break;
		case RIGHT:
			x += tileScaled/frameRate;
			break;
		case LEFT:
			x -= tileScaled/frameRate;
		}
		//System.out.println();
		this.setBounds(x, y, tileScaled, tileScaled);
	}
	
	
	/**
	 * Thread that allows the animation to be run separate of other tasks
	 * @author Jac Clarke
	 */
	 class Animate extends Thread {
		public void run() {
			try {
				int frameRate = tileScaled;
				for(int f = 0; f < frameRate; f++) {
					offsetSprite(frameRate, currentDir);
					Thread.sleep(1000/frameRate);
					if(f % (frameRate / 4) == 0) {
						update();
					}
				}
				
			} catch (InterruptedException e) {
				//Restore interupted state
				Thread.currentThread().interrupt();
			}
		}
	}

}
