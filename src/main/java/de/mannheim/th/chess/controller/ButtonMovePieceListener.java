package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.bhlangonijr.chesslib.move.Move;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.ui.SpielFrame.BoardMode;

public class ButtonMovePieceListener implements ActionListener {
  private SpielFrame sf;
  private Game game;
  private Move mv;

  public ButtonMovePieceListener(SpielFrame sf, Game game, Move mv) {
    this.sf = sf;
    this.game = game;
    this.mv = mv;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.playMove(this.mv);
    if (this.game.isDraw()) {
      this.game.stopClock();
      this.sf.setBoardMode(BoardMode.finished);
      this.sf.showDraw();
    } else if (this.game.isMate()) {
      this.game.stopClock();
      this.sf.setBoardMode(BoardMode.finished);
      this.sf.showWin(game.getActivePlayer());
    }
    this.sf.setBoardMode(BoardMode.normal);
    this.sf.setCursor(null);
    this.sf.erstelleBrett();
  }
}
