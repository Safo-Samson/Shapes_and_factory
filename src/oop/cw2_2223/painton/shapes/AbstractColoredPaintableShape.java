package oop.cw2_2223.painton.shapes;

import java.awt.Color;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.configui.PropertyKey;
import oop.cw2_2223.painton.shapes.configui.PropertyKey.PropertyType;

public abstract class AbstractColoredPaintableShape extends AbstractPaintableShape {

  public static PropertyKey colorKey = new PropertyKey("Color",PropertyType.COLOR);
  public static PropertyKey transparencyKey = new PropertyKey("Transparency",PropertyType.PROPORTION);
  
  public AbstractColoredPaintableShape(final String name, final JComponent imagePane) {
    super(name, imagePane);
    setPropertyValue(transparencyKey, 0.0);
    setPropertyValue(colorKey, Color.WHITE);
  }

  public Color getColor() {
    return (Color) getPropertyValue(colorKey);
  }
  
  public Color getColorWithTransparency() {
    final double alpha = 1 - (double) getPropertyValue(transparencyKey);
    final Color color = getColor();
    final Color tColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255 * alpha));
    return tColor;
  }
  
}
