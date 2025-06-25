
package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonViewLastListener extends BaseButtonViewListener implements ActionListener {

  public ButtonViewLastListener(Game game, SpielFrame sf) {
    super(game, sf);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(this.game.getMoveList().size());
    updateView();
  }

}
