
package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;

public class ButtonQuickloadListener implements ActionListener {
  private Game game;
  private SpielFrame sf;

  public ButtonQuickloadListener(Game game, SpielFrame sf) {
    this.game = game;
    this.sf = sf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.game.quickload();
    this.sf.erstelleBrett();
    this.sf.aktualisiereAusgabe();
  }
}
