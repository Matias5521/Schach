package de.mannheim.th.chess.domain;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;

import java.util.ArrayList;

import de.mannheim.th.chess.utl.Clock;

/**
 * Ist die zentrale Klasse für ein einzelnes Spiel. Ist praktisch die zentrale Steuerung davon.
 */
public class Game{
	
	private Board board;
	private Clock clock;
	private ArrayList<String> moves;
	
	public Game() {
		board = new Board();
		clock = new Clock();
		
	}
	
	public void doMove(Square sq1, Square sq2) {
		
	}
	
	public String toFEN() {
		return board.getFen();
	}
	
	
	
}