package test.nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ReadXML;
import nz.ac.vuw.ecs.swen225.gp21.persistency.WriteXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class WriteFileTests {
    @Test
    public void testWriteCurrentSave_01() throws JDOMException, IOException {
        //needed for the test, else game is null and read/write xml does not work
        Game game = new Game();
        ReadXML readXML = new ReadXML();
        readXML.readXMLFile("levels/level2.xml");
        WriteXML instance = new WriteXML();
        instance.writeXMLFile(instance.generateDocument(), "levels/currentSave.xml");

        assert(new File("levels/currentSave.xml").isFile());
    }

}
