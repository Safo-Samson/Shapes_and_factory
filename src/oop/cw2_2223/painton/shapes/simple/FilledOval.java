package oop.cw2_2223.painton.shapes.simple;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;

public class FilledOval extends AbstractColoredPaintableShape {
  
  public FilledOval(final JComponent imagePane) {
    super("Filled Oval", imagePane);
  }
  
  @Override
  public void paint(final Graphics2D g) {
    g.setColor(getColorWithTransparency());
    final Dimension size = getSize();
    g.fillOval(0, 0, size.width, size.height);
  }

}
