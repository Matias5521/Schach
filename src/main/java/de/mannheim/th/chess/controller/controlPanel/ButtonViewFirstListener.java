package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewFirstListener extends BaseButtonViewListener implements ActionListener {

  public ButtonViewFirstListener(Game game, SpielFrame sf) {
    super(game, sf);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(0);
    updateView();
  }

}
