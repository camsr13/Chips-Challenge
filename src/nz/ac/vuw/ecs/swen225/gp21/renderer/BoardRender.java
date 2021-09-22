package nz.ac.vuw.ecs.swen225.gp21.renderer;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
/**
 * Main rendered class responsible for initialising all sub classes
 * and returning the final assembled render to the app package
 * @author Jac Clarke
 *
 */
public class BoardRender {
	//private Game game;
	private JPanel boardPanel;
	private JLayeredPane basePane = new JLayeredPane();

	
	/**
	 * Generates board objects and puts them into the output layered pane
	 */
	public BoardRender(Game game) {
		//this.game = game;
		//loadImages();
		
		boardPanel = new BoardPanel(game);
		boardPanel.setVisible(true);
		boardPanel.setBounds(0,0, 128*5, 128*5);
		basePane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
		System.out.println(basePane.getPosition(boardPanel));
		basePane.setVisible(true);
		
	}
	
	/**
	 * Observer that refreshes the board based off either player movement or tick
	 * @return
	 */
	public void update(Integer... direction) {
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	
	
	/**
	 * Returns the entire render as a layered pane
	 * @return JLayeredPane
	 */
	public JLayeredPane getPane() {
		return basePane;
	}
	
	
	/**
	 * Tries to load the images used for board display
	 * TODO: migrate images from individual to a sprite sheet
	 */
	/*private void loadImages() {
		//Chap images
		ImageIcon c1 = new ImageIcon(this.getClass().getResource("images/Chap.png"));
		ImageIcon c2 = new ImageIcon(this.getClass().getResource("images/Chap_R.png"));
		ImageIcon c3 = new ImageIcon(this.getClass().getResource("images/Chap_B.png"));
		ImageIcon c4 = new ImageIcon(this.getClass().getResource("images/Chap_L.png"));
		Image[] chapImages = {c1.getImage(), c2.getImage(), c3.getImage(), c4.getImage()};
		
	}*/
	
	
}
