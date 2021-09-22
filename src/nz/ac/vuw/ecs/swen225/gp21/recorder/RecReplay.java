package nz.ac.vuw.ecs.swen225.gp21.recorder;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Queue;

public class RecReplay {

    private static Queue<Direction> moveHistory = new ArrayDeque<>(); // needs to be Game.Direction, String for testing
    private static boolean isRunning;
    private static boolean isRecording;
    private static int DELAY = 200;

    static Thread thread;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
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
            moveHistory.add(direction);
        }
    }


    /**
     * Returns the queue of player actions.
     *
     * @return moveHistory queue.
     */
    public static Queue<Direction> getMoveHistory() {
        return moveHistory;
    }


    // RECORD

    /**
     * Creates a new recording.
     */
    public static void newRecording() {
        isRecording = true;
        moveHistory.clear();
        // TODO populates moveHistory
        Direction[] arr = new Direction[]{Direction.LEFT, Direction.UP, Direction.UP, Direction.RIGHT};
        for (Direction d : arr) {
            moveHistory.offer(d);
        }
    }


    /**
     * Saves a recording.
     *
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public static void saveRecording(String filePath) throws ParserConfigurationException, TransformerException {
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

        Queue<String> moveQ = new ArrayDeque<>();
        for (Direction direction : moveHistory) {
            switch (direction) {
                case LEFT:
                    moveQ.offer("Left");
                    break;
                case RIGHT:
                    moveQ.offer("Right");
                    break;
                case UP:
                    moveQ.offer("Up");
                    break;
                case DOWN:
                    moveQ.offer("Down");
                    break;
                default:
                    break;
            }
        }

        movesElem.appendChild(doc.createTextNode(moveQ.poll()));
        for (String move : moveQ) {
            movesElem.appendChild(doc.createTextNode(" " + moveQ.poll()));
        }

        // write dom document to a file
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fn = "Chaps_Challenge_Save_" + dtf.format(now);
        try (FileOutputStream output =
                     new FileOutputStream(filePath + fn + ".xml")) {
            writeSaveXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        endRecording(); // clean up
    }


    /**
     * Writes the save file information to the XML and saves the XML to disk.
     *
     * @param doc document object to write.
     * @param out output stream.
     * @throws TransformerException
     */
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


    // REPLAY

    /**
     * Loads the recording file ready for replay.
     */
    public static void loadRecording() {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newDefaultInstance();

        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            org.w3c.dom.Document doc = docBuilder.parse("C:\\Users\\Hazel\\Documents\\VUW 2021 TRI 2\\SWEN225\\Assignments\\Project\\xmlTEST\\testout\\Chaps_Challenge_Save_2021-09-21_162606.xml");
            doc.getDocumentElement().normalize();

            // TESTING
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

                    String[] arr = moves.split(" ");

                    for (String direction : arr) {

                        switch (direction) {
                            case "Left":
                                moveHistory.add(Direction.LEFT);
                                break;
                            case "Right":
                                moveHistory.add(Direction.RIGHT);
                                break;
                            case "Up":
                                moveHistory.add(Direction.UP);
                                break;
                            case "Down":
                                moveHistory.add(Direction.DOWN);
                                break;
                            default:
                                break;
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
    public static void stepReplay() {
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
}
