package nz.ac.vuw.ecs.swen225.gp21.renderer;
import java.util.*;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.LockTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.WallTile;

import java.awt.*;

public class BoardRender {
	private JPanel boardPanel =  new JPanel();
	//temporary image storage, will likely move to hashmap
	private ArrayList<Image> keyImages = new ArrayList<Image>();
	private ArrayList<Image> doorImages = new ArrayList<Image>();
	private HashMap<Class<? extends Tile>, Image[]> images = new HashMap<Class<? extends Tile>, Image[]>();
	private Image blank;
	private Image wall;
	private Image exit; 
	private Game game;

	
	/**
	 * Packages board objects
	 */
	public BoardRender(Game game) {
		this.game = game;
		loadImages();
		drawPanel();
		boardPanel.setVisible(true);
	}
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 * @return
	 */
	public void update() {
		//boardPanel.revalidate();
		//boardPanel.repaint();
	}
	
	public void drawPanel() {
		JPanel basePanel = new JPanel() {
			/**
			 * Serialisation id
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
			
			//custom paint panel

			private void draw (Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Tile[][] boardTiles = game.getTilemap();
				Location chapPos = game.getPlayer().getLocation();
				
				for(int x = chapPos.getX() - 4; x < chapPos.getX() + 4; x++) {
					for(int y = chapPos.getY() - 4; y < chapPos.getX() + 4; y++) {
						Tile paintTile;
						ArrayList<Image> tileImages = new ArrayList<Image>();
						Image toPaint;
						try {
							paintTile = boardTiles[x][y];
						} catch(ArrayIndexOutOfBoundsException e) {
							paintTile = null;
						}
						
						if (paintTile == null) {
							tileImages.add(blank);
						} else {
							Image[] test = images.get(paintTile.getClass());
							Collections.addAll(tileImages, test);
						}
						
						if (tileImages.size() == 1) {
							toPaint = tileImages.get(0);
						} else {
							//TODO: figure out tile details
							toPaint = tileImages.get(0);
						}
						System.out.println(x *128 - 128);
						System.out.println(paintTile);
						g2d.drawImage(toPaint, x * 128 -128, y * 128 -128, this);
					}
					
				}
			}
		};
		
		basePanel.setPreferredSize(new Dimension(128*5, 128*5));
		boardPanel.add(basePanel);
	}
	
	public void moveChap(int direction) {
		
	}
	
	/**
	 * 
	 * @return JPanel containing the board\
	 */
	public JPanel getPanel() {
		return boardPanel;
	}
	
	/**
	 * Tries to load the images used for board display
	 * TODO: migrate images from individual to a sprite sheet
	 */
	private void loadImages() {
		//Chap images
		ImageIcon c1 = new ImageIcon(this.getClass().getResource("images/Chap.png"));
		ImageIcon c2 = new ImageIcon(this.getClass().getResource("images/Chap_R.png"));
		ImageIcon c3 = new ImageIcon(this.getClass().getResource("images/Chap_B.png"));
		ImageIcon c4 = new ImageIcon(this.getClass().getResource("images/Chap_L.png"));
		Image[] chapImages = {c1.getImage(), c2.getImage(), c3.getImage(), c4.getImage()};
		
		//wall
		ImageIcon wall = new ImageIcon(this.getClass().getResource("images/wall.png"));
		Image[] wallImages = {wall.getImage()};
		images.put(new WallTile(null).getClass(), wallImages);
		
		
		//ImageIcon e = new ImageIcon(this.getClass().getResource("images/exit.png"));
		//images.put(new ExitTile(null), floor.getImage());
		
		ImageIcon floor = new ImageIcon(this.getClass().getResource("images/floor.png"));
		Image[] floorImages = {floor.getImage()};
		images.put(new FreeTile(null).getClass(),floorImages);
		
		
		//doors
		ImageIcon dr = new ImageIcon(this.getClass().getResource("images/red door.png"));
		ImageIcon dy = new ImageIcon(this.getClass().getResource("images/yello door.png"));
		ImageIcon dg = new ImageIcon(this.getClass().getResource("images/green door.png"));
		ImageIcon db = new ImageIcon(this.getClass().getResource("images/blue door.png"));
		ImageIcon dl = new ImageIcon(this.getClass().getResource("images/exit lock.png"));
		Image[] doorImages = {dr.getImage(), dy.getImage(), dg.getImage(), db.getImage(), dl.getImage()};
		images.put(new LockTile(null, null).getClass(), doorImages);
		
		ImageIcon blankIcon = new ImageIcon(this.getClass().getResource("images/blank.png"));
		blank = blankIcon.getImage();
	}
	
	
}
