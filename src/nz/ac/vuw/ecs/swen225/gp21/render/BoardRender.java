package nz.ac.vuw.ecs.swen225.gp21.render;
import java.util.*;

import javax.swing.*;

import java.awt.*;

public class BoardRender {
	private JPanel boardPanel;
	private ImageIcon chapImage = new ImageIcon(this.getClass().getResource("images/Chap.png"));
	private ImageIcon keyImage = new ImageIcon(this.getClass().getResource("images/card.png"));
	private JLabel chapLabel = new JLabel();
	private JLabel keyLabel = new JLabel();
	
	/**
	 * Packages board objects
	 */
	public BoardRender() {
		keyLabel.setIcon(keyImage);
		chapLabel.setIcon(chapImage);
		boardPanel.add(chapLabel);
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
}
