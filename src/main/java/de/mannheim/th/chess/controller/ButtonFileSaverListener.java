package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;

public class ButtonFileSaverListener implements ActionListener {

  private static final Logger logger = LogManager.getLogger(App.class);

  private Game g;
  private JFrame sf;

  public ButtonFileSaverListener(JFrame sf, Game g) {
    this.sf = sf;
    this.g = g;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    logger.info("Spiel wird gespeichert.");

    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Documents"));

    chooser.setDialogTitle("Datei speichern");
    int userSelection = chooser.showSaveDialog(sf);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = chooser.getSelectedFile();

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {

        writer.write(g.getFen());

        logger.info(g.getFen());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else {
      logger.info("Speichern fehlgeschlagen.");
    }
  }

}
