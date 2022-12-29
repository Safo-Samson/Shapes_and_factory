package oop.cw2_2223.painton.shapes.configui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

import oop.cw2_2223.painton.shapes.AbstractPaintableShape;

public class PropertyInputPanel extends JPanel {
  private final AbstractPaintableShape shape;
  private final JFileChooser chooser = new JFileChooser();
    
  public PropertyInputPanel(final AbstractPaintableShape shape) {
    super(new NaturalGridLayout(2));
    this.shape = shape;

    for(final PropertyKey key : shape.getPropertyKeys()) {     
      switch(key.getType()) {
      case BOOLEAN:
        add(new JLabel(key.getName() + ": ", SwingConstants.RIGHT));
        final JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((Boolean)shape.getPropertyValue(key));
        checkBox.addActionListener((ev)->doBoolean(key,ev));
        add(checkBox);
        break;
      case COLOR:
        add(new JLabel(key.getName() + ": ", SwingConstants.RIGHT));
        final JButton button = new JButton();
        button.setOpaque(true);
        button.setBackground((Color)shape.getPropertyValue(key));
        button.addActionListener((ev)->doColor(key,ev));
        add(button);
        break;
      case FILE:
        add(new JLabel(key.getName() + ": ", SwingConstants.RIGHT));
        final JButton browse = new JButton();
        browse.setText(((File)shape.getPropertyValue(key)).getName());
        if (browse.getText().equals(".")) { browse.setText("Choose file path..."); }
        browse.addActionListener((ev)->doFile(key,ev));
        add(browse);
        break;
      case STRING:
        add(new JLabel(key.getName() + ": ", SwingConstants.RIGHT));
        final JTextField textField = new JTextField(16);
        textField.setText((String)shape.getPropertyValue(key));
        textField.addActionListener((ev)->doString(key,ev));
        add(textField);
        break;
      case PROPORTION:
        final JPanel panel = new NaturalGridLayout.MultiSpanJPanel(2);
        panel.setBorder(BorderFactory.createTitledBorder(key.getName()));
        final JSlider slider = new JSlider(0, 1000);
        slider.setValue((int) (((Double)shape.getPropertyValue(key))*1000));
        slider.addChangeListener((ev)->doProportion(key,ev));
        panel.add(slider);
        add(panel);
        break;
      default:
        break;      
      }      
    }
  }

  public void doString(final PropertyKey key, final ActionEvent e) {
    final String value = ((JTextField)e.getSource()).getText();
    this.shape.setPropertyValue(key, value);
    this.shape.repaint();
  }

  public void doBoolean(final PropertyKey key, final ActionEvent e) {
    final Boolean value = ((JCheckBox)e.getSource()).isSelected();
    this.shape.setPropertyValue(key, value);
    this.shape.repaint();
  }
  
  public void doProportion(final PropertyKey key, final ChangeEvent e) {
    final Double value = ((JSlider)e.getSource()).getValue() / 1000.0;
    this.shape.setPropertyValue(key, value);
    this.shape.repaint();
  }

  public void doColor(final PropertyKey key, final ActionEvent e) {
    final Color color = (Color) this.shape.getPropertyValue(key);
    final Color newColor = JColorChooser.showDialog(this, "Choose color", color);
    if (newColor != null) {
      this.shape.setPropertyValue(key, newColor);
      ((JButton)e.getSource()).setBackground(newColor);
      this.shape.repaint();
    }
  }

  public void doFile(final PropertyKey key, final ActionEvent e) {
    final File file = (File) this.shape.getPropertyValue(key);
    this.chooser.setCurrentDirectory(file);
    int choice = this.chooser.showDialog(this, "Choose file");
    if (choice == JFileChooser.APPROVE_OPTION) {
      final File newFile = this.chooser.getSelectedFile();
      this.shape.setPropertyValue(key, newFile);
      ((JButton)e.getSource()).setText(newFile.getName());
      this.shape.repaint();
    }
  }
}
