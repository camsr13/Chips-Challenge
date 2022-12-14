package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
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
        String directory = fileChooser();
        while(!directory.endsWith(".xml")){
            directory = fileChooser();
        }
        readXMLFile(directory);
    }

    /**
     * Allows the user to select the directory for a level xml
     *
     * @return directory string
     */
    public static String fileChooser(){
        JFileChooser fileDir = new JFileChooser();
        fileDir.showOpenDialog(null);
        File file = fileDir.getSelectedFile();
        String fileName = file.getAbsolutePath();

        return fileName;
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
                if(childElement.getText().equals("free")){
                    tilemap[count][i] = new FreeTile(new Location(count, i));
                }else if(childElement.getText().equals("wall")){
                    tilemap[count][i] = new WallTile(new Location(count, i));
                }else if(childElement.getText().equals("door")){
                    tilemap[count][i] = new LockTile(new Location(count, i), getColour(childElement.getAttributeValue("colour")));
                }else if(childElement.getText().equals("key")){
                    tilemap[count][i] = new KeyTile(new Location(count, i), getColour(childElement.getAttributeValue("colour")));
                }else if(childElement.getText().equals("treasure")){
                    tilemap[count][i] = new TreasureTile(new Location(count, i));
                }else if(childElement.getText().equals("info")){
                    tilemap[count][i] = new InfoTile(new Location(count, i), childElement.getAttributeValue("info"));
                }else if(childElement.getText().equals("gate")){
                    tilemap[count][i] = new ExitLockTile(new Location(count, i));
                }else if(childElement.getText().equals("exit")){
                    tilemap[count][i] = new ExitTile(new Location(count, i));
                }else if(childElement.getText().equals("arrow")){
                    tilemap[count][i] = new ArrowTile(new Location(count, i), getDirection(childElement.getAttributeValue("direction")));
                }else if(childElement.getText().equals("freeze")){
                    tilemap[count][i] = new FreezeTile(new Location(count, i));
                }
                //Incase an error happens/or missing tile type put a wall tile instead
                else{
                    tilemap[count][i] = new WallTile(new Location(count, i));

                }

                //count increase to loop through the file
                count+= 1;
            }
        }
        //in case loading a save file that does not have the exit lock
        ExitLockTile exitLTile;
        if(tilemap[exitLockX][exitLockY] instanceof ExitLockTile){
            exitLTile = (ExitLockTile)tilemap[exitLockX][exitLockY];
        }else{
            exitLTile = new ExitLockTile(new Location(0,0));
        }
        Game.instance.setupGame(tilemap, player, keysHeld, totalTreasure, collectedTreasures, exitLTile, actors);
    }

    /**
     *
     * getColour is a method to get a string colour and return the corresponding enum colour from Game
     * returns null at the end of the method, which should never be reached if getColour checks for all valid colours
     *
     * @return colour enum
     */
    private Game.KeyColour getColour(String colour){
        if(colour.equals("blue")){
            return Game.KeyColour.BLUE;
        }else if(colour.equals("yellow")){
            return Game.KeyColour.YELLOW;
        }else if(colour.equals("red")){
            return Game.KeyColour.RED;
        }else if(colour.equals("green")){
            return Game.KeyColour.GREEN;
        }
        return null;
    }

    /**
     *
     * getDirection is a method used when creating tiles for the tileMap
     * When creating an arrowTile a string direction from the xml file is passed and the enum Game.direction is returned
     *
     * @return enum direction from game
     */
    private Game.Direction getDirection(String direction){
        if(direction.equals("up")){
            return Game.Direction.UP;
        }else if(direction.equals("down")){
            return Game.Direction.DOWN;
        }else if(direction.equals("left")){
            return Game.Direction.LEFT;
        }else if(direction.equals("right")){
            return Game.Direction.RIGHT;
        }
        return null;
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
