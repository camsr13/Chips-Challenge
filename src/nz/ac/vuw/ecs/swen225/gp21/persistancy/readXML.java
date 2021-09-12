package nz.ac.vuw.ecs.swen225.gp21.persistancy;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
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
    private Tile[][] tilemap = new Tile[5][13];
    private Player player;

    //Read xml file and call setupGame
    public void readXMLFile(/**String fileName**/) throws JDOMException, IOException {
        //Variables to hold the position where the file is up to
        Element rowElement = null;
        Element tileElement = null;
        Element map = ((Document) (new SAXBuilder()).build(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistancy/testMap.xml" /**fileName**/))).getRootElement();

        //Get player info (location)
        List<Element> playerInfo = map.getChildren("player");
        player = new Player(new Location(Integer.parseInt(playerInfo.get(0).getText()), Integer.parseInt(playerInfo.get(1).getText())));

        //Loop through the file and goes through all the tileRows
        List<Element> rowsList = map.getChildren("tileRow");
        for(int i = 0; i < rowsList.size(); i++){
            rowElement = (Element) rowsList.get(i);
            Iterator<Element> Iterator = rowElement.getChildren("tile").iterator();

            //Loops through all the tiles within each tileRow
            int count = 0;
            while(Iterator.hasNext()){
                tileElement = (Element) Iterator.next();

                //check for what the tile type is then add it to the tile map
                if(tileElement.getText().equals("free")){
                    tilemap[i][count] = new FreeTile(new Location(count, i));
                }else if(tileElement.getText().equals("wall")){
                    tilemap[i][count] = new WallTile(new Location(count, i));
                }else if(tileElement.getText().equals("door")){
                    tilemap[i][count] = new LockTile(new Location(count, i), getColour(tileElement.getAttributeValue("colour")));
                }else if(tileElement.getText().equals("key")){
                    tilemap[i][count] = new KeyTile(new Location(count, i), getColour(tileElement.getAttributeValue("colour")));
                }/**else if(tileElement.getText().equals("treasure")){
                    //tilemap[i][count] = new treasureTile(new Location(count, i));
                }else if(tileElement.getText().equals("info")){
                    //tilemap[i][count] = new infoTile(new Location(count, i), info);
                }else if(tileElement.getText().equals("gate")){
                    //tilemap[i][count] = new gateTile(new Location(count, i));
                }else if(tileElement.getText().equals("exit")){
                    //tilemap[i][count] = new exitTile(new Location(count, i));
                }**/
                //Incase an error happens/or missing tile type put a wall tile instead
                else{
                    tilemap[i][count] = new WallTile(new Location(count, i));
                }

                //count increase to loop through the file
                count+= 1;
            }
        }
        Game.instance.setupGame(tilemap, player, new HashMap<Game.KeyColour, Boolean>());
    }

    public Player getPlayer(){
        return player;
    }

    private Game.KeyColour getColour(String colour){
        if(colour.equals("blue")){
            return Game.KeyColour.BLUE;
        }else if(colour.equals("yellow")){
            return Game.KeyColour.YELLOW;
        }
        return null;
    }

}
