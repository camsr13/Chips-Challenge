package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.ArrowTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.FreezeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.InfoTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.KeyTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.LockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.TreasureTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;

/**
 * A Modified JPanel which paints the current board tiles into a panel
 * @author Jac Clarke
 *
 */
class BoardPanel extends JPanel {
	/**
	 * Serial Id to satisfy warnings
	 */
	private static final long serialVersionUID = -3951679614322867873L;
	
	//Constants
	private static final String tileImageLocation = "images/Tile_sprite_sheet.png";
	//translate games enum into image locations
	private static final int[] colourOrder = {1, 3, 0, 2};
	
	//Number of sprites to load
	private static final int numTiles = 16;
	
	private static final Class<?>[] tileOrder = { FreeTile.class, KeyTile.class, KeyTile.class, KeyTile.class,
											KeyTile.class, TreasureTile.class, FreezeTile.class, InfoTile.class,
											WallTile.class, LockTile.class, LockTile.class, LockTile.class,
											LockTile.class, ExitLockTile.class, ExitTile.class, ArrowTile.class
											};

	/**
	 * Size of the tiles in the image file (pixels)
	 */
	protected static final int tileSize = 64;
	/**
	 * desired board width to be rendered, including offscreen tiles for moves
	 */
	protected static final int boardWidth = 11;
	
	private Game game;
	private double scale = 1.0;
	private Map<Class<? extends Tile>, ArrayList<BufferedImage>> images = new HashMap<Class<? extends Tile>, ArrayList<BufferedImage>>();
	
	/**
	 * Panel that is responsible for retrieving and displaying the board from game
	 * @param game
	 * @param initialScale 
	 */
	protected BoardPanel(Game game, double initialScale) {
		this.scale = initialScale;
		this.game = game;
		loadTileSprites();
	};
	
	
	/**
	 * Overrides the default paint method with custom draw method
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		g2d.scale(scale, scale);
        draw(g2d);

	}
	
	/**
	 * Method which retrieves game data to draw the board. 
	 * Draws an extra hidden tile to allow for animation without full board refresh
	 * TODO: add smooth animated board updates
	 * @param g
	 */
	private void draw (Graphics2D g) {
		
		//BREAKPOINT: board info retrieved
		Tile[][] boardTiles = game.getTilemap();
		//BREAKPOINT: player info retrieved
		Location chapPos = game.getPlayer().getLocation();
		
		
		int i = 0;
		for(int y = chapPos.getY() - 4; y < chapPos.getY() + 5; y++) {
			int j = 0;
			for(int x = chapPos.getX() - 4; x < chapPos.getX() + 5; x++) {
				//Get the tile if possible otherwise return null for the empty screen space
				Tile paintTile;
				Image toPaint;
				try {
					paintTile = boardTiles[x][y];
				} catch(ArrayIndexOutOfBoundsException e) {
					paintTile = null;
				}
				
				
				if (paintTile == null) {
					toPaint = images.get(null).get(0);
				} else if(paintTile instanceof LockTile || paintTile instanceof KeyTile) {
					
					int colour;
					
					if(paintTile instanceof KeyTile) {
						KeyTile tile = (KeyTile) paintTile;
						colour = colourOrder[tile.getKeyColour().ordinal()];
					} else {
						LockTile tile = (LockTile) paintTile;
						colour = colourOrder[tile.getKeyColour().ordinal()];
					}
					
					toPaint = images.get(paintTile.getClass()).get(colour);
					
				} else if(images.get(paintTile.getClass()) == null) {
					System.out.println("error: No tile image for:" + paintTile.getClass());
					System.out.println("Defaulting to no image");
					toPaint = images.get(null).get(0);
				} else {
					toPaint = images.get(paintTile.getClass()).get(0);
				} 	
				
				//Draw the image in the required location
				g.drawImage(toPaint, j * tileSize, i * tileSize , this);
				
				j++;
			}
			i++;
		}
	}
	
	/**
	 * Attempts to load the tiles images from the sprite sheet
	 * and stores them in the image map with their associated objects
	 */
	@SuppressWarnings("unchecked")
	private void loadTileSprites() {
		URL imageURL =  getClass().getResource(tileImageLocation);
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(new File(imageURL.getPath()));
		} catch (IOException e) {
			//Pass the error on as a critical error
			throw new Error("Unable to load the sprite sheet for the tiles."
					+ " Please check that it is correctly stored in the 'images' folder.");
		}
		
		//Now get the sub images
		int y = 0;
		int x = 0;
		int i = 0;
		while ( i < numTiles ) {
			BufferedImage subImage = sheet.getSubimage(x, y, tileSize, tileSize);
			Class<? extends Tile> curTile;
			if (Tile.class.isAssignableFrom(tileOrder[i])) {
				 curTile = (Class<? extends Tile>) tileOrder[i];
			} else {
				throw new Error("Class for tile " + i + " is not an extension of Tile" );
			}
			
			if(images.get(curTile) == null) {
				images.put(curTile, new ArrayList<BufferedImage>());
			}
			images.get(curTile).add(subImage);
			x += 64;
			i++;
			if(x >= sheet.getWidth()) {
				y += tileSize;
				x = 0;
			}
		}
		
		//add the blank tile, always in the bottom right
		BufferedImage blankImage = sheet.getSubimage(sheet.getWidth() - tileSize, sheet.getHeight() - tileSize, tileSize, tileSize);
		images.put(null, new ArrayList<BufferedImage>());
		images.get(null).add(blankImage);
	}
	
	/**
	 * Updates the scale of the board
	 * @param scale
	 */
	protected void setScale(double scale) {
		this.scale = scale;
		//paintComponent(this.getComponentGraphics(getGraphics()));
	}
	
}
