package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.ui.SpielFrame.BoardMode;

public class ButtonToNormalListener implements ActionListener {
  private SpielFrame sf;

  public ButtonToNormalListener(SpielFrame sf) {
    this.sf = sf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.sf.setBoardMode(BoardMode.normal);
    this.sf.setSelectedSquare(null);
    this.sf.setCursor(null);
    this.sf.erstelleBrett();
	
  }

}
