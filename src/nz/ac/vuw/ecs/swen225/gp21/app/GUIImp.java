package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom2.JDOMException;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ReadXML;
import nz.ac.vuw.ecs.swen225.gp21.persistency.WriteXML;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay;
import nz.ac.vuw.ecs.swen225.gp21.renderer.*;


public class GUIImp implements GUIAbstract{



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

	//Boolean if Movement allowed
	private boolean canMove = true;
	private boolean waitSecond = true;


    //Create MenuBar and buttons
	private JMenuBar menuBar = new JMenuBar();

	//Temp board placeholder
	private final JPanel board = new CustomPanel(500, 500);
	private JLayeredPane gameBoard = new JLayeredPane();
	private BoardRender boardRender;

	//Game
	private Game game = new Game();
	private ReadXML currXML = new ReadXML();
	private WriteXML saveXML = new WriteXML();
	protected String currFile;


	//Content Panel
	private final JPanel area = new CustomPanel(700, 500);

	private final RecReplay recorder = new RecReplay();

    public GUIImp() {
		initGUI();
	}

		public GUIImp(String file) {
    	this.currFile = file;
    	loadLevel();
			initFrame();
			initMenu();
			initBoard();
			initSideBar();
			countdown();
		}

    protected void initGUI(){

    	startPopUp();
	    initFrame();
	    initMenu();
	    initBoard();
	    initSideBar();

    }

    protected void startPopUp() {

    	Object[] options = {"Load Level 1",
                "Load Level 2",
                "Load TestFile", };

    	int n = JOptionPane.showOptionDialog(frame,
    		    "Welcome",
    		    "Hello",
    		    JOptionPane.YES_NO_CANCEL_OPTION,
    		    JOptionPane.QUESTION_MESSAGE,
    		    null,
    		    options,
    		    options[0]);

    	switch(n){
    		case 0:
    			doStartLevel1();
    			break;
    		case 1:
    			doStartLevel2();
    			break;
    		case 2:
    			doStartTest();
    			break;
    	}
	}

	protected void initFrame() {
		frame.setResizable(true);
		frame.setContentPane(area);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
	}


    protected void initMenu(){
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

    	JMenuItem loadGame = new JMenuItem(new AbstractAction("Load Game") {
		    public void actionPerformed(ActionEvent ae) {

		    }
		});

    	JMenuItem replayGame = new JMenuItem(new AbstractAction("Replay Game") {
		    public void actionPerformed(ActionEvent ae) {
		    	try {
					RecReplay.onReplay();
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});

    	JMenuItem startLevel1 = new JMenuItem(new AbstractAction("Play Level 1") {
		    public void actionPerformed(ActionEvent ae) {

		    	timer.stop();
				clearLevel();

				Object[] options1 = {"Yes",
						"No"};

		    	int n = JOptionPane.showOptionDialog(frame,
		    		    "Would you like to Save your recording?",
		    		    "Game Over",
		    		    JOptionPane.YES_NO_CANCEL_OPTION,
		    		    JOptionPane.QUESTION_MESSAGE,
		    		    null,
		    		    options1,
		    		    options1[0]);

		    	switch(n){
		    		case 0:
		    			saveRecording();
		    			break;

		    	}

		    	doStartLevel1();
		    }
		});

    	JMenuItem startLevel2 = new JMenuItem(new AbstractAction("Play Level 2") {
		    public void actionPerformed(ActionEvent ae) {
		    	timer.stop();
				clearLevel();

				Object[] options1 = {"Yes",
						"No"};

		    	int n = JOptionPane.showOptionDialog(frame,
		    		    "Would you like to Save your recording?",
		    		    "Game Over",
		    		    JOptionPane.YES_NO_CANCEL_OPTION,
		    		    JOptionPane.QUESTION_MESSAGE,
		    		    null,
		    		    options1,
		    		    options1[0]);

		    	switch(n){
		    		case 0:
		    			saveRecording();
		    			break;

		    	}

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
    	menuGame.add(loadGame);
    	menuOptions.add(replayGame);
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
		currFile = "levels/level2.xml";
		loadLevel();
	}

	protected void doStartLevel1() {

		currFile = "levels/level1.xml";
		loadLevel();

	}

	protected void doStartTest() {

		currFile = "levels/testMap.xml";
		loadLevel();

	}

	protected void doResumeGame() {
		timer.start();

	}

	protected void doExitGameS() {
		saveXML.writeXMLFile(saveXML.generateDocument(), "levels/currentSave.xml");


	}

	protected void doExitGameX() {
		System.exit(0);
	}

	protected void initBoard(){

    	//padding around the board
		area.setLayout(new BorderLayout(3, 3));
		area.setBorder(new EmptyBorder(5, 5, 5, 5));

		board.setBackground(Color.PINK);

	    area.add(gameBoard);

    }

    protected void initSideBar(){

    	levelPanel.add(levelLabel);
    	timePanel.add(timeTitleLabel);
    	timePanel.add(timeFigureLabel);
    	keysPanel.add(keysLabel);
    	keysPanel.add(keysFigureLabel);
    	treasuresPanel.add(treasuresLabel);
    	treasuresPanel.add(treasuresFigureLabel);

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
    			doExitGameS();
    			break;
    		case 2:
    			doExitGameX();
    			break;
    		case 3:
    			loadLevel();
    			break;

    	}

	}

	private void doResumeFromPause() {
		countdown();

	}

	public void doWestMove() {
		if(canMove) {
			game.inputDirection(nz.ac.vuw.ecs.swen225.gp21.domain.Game.Direction.LEFT);
			RecReplay.addAction(nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay.Direction.LEFT);
			boardRender.updateChap();
			updateDisplay();
			freezeMovement();
		}
	}

	public void doEastMove() {
		if(canMove) {
			game.inputDirection(nz.ac.vuw.ecs.swen225.gp21.domain.Game.Direction.RIGHT);
			RecReplay.addAction(nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay.Direction.RIGHT);
			boardRender.updateChap();
			updateDisplay();
			freezeMovement();
		}
	}

	public void doSouthMove() {
		if(canMove) {
			game.inputDirection(nz.ac.vuw.ecs.swen225.gp21.domain.Game.Direction.DOWN);
			RecReplay.addAction(nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay.Direction.DOWN);
			boardRender.updateChap();
			updateDisplay();
			freezeMovement();
		}
	}

	public void doNorthMove() {
		if(canMove) {
			game.inputDirection(nz.ac.vuw.ecs.swen225.gp21.domain.Game.Direction.UP);
			RecReplay.addAction(nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay.Direction.UP);
			boardRender.updateChap();
			updateDisplay();
			freezeMovement();
		}
	}

	private void updateDisplay() {

		//UPdate Treasures collected.
		treasuresFigureLabel.setText(String.valueOf(game.getTotalTreasures()-game.getCollectedTreasures()));
		if(game.isLevelComplete()) {
			levelCompleted();
		}
	}

	private void levelCompleted() {

    	//stop game
    	timer.stop();


    	RecReplay.endRecording();

    	JOptionPane.showMessageDialog(null, "You have won!");


    	levelOver();

	}

	protected void freezeMovement() {

		canMove = false;
		int wait = 1000;
		Timer freezeTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent i) {
            	if(waitSecond) {
            		canMove = true;
            	}
            	waitSecond = true;

            }
        });
		freezeTimer.setRepeats(false);
		freezeTimer.start();

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

    protected void countdown() {
        timer = new Timer(1001, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	//For every tick
            	timeFigureLabel.setText(String.valueOf(timeRemaining--));
            	boardRender.updateOnTick();
            	saveXML.updateTime(timeRemaining);
            	boardRender.updateChap();
            	Game.instance.tick();

            	//When time expires
            	if(timeRemaining <= 0) {
            		doTimeExpired();
            	}
            }
        });
        timer.start();

    }

	private void doTimeExpired() {

    	//stop game end recording
    	timer.stop();
    	RecReplay.endRecording();

    	//Pop up message
    	JOptionPane.showMessageDialog(null, "Time has expired, you have lost :(");

    	//End Level Protocols
    	levelOver();

	}

	private void saveRecording() {

		try {
			RecReplay.saveConfirmDialogue();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void levelOver() {



		Object[] options1 = {"Yes",
				"No"};

    	int n = JOptionPane.showOptionDialog(frame,
    		    "Would you like to Save your recording?",
    		    "Game Over",
    		    JOptionPane.YES_NO_CANCEL_OPTION,
    		    JOptionPane.QUESTION_MESSAGE,
    		    null,
    		    options1,
    		    options1[0]);

    	switch(n){
    		case 0:
    			saveRecording();
    			break;

    	}

		Object[] options2 = {"Play Level 1",
				"Play Level 2",
                "Exit Game X",};

    	int x = JOptionPane.showOptionDialog(frame,
    		    "Whats Next?",
    		    "Game Over",
    		    JOptionPane.YES_NO_CANCEL_OPTION,
    		    JOptionPane.QUESTION_MESSAGE,
    		    null,
    		    options2,
    		    options2[2]);

    	switch(x){
    		case 0:
    			timer.stop();
				clearLevel();
    			doStartLevel1();
    			break;
    		case 1:
    			timer.stop();
				clearLevel();
    			doStartLevel2();
    			break;
    		case 2:
    			doExitGameX();
    			break;
    	}
	}

	protected void loadLevel(){


		try {
			currXML.readXMLFile(currFile);
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Get time Limit
		levelTimeMax = currXML.getTimeLimit();
		timeRemaining = currXML.getTimeLimit();


		game = currXML.getGameInstance();
		boardRender = new BoardRender(game);
		gameBoard = boardRender.getPane();
		boardRender.initaliseBoard(500);
		RecReplay.newRecording();
		RecReplay.getGUIImp(this);
		initBoard();
		initSideBar();
		countdown();
		updateDisplay();

	}

	private void clearLevel() {

		game.setLevelComplete(false);
		game = null;
		levelTimeMax = 0;
		timeRemaining = 0;
		boardRender = null;
		initBoard();

	}


}



