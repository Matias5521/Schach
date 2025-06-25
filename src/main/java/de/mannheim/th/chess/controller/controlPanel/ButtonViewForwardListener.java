package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewForwardListener extends BaseButtonViewListener implements ActionListener {

  public ButtonViewForwardListener(Game game, SpielFrame sf) {
    super(game, sf);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.game.getMoveList().size() > this.game.getViewPointer()) {
      this.game.setViewPointer(this.game.getViewPointer() + 1);
      updateView();
    }

  }

}
