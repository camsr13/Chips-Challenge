package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Document;
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

public class readXML {

    //Test map size
    private Tile[][] tilemap;
    private Player player;


    /**
     *
     * incase ReadXMLFile is called without a filename it will call the real ReadXMLFile with the testmap as the filename
     *
     * @throws JDOMException
     * @throws IOException
     */
    public void readXMLFile() throws JDOMException, IOException {
        readXMLFile("src/nz/ac/vuw/ecs/swen225/gp21/persistency/testMap.xml");
    }

    /**
     *
     * readXMLFile takes a fileName as a string input, then reads that file to create a tilemap and player object
     *
     * @throws JDOMException
     * @throws IOException
     */
    public void readXMLFile(String fileDirectory) throws JDOMException, IOException {
        //Variables to hold the position where the file is up to
        Element parentElement = null;
        Element childElement = null;
        Element map = ((Document) (new SAXBuilder()).build(new File(fileDirectory))).getRootElement();

        //Get map variables
        parentElement = map.getChild("mapVariables");
        //get the map size and create a new tileMap (2d array) of that size
        tilemap = new Tile[Integer.parseInt(parentElement.getChild("sizeX").getText())][Integer.parseInt(parentElement.getChild("sizeY").getText())];
        //get and store the totalTreasures and collectedTreasures
        int totalTreasure = Integer.parseInt(parentElement.getChild("total").getText());
        int collectedTreasures = Integer.parseInt(parentElement.getChild("collected").getText());
        //get the coordinates of the exit lock
        int exitLockX = Integer.parseInt(parentElement.getChild("exitLockX").getText());
        int exitLockY = Integer.parseInt(parentElement.getChild("exitLockY").getText());

        //Get keysHeld
        HashMap<Game.KeyColour, Integer> keysHeld = new HashMap<Game.KeyColour, Integer>();
        parentElement = map.getChild("keysHeld");
        Iterator<Element> Iterator = parentElement.getChildren("key").iterator();
        //For every key colour, add the it to the hashmap
        while(Iterator.hasNext()) {
            childElement = (Element) Iterator.next();
            keysHeld.put(getColour(childElement.getAttributeValue("colour")),Integer.parseInt(childElement.getText()));
        }

        //Get player info (location)
        parentElement = map.getChild("player");
        Iterator = parentElement.getChildren("location").iterator();
        //There will always be two location elements in the XML file for the players x and y coords
        player = new Player(new Location(Integer.parseInt(Iterator.next().getText()), Integer.parseInt(Iterator.next().getText())), Integer.parseInt(parentElement.getChild("frozen").getText()));

        //get actors
        List<Actor> actors = new ArrayList<Actor>();
        parentElement = map.getChild("actors");
        //check if there are actors on the current xml level
        if(parentElement != null){
            Iterator = parentElement.getChildren("freeze").iterator();
            while(Iterator.hasNext()){
                childElement = (Element) Iterator.next();
                actors.add(new FreezeActor(new Location(Integer.parseInt(childElement.getAttributeValue("coordX")), Integer.parseInt(childElement.getAttributeValue("coordY"))), getDirection(childElement.getText())));
            }
        }


        //Loop through the file and goes through all the tileRows
        List<Element> rowsList = map.getChildren("tileRow");
        for(int i = 0; i < rowsList.size(); i++){
            parentElement = (Element) rowsList.get(i);
            Iterator = parentElement.getChildren("tile").iterator();

            //Loops through all the tiles within each tileRow
            int count = 0;
            while(Iterator.hasNext()){
                childElement = (Element) Iterator.next();

                //check for what the tile type is then add it to the tile map
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
        Game.instance.setupGame(tilemap, player, keysHeld, totalTreasure, collectedTreasures, (ExitLockTile) tilemap[exitLockX][exitLockY], actors);
    }

    /**
     *
     * getColour is a method to get a string colour and return the corresponding enum colour from Game
     * returns null at the end of the method, which should never be reached if getColour checks for all valid colours
     *
     * @param colour
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
     * @param direction
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
     * fetches the current game instance from Game for Recorder
     * @return Game.instance
     */
    public Game getGameInstance(){
        return Game.instance;
    }

}
