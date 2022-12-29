package oop.cw2_2223.painton.shapes.compound;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;
import oop.cw2_2223.painton.shapes.CompoundShape;
import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.simple.*;

import javax.swing.*;

public class TextRectangle extends CompoundShape {
    public TextRectangle(JComponent imagePane) {
        super("Text Oval", new PaintableShape[] { new FilledRectangle(imagePane), new OpenRectangle(imagePane), new TextShape(imagePane)});
        ((FilledRectangle)this.shapes[0]).setPropertyValue(AbstractColoredPaintableShape.transparencyKey, 0.5);
    }
}
