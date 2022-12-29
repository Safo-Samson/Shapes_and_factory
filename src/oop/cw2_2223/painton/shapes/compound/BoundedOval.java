package oop.cw2_2223.painton.shapes.compound;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.CompoundShape;
import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.simple.FilledOval;
import oop.cw2_2223.painton.shapes.simple.OpenOval;

public class BoundedOval extends CompoundShape {
  public BoundedOval(JComponent imagePane) {
    super("Bounded Oval", new PaintableShape[] { new FilledOval(imagePane), new OpenOval(imagePane)});
  }

}
