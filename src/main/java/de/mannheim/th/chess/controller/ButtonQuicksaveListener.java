package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;

public class ButtonQuicksaveListener implements ActionListener {
  private Game game;

  public ButtonQuicksaveListener(Game game) {
    this.game = game;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.quicksave();
  }
}
