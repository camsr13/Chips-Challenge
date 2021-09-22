package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.LockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;

class BoardPanel extends JPanel {
	/**
	 * Serial Id to satisfy warnings
	 */
	private static final long serialVersionUID = -3951679614322867873L;
	private Game game;
	private Map<Class<? extends Tile>, Image[]> images = new HashMap<Class<? extends Tile>, Image[]>();
	
	BoardPanel(Game game) {
		this.game = game;
		loadTileImages();
	};
	
	
	/**
	 * Overrides the default paint method with custom draw method
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	/**
	 * Method which retrieves game data to draw the board. 
	 * Draws an extra hidden tile to allow for animation without full board refresh
	 * TODO: add smooth board updates
	 * @param g
	 */
	private void draw (Graphics g) {
		Tile[][] boardTiles = game.getTilemap();
		Location chapPos = game.getPlayer().getLocation();
		int i = 0;
		for(int x = chapPos.getX() - 3; x < chapPos.getX() + 4; x++) {
			int j = 0;
			for(int y = chapPos.getY() - 3; y < chapPos.getY() + 4; y++) {
				Tile paintTile;
				ArrayList<Image> tileImages = new ArrayList<Image>();
				Image toPaint;
				try {
					paintTile = boardTiles[x][y];
				} catch(ArrayIndexOutOfBoundsException e) {
					paintTile = null;
				}
				
				if (paintTile == null) {
					Collections.addAll(tileImages, images.get(null));
				} else {
					Collections.addAll(tileImages, images.get(paintTile.getClass()));
				}
				
				if (tileImages.size() == 1) {
					toPaint = tileImages.get(0);
				} else {
					//TODO: retrieve special tile details
					toPaint = tileImages.get(0);
				}
				g.drawImage(toPaint, i * 128, j * 128 , this);
				
				j++;
			}
			i++;
		}
	}
	
	/**
	 * Attempts to load all the required images for displaying the board
	 * and stores them in the image map with their associated objects
	 */
	private void loadTileImages() {
		
		ImageIcon wall = new ImageIcon(this.getClass().getResource("images/wall.png"));
		Image[] wallImages = {wall.getImage()};
		images.put(new WallTile(null).getClass(), wallImages);
		
		ImageIcon floor = new ImageIcon(this.getClass().getResource("images/floor.png"));
		Image[] floorImages = {floor.getImage()};
		images.put(new FreeTile(null).getClass(),floorImages);
		
		//Exit tile needs class
		ImageIcon e = new ImageIcon(this.getClass().getResource("images/exit.png"));
		//images.put(new ExitTile(null), floor.getImage());
		
		//doors
		ImageIcon dr = new ImageIcon(this.getClass().getResource("images/red door.png"));
		ImageIcon dy = new ImageIcon(this.getClass().getResource("images/yello door.png"));
		ImageIcon dg = new ImageIcon(this.getClass().getResource("images/green door.png"));
		ImageIcon db = new ImageIcon(this.getClass().getResource("images/blue door.png"));
		ImageIcon dl = new ImageIcon(this.getClass().getResource("images/exit lock.png"));
		Image[] doorImages = {dr.getImage(), dy.getImage(), dg.getImage(), db.getImage(), dl.getImage()};
		images.put(new LockTile(null, null).getClass(), doorImages);
		
		ImageIcon blankIcon = new ImageIcon(this.getClass().getResource("images/blank.png"));
		Image[] blankIcons = {blankIcon.getImage()};
		images.put(null,  blankIcons);
	}
}
