package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.persistancy.readXML;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    private static Queue<Game.Direction> moveHistory = new ArrayDeque<>(); // needs to be Game.Direction, String for testing
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
        moveHistory.clear();
        // TODO gets the game state from PERSISTENCE
    }


    /**
     * Saves a recording
     */
    public static void saveRecording(String saveName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root
        org.w3c.dom.Document doc = docBuilder.newDocument();
        org.w3c.dom.Element rootElem = doc.createElement("save");
        doc.appendChild(rootElem);

        // contents
        org.w3c.dom.Element levelElem = doc.createElement("level");
        rootElem.appendChild(levelElem);
        levelElem.appendChild(doc.createTextNode("1"));

        org.w3c.dom.Element movesElem = doc.createElement("moves");
        rootElem.appendChild(movesElem);

        movesElem.appendChild(doc.createTextNode(moveHistory.poll()));
        for (String move : moveHistory) {
            movesElem.appendChild(doc.createTextNode(" " + moveHistory.poll()));
        }

        // write dom document to a file
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fn = "Chaps_Challenge_Save_" + dtf.format(now);
        try (FileOutputStream output =
                     new FileOutputStream("C:\\Users\\Hazel\\Documents\\VUW 2021 TRI 2\\SWEN225\\Assignments\\Project\\xmlTEST\\testout\\" + fn + ".xml")) {
            writeSaveXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeSaveXML(org.w3c.dom.Document doc, OutputStream out) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(out);

        transformer.transform(source, result);
    }


    /**
     * Ends a recording
     */
    public static void endRecording() {
        // TODO ends recording and resets vars
        isRecording = false;
        isRunning = false;
        moveHistory.clear();
    }


    //=================================================
    //                     REPLAY
    //=================================================


    public static void loadRecording() {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newDefaultInstance();

        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            org.w3c.dom.Document doc = docBuilder.parse("C:\\Users\\Hazel\\Documents\\VUW 2021 TRI 2\\SWEN225\\Assignments\\Project\\xmlTEST\\testout\\Chaps_Challenge_Save_2021-09-21_162606.xml");
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            NodeList list = doc.getElementsByTagName("save");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                    // get level
                    String level = element.getElementsByTagName("level").item(0).getTextContent();

                    // get moves list
                    String moves = element.getElementsByTagName("moves").item(0).getTextContent();

                    // TESTING
                    System.out.println("Current Element : " + node.getNodeName());
                    System.out.println("level : " + level);
                    System.out.println("moves : " + moves);

                    String arr[] = moves.split(" ");

                    if (arr != null) {
                        for (String direction : arr) {

                            switch (direction) {
                                case "Left":
                                    moveHistory.add(Game.Direction.LEFT);
                                    break;
                                case "Right":
                                    moveHistory.add(Game.Direction.RIGHT);
                                    break;
                                case "Up":
                                    moveHistory.add(Game.Direction.UP);
                                    break;
                                case "Down":
                                    moveHistory.add(Game.Direction.DOWN);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    System.out.println(moveHistory);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Steps the replay forward by one
     */
    public static void stepReplay(Game g) {
        // If the game is running and there are moves left to replay, step forward by one
        if (isRunning && moveHistory.size() > 0) {
            //game move player method -> (actionHistory.poll())
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
    public static void runReplay(Game g) {
        Runnable run = () -> {
            while (isRunning && moveHistory.size() > 0) {
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
            moveHistory.add(direction);
        }
    }


    /**
     * Returns the queue of player actions
     *
     * @return
     */
    public static Queue<Game.Direction> getMoveHistory() {
        return moveHistory;
    }

}
