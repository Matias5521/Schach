
package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;

public class ButtonViewLastListener implements ActionListener {
  private Game game;

  public ButtonViewLastListener(Game game) {
    this.game = game;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(this.game.getMoveList().size() - 1);
    // TODO Auto-generated method stub

  }

}
