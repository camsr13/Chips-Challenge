package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Rhys Hanrahan
 *
 *  writeXML, writes the current tileMap in Game to a xml file called currentSave.xml
 *
 *  Some code is commented out due to not yet being implemented
 *
 */

public class writeXML {

    private Player player;
    private Tile[][] tileMap;
    private HashMap<Game.KeyColour, Integer> keysHeld;
    private int totalTreasures;
    private int collectedTreasures;
    private ExitLockTile exitLock;
    private List<Actor> actors;

    /**
     *
     * writeXMLFile writes the current state of tilemap and player to an xmlFile
     *
     */
    public void writeXMLFile(){
        //sets up variables from Game
        player = Game.instance.getPlayer();
        tileMap = Game.instance.getTilemap();
        keysHeld = Game.instance.getKeysHeld();
        totalTreasures = Game.instance.getTotalTreasures();
        collectedTreasures = Game.instance.getCollectedTreasures();
        exitLock = Game.instance.getExitLock();
        actors = Game.instance.getActors();

        //creates new document and root element
        Document document = new Document();
        Element mapElement = new Element("map");
        document.setRootElement(mapElement);
        //call generateMapSize() to write the tileMap size
        generateMapVariables(mapElement);
        //call generateKeysHeld() to write the hashMap of keys on the board and keys the player has
        generateKeysHeld(mapElement);
        //call generatePlayer() to write the playerinfo section with location
        generatePlayer(mapElement);
        //call generate actors to write all the actors on the current level
        generateActors(mapElement, actors);
        //call generateTileRow() for each tileRow in tileMap
        generateTileRows(mapElement, tileMap);
        //Set outputStream and write generated XML file
        XMLOutputter xmlOutputter = new XMLOutputter();
        try(FileOutputStream fileOutputStream = new FileOutputStream("src/nz/ac/vuw/ecs/swen225/gp21/persistency/currentSave.xml")){
            xmlOutputter.output(document, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * generateMapSize gets the number of rows and columns in tileMap and writes them to the xml file under 'size' within 'mapSize'
     *
     * @param mapElement
     */
    private void generateMapVariables(Element mapElement){
        Element mapVariableElement = new Element("mapVariables");
        mapElement.addContent(mapVariableElement);
        //write the number of rows in tileRow
        Element sizeElement = new Element("sizeX");
        sizeElement.addContent(String.valueOf(tileMap.length));
        mapVariableElement.addContent(sizeElement);
        //write the number of columns in tileRow
        sizeElement = new Element("sizeY");
        sizeElement.addContent(String.valueOf(tileMap[0].length));
        mapVariableElement.addContent(sizeElement);
        //write the total number of treasures that are on the current map
        sizeElement = new Element("total");
        sizeElement.addContent(String.valueOf(totalTreasures));
        mapVariableElement.addContent(sizeElement);
        //write the number of collected treasures
        sizeElement = new Element("collected");
        sizeElement.addContent(String.valueOf(collectedTreasures));
        mapVariableElement.addContent(sizeElement);
        //write the number of columns in tileRow
        sizeElement = new Element("exitLockX");
        sizeElement.addContent(String.valueOf(exitLock.getLocation().getX()));
        mapVariableElement.addContent(sizeElement);
        //write the number of columns in tileRow
        sizeElement = new Element("exitLockY");
        sizeElement.addContent(String.valueOf(exitLock.getLocation().getY()));
        mapVariableElement.addContent(sizeElement);
    }

    /**
     *
     * generateKeysHeld read the hashMap of keysHeld and writes it to the save file
     *
     * @param mapElement
     */
    private void generateKeysHeld(Element mapElement){
        //create new tileRowElement for each tileRow
        Element tileKeysHeld = new Element("keysHeld");
        mapElement.addContent(tileKeysHeld);
        //add a new tileElement for each tile in a tileRow
        for(Map.Entry<Game.KeyColour, Integer> entry: keysHeld.entrySet()){
            Element keyElement = new Element("key");
            keyElement.setAttribute("colour", getKeysHeldColour(entry.getKey()));
            keyElement.addContent(String.valueOf(entry.getValue()));
            tileKeysHeld.addContent(keyElement);
        }
    }

    /**
     *
     * generatePlayer gets the x, y location of the player and it to the xml file under 'location' within 'player'
     *
     * @param mapElement
     */
    private void generatePlayer(Element mapElement){
        Element playerElement = new Element("player");
        mapElement.addContent(playerElement);
        //write x coord location of player
        Element locationElement = new Element("location");
        locationElement.addContent(Integer.toString(player.getLocation().getX()));
        playerElement.addContent(locationElement);
        //write y coord location of player
        locationElement = new Element("location");
        locationElement.addContent(Integer.toString(player.getLocation().getY()));
        playerElement.addContent(locationElement);
        //write the time the player is frozen for
        Element frozenElement = new Element("frozen");
        frozenElement.addContent(Integer.toString(player.getTimeFrozen()));
        playerElement.addContent(frozenElement);
    }

    /**
     *
     * generateActors uses the list of actors for a given level and writes their location and direction for each actor
     *
     * @param mapElement
     * @param actors
     */
    private void generateActors(Element mapElement, List<Actor> actors){
        //check if the current level contains any actors
        if(!actors.isEmpty()){
            Element actorsElement = new Element("actors");
            //write each actor to the xml file
            mapElement.addContent(actorsElement);
            for(Actor actor : actors){
                Element freezeElement = new Element("freeze");
                freezeElement.setAttribute("coordX", Integer.toString(actor.getLocation().getX()));
                freezeElement.setAttribute("coordY", Integer.toString(actor.getLocation().getY()));
                freezeElement.addContent(getDirection(((FreezeActor)actor).currentDirection));
                actorsElement.addContent(freezeElement);
            }
        }
    }

    /**
     *
     * generateTileRow cycles through each row of tileMap and writes each tile type to the xml file
     *
     * @param mapElement
     * @param tileMap
     */
    private void generateTileRows(Element mapElement, Tile[][] tileMap){
        //create new tileRowElement for each tileRow
        for(int y = 0; y < tileMap[0].length; y++){
            Element tileRowElement = new Element("tileRow");
            mapElement.addContent(tileRowElement);
            //add a new tileElement for each tile in a tileRow
            for(int x = 0; x < tileMap.length; x++){
                Element tileElement = new Element("tile");
                tileElement.addContent(getTileType(tileMap[x][y], tileElement));
                tileRowElement.addContent(tileElement);
            }
        }
    }

    /**
     *
     * getTileType checks what tile type the current tile is and saves returns a corresponding string word
     *
     * @param tile
     * @param tileElement
     * @return a string representing the tile type
     */
    private String getTileType(Tile tile, Element tileElement){
        if(tile instanceof FreeTile){
            return "free";
        }else if(tile instanceof WallTile){
            return "wall";
        }else if(tile instanceof LockTile){
            tileElement.setAttribute("colour", getLockColour(tile));
            return "door";
        }else if(tile instanceof KeyTile){
            tileElement.setAttribute("colour", getKeyColour(tile));
            return "key";
        }else if(tile instanceof TreasureTile){
            return "treasure";
        }else if(tile instanceof InfoTile){
            tileElement.setAttribute("info", ((InfoTile) tile).getMessage());
            return "info";
        }else if(tile instanceof ExitLockTile){
            return "gate";
        }else if(tile instanceof ExitTile){
            return "exit";
        }else if(tile instanceof ArrowTile){
            tileElement.setAttribute("direction", getDirection(((ArrowTile)tile).getDirection()));
            return "arrow";
        }else if(tile instanceof FreezeTile){
            return "freeze";
        }
        //Incase error or unknown tile type occurs save as wall tile
        else{
            return "wall";
        }
    }

    /**
     *
     * getLockColour is passed a tile then returns a colour string from a corresponding colour enum
     * passed tile is set to be a LockTile as this method is only called when its known the tile is a locked tile
     *
     * @param tile
     * @return a colour string
     */
    private String getLockColour(Tile tile){
        if(((LockTile)tile).getKeyColour() == Game.KeyColour.BLUE){
            return "blue";
        }else if(((LockTile)tile).getKeyColour() == Game.KeyColour.YELLOW){
            return "yellow";
        }else if(((LockTile)tile).getKeyColour() == Game.KeyColour.RED){
            return "red";
        }else if(((LockTile)tile).getKeyColour() == Game.KeyColour.GREEN){
            return "green";
        }else{
            return null;
        }
    }

    /**
     *
     * getKeyColour is passed a tile then returns a colour string from a corresponding colour enum
     * passed tile is set to be a KeyTile as this method is only called when its known the tile is a key tile
     *
     * @param tile
     * @return a colour string
     */
    private String getKeyColour(Tile tile){
        if(((KeyTile)tile).getKeyColour() == Game.KeyColour.BLUE){
            return "blue";
        }else if(((KeyTile)tile).getKeyColour() == Game.KeyColour.YELLOW){
            return "yellow";
        }else if(((KeyTile)tile).getKeyColour() == Game.KeyColour.RED){
            return "red";
        }else if(((KeyTile)tile).getKeyColour() == Game.KeyColour.GREEN){
            return "green";
        }else{
            return null;
        }
    }

    /**
     *
     * get the colour of keys on the map and return string colour
     *
     * @param colour
     * @return
     */
    private String getKeysHeldColour(Game.KeyColour colour){
        if(colour == Game.KeyColour.BLUE){
            return "blue";
        }else if(colour == Game.KeyColour.YELLOW){
            return "yellow";
        }else if(colour == Game.KeyColour.RED){
            return "red";
        }else if(colour == Game.KeyColour.GREEN){
            return "green";
        }else{
            return null;
        }
    }

    /**
     *
     * getDirection is a method used when writing arrow tiles to the xml file
     * When writing an arrowTile a enum Game.direction is passed and a string direction is returned
     *
     * @param direction
     * @return string
     */
    private String getDirection(Game.Direction direction){
        if(direction == Game.Direction.UP){
            return "up";
        }else if(direction == Game.Direction.DOWN){
            return "down";
        }else if(direction == Game.Direction.LEFT){
            return "left";
        }else if(direction == Game.Direction.RIGHT){
            return "right";
        }
        return null;
    }

}
