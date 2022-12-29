package oop.cw2_2223.painton.shapes.compound;

import oop.cw2_2223.painton.shapes.CompoundShape;
import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.simple.FilledOval;
import oop.cw2_2223.painton.shapes.simple.FilledRectangle;
import oop.cw2_2223.painton.shapes.simple.OpenOval;
import oop.cw2_2223.painton.shapes.simple.OpenRectangle;

import javax.swing.*;

public class BoundedRectangle extends CompoundShape {
    public BoundedRectangle(JComponent imagePane) {
        super("Bounded Rectangle",  new PaintableShape[] { new FilledRectangle(imagePane), new OpenRectangle(imagePane)});
    }
}
