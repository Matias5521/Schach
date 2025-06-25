package de.mannheim.th.chess.controller.controlPanel;

import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.domain.Game;

public abstract class BaseButtonViewListener {
  protected SpielFrame sf;
  protected Game game;

  public BaseButtonViewListener(Game game, SpielFrame sf) {
    this.sf = sf;
    this.game = game;
  }

  /**
   * Loads the gamestate and renders the board
   */
  protected void updateView() {
    this.game.loadView();
    this.sf.setDefaultButtons();
    this.sf.applyBoardButtons();
    this.sf.ladeBrett();
  }
}
