package test.nz.ac.vuw.ecs.swen225.gp21.persistancy;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.persistancy.readXML;
import nz.ac.vuw.ecs.swen225.gp21.persistancy.writeXML;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class WriteFileTests {
    @Test
    public void testWriteCurrentSave_01() throws JDOMException, IOException {
        Game game = new Game();
        readXML readXML = new readXML();
        readXML.readXMLFile();
        writeXML instance = new writeXML();
        instance.writeXMLFile();

        assert(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistancy/currentSave.xml").isFile());
    }

}
