package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;

/**
 * Base abstract class to store and update actor images on the board from
 * SudentID: 300511942
 * @author Jac Clarke
 *
 */
abstract class ActorImage {
	private int tileSize;
	private int frame = 0;
	private int totalFrames;
	/**
	 * The current image of the actor
	 */
	protected BufferedImage image;
	
	/**
	 * Initializes the values needed for functions that apply to all actorImages
	 * @param actor the actor to display the images for
	 * @param tileSize the size of the images to get and the size of the board tiles
	 * @param totalFrames the number of frames in the sheet for each direction
	 */
	protected ActorImage(Actor actor,int tileSize, int totalFrames){
		//this.actor = actor;
		this.tileSize = tileSize;
		this.totalFrames = totalFrames;
	}
	
	/**
	 * Gets the image list for this actor
	 * @return the list of images
	 */
	protected abstract List<BufferedImage> getImages();
	
	/**
	 * Gets the current direction to display the image of as an integer
	 * (Up = 0, Down = 1, Left = 2, Right = 3)
	 * @return integer (0-3) indicating direction
	 */
	protected abstract int getCurDirection();
	
	/**
	 * Draws the actor relative to chaps position, if the actor has a location
	 * @param g the graphic object to draw to
	 * @param chapPos the position of the player
	 * @param offsets sets the offset for the player
	 * @param board the board to assign the images to
	 */
	abstract void drawActor(Graphics2D g, Location chapPos,int[] offsets, JPanel board);
	
	/**Gets the next frame of the animation
	 * @return true if the next frame is the start frame
	 */
	protected boolean setNextFrame() {
		image = this.getImages().get(frame + (totalFrames* getCurDirection()));
		frame++;
		if(frame == totalFrames) {
			frame = 0;
			return true;
		}
		return false;
	}
	/**
	 * Sets the image to frame one of the current direction
	 */
	protected void setImageByDir() {
		image = this.getImages().get((totalFrames* getCurDirection()));
	}
	
	
	/**
	 * Loads all the images into the image array, from the sheet given by the path of sheetname
	 * @param sheetName the relative path to the image sheet
	 * @return a list of images loaded from sheet Name
	 */
	protected List<BufferedImage> loadImages(String sheetName) {
		//load the sheet
		BufferedImage sheet;
		
		
		try {
		URL sheetURL = this.getClass().getResource(sheetName);
			sheet = ImageIO.read(new File(sheetURL.getPath()));
		} catch (IOException e) {
			throw new Error("Unable to load sprite sheet for freeze actor");
		}
		
		//split the sheet
		List<BufferedImage> images = new ArrayList<BufferedImage>();
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
		return images;
	}

	
	
}
