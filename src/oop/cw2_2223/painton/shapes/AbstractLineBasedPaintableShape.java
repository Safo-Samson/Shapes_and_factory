package oop.cw2_2223.painton.shapes;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.configui.PropertyKey;
import oop.cw2_2223.painton.shapes.configui.PropertyKey.PropertyType;

public abstract class AbstractLineBasedPaintableShape extends AbstractColoredPaintableShape {
  public static PropertyKey lineThicknessKey = new PropertyKey("Line thickness",PropertyType.STRING);
  public AbstractLineBasedPaintableShape(final String name, final JComponent imagePane) {
    super(name, imagePane);
    setPropertyValue(lineThicknessKey, "10");
  }

}
