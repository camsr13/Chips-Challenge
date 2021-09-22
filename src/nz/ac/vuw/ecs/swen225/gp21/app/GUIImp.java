package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class GUIImp implements GUIAbstract{

    // the game object
    //Game game;

    //Swing variable declarations


	//Game information
    private Timer timer;
    private int levelTimeMax;
    private int timeRemaining = 1000;
    private String level;


	//The game frame
	private final JFrame frame = new JFrame("Game");

	//SideBar to house clock, level and chips count
	private final JPanel sideBar = new CustomPanel(250, 700);
	private final JPanel sidebarNorth = new CustomPanel(250, 400);
	private final JPanel sidebarSouth = new CustomPanel(250, 400);
	private final JPanel levelPanel = new CustomPanel(250, 100);
	private final JPanel timePanel = new CustomPanel(250, 100);
	private final JPanel keysPanel = new CustomPanel(250, 100);
	private final JPanel treasuresPanel = new CustomPanel(250, 100);

	//Sidebar Labels
	JTextArea levelLabel = new JTextArea("Level");
	JTextArea levelFigureLabel = new JTextArea("");
	JTextArea timeTitleLabel = new JTextArea("Time");
	JTextArea timeFigureLabel = new JTextArea("0");
	JTextArea keysLabel = new JTextArea("Keys Collected");
	JTextArea keysFigureLabel = new JTextArea("0");
	JTextArea treasuresLabel = new JTextArea("Treasures Remaining");
	JTextArea treasuresFigureLabel = new JTextArea("0");

	//Navigation and Pause Buttons
	private final JButton north = new JButton("Up");
	private final JButton east = new JButton("Right");
	private final JButton south = new JButton("Down");
	private final JButton west = new JButton("Left");
	private final JButton pause = new JButton("Pause");

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
	    countdown();
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

    	//Create menu items
    	JMenuItem exitGameX = new JMenuItem(new AbstractAction("Exit Game") {
		    public void actionPerformed(ActionEvent ae) {
		    	doExitGameX();
		    }
		});

    	JMenuItem exitGameS = new JMenuItem(new AbstractAction("Exit Game") {
		    public void actionPerformed(ActionEvent ae) {
		    	doExitGameS();
		    }
		});

    	JMenuItem resumeGame = new JMenuItem(new AbstractAction("Resume Game") {
		    public void actionPerformed(ActionEvent ae) {
		    	doResumeGame();
		    }
		});

    	JMenuItem startLevel1 = new JMenuItem(new AbstractAction("Play Level 1") {
		    public void actionPerformed(ActionEvent ae) {
		    	doStartLevel1();
		    }
		});

    	JMenuItem startLevel2 = new JMenuItem(new AbstractAction("Play Level 2") {
		    public void actionPerformed(ActionEvent ae) {
		    	doStartLevel2();
		    }
		});

    	//Set shortcuts
    	exitGameS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    	exitGameX.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
    	resumeGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
    	startLevel1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK));
    	startLevel2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK));

    	//Add menu items to appropriate menus
    	menuGame.add(exitGameS);
    	menuGame.add(exitGameX);
    	menuGame.add(resumeGame);
    	menuLevel.add(startLevel1);
    	menuLevel.add(startLevel2);

		//Add menu items to menubar
		menuBar.add(menuGame);
		menuBar.add(menuOptions);
		menuBar.add(menuLevel);
		menuBar.add(menuHelp);


		//Add menuBar to content panel
		frame.setJMenuBar(menuBar);

    }




	protected void doStartLevel2() {
		// TODO Auto-generated method stub

	}

	protected void doStartLevel1() {
		// TODO Auto-generated method stub

	}

	protected void doResumeGame() {
		// TODO Auto-generated method stub

	}

	protected void doExitGameS() {
		// TODO Auto-generated method stub

	}

	protected void doExitGameX() {
		// TODO Auto-generated method stub

	}

	private void initBoard(){

    	//padding around the board
		area.setLayout(new BorderLayout(3, 3));
		area.setBorder(new EmptyBorder(5, 5, 5, 5));

		board.setBackground(Color.PINK);

	    area.add(board, BorderLayout.CENTER);

    }

    private void initSideBar(){

    	levelPanel.add(levelLabel);
    	timePanel.add(timeTitleLabel);
    	timePanel.add(timeFigureLabel);
    	keysPanel.add(keysLabel);
    	treasuresPanel.add(treasuresLabel);

    	//add parts parts to sideBarSouth
		sidebarNorth.setBackground(Color.WHITE);
		sidebarNorth.add(levelPanel, BorderLayout.NORTH);
		sidebarNorth.add(timePanel, BorderLayout.CENTER);
		sidebarNorth.add(keysPanel, BorderLayout.SOUTH);
		sidebarNorth.add(treasuresPanel, BorderLayout.SOUTH);

		//add buttons to sidebar
		sidebarSouth.add(north, new MoveButtonConstraints(2, 1));
		sidebarSouth.add(south, new MoveButtonConstraints(0, 1));
		sidebarSouth.add(east, new MoveButtonConstraints(1, 2));
		sidebarSouth.add(west, new MoveButtonConstraints(1, 0));
		sidebarSouth.add(pause, new MoveButtonConstraints(1, 4));

		//hotkeys
		south.setMnemonic(KeyEvent.VK_DOWN);
		north.setMnemonic(KeyEvent.VK_UP);
		west.setMnemonic(KeyEvent.VK_LEFT);
		east.setMnemonic(KeyEvent.VK_RIGHT);
		pause.setMnemonic(KeyEvent.VK_SPACE);

		//event listeners
		north.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doNorthMove();
			}
		});
		south.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doSouthMove();
			}
		});
		east.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doEastMove();
			}
		});
		west.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doWestMove();
			}
		});

		pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doPauseGame();
			}
		});

		//add sidebar to content frame
		sideBar.add(sidebarNorth);
		sideBar.add(sidebarSouth);
    	area.add(sideBar, BorderLayout.LINE_END);

    }

    protected void doPauseGame() {
		// TODO Auto-generated method stub

    	//stop game
    	timer.stop();

    	Object[] options = {"Resume Level",
                "Exit Game S",
                "Exit Game X",
                "Restart Level"};

    	int n = JOptionPane.showOptionDialog(frame,
    		    "The game is Paused, please select an option",
    		    "Paused Game",
    		    JOptionPane.YES_NO_CANCEL_OPTION,
    		    JOptionPane.QUESTION_MESSAGE,
    		    null,
    		    options,
    		    options[2]);

    	switch(n){
    		case 0:
    			doResumeFromPause();
    		case 1:
    			System.out.print("1");
    			break;
    		case 2:
    			System.out.print("2");
    			break;
    		case 3:
    			System.out.print("3");
    			break;



    	}

	}

	private void doResumeFromPause() {
		countdown();

	}

	protected void doWestMove() {
		// TODO Auto-generated method stub

	}

	protected void doEastMove() {
		// TODO Auto-generated method stub

	}

	protected void doSouthMove() {
		// TODO Auto-generated method stub

	}

	protected void doNorthMove() {
		// TODO Auto-generated method stub

	}

	/**
	 * This helper class initialises GridBagConstraints with two options (gridx, gridy). By default GridBagConstraints
	 * only supports initialisation with either 0 options, or every option.
	 */
	private static class MoveButtonConstraints extends GridBagConstraints {
		public MoveButtonConstraints(int gridx, int gridy) {
			super();
			this.gridx = gridx;
			this.gridy = gridy;
		}
	}


    public JFrame getMainWindow() {
		return frame;
	}

    private void countdown() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	timeFigureLabel.setText(String.valueOf(timeRemaining--));

            	if(timeRemaining <= 0) {
            		doTimeExpired();
            	}
            }
        });

        timer.start();

    }

	private void doTimeExpired() {
		// TODO Auto-generated method stub

	}
}

