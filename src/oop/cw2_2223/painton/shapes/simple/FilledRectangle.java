package oop.cw2_2223.painton.shapes.simple;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;

import javax.swing.*;
import java.awt.*;

public class FilledRectangle extends AbstractColoredPaintableShape {

    public FilledRectangle(JComponent imagePane) {
        super("Filled Rectangle", imagePane);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(getColorWithTransparency());
        final Dimension size = getSize();
        g.fillRect(0, 0, size.width, size.height);
    }
}
