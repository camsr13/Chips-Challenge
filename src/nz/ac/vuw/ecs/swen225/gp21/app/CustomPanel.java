package nz.ac.vuw.ecs.swen225.gp21.app;
import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    private final int width;
    private final int height;

    public CustomPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setOpaque(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
