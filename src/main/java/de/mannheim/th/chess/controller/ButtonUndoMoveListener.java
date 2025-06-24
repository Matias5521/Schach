package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.ui.SpielFrame.BoardMode;

public class ButtonUndoMoveListener implements ActionListener {

	private static final Logger logger = LogManager.getLogger(App.class);

	private SpielFrame sf;
	private Game game;

	public ButtonUndoMoveListener(SpielFrame sf, Game game) {
		this.sf = sf;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object source = e.getSource();

		if (sf.getMode() != BoardMode.normal || !game.movesNotNull()) {
			return;
		}

		if (source == sf.getUndo()) { // Spieler 2 drückt seinen Button
			if (sf.getUndo().getText().equals("Zug zurücknehmen") && game.getActivePlayer() == 1) {
				sf.getUndo2().setText("Zurücknahme genehmigen?");
				sf.getUndo2().setEnabled(true);
				logger.info("Spieler 2 hat zurücknahme angefordert.");
			} else if (sf.getUndo().getText().equals("Zurücknahme genehmigen?")) {
				logger.info("Zug zurücknehmen wurde von Spieler 2 genehmigt.");
				sf.getUndo().setText("Zug zurücknehmen");
				sf.getUndo2().setText("Zug zurücknehmen");
				sf.getUndo2().setEnabled(false);
				game.undo();
				sf.getClock().switchClock();
				sf.deleteLastAusgabe();
				sf.erstelleBrett();
			}
		} else if (source == sf.getUndo2()) { // Spieler 1 drückt seinen Button
			if (sf.getUndo2().getText().equals("Zug zurücknehmen") && game.getActivePlayer() == 2) {
				sf.getUndo().setText("Zurücknahme genehmigen?");
				sf.getUndo().setEnabled(true);
				logger.info("Spieler 1 hat zurücknahme angefordert.");
			} else if (sf.getUndo2().getText().equals("Zurücknahme genehmigen?")) {
				logger.info("Zug zurücknehmen wurde von Spieler 1 genehmigt.");
				sf.getUndo2().setText("Zug zurücknehmen");
				sf.getUndo().setText("Zug zurücknehmen");
				sf.getUndo().setEnabled(false);
				game.undo();
				sf.getClock().switchClock();
				sf.deleteLastAusgabe();
				sf.erstelleBrett();
			}
		}

	}

}
