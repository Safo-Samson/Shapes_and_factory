package oop.cw2_2223.painton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;

import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.ShapeFactory.ShapeFactory;
import oop.cw2_2223.painton.shapes.ShapeFactory.ShapeFactory.*;
import oop.cw2_2223.painton.shapes.simple.*;
import oop.cw2_2223.painton.shapes.compound.*;


import static oop.cw2_2223.painton.shapes.ShapeFactory.ShapeFactory.ShapeType.*;

public class ImagePane extends JComponent {
  private final BufferedImage background;
  private final List<PaintableShape> paintedShapes = new ArrayList<>();
  private String currentShapeName;
  private PaintableShape selectedShape;
  private JDialog configDialog;
  private final Rectangle mouseBox = new Rectangle();

  public ImagePane() {
    this(new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB));
    final Graphics g = this.background.getGraphics();
    g.setColor(Color.CYAN);
    g.fillRect(0, 0, this.background.getWidth(), this.background.getHeight());
  }

  public ImagePane(final BufferedImage background) {
    this.background = background;
    setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
    final IPMouseMotionListener ml = new IPMouseMotionListener();
    addMouseListener(ml);
    addMouseMotionListener(ml);
  }

  public BufferedImage paintToImage() {
    final BufferedImage output = new BufferedImage(this.background.getWidth(), this.background.getHeight(),
        this.background.getType());
    paintComponent(output.getGraphics());
    return output;
  }

  public void setCurrentShape(final String currentShapeName) {
    this.currentShapeName = currentShapeName;
  }

  public void showConfigurationDialog() {
    if (this.selectedShape != null) {
      if (this.configDialog == null) {
        this.configDialog = new JDialog((Window)getTopLevelAncestor());
      }
      this.configDialog.setTitle(this.selectedShape.getName());
      this.configDialog.getContentPane().removeAll();
      this.configDialog.getContentPane().add(this.selectedShape.getUI());
      this.configDialog.pack();
      this.configDialog.setVisible(true);
      repaint();
    }
  }

  public void startNewMouseBox(final int x, final int y) {
    this.mouseBox.x = x;
    this.mouseBox.y = y;
    this.mouseBox.width = 0;
    this.mouseBox.height = 0;
  }

  public void resizeMouseBox(final int x, final int y) {
    this.mouseBox.width = x - this.mouseBox.x;
    this.mouseBox.height = y - this.mouseBox.y;
    repaint();
  }

  public void deleteMouseBox() {
    this.mouseBox.x = 0;
    this.mouseBox.y = 0;
    this.mouseBox.width = 0;
    this.mouseBox.height = 0;
  }

  public void addShape() {
    PaintableShape shape = null;

  shape = ShapeFactory.createShapee(this.currentShapeName,this);


//    String name = this.currentShapeName;
//    shape = ShapeFactory.createShape(ShapeType.valueOf(name), this);
//    if (this.currentShapeName.equals("Open Oval")) {
//      shape = new OpenOval(this);
//    } else if (this.currentShapeName.equals("Filled Oval")) {
//      shape = new FilledOval(this);
//    } else if (this.currentShapeName.equals("Bounded Oval")) {
//      shape = new BoundedOval(this);
//    } else if (this.currentShapeName.equals("Text")) {
//      shape = new TextShape(this);
//    } else if (this.currentShapeName.equals("Text Oval")) {
//      shape = new TextOval(this);
//    } else if (this.currentShapeName.equals("Image")) {
//      shape = new ImageShape(this);
//    }
    if (shape != null) {
      final Rectangle r = makeTopLeft(this.mouseBox);
      shape.setPosition(new Point(r.x, r.y));
      shape.setSize(new Dimension(r.width, r.height));
      this.paintedShapes.add(shape);
      this.selectedShape = shape;
      deleteMouseBox();
      repaint();
      showConfigurationDialog();
    }
  }
  public void deleteSelectedShape() {
    if (this.selectedShape != null) {
      this.paintedShapes.remove(this.selectedShape);
      this.selectedShape = null;
      repaint();
    }
  }
  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final Graphics2D g2 = (Graphics2D) g.create();
    // paint background
    g2.drawImage(this.background, 0, 0, this);

    g2.setClip(0, 0, this.background.getWidth(), this.background.getHeight());
    for (final PaintableShape p : this.paintedShapes) {
      final Graphics2D localGraphics = (Graphics2D) g2.create();
      final Point position = p.getPosition();
      localGraphics.translate(position.getX(), position.getY());
      p.paint(localGraphics);
    }

    if ((this.mouseBox.height != 0) || (this.mouseBox.width != 0)) {
      g2.setXORMode(Color.BLACK);
      g2.setColor(Color.WHITE);
      final Rectangle r = makeTopLeft(this.mouseBox);
      g2.drawRect(r.x, r.y, r.width, r.height);
    }

    if (this.selectedShape != null) {
      g2.setXORMode(Color.BLACK);
      g2.setColor(Color.GREEN);
      final Point position = this.selectedShape.getPosition();
      final Dimension size = this.selectedShape.getSize();
      g2.drawRect(position.x, position.y, size.width, size.height);
    }
  }

  public static Rectangle makeTopLeft(final Rectangle r) {
    int x = r.x;
    int y = r.y;
    int w = r.width;
    int h = r.height;
    if (w < 0) {
      w = -w;
      x -= w;
    }
    if (h < 0) {
      h = -h;
      y -= h;
    }
    return new Rectangle(x, y, w, h);
  }

  private class IPMouseMotionListener implements MouseListener, MouseMotionListener {

    private int dragButton;
    private int dragOffsetX;
    private int dragOffsetY;

    @Override
    public void mouseDragged(final MouseEvent e) {
      if (ImagePane.this.selectedShape == null) {
        resizeMouseBox(e.getX(), e.getY());
      } else {
        if (this.dragButton == MouseEvent.BUTTON1) {
          ImagePane.this.selectedShape.setPosition(new Point(e.getX() - this.dragOffsetX, e.getY() - this.dragOffsetY));
        } else if (this.dragButton == MouseEvent.BUTTON3) {
          Rectangle bounds = ImagePane.this.selectedShape.getBounds();
          bounds.width = e.getX() - bounds.x;
          bounds.height = e.getY() - bounds.y;
          bounds = makeTopLeft(bounds);
          ImagePane.this.selectedShape.setPosition(new Point(bounds.x, bounds.y));
          ImagePane.this.selectedShape.setSize(new Dimension(bounds.width, bounds.height));
        }
        repaint();
      }
    }

    @Override
    public void mouseMoved(final MouseEvent e) {

    }

    @Override
    public void mouseClicked(final MouseEvent e) {
      final List<PaintableShape> possibles = new ArrayList<>();
      final int x = e.getX();
      final int y = e.getY();
      for (final PaintableShape shape : ImagePane.this.paintedShapes) {
        if (shape.getBounds().contains(x, y)) {
          possibles.add(shape);
        }
      }
      if (possibles.size() == 1) {
        ImagePane.this.selectedShape = possibles.get(0);
      } else if (possibles.size() == 0) {
        ImagePane.this.selectedShape = null;
      } else {
        double minDistance = 1e6;
        for (final PaintableShape shape : possibles) {
          final Rectangle bounds = shape.getBounds();
          final double cx = bounds.getCenterX();
          final double cy = bounds.getCenterY();
          final double distance = Point2D.distance(x, y, cx, cy);
          if (distance < minDistance) {
            minDistance = distance;
            ImagePane.this.selectedShape = shape;
          }
        }
      }
      repaint();
    }

    @Override
    public void mousePressed(final MouseEvent e) {
      this.dragButton = e.getButton();
      if (ImagePane.this.selectedShape != null) {
        this.dragOffsetX = e.getX() - ImagePane.this.selectedShape.getPosition().x;
        this.dragOffsetY = e.getY() - ImagePane.this.selectedShape.getPosition().y;
      } else if (e.getButton() == MouseEvent.BUTTON1) {
        this.dragOffsetX = 0;
        this.dragOffsetY = 0;
        startNewMouseBox(e.getX(), e.getY());
      }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
      if (ImagePane.this.selectedShape == null) {
        if ((e.getButton() == MouseEvent.BUTTON1) && (ImagePane.this.currentShapeName != null)) {
          resizeMouseBox(e.getX(), e.getY());
          if ((ImagePane.this.mouseBox.width + ImagePane.this.mouseBox.height) > 0) {
            addShape();
          } else {
            deleteMouseBox();
          }
        }
      }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(final MouseEvent e) {
      // TODO Auto-generated method stub

    }

  }

}
