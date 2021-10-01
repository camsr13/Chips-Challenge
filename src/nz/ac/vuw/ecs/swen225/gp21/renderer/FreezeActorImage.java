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

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeActor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

public class FreezeActorImage extends ActorImage {
	private FreezeActor actor;
	private Game game;
	private BufferedImage curImage;
	private int tileSize;
	private static final String sheetName = "images/Freeze_Actor_Sheet.png";
	private static  ArrayList<BufferedImage> images = null;
	protected FreezeActorImage(FreezeActor actor, int tileSize) {
		this.actor = actor;
		this.tileSize = tileSize;
		if(images == null) {
			loadImages();
		}
	}
	
	private void loadImages() {
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
	void drawActor(Graphics2D g, Location chapPos, int tileSize) {
		for (Actor a : game.getActors()) {
			if(a instanceof FreezeActor) {
				FreezeActor f = (FreezeActor) a;
				Location loc = f.getLocation();
				int x = chapPos.getX() - loc.getX();
				int y = chapPos.getY() - loc.getY();
				//System.out.println(x +" " + y);
				//if() {
					//int x = tileSize * loc.getX();
					//g.drawImage(curImage, );
				//}
			}
		}
	}
}
