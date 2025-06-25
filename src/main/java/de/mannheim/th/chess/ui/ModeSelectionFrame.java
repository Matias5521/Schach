package de.mannheim.th.chess.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.controller.ButtonFileLoaderListener;
import de.mannheim.th.chess.domain.Game;

public class ModeSelectionFrame extends JFrame {

  private static final Logger logger = LogManager.getLogger(App.class);

  private static final long serialVersionUID = 1L;
  private final JPanel contentPane;
  private final ArrayList<Game> spiele = new ArrayList<>();
  private String fen;

  public ModeSelectionFrame(MainFrame mf) {
    // Frame-Eigenschaften
    setTitle("Modusauswahl");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 500, 500);
    setResizable(true);
    setAlwaysOnTop(true);

    // Panel konfigurieren
    contentPane = new JPanel();
    contentPane.setBackground(new Color(90, 90, 90));
    contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    setContentPane(contentPane);

    // Überschrift
    JLabel jl = new JLabel("Welchen Modus wollen Sie spielen?");
    jl.setFont(new Font("Calibri", Font.BOLD, 20));
    jl.setForeground(Color.BLACK);
    jl.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(jl);
    contentPane.add(Box.createVerticalStrut(15));

    // Modusauswahl
    String[] modi = { "Blitz", "Schnellschach", "Klassisch" };
    JComboBox<String> jcb1 = new JComboBox<>(modi);
    jcb1.setMaximumSize(new Dimension(150, 30));
    jcb1.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(jcb1);
    contentPane.add(Box.createVerticalStrut(15));

    // Spielbrettdrehen
    JLabel jl2 = new JLabel("Soll das Spielbrett nach jedem Zug gedreht werden?");
    jl2.setFont(new Font("Calibri", Font.BOLD, 20));
    jl2.setForeground(Color.BLACK);
    jl2.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(jl2);

    JCheckBox jb1 = new JCheckBox();
    jb1.setOpaque(false);
    jb1.setFocusPainted(false);
    jb1.setForeground(Color.BLACK);
    jb1.setAlignmentX(Component.CENTER_ALIGNMENT);
    jb1.setMaximumSize(new Dimension(30, 30));
    contentPane.add(jb1);
    contentPane.add(Box.createVerticalStrut(15));

    // Zurücknahmeoption
    JLabel jl3 = new JLabel("Sollen Zurücknahmen erlaubt sein?");
    jl3.setFont(new Font("Calibri", Font.BOLD, 20));
    jl3.setForeground(Color.BLACK);
    jl3.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(jl3);

    JCheckBox jb2 = new JCheckBox();
    jb2.setOpaque(false);
    jb2.setFocusPainted(false);
    jb2.setForeground(Color.BLACK);
    jb2.setAlignmentX(Component.CENTER_ALIGNMENT);
    jb2.setMaximumSize(new Dimension(30, 30));
    contentPane.add(jb2);

    contentPane.add(Box.createVerticalStrut(15));

    JButton btnNewButton_1 = new JButton("Vergangenes Spiel laden");

    btnNewButton_1.setBackground(Color.LIGHT_GRAY);
    btnNewButton_1.setForeground(Color.BLACK);
    btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnNewButton_1.addActionListener(new ButtonFileLoaderListener(this));

    contentPane.add(btnNewButton_1);

    contentPane.add(Box.createVerticalStrut(25));

    // Spiel starten Button
    JButton btnNewButton = new JButton("Spiel starten");
    btnNewButton.setBackground(Color.LIGHT_GRAY);
    btnNewButton.setForeground(Color.BLACK);
    btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(btnNewButton);

    // Button-Listener
    btnNewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String modus = (String) jcb1.getSelectedItem();
        boolean rotieren = jb1.isSelected();
        boolean zuruecknahme = jb2.isSelected();

        Game game = new Game(modus, rotieren, zuruecknahme, fen);
        mf.setGame(game);
        mf.startGame();

        // spiele.add(game);

        dispose();
      }
    });

    setVisible(true);
  }

  public void setFen(String fen) {
    this.fen = fen;
  }
}
