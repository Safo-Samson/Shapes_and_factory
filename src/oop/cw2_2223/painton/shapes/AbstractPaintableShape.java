package oop.cw2_2223.painton.shapes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.configui.PropertyInputPanel;
import oop.cw2_2223.painton.shapes.configui.PropertyKey;
import oop.cw2_2223.painton.shapes.configui.ShapeConfigurationUI;

public abstract class AbstractPaintableShape implements PaintableShape {

  private final JComponent imagePane;
  private final String name;
  private Point position;
  private Dimension size;
    
  private final Map<PropertyKey,Object> properties = new TreeMap<>();
  
  public AbstractPaintableShape(final String name, final JComponent imagePane) {
    this.name = name;
    this.imagePane = imagePane;
  }
   
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public Point getPosition() {
    return this.position;
  }

  @Override
  public void setPosition(final Point position) {
    this.position = position;
  }

  @Override
  public Dimension getSize() {
    return this.size;
  }

  @Override
  public void setSize(final Dimension size) {
    this.size = size;
  }
  
  @Override
  public Rectangle getBounds() {
    return new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);
  }
  
  public Set<PropertyKey> getPropertyKeys() {
    return this.properties.keySet();
  }
  
  public Object getPropertyValue(final PropertyKey key) {
    return this.properties.get(key);
  }
  
  public void setPropertyValue(final PropertyKey key, final Object value) {
    this.properties.put( key, value);
  }
 
  public void repaint() {
    this.imagePane.repaint();
  }
  
  @Override
  public ShapeConfigurationUI getUI() {
    final ShapeConfigurationUI ui = new ShapeConfigurationUI();
    if (getPropertyKeys().size() > 0) {
      final PropertyInputPanel propertiesPanel = new PropertyInputPanel(this);
      ui.addConfigurationPanel("Properties", propertiesPanel);
    }    
    return ui;
  }
}
