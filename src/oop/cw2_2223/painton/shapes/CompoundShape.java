package oop.cw2_2223.painton.shapes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import oop.cw2_2223.painton.shapes.configui.ShapeConfigurationUI;

public class CompoundShape implements PaintableShape {
  private final String name;
  protected final PaintableShape[] shapes;
  
  public CompoundShape(final String name, final PaintableShape[] shapes) {
    this.name = name;
    this.shapes = shapes;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setPosition(final Point point) {
    for(final PaintableShape shape : this.shapes) {
      shape.setPosition(point);
    }
  }

  @Override
  public Point getPosition() {
    return this.shapes[0].getPosition();
  }

  @Override
  public void setSize(final Dimension dimension) {
    for(final PaintableShape shape : this.shapes) {
      shape.setSize(dimension);
    }
  }

  @Override
  public Dimension getSize() {
    return this.shapes[0].getSize();
  }

  @Override
  public Rectangle getBounds() {
    return this.shapes[0].getBounds();
  }

  @Override
  public void paint(final Graphics2D g) {
    for(final PaintableShape shape : this.shapes) {
      shape.paint(g);
    }
  }
  @Override
  public ShapeConfigurationUI getUI() {
    final ShapeConfigurationUI ui = new ShapeConfigurationUI();
    for(final PaintableShape shape : this.shapes) {
      ui.addConfigurationPanel(shape.getName(), shape.getUI());
    }
    return ui;
  }

}
