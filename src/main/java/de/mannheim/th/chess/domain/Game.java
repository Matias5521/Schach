package de.mannheim.th.chess.domain;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.mannheim.th.chess.utl.Clock;

/**
 * Ist die zentrale Klasse für ein einzelnes Spiel. Ist praktisch die zentrale Steuerung davon.
 */
public class Game{
	
	private Board board;
	private Clock clock;
	private ArrayList<String> moves;
	private ArrayList<Move> movelist = new ArrayList<>();
	
	public Game() {
		board = new Board();
		clock = new Clock("blitz");
		
	}
	
	public void doMove(Square sq1, Square sq2) {
		
	}
	
	public String toFEN() {
		return board.getFen();
	}

	 

	  /**
	   * Plays the move on the board and adds it to the movelist
	   *
	   * @param move the move to be played
	   */
	  public void playMove(Move move) {
	    this.board.doMove(move);
	    this.movelist.add(move);
	  }

	  /**
	   * Plays the move on the board and adds it to the movelist
	   *
	   * @param origin     The square from wich it moves from.
	   * @param desination The square where it will move to.
	   */
	  public void playMove(Square origin, Square desination) {
	    Move move = new Move(origin, desination);
	    this.board.doMove(move);
	    this.movelist.add(move);
	  }

	  /**
	   * Retrieves a list of legal moves originating from the specified square.
	   *
	   * @param square The square from which to retrieve legal moves.
	   *
	   * @return A list of legal moves that originate from the specified square.
	   */
	  public List<Move> getLegalMoves(Square square) {
	    return this.board.legalMoves().stream()
	        .filter(move -> move.getFrom() == square)
	        .collect(Collectors.toList());

	  }
	
	
	
}