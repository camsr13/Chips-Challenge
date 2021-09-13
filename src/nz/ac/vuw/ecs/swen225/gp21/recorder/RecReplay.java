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
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class RecReplay {

    private static Queue<Game.Direction> actionHistory = new ArrayDeque<>();
    private static boolean isRunning;
    private static boolean isRecording;
    private static int DELAY = 200;


    /*
    TODO :
        - need a getGame() method from app
        -
     */

    /**
     * Sets the playback delay to the int specified
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
    public static void newRecording() {
        isRecording = true;
        // some kind of naming convention (date??)
        // clear any outstanding collections/variables from last recording
        // gets the game state from PERSISTENCE

    }


    /**
     * Saves a recording
     */
    public static void saveRecording(String saveName) {


        if (isRecording) {
            Document doc = new Document();

            for (int i = 0; i < actionHistory.size(); i++) {

            }
        }
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        // TODO ends recording and resets vars
    }


    //=================================================
    //                     REPLAY
    //=================================================

    /**
     * Loads saved recording from file
     */
    public void loadRecording(String filename) {
        //readXML.readXMLFile(filename);
        Document xmlFile; // get this from read XML
        List<Element> movesList = null;

        try {
            // TODO need to load the game state via persistence
            readXML.readXMLFile();

            // Load in actions
            actionHistory.clear();

            try {
                SAXBuilder sax = new SAXBuilder();

                // XML as local file
                Document doc = sax.build(new File(filename));

                // Elements
                Element root = doc.getRootElement();
                movesList = root.getChildren("moves");

            } catch (IOException | JDOMException e) {
                e.printStackTrace();
                System.out.println("Error reading file: " + e);
                return;
            }

            if(movesList != null) {
                for (Element elem : movesList) {

                    String direction = elem.getText();

                    switch(direction) {
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
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
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
    public static void runReplay() {

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
     * @return
     */
    public static Queue<Game.Direction> getActionHistory() {
        return actionHistory;
    }

}
