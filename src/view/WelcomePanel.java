package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the panel for the welcome page of Doctor Lucky game.
 *
 */
public class WelcomePanel extends JPanel{
  
  private static final long serialVersionUID = -8760005062561086596L;
  private JLabel welcome;
  private JLabel credits;

  /**
   * Constructor of welcome panel.
   */
  public WelcomePanel() {
    this.setLayout(new BorderLayout());
    welcome = new JLabel("Welcome to Kill Doctor Lucky Game!");
    credits = new JLabel("<html><body><p align=\"center\"><strong>Credits:</strong><br>"
        + "ZeSheng Li<br>Zifei Song<br></p><br><br></body></html>");
    //position
    welcome.setHorizontalAlignment(JLabel.CENTER);
    welcome.setHorizontalTextPosition(JLabel.CENTER);
    welcome.setVerticalTextPosition(JLabel.CENTER);  
    credits.setHorizontalAlignment(JLabel.CENTER);
    //text color
    welcome.setForeground(Color.DARK_GRAY);
    //label font
    welcome.setFont(new Font("MV Boli", Font.PLAIN, 40));
    credits.setFont(new Font("MV Boli", Font.PLAIN, 20));
    //background color
    this.setBackground(Color.gray);
    this.add(welcome, BorderLayout.CENTER);
    this.add(credits, BorderLayout.SOUTH);
  }

}
