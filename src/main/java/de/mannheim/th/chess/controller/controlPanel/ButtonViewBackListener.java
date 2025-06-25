package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewBackListener extends BaseButtonViewListener implements ActionListener {

  public ButtonViewBackListener(Game game, SpielFrame sf) {
    super(game, sf);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.game.getViewPointer() > 0) {
      this.game.setViewPointer(this.game.getViewPointer() - 1);
      updateView();
    }

  }

}
