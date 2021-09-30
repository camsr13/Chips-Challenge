package test.nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ReadXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadFileTests {

    @Test
    public void testReadTestMap_01() throws JDOMException, IOException {
        new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile();
        //get the created tilemap from Game and print it
        Tile[][] tilemap = Game.instance.getTilemap();
        for(int y = 0; y < tilemap[0].length; y++){
            for (Tile[] tiles : tilemap) {
                System.out.print(tiles[y].toChar() + "");
            }
            System.out.println();
        }
        //Check to see if all tile types were created and in correct locations
        assert (tilemap[0][2] instanceof WallTile);
        assert (tilemap[1][2] instanceof FreeTile);
        assert (tilemap[2][2] instanceof KeyTile);
        assert (tilemap[6][2] instanceof LockTile);
        assert (tilemap[8][2] instanceof InfoTile);
        assert (tilemap[9][2] instanceof TreasureTile);
        assert (tilemap[11][2] instanceof ExitLockTile);
        assert (tilemap[12][2] instanceof ExitTile);
        assert (tilemap[2][1] instanceof FreezeTile);
        assert (tilemap[8][1] instanceof ArrowTile);

        //Check if a freezeActor was created and is in the correct location
        assert (Game.instance.getActors().get(0).getLocation().getX() == 8);
        assert (Game.instance.getActors().get(0).getLocation().getY() == 3);

        //get the created player from Game and check it
        Player player = Game.instance.getPlayer();
        System.out.println("Player located at x: " + player.getLocation().getX() + ", y: " + player.getLocation().getY());

        assert (player.getLocation().getX() == 4);
        assert (player.getLocation().getY() == 2);

        //check getInstance is returning instance equal to Game.instance
        assert (readXML.getGameInstance().equals(Game.instance));

        //check getTimeLimit is equal to the set time limit in the xml file
        assert(readXML.getTimeLimit() == 999);
    }

    @Test
    public void testReadLevel1_01() throws JDOMException, IOException {
        new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level1.xml");
        //get the created tilemap from Game and print it
        Tile[][] tilemap = Game.instance.getTilemap();
        for(int y = 0; y < tilemap[0].length; y++){
            for (Tile[] tiles : tilemap) {
                System.out.print(tiles[y].toChar() + "");
            }
            System.out.println();
        }

        //Check to see if all tile types were created and in correct locations
        assert (tilemap[0][0] instanceof WallTile);
        assert (tilemap[1][1] instanceof FreeTile);
        assert (tilemap[6][2] instanceof KeyTile);
        assert (tilemap[3][6] instanceof LockTile);
        assert (tilemap[3][9] instanceof InfoTile);
        assert (tilemap[1][9] instanceof TreasureTile);
        assert (tilemap[17][9] instanceof ExitLockTile);
        assert (tilemap[18][9] instanceof ExitTile);

        //get the created player from Game and check it
        Player player = Game.instance.getPlayer();
        System.out.println("Player located at x: " + player.getLocation().getX() + ", y: " + player.getLocation().getY());

        assert (player.getLocation().getX() == 3);
        assert (player.getLocation().getY() == 9);
    }

    @Test
    public void testReadLevel2_01() throws JDOMException, IOException {
        new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level2.xml");
        //get the created tilemap from Game and print it
        Tile[][] tilemap = Game.instance.getTilemap();
        for(int y = 0; y < tilemap[0].length; y++){
            for (Tile[] tiles : tilemap) {
                System.out.print(tiles[y].toChar() + "");
            }
            System.out.println();
        }

        //Check to see if all tile types were created and in correct locations
        assert (tilemap[0][0] instanceof WallTile);
        assert (tilemap[2][2] instanceof FreeTile);
        assert (tilemap[1][14] instanceof KeyTile);
        assert (tilemap[6][6] instanceof LockTile);
        assert (tilemap[9][8] instanceof InfoTile);
        assert (tilemap[1][2] instanceof TreasureTile);
        assert (tilemap[9][6] instanceof ExitLockTile);
        assert (tilemap[9][1] instanceof ExitTile);
        assert (tilemap[7][1] instanceof FreezeTile);
        assert (tilemap[4][10] instanceof ArrowTile);

        //Check if three freezeActor was created and is in the correct location
        assert (Game.instance.getActors().get(0).getLocation().getX() == 2);
        assert (Game.instance.getActors().get(0).getLocation().getY() == 1);
        assert (Game.instance.getActors().get(1).getLocation().getX() == 1);
        assert (Game.instance.getActors().get(1).getLocation().getY() == 18);
        assert (Game.instance.getActors().get(2).getLocation().getX() == 2);
        assert (Game.instance.getActors().get(2).getLocation().getY() == 19);

        //get the created player from Game and check if they start in the correct place
        Player player = Game.instance.getPlayer();
        System.out.println("Player located at x: " + player.getLocation().getX() + ", y: " + player.getLocation().getY());

        assert (player.getLocation().getX() == 9);
        assert (player.getLocation().getY() == 8);
    }

}
