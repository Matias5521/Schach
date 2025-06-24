package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewFirstListener implements ActionListener {
  private Game game;
  private SpielFrame sf;

  public ButtonViewFirstListener(Game game, SpielFrame sf) {
    this.game = game;
    this.sf = sf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(0);
    this.game.loadView();
    this.sf.setDefaultButtons();
    this.sf.applyBoardButtons();
    this.sf.ladeBrett();
  }

}
