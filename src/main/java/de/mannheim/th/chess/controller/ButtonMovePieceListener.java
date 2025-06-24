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
    if (game.isPromotionMove(this.mv))
      game.doPromotionMove(this.sf.showPromotion(), mv.getFrom(), mv.getTo());
    else
      this.game.playMove(this.mv);

    if (this.game.isDraw()) {
      this.game.stopClock();
      this.sf.showResult("Unentschieden!");
      this.sf.setBoardMode(BoardMode.gameEnd);
      
    } else if (this.game.isMate()) {
      this.game.stopClock();
      this.sf.showResult("Spieler "+game.getActivePlayer()+" hat gewonnen!");
      
      
      this.sf.setBoardMode(BoardMode.gameEnd);
      
    }
    this.sf.setBoardMode(BoardMode.normal);
    this.sf.setCursor(null);
    this.sf.erstelleBrett();
    
    if (game.getLastMove() != null) {
        
        sf.aktualisiereAusgabe();
    }
  }
}
