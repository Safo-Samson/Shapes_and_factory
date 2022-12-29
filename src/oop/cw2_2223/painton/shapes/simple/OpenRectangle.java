package oop.cw2_2223.painton.shapes.simple;

import oop.cw2_2223.painton.shapes.AbstractLineBasedPaintableShape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class OpenRectangle extends AbstractLineBasedPaintableShape {


    public OpenRectangle(JComponent imagePane){
        super("Open Rectangle", imagePane);
    }
    @Override
    public void paint(Graphics2D g) {
        g.setColor(getColorWithTransparency());
        final Dimension size = getSize();
        float thickness = 1;
        final String tv = (String) getPropertyValue(lineThicknessKey);
        if (tv != null) {
            try {
                thickness = Float.parseFloat(tv);
            } catch(final NumberFormatException x) {
            }
        }
        final BasicStroke stroke = new BasicStroke(thickness);
        g.setStroke(stroke);
        g.draw(new Rectangle2D.Double(0, 0, size.width, size.height));
    }

}
