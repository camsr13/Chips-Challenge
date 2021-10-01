package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * @author Jac Clarke
 *
 */
class FreezeActorImage extends ActorImage {
	private FreezeActor actor;
	private Game game;
	private BufferedImage curImage;
	private int tileSize;
	private static final String sheetName = "images/Freeze_Actor_Sheet.png";
	private static final int frames = 4;
	private static List<BufferedImage> freezeImages = null;
	private Game.Direction currentDir =  Game.Direction.DOWN;
	
	
	/**
	 * Draws the image for the Freeze Actor onto the booard
	 * @param game
	 * @param actor
	 * @param tileSize
	 */
	protected FreezeActorImage(Game game, FreezeActor actor, int tileSize) {
		super(actor, tileSize, frames);
		this.actor = actor;
		this.game = game;
		this.tileSize = tileSize;
		
		if (freezeImages == null) {
			super.loadImages(sheetName);
		}
	}
	
	@Override
	protected
	List<BufferedImage> getImages(){
		return freezeImages;
	}
	
	@Override 
	void drawActor(Graphics2D g, Location chapPos) {
		for (Actor a : game.getActors()) {
			if(a instanceof FreezeActor) {
				FreezeActor f = (FreezeActor) a;
				Location loc = f.getLocation();
				int x = chapPos.getX() - loc.getX();
				int y = chapPos.getY() - loc.getY();
				if(x > -6 && x < 6 && y > -6 && y < 6) {
					//int x = tileSize * loc.getX();
					//g.drawImage(curImage, );
				}
			}
		}
	}
	
	protected void setDirection(Game.Direction currentDir) {
		this.currentDir = currentDir;
	}

	@Override
	protected int getCurDirection() {
		return currentDir.ordinal();
	}
}
