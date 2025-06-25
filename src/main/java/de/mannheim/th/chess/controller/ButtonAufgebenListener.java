package de.mannheim.th.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.ui.SpielFrame.BoardMode;

public class ButtonAufgebenListener extends JFrame implements ActionListener {

	private static final Logger logger = LogManager.getLogger(App.class);

	private static final long serialVersionUID = 1L;
	private SpielFrame sf;
	private Game g;

	public ButtonAufgebenListener(SpielFrame sf, Game g) {
		this.sf = sf;
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Object source = e.getSource();
		
		g.stopClock();

		// weil nur der aktive Spieler button drücken darf
		if (source == sf.getAufgeben()) {
			logger.info("Spieler 1 will aufgeben.");
			sf.getAufgeben2().setEnabled(false);
			sf.showResult("Spieler 2 hat gewonnen!");
		} else if (source == sf.getAufgeben2()) {
			logger.info("Spieler 2 will aufgeben.");
			sf.getAufgeben().setEnabled(false);
			sf.showResult("Spieler 1 hat gewonnen!");
		}
		
		this.sf.setBoardMode(BoardMode.finished);
		this.sf.enableControlPanelButtons();

		sf.setButtonsActions();
		
	}

}
