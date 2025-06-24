package de.mannheim.th.chess.controller.controlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;

public class ButtonViewForwardListener implements ActionListener {
  private Game game;

  public ButtonViewForwardListener(Game game) {
    this.game = game;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.setViewPointer(this.game.getViewPointer() + 1);
    // TODO Auto-generated method stub

  }

}
