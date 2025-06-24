
package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewLastListener implements ActionListener {
  private Game game;
  private SpielFrame sf;

  public ButtonViewLastListener(Game game, SpielFrame sf) {
    this.game = game;
    this.sf = sf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(this.game.getMoveList().size());
    this.game.loadView();
    this.sf.setDefaultButtons();
    this.sf.applyBoardButtons();
    this.sf.ladeBrett();
  }

}
