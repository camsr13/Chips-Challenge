package nz.ac.vuw.ecs.swen225.gp21.persistancy;

import nz.ac.vuw.ecs.swen225.gp21.domain.Location;
import nz.ac.vuw.ecs.swen225.gp21.domain.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
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

    //Read xml file and return tilemap
    public Tile[][] readXMLFile(/**String fileName**/) throws JDOMException, IOException {
        //Variables to hold the position where the file is up to
        Element rowElement = null;
        Element tileElement = null;
        Element map = ((Document) (new SAXBuilder()).build(new File("textMap.xml"/**fileName**/))).getRootElement();

        //Get player info (location)
        List<Element> playerInfo = map.getChildren("player");
        player = new Player(new Location(Integer.parseInt(playerInfo.get(0).getText()), Integer.parseInt(playerInfo.get(1).getText())));

        //Loop through the file and goes through all the tileRows
        List<Element> rowsList = map.getChildren("tileRow");
        for(int i = 0; i < rowsList.size(); i++){
            rowElement = (Element) rowsList.get(i);
            Iterator<Element> tutorialsIterator = rowElement.getChildren("tile").iterator();

            //Loops through all the tiles within each tileRow
            int count = 0;
            while(tutorialsIterator.hasNext()){
                tileElement = (Element) tutorialsIterator.next();

                //check for what the tile type is then add it to the tile map
                if(tileElement.getText().equals("free")){
                    //tilemap[i][count] = new freeTile(new Location(count, i), true);
                }else if(tileElement.getText().equals("wall")){
                    //tilemap[i][count] = new wallTile(new Location(count, i), false);
                }else if(tileElement.getText().equals("door")){
                    //tilemap[i][count] = new doorTile(new Location(count, i), false, colour/id);
                }else if(tileElement.getText().equals("key")){
                    //tilemap[i][count] = new keyTile(new Location(count, i), true, colour/id);
                }else if(tileElement.getText().equals("treasure")){
                    //tilemap[i][count] = new treasureTile(new Location(count, i), true);
                }else if(tileElement.getText().equals("info")){
                    //tilemap[i][count] = new infoTile(new Location(count, i), true, info);
                }else if(tileElement.getText().equals("gate")){
                    //tilemap[i][count] = new gateTile(new Location(count, i), false);
                }else if(tileElement.getText().equals("exit")){
                    //tilemap[i][count] = new exitTile(new Location(count, i), true);
                }
                //Incase an error happens/or missing tile type put a wall tile instead
                else{
                    //tilemap[i][count] = new wallTile(new Location(count, i), false);
                }

                //count increase to loop through the file
                count+= 1;
            }
        }
        return tilemap;
    }

    public Player getPlayer(){
        return player;
    }

}