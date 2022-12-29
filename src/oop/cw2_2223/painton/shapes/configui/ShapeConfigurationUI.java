package oop.cw2_2223.painton.shapes.configui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ShapeConfigurationUI extends JPanel {

  private final JPanel mainPanel;
  public ShapeConfigurationUI() {
    super(new BorderLayout());
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
    add(this.mainPanel);
  }
  public void addConfigurationPanel(final String name, final JPanel panel) {
    panel.setBorder(BorderFactory.createTitledBorder(name));
    this.mainPanel.add(panel);
  }

}
