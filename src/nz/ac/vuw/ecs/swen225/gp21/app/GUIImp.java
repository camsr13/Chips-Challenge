package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GameWindowImp implements GameWindow, Observer {
     
     // the game object
    Game game;

    //Swing variable declarations

	private final JFrame frame = new JFrame("Game");

    //Create MenuBar and buttons
	private JMenuBar mb = new JMenuBar();
	private JMenu menu = new JMenu("Menu");

    public GameWindowImp() {
		initGUI();
	}

    private void initGUI() {
            
            initFrame();
            initMenu();
    }

    private void initFrame() {
		frame.setResizable(true);
		frame.setContentPane(area);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
	}
	
    private void initMenu(){
        
    }
