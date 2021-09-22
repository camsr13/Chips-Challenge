package test.nz.ac.vuw.ecs.swen225.gp21.persistancy;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.persistancy.readXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadFileTests {

    @Test
    public void testReadTestMap_01() throws JDOMException, IOException {
        new Game();
        readXML readXML = new readXML();
        readXML.readXMLFile();
        //get the created tilemap from Game and print it
        Tile[][] tilemap = Game.instance.getTilemap();
        for(Tile[] x : tilemap){
            for(Tile y : x){
                System.out.print(y.toChar() + "");
            }
            System.out.println();
        }
        //Check to see if all tile types were created and in correct locations
        assert (tilemap[2][0] instanceof WallTile);
        assert (tilemap[2][1] instanceof FreeTile);
        assert (tilemap[2][2] instanceof KeyTile);
        assert (tilemap[2][6] instanceof LockTile);
        assert (tilemap[2][8] instanceof InfoTile);
        assert (tilemap[2][9] instanceof TreasureTile);
        assert (tilemap[2][11] instanceof ExitLockTile);
        assert (tilemap[2][12] instanceof ExitTile);

        //get the created player from Game and check it
        Player player = Game.instance.getPlayer();
        System.out.println("Player located at x: " + player.getLocation().getX() + ", y: " + player.getLocation().getY());

        assert (player.getLocation().getX() == 4);
        assert (player.getLocation().getY() == 2);
    }
}
