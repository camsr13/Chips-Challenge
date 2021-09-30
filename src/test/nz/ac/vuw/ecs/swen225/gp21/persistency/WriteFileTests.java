package test.nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ReadXML;
import nz.ac.vuw.ecs.swen225.gp21.persistency.WriteXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class WriteFileTests {
    @Test
    public void testWriteTestFile_01() throws JDOMException, IOException {
        //needed for the test, else game is null and read/write xml does not work
        Game game = new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level1.xml");
        WriteXML instance = new WriteXML();
        instance.writeXMLFile(instance.generateDocument(), "levels/testFile.xml");

        assert (new File("levels/testFile.xml").isFile());
    }

    @Test
    public void testReadTestFile_01() throws JDOMException, IOException {
        new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level1.xml");
        //get the created tilemap from Game and print it
        Tile[][] tilemap = Game.instance.getTilemap();
        for (int y = 0; y < tilemap[0].length; y++) {
            for (Tile[] tiles : tilemap) {
                System.out.print(tiles[y].toChar() + "");
            }
            System.out.println();
        }

        //check the location of a key and treasure
        assert (tilemap[3][11] instanceof KeyTile);
        assert (tilemap[5][9] instanceof TreasureTile);

        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.RIGHT);
        Game.instance.inputDirection(Game.Direction.RIGHT);
        Game.instance.inputDirection(Game.Direction.UP);
        Game.instance.inputDirection(Game.Direction.UP);
        Game.instance.inputDirection(Game.Direction.UP);

        WriteXML writeXML = new WriteXML();
        writeXML.writeXMLFile(writeXML.generateDocument(), "levels/testFile.xml");

        readXML.readXMLFile("levels/testFile.xml");

        //get the created tilemap from Game and print it
        tilemap = Game.instance.getTilemap();
        for (int y = 0; y < tilemap[0].length; y++) {
            for (Tile[] tiles : tilemap) {
                System.out.print(tiles[y].toChar() + "");
            }
            System.out.println();
        }

        //check the location of the key and treasure are now freeTiles
        assert (tilemap[3][11] instanceof FreeTile);
        assert (tilemap[5][9] instanceof FreeTile);
        //Check there is a door at location
        assert (tilemap[3][6] instanceof LockTile);

        //move player through door with key picked up before saving
        Game.instance.inputDirection(Game.Direction.LEFT);
        Game.instance.inputDirection(Game.Direction.LEFT);
        Game.instance.inputDirection(Game.Direction.UP);
        Game.instance.inputDirection(Game.Direction.UP);
        Game.instance.inputDirection(Game.Direction.UP);

        //check the door was unlocked and the player went through the door
        assert (tilemap[3][6] instanceof FreeTile);
        assert (Game.instance.getPlayer().getLocation().getX() == 3);
        assert (Game.instance.getPlayer().getLocation().getY() == 5);
    }

    @Test
    public void testReadLevel_01() throws JDOMException, IOException {
        new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level2.xml");
        Tile[][] tilemap = Game.instance.getTilemap();

        //Get the player through the ArrowTile maze
        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.DOWN);
        Game.instance.inputDirection(Game.Direction.RIGHT);
        Game.instance.inputDirection(Game.Direction.RIGHT);

        Game.instance.inputDirection(Game.Direction.DOWN);
        for(int i = 0; i < 11; i++){
            Game.instance.tick();
        }
        Game.instance.inputDirection(Game.Direction.LEFT);
        for(int i = 0; i < 6; i++){
            Game.instance.tick();
        }
        Game.instance.inputDirection(Game.Direction.UP);
        for(int i = 0; i < 8; i++){
            Game.instance.tick();
        }

        //Save the game half way through the arrow maze
        WriteXML writeXML = new WriteXML();
        writeXML.writeXMLFile(writeXML.generateDocument(), "levels/testFile.xml");
        readXML.readXMLFile("levels/testFile.xml");

        //continue the arrow maze
        Game.instance.inputDirection(Game.Direction.LEFT);
        for(int i = 0; i < 7; i++){
            Game.instance.tick();
        }
        Game.instance.inputDirection(Game.Direction.DOWN);
        for(int i = 0; i < 5; i++){
            Game.instance.tick();
        }

        //check to see if the player is at the end of the arrow maze
        assert (Game.instance.getPlayer().getLocation().getX() == 6);
        assert (Game.instance.getPlayer().getLocation().getY() == 26);
    }

}
