package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.persistancy.readXML;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.app.*;
import org.jdom2.Document;

import java.util.ArrayDeque;
import java.util.Queue;

public class RecReplay {

    private static Queue<Game.Direction> actionHistory = new ArrayDeque<>();
    private static boolean isRunning;
    private static boolean isRecording;
    private static int DELAY = 200;


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
    public static void saveRecording() {
        if (isRecording) {

        }
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        // ends recording and resets vars
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




    }


    /**
     * Steps the replay forward by one
     */
    public static void stepReplay() {

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
