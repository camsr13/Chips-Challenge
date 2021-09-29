package test.nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.persistency.readXML;
import nz.ac.vuw.ecs.swen225.gp21.persistency.writeXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class WriteFileTests {
    @Test
    public void testWriteCurrentSave_01() throws JDOMException, IOException {
        Game game = new Game();
        readXML readXML = new readXML();
        readXML.readXMLFile("src/nz/ac/vuw/ecs/swen225/gp21/persistency/level2.xml");
        writeXML instance = new writeXML();
        instance.writeXMLFile(instance.generateDocument(), "src/nz/ac/vuw/ecs/swen225/gp21/persistency/currentSave.xml");

        assert(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/currentSave.xml").isFile());
    }

}
