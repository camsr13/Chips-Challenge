package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.persistancy.readXML;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.app.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class RecReplay {

    private static Queue<Game.Direction> actionHistory = new ArrayDeque<>();
    private static boolean isRunning;
    private static boolean isRecording;
    private static int DELAY = 200;

    static Thread thread;


    /*
    TODO :
        - need a getGame() method from app
        - what do I need from app and persistence? -> make a list
        - decide on naming conventions for recording file names -> where do we save to?


     */

    /**
     * Sets the playback delay to the int specified
     *
     * @param delay
     */
    public static void setDelay(int delay) {
        DELAY = delay;
    }


    //=================================================
    //                    RECORD
    //=================================================

    /**
     * Creates a new recording
     */
    public static void newRecording(Game g, String) {
        isRecording = true;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String saveName = "Chaps_Challenge_Save_" + dtf.format(now);
        actionHistory.clear();
        // TODO gets the game state from PERSISTENCE
    }


    /**
     * Saves a recording
     */
    public static void saveRecording(String saveName) {
        /*
        TODO:
            - make a new Doc
            - add elements??
            - iterate through action history to add in the moves
            - what level??
         */

        if (isRecording) {
            Element moves;

            for (int i = 0; i < actionHistory.size(); i++) {
                moves.addContent()

            }
        }
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        // TODO ends recording and resets vars
        isRecording = false;
        isRunning = false;
        actionHistory.clear();
    }


    //=================================================
    //                     REPLAY
    //=================================================

    /**
     * Loads saved recording from file
     */
    public void loadRecording(String filename) throws IOException { // when called needs a try catch??
        //readXML.readXMLFile(filename);
        Document xmlFile; // get this from read XML
        List<Element> movesList = null;
        /*
        TODO:
            - get the level ->
            - get persistence to start the level
            - get the moves from xml -> put them into action history queue
            - stack for forward back undo redo????
            -

         */

        // TODO need to load the game state via persistence
        readXML.readXMLFile(); // read the level file

        // Load in actions
        actionHistory.clear();


        SAXBuilder sax = new SAXBuilder();

        // XML as local file
        Document doc = sax.build(new File(filename));

        // Elements
        Element root = doc.getRootElement();
        movesList = root.getChildren("moves");


        if (movesList != null) {
            for (Element elem : movesList) {

                String direction = elem.getText();

                switch (direction) {
                    case "Left":
                        actionHistory.add(Game.Direction.LEFT);
                        break;
                    case "Right":
                        actionHistory.add(Game.Direction.RIGHT);
                        break;
                    case "Up":
                        actionHistory.add(Game.Direction.UP);
                        break;
                    case "Down":
                        actionHistory.add(Game.Direction.DOWN);
                        break;
                    default:
                        break;
                }
            }
        }

    }


    /**
     * Steps the replay forward by one
     */
    public static void stepReplay(Game g) {
        // If the game is running and there are moves left to replay, step forward by one
        if (isRunning && actionHistory.size() > 0) {
            //game move player method -> (actionHistory.poll())
        }
        // When there are no moves left to replay, the game should no longer be running
        if (actionHistory.size() == 0) {
            isRunning = false;
            // TODO send message to Game?? or app?
        }
    }


    /**
     * Runs through the recorded actions
     * Stops once replay is complete
     */
    public static void runReplay(Game g) {
        Runnable run = () -> {
            while (isRunning && actionHistory.size() > 0) {
                try {
                    Thread.sleep(DELAY);
                    stepReplay(g);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;

        };
        thread = new Thread(run);
        thread.start();
    }


    //=================================================
    //                    ACTION
    //=================================================

    /**
     * Add a player action to actionHistory.
     * TODO Should be called by app??? on movement???
     *
     * @param direction the direction of action
     */
    public static void addAction(Game.Direction direction) {
        // adds to actionHistory
        if (isRecording) {
            actionHistory.add(direction);
        }
    }


    /**
     * Returns the queue of player actions
     *
     * @return
     */
    public static Queue<Game.Direction> getActionHistory() {
        return actionHistory;
    }

}
