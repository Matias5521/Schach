package de.mannheim.th.chess.domain;

import com.github.bhlangonijr.chesslib.Board;

import de.mannheim.th.chess.utl.Clock;

/**
 * Ist die zentrale Klasse für ein einzelnes Spiel. Ist praktisch die zentrale Steuerung davon.
 */
public class Game{
	
	private Board bord = new Board();
	private Clock clockPlayer1 = new Clock();
	private Clock clockPlayer2 = new Clock();
	
	public Game() {
		
	}
	
}