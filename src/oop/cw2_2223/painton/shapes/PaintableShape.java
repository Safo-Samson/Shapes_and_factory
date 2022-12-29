package oop.cw2_2223.painton.shapes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import oop.cw2_2223.painton.shapes.configui.ShapeConfigurationUI;

public interface PaintableShape {
  String getName();
  void setPosition(Point point);
  Point getPosition();  
  void setSize(Dimension dimension);
  Dimension getSize();
  Rectangle getBounds();
  void paint(Graphics2D g);
  ShapeConfigurationUI getUI();  
}
