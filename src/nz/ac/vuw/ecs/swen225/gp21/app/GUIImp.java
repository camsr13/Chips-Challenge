package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUIImp implements GUIAbstract{

    // the game object
    //Game game;

    //Swing variable declarations

	//The game frame
	private final JFrame frame = new JFrame("Game");

	//SideBar to house clock, level and chips count
	private final JPanel sideBar = new CustomPanel(250, 500);
	private final JPanel levelPanel = new CustomPanel(250, 100);
	private final JPanel timePanel = new CustomPanel(250, 100);
	private final JPanel chipsPanel = new CustomPanel(250, 100);

    //Create MenuBar and buttons
	private JMenuBar menuBar = new JMenuBar();

	//Temp board placeholder
	private final JPanel board = new CustomPanel(500, 500);

	//Content Panel
	private final JPanel area = new CustomPanel(700, 500);

    public GUIImp() {
		initGUI();
	}

    private void initGUI() {

	    initFrame();
	    initMenu();
	    initBoard();
	    initSideBar();

    }

    private void initFrame() {
		frame.setResizable(true);
		frame.setContentPane(area);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
	}

    private void initMenu(){
    	//Create menuBar options
    	JMenu menuGame = new JMenu("Game");
    	JMenu menuOptions = new JMenu("Options");
    	JMenu menuLevel = new JMenu("Level");
    	JMenu menuHelp = new JMenu("Help");

		//Add menu items to menu
		menuBar.add(menuGame);
		menuBar.add(menuOptions);
		menuBar.add(menuLevel);
		menuBar.add(menuHelp);


		//Add menuBar to area
		frame.setJMenuBar(menuBar);

    }


    private void initBoard(){

    	//padding around the board
		area.setLayout(new BorderLayout(3, 3));
		area.setBorder(new EmptyBorder(5, 5, 5, 5));

		board.setBackground(Color.PINK);

	    area.add(board, BorderLayout.CENTER);

    }

    private void initSideBar(){

    	JTextArea levelLabel = new JTextArea("Level");
    	JTextArea timeLabel = new JTextArea("Time");
    	JTextArea chipsLabel = new JTextArea("Chips Left");

    	levelPanel.add(levelLabel);
    	timePanel.add(timeLabel);
    	chipsPanel.add(chipsLabel);

    	//add parts parts to sideBar
		sideBar.setBackground(Color.WHITE);
		sideBar.add(levelPanel, BorderLayout.NORTH);
		sideBar.add(timePanel, BorderLayout.CENTER);
		sideBar.add(chipsPanel, BorderLayout.SOUTH);

		//add sidebar to content frame
    	area.add(sideBar, BorderLayout.LINE_END);

    }

    public JFrame getMainWindow() {
		return frame;
	}
}
