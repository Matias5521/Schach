package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.mannheim.th.chess.ui.ModeSelectionFrame;

public class ButtonFileLoaderListener implements ActionListener {

  private ModeSelectionFrame msf;

  public ButtonFileLoaderListener(ModeSelectionFrame msf) {
    this.msf = msf;

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

    JFileChooser dateiWaehler = new JFileChooser();
    JFrame jfFile = new JFrame();
    int auswahl = dateiWaehler.showOpenDialog(jfFile);

    if (auswahl == JFileChooser.APPROVE_OPTION) {
      File ausgewaehlteDatei = dateiWaehler.getSelectedFile();
      JOptionPane.showMessageDialog(jfFile, "Gewählte Datei:\n" + ausgewaehlteDatei.getAbsolutePath());

      try {
        BufferedReader br = new BufferedReader(new FileReader(ausgewaehlteDatei));

        msf.setFen(br.readLine());

        br.close();

      } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

  }

}
