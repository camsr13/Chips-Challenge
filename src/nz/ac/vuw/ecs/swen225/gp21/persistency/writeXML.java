package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    /**
     *
     * writeXMLFile writes the current state of tilemap and player to an xmlFile
     *
     */
    public void writeXMLFile(){
        //sets up variables from Game
        player = Game.instance.getPlayer();
        tileMap = Game.instance.getTilemap();

        //creates new document and root element
        Document document = new Document();
        Element mapElement = new Element("map");
        document.setRootElement(mapElement);
        //call generateMapSize() to write the tileMap size
        generateMapSize(mapElement);
        //call generatePlayer() to write the playerinfo section with location
        generatePlayer(mapElement);
        //call generateTileRow() for each tileRow in tileMap
        for(Tile[] aTileRow : tileMap){
            generateTileRow(mapElement, aTileRow);
        }
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
    private void generateMapSize(Element mapElement){
        Element mapSizeElement = new Element("mapSize");
        mapElement.addContent(mapSizeElement);
        //write the number of rows in tileRow
        Element sizeElement = new Element("size");
        sizeElement.addContent(String.valueOf(tileMap.length));
        mapSizeElement.addContent(sizeElement);
        //write the number of columns in tileRow
        sizeElement = new Element("size");
        sizeElement.addContent(String.valueOf(tileMap[0].length));
        mapSizeElement.addContent(sizeElement);
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
    }

    /**
     *
     * generateTileRow cycles through each row of tileMap and writes each tile type to the xml file
     *
     * @param mapElement
     * @param row
     */
    private void generateTileRow(Element mapElement, Tile[] row){
        //create new tileRowElement for each tileRow
        Element tileRowElement = new Element("tileRow");
        mapElement.addContent(tileRowElement);
        //add a new tileElement for each tile in a tileRow
        for(Tile aTile : row){
            Element tileElement = new Element("tile");
            tileElement.addContent(getTileType(aTile, tileElement));
            tileRowElement.addContent(tileElement);
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
            return "info";
        }else if(tile instanceof ExitLockTile){
            return "gate";
        }else if(tile instanceof ExitTile){
            return "exit";
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
        }else{
            return null;
        }
    }

}
