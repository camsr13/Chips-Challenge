package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ReadXML;
import nz.ac.vuw.ecs.swen225.gp21.persistency.WriteXML;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class RecReplay {

    private static Queue<String> moveHistory = new ArrayDeque<>();
    private static boolean isRecording;
    private static int DELAY = 1500;

    static GUIImp GUI;
    static Thread thread;
    static WriteXML xmlWriter;
    static Document curSaveDoc;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


    // GETTERS //

    /**
     * Called by app to get the current instance of GUIImp.
     * @param g instance of GUIImp
     */
    public static void getGUIImp(GUIImp g) {
        GUI = g;
    }


    /**
     * Returns the queue of player actions.
     *
     * @return moveHistory queue.
     */
    public static Queue<String> getMoveHistory() {
        return moveHistory;
    }


    // ACTION METHODS //

    /**
     * Add a player action to moveHistory.
     *
     * @param direction the direction of action.
     */
    public static void addAction(Direction direction) {
        if (isRecording) {
            moveHistory.add(direction.toString()); // add the string version of enum
        }
    }


    // DIALOG METHODS //

    /**
     * Launches a file select dialogue to save the recording.
     *
     * @param document document to write to.
     * @param xmlWriter instance of WriteXML.
     */
    public static void fileSelectDialogue(Document document, WriteXML xmlWriter) {
        JFrame window = GUI.getMainWindow();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(window);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            xmlWriter.writeXMLFile(document, fileToSave.getAbsolutePath());
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }


    /**
     * Dialogue for user to select replay mode.
     */
    public static void selectModeDialogue() {
        String[] options = new String[]{"Step-by-step", "Auto", "Custom Speed"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select Replay Mode: ");
        JComboBox comboBox = new JComboBox(options);

        // Setup
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(comboBox);

        // JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel,"Replay ", JOptionPane.CLOSED_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String selection = comboBox.getSelectedItem().toString();

            switch (selection) {
                case "Step-by-step":
                    stepMode();
                    break;
                case "Auto":
                    runReplay();
                    break;
                case "Custom Speed":
                    customSpeedMode();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Replays in stepped mode, one action executed per button press.
     */
    public static void stepMode() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Step forward one: ");
        JButton button = new JButton("Step");

        // Setup
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(button);

        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                stepReplay();
            }
        });

        // JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel,"Replay ", JOptionPane.CLOSED_OPTION);
        if (result == JOptionPane.CLOSED_OPTION) {
        }
    }


    /**
     * Allows user to custom select speed for auto replay.
     */
    public static void customSpeedMode() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select custom speed: ");
        JSpinner spinner = new JSpinner();
        spinner.setValue(DELAY);

        // Setup
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(spinner);

        // JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel,"Replay ", JOptionPane.CLOSED_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int selection = (int) spinner.getValue();
            DELAY = selection;
            runReplay();
        }
    }


    // RECORDER METHODS //

    /**
     * Creates a new recording.
     */
    public static void newRecording() {
        isRecording = true;
        moveHistory.clear();
        xmlWriter = new WriteXML();
        curSaveDoc = xmlWriter.generateDocument();
    }


    /**
     * Makes document for saving recording.
     */
    public static void saveRecording() {
        Element root = curSaveDoc.getRootElement();

        // Adds player moves to xml save
        Element playerMovesElem = new Element("playerMoves");
        root.addContent(playerMovesElem);
        System.out.println(moveHistory);
        for (String move : moveHistory) {
            addPlayerMovesElement(playerMovesElem, moveHistory.poll());
        }

        // Get filepath to save, and save
        fileSelectDialogue(curSaveDoc, xmlWriter);

        endRecording(); // clean up
    }


    /**
     * Adds each move to the playMoves Element.
     *
     * @param root element moves are to be saved to.
     * @param dir player move direction.
     */
    public static void addPlayerMovesElement(Element root, String dir) {
        Element move = new Element("move");
        move.addContent(dir);
        root.addContent(move);
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        isRecording = false;
        moveHistory.clear();
    }


    // REPLAY METHODS //

    /**
     * Called by App to start the replay process.
     *
     * @throws JDOMException exception
     * @throws IOException exception
     */
    public static void onReplay() throws JDOMException, IOException {
        String fp = ReadXML.fileChooser();
        ReadXML xmlReader = new ReadXML();
        xmlReader.readXMLFile(fp);
        loadRecording(fp);
        selectModeDialogue();
    }


    /**
     * Loads the recording xml from a filepath to read the moves.
     * Populated move history with recorded moves.
     * @param fp filepath
     * @throws JDOMException exception
     * @throws IOException exception
     */
    public static void loadRecording(String fp) throws JDOMException, IOException {
        moveHistory.clear(); // Ensures moveHistory is empty for re-population

        Element root = ((Document) (new SAXBuilder()).build(new File(fp))).getRootElement();
        Element playerMoves = root.getChild("playerMoves");
        System.out.println(playerMoves);
        List<Element> moves = playerMoves.getChildren();

        // Adds all moves to move history
        for (Element move : moves) {
            moveHistory.add(move.getText());
        }
    }


    /**
     * Steps the replay forward by one
     */
    public static void stepReplay() {
        isRecording = false; // Ensure no new moves are being recorded.

        if (moveHistory.size() > 0) {
            String move = moveHistory.poll();

            switch (move) {
                case "LEFT":
                    GUI.doWestMove();
                    break;
                case "RIGHT":
                    GUI.doEastMove();
                    break;
                case "UP":
                    GUI.doNorthMove();
                    break;
                case "DOWN":
                    GUI.doSouthMove();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Runs through the recorded actions.
     * Stops once replay is complete.
     */
    public static void runReplay() {
        Runnable run = () -> {
            while (moveHistory.size() > 0) {
                try {
                    Thread.sleep(DELAY);
                    stepReplay();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread = new Thread(run);
        thread.start();
    }
}
