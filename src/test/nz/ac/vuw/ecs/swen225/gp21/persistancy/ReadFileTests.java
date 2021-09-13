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
        assert (tilemap[2][0] instanceof WallTile);
        assert (tilemap[2][1] instanceof FreeTile);
        assert (tilemap[2][2] instanceof KeyTile);
        assert (tilemap[2][6] instanceof LockTile);

        //get the created player from Game and check it
        Player player = Game.instance.getPlayer();
        assert (player.getLocation().getX() == 4);
        assert (player.getLocation().getY() == 2);
    }
}
