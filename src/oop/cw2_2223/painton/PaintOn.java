package oop.cw2_2223.painton;

import oop.cw2_2223.painton.shapes.ShapeFactory.ShapeFactory;
import oop.cw2_2223.painton.shapes.ShapeFactory.ShapeFactory.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PaintOn extends JFrame {

  private ImagePane imagePane;
  private final JMenu shapeMenu = new JMenu("Shape");
  private final ButtonGroup shapeButtonGroup = new ButtonGroup();
  private final JFileChooser chooser = new JFileChooser();

  public PaintOn() {
    setTitle("PaintOn");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.imagePane = new ImagePane();
    this.chooser.setCurrentDirectory(new File("."));

    final JMenuBar menuBar = new JMenuBar();
    final JMenu fileMenu = new JMenu("File");
    final JMenu selectedShapeMenu = new JMenu("Selected shape");

    final JMenuItem openItem = new JMenuItem("Open");
    openItem.addActionListener(ev -> doOpenImage());
    fileMenu.add(openItem);

    final JMenuItem saveItem = new JMenuItem("Save");
    saveItem.addActionListener(ev -> doSaveImage());
    fileMenu.add(saveItem);

    final JMenuItem configureItem = new JMenuItem("Configure");
    configureItem.addActionListener(ev -> configureShape());
    selectedShapeMenu.add(configureItem);

    final JMenuItem deleteShapeItem = new JMenuItem("Delete");
    deleteShapeItem.addActionListener(ev -> deleteShape());
    selectedShapeMenu.add(deleteShapeItem);

    menuBar.add(fileMenu);
    menuBar.add(this.shapeMenu);
    menuBar.add(selectedShapeMenu);
    setJMenuBar(menuBar);

// //todo
//    addMenuOperation("Open Oval");
//    addMenuOperation("Filled Oval");
//    addMenuOperation("Bounded Oval");
//    addMenuOperation("Text");
//    addMenuOperation("Text Oval");
//    addMenuOperation("Image");

/**
   *replacing the above with the factory enum, ShapeType.
   * I have imported ShapeType from ShapeFactory so that I can use it
   * directly
 * */
//
//    for (ShapeType shape: ShapeType.values()) {
//      addMenuOperation(shape.name());
//    }

    for (String name : ShapeFactory.getMenu())
           addMenuOperation(name);

    add(this.imagePane);
    pack();
    setVisible(true);
  }

  private void addMenuOperation(final String identifier) {
    final JRadioButtonMenuItem item = new JRadioButtonMenuItem(identifier);
    this.shapeButtonGroup.add(item);
    item.addActionListener(ev -> chooseShape(identifier));
    this.shapeMenu.add(item);
  }

  private void chooseShape(final String identifier) {
    this.imagePane.setCurrentShape(identifier);
  }

  private File chooseImageFile() {
    if (this.chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return this.chooser.getSelectedFile();
    } else {
      return null;
    }
  }
  private void doOpenImage() {
    final File file = chooseImageFile();
    if (file != null) {
      try {
        setImage(ImageIO.read(file));
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void doSaveImage() {
    File file = chooseImageFile();
    if (file != null) {
      try {
        String format = "png";
        final String filename = file.getName();
        final int p = filename.lastIndexOf(".");
        if (p >= 0) {
          final String extension = filename.substring(p + 1).toLowerCase();
          // if the filename the user has chosen corresponds to a known image format,
          // use that format, otherwise default to png.
          if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(extension)) {
            format = extension;
          } else {
            file = new File(file.getParent(), filename + ".png");
          }
        }
        final BufferedImage image = this.imagePane.paintToImage();
        ImageIO.write(image, format, file);
        JOptionPane.showMessageDialog(this, "File saved as '" + file.getName() + "'");
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void setImage(final BufferedImage image) {
    remove(this.imagePane);
    this.imagePane = new ImagePane(image);
    add(this.imagePane);
    pack();
  }

  private void configureShape() {
    this.imagePane.showConfigurationDialog();
  }

  private void deleteShape() {
    this.imagePane.deleteSelectedShape();
  }

  private static void launch() {
    new PaintOn();
  }

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> launch());
  }

}
