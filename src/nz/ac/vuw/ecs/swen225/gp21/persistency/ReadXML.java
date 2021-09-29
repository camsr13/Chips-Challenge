package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Rhys Hanrahan
 *
 *  readXML is passed a file name, then reads through it and creates a tilemap and player object
 *
 *  The tilemap currently has coordinates set to be 0,0 top left. Counting up when X goes right and Y goes down.
 *
 */

public class ReadXML {

    //Set default timeLimit to 999 seconds
    private int timeLimit = 999;


    /**
     *
     * in case ReadXMLFile is called without a filename it will call the real ReadXMLFile with the testMap as the filename
     *
     */
    public void readXMLFile() throws JDOMException, IOException {
        readXMLFile("levels/testMap.xml");
    }

    /**
     *
     * readXMLFile takes a fileName as a string input, then reads that file to create a tilemap and player object
     *
     */
    public void readXMLFile(String fileDirectory) throws JDOMException, IOException {
        //Variables to hold the position where the file is up to
        Element parentElement;
        Element childElement;
        Element map = (new SAXBuilder()).build(new File(fileDirectory)).getRootElement();

        //Get map variables
        parentElement = map.getChild("mapVariables");
        //get the map size and create a new tileMap (2d array) of that size
        //Test map size
        Tile[][] tilemap = new Tile[Integer.parseInt(parentElement.getChild("sizeX").getText())][Integer.parseInt(parentElement.getChild("sizeY").getText())];
        //get and store the totalTreasures and collectedTreasures
        int totalTreasure = Integer.parseInt(parentElement.getChild("total").getText());
        int collectedTreasures = Integer.parseInt(parentElement.getChild("collected").getText());
        //get the coordinates of the exit lock
        int exitLockX = Integer.parseInt(parentElement.getChild("exitLockX").getText());
        int exitLockY = Integer.parseInt(parentElement.getChild("exitLockY").getText());
        //get the time limit for the level
        timeLimit = Integer.parseInt(parentElement.getChild("time").getText());

        //Get keysHeld
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<>();
        parentElement = map.getChild("keysHeld");
        Iterator<Element> Iterator = parentElement.getChildren("key").iterator();
        //For every key colour, add the it to the hashmap
        while(Iterator.hasNext()) {
            childElement = Iterator.next();
            keysHeld.put(getColour(childElement.getAttributeValue("colour")),Integer.parseInt(childElement.getText()));
        }

        //Get player info (location)
        parentElement = map.getChild("player");
        Iterator = parentElement.getChildren("location").iterator();
        //There will always be two location elements in the XML file for the players x and y coordinates
        Player player = new Player(new Location(Integer.parseInt(Iterator.next().getText()), Integer.parseInt(Iterator.next().getText())), Integer.parseInt(parentElement.getChild("frozen").getText()));

        //get actors
        List<Actor> actors = new ArrayList<>();
        parentElement = map.getChild("actors");
        //check if there are actors on the current xml level
        if(parentElement != null){
            Iterator = parentElement.getChildren("freeze").iterator();
            while(Iterator.hasNext()){
                childElement = Iterator.next();
                actors.add(new FreezeActor(new Location(Integer.parseInt(childElement.getAttributeValue("coordX")), Integer.parseInt(childElement.getAttributeValue("coordY"))), getDirection(childElement.getText())));
            }
        }


        //Loop through the file and goes through all the tileRows
        List<Element> rowsList = map.getChildren("tileRow");
        for(int i = 0; i < rowsList.size(); i++){
            parentElement = rowsList.get(i);
            Iterator = parentElement.getChildren("tile").iterator();

            //Loops through all the tiles within each tileRow
            int count = 0;
            while(Iterator.hasNext()){
                childElement = Iterator.next();

                //check for what the tile type is then add it to the tile map
                //In case an error happens/or missing tile type put a wall tile instead
                switch (childElement.getText()) {
                    case "free" -> tilemap[count][i] = new FreeTile(new Location(count, i));
                    case "door" -> tilemap[count][i] = new LockTile(new Location(count, i), getColour(childElement.getAttributeValue("colour")));
                    case "key" -> tilemap[count][i] = new KeyTile(new Location(count, i), getColour(childElement.getAttributeValue("colour")));
                    case "treasure" -> tilemap[count][i] = new TreasureTile(new Location(count, i));
                    case "info" -> tilemap[count][i] = new InfoTile(new Location(count, i), childElement.getAttributeValue("info"));
                    case "gate" -> tilemap[count][i] = new ExitLockTile(new Location(count, i));
                    case "exit" -> tilemap[count][i] = new ExitTile(new Location(count, i));
                    case "arrow" -> tilemap[count][i] = new ArrowTile(new Location(count, i), getDirection(childElement.getAttributeValue("direction")));
                    case "freeze" -> tilemap[count][i] = new FreezeTile(new Location(count, i));
                    default -> tilemap[count][i] = new WallTile(new Location(count, i));
                }

                //count increase to loop through the file
                count+= 1;
            }
        }
        Game.instance.setupGame(tilemap, player, keysHeld, totalTreasure, collectedTreasures, (ExitLockTile) tilemap[exitLockX][exitLockY], actors);
    }

    /**
     *
     * getColour is a method to get a string colour and return the corresponding enum colour from Game
     * returns null at the end of the method, which should never be reached if getColour checks for all valid colours
     *
     * @return colour enum
     */
    private Game.KeyColour getColour(String colour){
        return switch (colour) {
            case "blue" -> Game.KeyColour.BLUE;
            case "yellow" -> Game.KeyColour.YELLOW;
            case "red" -> Game.KeyColour.RED;
            case "green" -> Game.KeyColour.GREEN;
            default -> null;
        };
    }

    /**
     *
     * getDirection is a method used when creating tiles for the tileMap
     * When creating an arrowTile a string direction from the xml file is passed and the enum Game.direction is returned
     *
     * @return enum direction from game
     */
    private Game.Direction getDirection(String direction){
        return switch (direction) {
            case "up" -> Game.Direction.UP;
            case "down" -> Game.Direction.DOWN;
            case "left" -> Game.Direction.LEFT;
            case "right" -> Game.Direction.RIGHT;
            default -> null;
        };
    }


    /**
     *
     * @return the time limit within the level file
     */
    public int getTimeLimit(){
        return timeLimit;
    }

    /**
     *
     * fetches the current game instance from Game
     * @return Game.instance
     */
    public Game getGameInstance(){
        return Game.instance;
    }

}
