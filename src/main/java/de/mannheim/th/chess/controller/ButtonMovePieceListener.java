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

    this.game.setViewPointer(this.game.getMoveList().size() - 1);

    if (this.game.isDraw()) {
      this.game.stopClock();
      this.sf.setBoardMode(BoardMode.finished);
      this.sf.enableControlPanelButtons();
      this.sf.showResult("Unentschieden!");
    } else if (this.game.isMate()) {
      this.game.stopClock();
      this.sf.setBoardMode(BoardMode.finished);
      this.sf.enableControlPanelButtons();
      this.sf.showResult("Spieler "+game.getActivePlayer()+" hat gewonnen!");
    } else {
      this.sf.setBoardMode(BoardMode.normal);
    }

    this.sf.setCursor(null);
    
    //hier rotieren markieren
    
    if(game.isRotieren())sf.setWechsel(!sf.isWechsel());
    
    this.sf.erstelleBrett();

    if (game.getLastMove() != null) {

      sf.aktualisiereAusgabe();
    }
  }
}
