package de.mannheim.th.chess.controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.bhlangonijr.chesslib.Square;

import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonSelectPieceListener implements ActionListener {
  private SpielFrame sf;
  private Square selectedSquare;

  public ButtonSelectPieceListener(SpielFrame sf, Square sq) {
    this.sf = sf;
    this.selectedSquare = sq;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    sf.setBoardMode(SpielFrame.BoardMode.pieceSelected);
    sf.setSelectedSquare(this.selectedSquare);

    String symbolChoosed = sf.getBelegung().get(e.getSource());

    // setzt cursor auf spielfigur für die animation
    String pfad = "src/main/resources/" + (int) symbolChoosed.toCharArray()[2] + ".png";

    // Bild laden und Cursor im gesamten Frame setzen
    Image image = Toolkit.getDefaultToolkit().getImage(pfad);
    Image scaled = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    Cursor figurCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaled, new Point(0, 0),
        "figurCursor");
    sf.setCursor(figurCursor);

    sf.erstelleBrett();
  }

}
