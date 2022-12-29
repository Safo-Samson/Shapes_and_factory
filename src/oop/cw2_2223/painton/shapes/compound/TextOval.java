package oop.cw2_2223.painton.shapes.compound;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;
import oop.cw2_2223.painton.shapes.CompoundShape;
import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.simple.FilledOval;
import oop.cw2_2223.painton.shapes.simple.OpenOval;
import oop.cw2_2223.painton.shapes.simple.TextShape;

public class TextOval extends CompoundShape {

  public TextOval(JComponent imagePane) {
    super("Text Oval", new PaintableShape[] { new FilledOval(imagePane), new OpenOval(imagePane), new TextShape(imagePane)});
    ((FilledOval)this.shapes[0]).setPropertyValue(AbstractColoredPaintableShape.transparencyKey, 0.5);
  }

}
