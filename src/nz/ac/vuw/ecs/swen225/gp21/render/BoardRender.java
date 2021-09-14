package nz.ac.vuw.ecs.swen225.gp21.render;
import java.util.*;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;

import java.awt.*;

public class BoardRender {
	private JPanel boardPanel =  new JPanel();
	//temporary image storage, will likely move to hashmap
	private ArrayList<Image> keyImages = new ArrayList<Image>();
	private ArrayList<Image> doorImages = new ArrayList<Image>();
	private ArrayList<Image> chapImages = new ArrayList<Image>();
	private Image wall;
	private Image exit; 
	private Game game;
	
	/**
	 * Packages board objects
	 */
	public BoardRender(Game game) {
		this.game = game;
		loadImages();
		JLabel testLabel = new JLabel();
		testLabel.setIcon(new ImageIcon(wall));
		boardPanel.add(testLabel);
	}
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 * @return
	 */
	public void update() {
		
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
		chapImages.add(c1.getImage());
		ImageIcon c2 = new ImageIcon(this.getClass().getResource("images/Chap.png"));
		chapImages.add(c2.getImage());
		ImageIcon c3 = new ImageIcon(this.getClass().getResource("images/Chap.png"));
		chapImages.add(c3.getImage());
		ImageIcon c4 = new ImageIcon(this.getClass().getResource("images/Chap.png"));
		chapImages.add(c4.getImage());
		
		//wall
		ImageIcon w = new ImageIcon(this.getClass().getResource("images/wall.png"));
		wall = w.getImage();
		
		ImageIcon e = new ImageIcon(this.getClass().getResource("images/exit.png"));
		
		//doors
		ImageIcon dr = new ImageIcon(this.getClass().getResource("images/red door.png"));
		ImageIcon dy = new ImageIcon(this.getClass().getResource("images/yello door.png"));
		ImageIcon dg = new ImageIcon(this.getClass().getResource("images/green door.png"));
		ImageIcon db = new ImageIcon(this.getClass().getResource("images/blue door.png"));
		ImageIcon dl = new ImageIcon(this.getClass().getResource("images/exit lock.png"));
		doorImages.add(dr.getImage());
		doorImages.add(dy.getImage());
		doorImages.add(dg.getImage());
		doorImages.add(db.getImage());
		doorImages.add(dl.getImage());
		
	}
}
