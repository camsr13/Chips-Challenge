package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecReplay.Direction;
import nz.ac.vuw.ecs.swen225.gp21.renderer.*;


/**
 * @author richarcame1
 *
 */
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
	private final JPanel sideBar = new CustomPanel(300, 500);
	private final JPanel sidebarNorth = new CustomPanel(250, 250);
	private final JPanel sidebarSouth = new CustomPanel(250, 250);
	private final JPanel levelPanel = new CustomPanel(250, 30);
	private final JPanel timePanel = new CustomPanel(250, 30);
	private final JPanel keysPanel = new CustomPanel(250, 100);
	private final JPanel treasuresPanel = new CustomPanel(250, 30);
	private final JPanel movePanel = new CustomPanel(250, 100);
	private final JPanel pausePanel = new CustomPanel(250, 30);

	//Sidebar Labels
	private JTextArea levelLabel = new JTextArea("Level");
	private JTextArea levelFigureLabel = new JTextArea("");
	private JTextArea timeTitleLabel = new JTextArea("Time");
	private JTextArea timeFigureLabel = new JTextArea("0");
	private JTextArea keysLabel = new JTextArea("Keys Collected");
	private JTextArea keysFigureLabel = new JTextArea("0");
	private JTextArea treasuresLabel = new JTextArea("Treasures Remaining");
	private JTextArea treasuresFigureLabel = new JTextArea("0");

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
	private JLayeredPane gameBoard = new JLayeredPane();
	private BoardRender boardRender;

	//Game
	private Game game = new Game();
	private ReadXML currXML = new ReadXML();
	private WriteXML saveXML = new WriteXML();
	protected String currFile;


	//Content Panel
	private final JPanel area = new CustomPanel(900, 500);
	GridBagConstraints gbc = new GridBagConstraints();

	private final RecReplay recorder = new RecReplay();


	//
	nz.ac.vuw.ecs.swen225.gp21.domain.Game.Direction lastMove;
	private Boolean halfTick = true;

	/** Constructor
	*/
    public GUIImp() {
		initGUI();
	}

    /** Testing Constructor for Recorder
     * @param file - A string representing the filename of the Recording
    */
	public GUIImp(String file) {
	this.currFile = file;
	loadLevel();
		initFrame();
		initMenu();
		initBoard();
		initSideBar();
		countdown();
	}

    /** This method initialises and adds all JSwing components to the GUI.
     *
     */
    protected void initGUI(){

    	startPopUp();
	    initFrame();
	    initMenu();
	    initBoard();
	    initSideBar();

    }

	/**
	 *
	 */
	protected void initFrame() {

		frame.setResizable(true);
		frame.setContentPane(area);
		area.setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		area.setLayout(new BorderLayout(3, 3));
		area.setBorder(new EmptyBorder(20, 20, 20, 20));


	}


    /** This method initialises the adds the menu bar and the menu items to the GUi, adding the functionailty
     * to menu items as well.
     */
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
		    	try {
					currXML.readXMLFile();
				} catch (JDOMException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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



	/**This method initialises the board onto the content pane.
	 *
	 */
	protected void initBoard(){

    	//padding around the board
		gameBoard.setBackground(Color.PINK);
		gameBoard.setBorder(new EmptyBorder(5, 5, 5, 5));
	    area.add(gameBoard, BorderLayout.WEST);

    }

    /** This method initialises the Side bar containing game-state info and movement buttons.
     *
     */
    protected void initSideBar(){

    	//add sidebar to content frame
    	area.add(sideBar, BorderLayout.EAST);
    	sideBar.setBackground(Color.GREEN);
    	sidebarSouth.setBackground(Color.GREEN);
    	sidebarNorth.setBackground(Color.GREEN);
    	sideBar.add(sidebarNorth, BorderLayout.NORTH);
		sideBar.add(sidebarSouth, BorderLayout.SOUTH);
		movePanel.setLayout(new GridBagLayout());


    	levelPanel.add(levelLabel);
    	timePanel.add(timeTitleLabel);
    	timePanel.add(timeFigureLabel);
    	keysPanel.add(keysLabel);
    	keysPanel.add(keysFigureLabel);
    	treasuresPanel.add(treasuresLabel);
    	treasuresPanel.add(treasuresFigureLabel);

    	levelPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
    	timePanel.setBorder(new EmptyBorder(7, 7, 7, 7));
    	keysPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
    	treasuresPanel.setBorder(new EmptyBorder(7, 7, 7, 7));

    	//add parts parts to sideBarSouth
		sidebarNorth.add(levelPanel, BorderLayout.NORTH);
		sidebarNorth.add(timePanel, BorderLayout.CENTER);
		sidebarNorth.add(treasuresPanel, BorderLayout.SOUTH);
		sidebarNorth.add(keysPanel, BorderLayout.SOUTH);
		sidebarSouth.add(movePanel, BorderLayout.NORTH);
		sidebarSouth.add(pausePanel, BorderLayout.SOUTH);

		gbc.gridx = 1;
		gbc.gridy = 0;
		movePanel.add(north, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		movePanel.add(south, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		movePanel.add(west, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		movePanel.add(east, gbc);

		//gbc.gridx = 1;
		//gbc.gridy = 4;
		pausePanel.add(pause);

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

    }

    /**This method is called upon launch, promoting the user to enter which level they would like to play upon load
     *
     */
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

    /**This comment loads and starts Level 2.
     *
     */
    protected void doStartLevel2() {
		currFile = "levels/level2.xml";
		loadLevel();
	}

    /**This comment loads and starts Level 1.
	 *
	 */
	protected void doStartLevel1() {

		currFile = "levels/level1.xml";
		loadLevel();

	}

	/**This comment loads and starts the test Map.
	 *
	 */
	protected void doStartTest() {

		currFile = "levels/testMap.xml";
		loadLevel();

	}

	/** This method is called when resuming the game from a paused state.
	 *
	 */
	protected void doResumeGame() {
		timer.start();

	}

	/**This method first saves a XML file of the game before closing the program.
	 *
	 */
	protected void doExitGameS() {
		saveXML.writeXMLFile(saveXML.generateDocument(), "levels/currentSave.xml");
		System.exit(0);

	}

	/** This method exits the program without a save.
	 *
	 */
	protected void doExitGameX() {
		System.exit(0);
	}

    /** This method pauses the game, stoping the clock and triggering a JOptionPane
     * with user action options.
     *
     */
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


	/**
	 * Sets the move direction to the Left, which will execute via the Countdown() timer
	 */
	public void doWestMove() {
		if(canMove) {
			lastMove = Game.Direction.LEFT;
			canMove = false;
		}
	}

	/**
	 * Sets the move direction to the Right, which will execute via the Countdown() timer
	 */
	public void doEastMove() {
		if(canMove) {
			lastMove = Game.Direction.RIGHT;
			canMove = false;
		}
	}

	/**
	 * Sets the move direction downwards, which will execute via the Countdown() timer
	 */
	public void doSouthMove() {
		if(canMove) {
			lastMove = Game.Direction.DOWN;
			canMove = false;
		}
	}

	/**
	 * Sets the move direction upwards, which will execute via the Countdown() timer
	 */
	public void doNorthMove() {
		if(canMove) {
			lastMove = Game.Direction.UP;
			canMove = false;
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

	/**
	 * This is a legacy method which stops movements from being accepted whilst animations are occurring
	 */
	protected void freezeMovement() {

		canMove = false;
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
	/** Gets the GUI's frame object
     * @return the GUI's JSwing frame object
     */
    public JFrame getMainWindow() {
		return frame;

	}

    /** This method handles the JSwing timer along with the logic that occurs on each tick, including updating Game
     * and Renderer packages to be aware of player movement, counting down time remaining and updating Key and
     * Treasure counts
     *
     */
    protected void countdown() {
        timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(halfTick) {

            		halfTick = false;

            		if(lastMove != null) {
            			canMove = true;
            			game.inputDirection(lastMove);

            			boardRender.updateChap();

                		switch(lastMove) {
                			case DOWN :
                				RecReplay.addAction(RecReplay.Direction.DOWN);
                				break;
                			case UP :
                				RecReplay.addAction(RecReplay.Direction.UP);
                				break;
                			case LEFT :
                				RecReplay.addAction(RecReplay.Direction.LEFT);
                				break;
                			case RIGHT :
                				RecReplay.addAction(RecReplay.Direction.RIGHT);
                				break;
                		}
                		lastMove = null;
            		}

            	}else {

            		//For every tick

        			timeFigureLabel.setText(String.valueOf(timeRemaining--));
	            	saveXML.updateTime(timeRemaining);
	            	Game.instance.tick();
	            	updateDisplay();

	            	halfTick = true;

	            	boardRender.updateOnTick();

	            	updateDisplay();
            	}

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
		boardRender.initaliseBoard(603);
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

