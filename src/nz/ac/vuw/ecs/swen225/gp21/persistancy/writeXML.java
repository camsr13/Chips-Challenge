package nz.ac.vuw.ecs.swen225.gp21.persistancy;

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

    public void writeXMLFile(Game instance){
        //sets up variables from Game
        player = Game.instance.getPlayer();
        tileMap = Game.instance.getTilemap();

        //creates new document and root element
        Document document = new Document();
        Element mapElement = new Element("map");
        document.setRootElement(mapElement);
        //call generatePlayer() to write the playerinfo section with location
        generatePlayer(mapElement);
        //call generateTileRow() for each tileRow in tileMap
        for(Tile[] aTileRow : tileMap){
            generateTileRow(mapElement, aTileRow);
        }
        //Set outputStream and write generated XML file
        XMLOutputter xmlOutputter = new XMLOutputter();
        try(FileOutputStream fileOutputStream = new FileOutputStream("src/nz/ac/vuw/ecs/swen225/gp21/persistancy/currentSave.xml")){
            xmlOutputter.output(document, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        }/**else if(tile instanceof TreasureTile){
            return "treasure";
        }else if(tile instanceof InfoTile){
            return "info";
        }else if(tile instanceof GateTile){
            return "gate";
        }else if(tile instanceof ExitTile){
            return "exit";
        }**/
        else{
            return "wall";
        }
    }

    private String getLockColour(Tile tile){
        //commented out section due to getKeyColour not existing
        /**if(((LockTile)tile).getKeyColour() == Game.KeyColour.BLUE){
            return "blue";
        }else if(((LockTile)tile).getKeyColour() == Game.KeyColour.YELLOW){
            return "yellow";
        }else{
            return null;
        }**/
        return "blue";
    }

    private String getKeyColour(Tile tile){
        //commented out section due to getKeyColour not existing
        /**if(((KeyTile)tile).getKeyColour() == Game.KeyColour.BLUE){
            return "blue";
        }else if(((KeyTile)tile).getKeyColour() == Game.KeyColour.YELLOW){
            return "yellow";
        }else{
            return null;
        }**/
        return "blue";
    }

}
