package de.mannheim.th.chess;

import java.io.IOException;

import de.mannheim.th.chess.ui.MainFrame;
import de.mannheim.th.chess.utl.OpeningRecognizer;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

/**
 * Eine einfache Schach App mithilfe von
 * {@linkplain https://github.com/bhlangonijr/chesslib} entwickelt.
 * 
 * @author Matias Mas Viehl, Dominik Stuck und Marius Guendel
 * @version 0.0.1
 */
public class App {

  // private static final Logger logger = LogManager.getLogger(App.class);

  /**
   * Main-Methode.
   * 
   * @param args
 * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    new MainFrame();
    OpeningRecognizer.loadOpenings();
  }
}
