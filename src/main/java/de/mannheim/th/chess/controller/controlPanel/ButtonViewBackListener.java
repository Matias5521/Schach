package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewBackListener implements ActionListener {
  private Game game;
  private SpielFrame sf;

  public ButtonViewBackListener(Game game, SpielFrame sf) {
    this.game = game;
    this.sf = sf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.game.getViewPointer() > 0) {
      this.game.setViewPointer(this.game.getViewPointer() - 1);
      this.game.loadView();
      this.sf.setDefaultButtons();
      this.sf.applyBoardButtons();
      this.sf.ladeBrett();
    }

  }

}
