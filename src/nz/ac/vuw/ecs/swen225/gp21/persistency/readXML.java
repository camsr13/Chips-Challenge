package nz.ac.vuw.ecs.swen225.gp21.persistency;

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
        readXMLFile("testMap.xml");
    }

    /**
     *
     * readXMLFile takes a fileName as a string input, then reads that file to create a tilemap and player object
     *
     * @throws JDOMException
     * @throws IOException
     */
    public void readXMLFile(String fileName) throws JDOMException, IOException {
        //Variables to hold the position where the file is up to
        Element rowElement = null;
        Element tileElement = null;
        Element map = ((Document) (new SAXBuilder()).build(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/" + fileName))).getRootElement();

        //Get map size
        List<Element> mapSize = map.getChildren("mapSize");
        for(int i = 0; i < mapSize.size(); i++){
            rowElement = (Element) mapSize.get(i);
            Iterator<Element> Iterator = rowElement.getChildren("size").iterator();
            //There will always be two size elements in the XML file for the mapSize rows and columns
            tilemap = new Tile[Integer.parseInt(Iterator.next().getText())][Integer.parseInt(Iterator.next().getText())];
        }

        //Get player info (location)
        List<Element> playerInfo = map.getChildren("player");
        for(int i = 0; i < playerInfo.size(); i++){
            rowElement = (Element) playerInfo.get(i);
            Iterator<Element> Iterator = rowElement.getChildren("location").iterator();
            //There will always be two location elements in the XML file for the players x and y coords
            player = new Player(new Location(Integer.parseInt(Iterator.next().getText()), Integer.parseInt(Iterator.next().getText())));
        }

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
                    tilemap[count][i] = new FreeTile(new Location(count, i));
                }else if(tileElement.getText().equals("wall")){
                    tilemap[count][i] = new WallTile(new Location(count, i));
                }else if(tileElement.getText().equals("door")){
                    tilemap[count][i] = new LockTile(new Location(count, i), getColour(tileElement.getAttributeValue("colour")));
                }else if(tileElement.getText().equals("key")){
                    tilemap[count][i] = new KeyTile(new Location(count, i), getColour(tileElement.getAttributeValue("colour")));
                }else if(tileElement.getText().equals("treasure")){
                    tilemap[count][i] = new TreasureTile(new Location(count, i));
                }else if(tileElement.getText().equals("info")){
                    tilemap[count][i] = new InfoTile(new Location(count, i));
                }else if(tileElement.getText().equals("gate")){
                    tilemap[count][i] = new ExitLockTile(new Location(count, i));
                }else if(tileElement.getText().equals("exit")){
                    tilemap[count][i] = new ExitTile(new Location(count, i));
                }
                //Incase an error happens/or missing tile type put a wall tile instead
                else{
                    tilemap[count][i] = new WallTile(new Location(count, i));
                }

                //count increase to loop through the file
                count+= 1;
            }
        }
        Game.instance.setupGame(tilemap, player, new HashMap<Game.KeyColour, Boolean>());
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
