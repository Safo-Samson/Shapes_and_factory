package oop.cw2_2223.painton.shapes.simple;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import oop.cw2_2223.painton.shapes.AbstractColoredPaintableShape;
import oop.cw2_2223.painton.shapes.AbstractPaintableShape;
import oop.cw2_2223.painton.shapes.configui.PropertyKey;

public class ImageShape extends AbstractPaintableShape {

  public static PropertyKey imageKey = new PropertyKey("Image",PropertyKey.PropertyType.FILE);
  
  private File imageFilePath = new File(".");
  private BufferedImage image;
  
  public ImageShape(JComponent imagePane) {
    super("Image", imagePane);
    setPropertyValue(imageKey,this.imageFilePath);
    setPropertyValue(AbstractColoredPaintableShape.transparencyKey, 0.0);
  }

  @Override
  public void paint(Graphics2D g) {
    File currentImageFilePath = (File) getPropertyValue(imageKey);
    if (this.image == null || !this.imageFilePath.equals(currentImageFilePath)) {
      File imageFile = currentImageFilePath;
      try {
        this.image = ImageIO.read(imageFile);
        this.imageFilePath = currentImageFilePath;
      } catch (IOException e) {
        System.err.println(e + " '" + imageFile + "'");
      }
    }
    if (this.image != null) {
      Dimension size = getSize();
      final double alpha = 1 - (double) getPropertyValue(AbstractColoredPaintableShape.transparencyKey); 
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha);
      g.setComposite(ac);
      g.drawImage(this.image, 0, 0, (int)size.getWidth(), (int)size.getHeight(), null);
    }
  }

}
