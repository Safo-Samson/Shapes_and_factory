package oop.cw2_2223.painton.shapes.simple;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;
import oop.cw2_2223.painton.shapes.configui.PropertyKey;
import oop.cw2_2223.painton.shapes.configui.PropertyKey.PropertyType;

public class TextShape extends AbstractColoredPaintableShape {

  public static PropertyKey textKey = new PropertyKey("text",PropertyType.STRING);
  public static PropertyKey fontKey = new PropertyKey("font",PropertyType.STRING);

  
  public TextShape(final JComponent imagePane) {
    super("Text", imagePane);
    setPropertyValue(textKey,"Hello World");
    setPropertyValue(fontKey,"SANS-SERIF-PLAIN-32");
  }

  @Override
  public void paint(final Graphics2D g) {

    g.setColor(getColorWithTransparency());

    final Font font = Font.decode((String) getPropertyValue(fontKey));
    g.setFont(font);
    final Dimension size = getSize();
    final String text = (String) getPropertyValue(textKey);
    final Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
    g.drawString(text,(int)((size.width - bounds.getWidth()) / 2), (int)(((size.height - bounds.getHeight()) / 2) - bounds.getY()));

  }

}
