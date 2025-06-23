package de.mannheim.th.chess.controller;

import java.awt.Color;
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
    
    if (game.getLastMove() != null) {
    	char[] z = game.getLastMove().toString().toCharArray();
    	String moveString = "";
    	if(game.getActivePlayer() == 1) {
    		moveString = "        " + String.valueOf(z[0]) + String.valueOf(z[1]) + " -> " + String.valueOf(z[2]) + String.valueOf(z[3]);
    	}else if(game.getActivePlayer() == 2){
    		moveString = String.valueOf(z[0]) + String.valueOf(z[1]) + " -> " + String.valueOf(z[2]) + String.valueOf(z[3]+"        ");
    	}
        
        
        sf.appendText(moveString);
    }
  }
}
