package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.app.GUIImp;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class RecReplay {

    private static Queue<String> moveHistory = new ArrayDeque<>();
    private static boolean isRunning;
    private static boolean isRecording;
    private static int DELAY = 200;
    static GUIImp GUI;
    static Thread thread;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static void getGUIImp(GUIImp g) {
        GUI = g;
    }

    /**
     * Sets the playback delay to the int specified
     *
     * @param delay
     */
    public static void setDelay(int delay) {
        DELAY = delay;
    }


    // ACTION

    /**
     * Add a player action to actionHistory.
     * TODO Should be called by app??? on movement???
     *
     * @param direction the direction of action.
     */
    public static void addAction(Direction direction) {
        // adds to actionHistory
        if (isRecording) {
            moveHistory.add(direction.toString());
            System.out.println(direction.toString());
        }
    }


    /**
     * Returns the queue of player actions.
     *
     * @return moveHistory queue.
     */
    public static Queue<String> getMoveHistory() {
        return moveHistory;
    }



    // DIALOGS
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

    public static void selectReplayFileDialogue() throws JDOMException, IOException {
        JFrame window = GUI.getMainWindow();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(window);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            ReadXML xmlReader = new ReadXML();
            xmlReader.readXMLFile(fileToSave.getAbsolutePath());
            loadRecording(fileToSave.getAbsolutePath());
        }
    }



    public static void saveConfirmDialogue() throws JDOMException, IOException {
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to save a recording?", "Save recording: ",JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            saveRecording();
        }
    }

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


    public static void customSpeedMode() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select custom speed: ");
        JSpinner spinner = new JSpinner();
        spinner.setValue(200);

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




    // RECORD

    /**
     * Creates a new recording.
     */
    public static void newRecording() {
        isRecording = true;
        moveHistory.clear();
        // TODO populates moveHistory
/*        Direction[] arr = new Direction[]{Direction.LEFT, Direction.UP, Direction.UP, Direction.RIGHT};
        for (Direction d : arr) {
            moveHistory.offer(d);
        }*/
    }


    /**
     * Saves a recording.
     *
     * @throws TransformerException
     */
    public static void saveRecording() {
        WriteXML xmlWriter = new WriteXML();
        Document document = xmlWriter.generateDocument();
        //creates new document and root element
        //Document document = new Document();
        //Element root = new Element("recorded");
        //document.setRootElement(root);
        Element root = document.getRootElement();

        // player moves
        Element playerMovesElem = new Element("playerMoves");
        root.addContent(playerMovesElem);
        System.out.println(moveHistory);
        for (String move : moveHistory) {
            addPlayerMovesElement(playerMovesElem, moveHistory.poll());
        }

        // TODO mob moves element

        // level element -> game state
        Element levelInfoElem = new Element("levelInfo");
        root.addContent(levelInfoElem);
        addLevelElement(levelInfoElem, "1");

        fileSelectDialogue(document, xmlWriter);
        //writeSaveXML(document, filePath);

        endRecording(); // clean up
    }


    public static void addPlayerMovesElement(Element root, String dir) {
        Element move = new Element("move");
        move.addContent(dir);
        root.addContent(move);
    }


    public static void addLevelElement(Element root, String n) {
        Element level = new Element("level");
        level.addContent(n);
        root.addContent(level);
    }


    /**
     * Writes the save file information to the XML and saves the XML to disk.
     *
     * @param doc document object to write.
     * @param fp output file path in string form.
     */
    public static void writeSaveXML(Document doc, String fp) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fn = "Chaps_Challenge_Save_" + dtf.format(now);
        //Set outputStream and write generated XML file
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try(FileOutputStream fileOutputStream = new FileOutputStream(fp)){
            xmlOutputter.output(doc, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        // TODO ends recording and resets vars
        isRecording = false;
        isRunning = false;
        //moveHistory.clear();
    }


    // REPLAY

    public static void onReplay() throws JDOMException, IOException {
        //TODO
        selectReplayFileDialogue();
        selectModeDialogue();
    }

    /**
     * Loads the recording file ready for replay.
     */
    public static void loadRecording(String fp) throws JDOMException, IOException {
        moveHistory.clear();
        Element root = ((Document) (new SAXBuilder()).build(new File(fp))).getRootElement();

        Element playerMoves = root.getChild("playerMoves");
        System.out.println(playerMoves);
        List<Element> moves = playerMoves.getChildren();

        for (Element move : moves) {
            moveHistory.add(move.getText());
        }
        System.out.println(moveHistory);
    }



    /**
     * Steps the replay forward by one
     */
    public static void stepReplay() {
        isRecording = false;
        isRunning = true;
        // If the game is running and there are moves left to replay, step forward by one
        if (isRunning && moveHistory.size() > 0) {
            //game move player method -> (actionHistory.poll())
            String move = moveHistory.poll();
            System.out.println(move);
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
        // When there are no moves left to replay, the game should no longer be running
        if (moveHistory.size() == 0) {
            isRunning = false;
            // TODO send message to Game?? or app?
        }
    }


    /**
     * Runs through the recorded actions
     * Stops once replay is complete
     */
    public static void runReplay() {
        // FIXME
        Runnable run = () -> {
            while (isRunning && moveHistory.size() > 0) {
                try {
                    Thread.sleep(DELAY);
                    stepReplay();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;

        };
        thread = new Thread(run);
        thread.start();
    }

    public static void main(String[] args) throws JDOMException, IOException {
        newRecording();
        saveRecording();
    }
}
