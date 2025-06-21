package de.mannheim.th.chess.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.ui.SpielFrame;
import de.mannheim.th.chess.utl.Clock;

/**
 * Ist die zentrale Klasse für ein einzelnes Spiel. Ist praktisch die zentrale
 * Steuerung davon.
 */
public class Game {
	
	private static final Logger logger = LogManager.getLogger(App.class);

	private Board board;
	private Clock clock;
	private SpielFrame sp;
	private String modus;
	private boolean rotieren, zuruecknahme;

	private MoveList movelist;

	/**
	 * Conststructs a new standard GameBoard.
	 */
	public Game(String modus, boolean rotieren, boolean zuruecknahme, String fen) {
		this.modus = modus;
		this.rotieren = rotieren;
		this.zuruecknahme = zuruecknahme;
		
		this.board = new Board();
		
		if(fen == null) fen = board.getFen();
		
		this.board.loadFromFen(fen);

		this.movelist = new MoveList();
		
		clock = new Clock(modus);
		
		sp = new SpielFrame(this);

	}

	/**
	 * Constructs a new standard GameBoard and applies the provides moves.
	 *
	 * @param movelist The list of moves that get played.
	 */
	public Game(MoveList movelist) {
		this.board = new Board();

		this.movelist = movelist;

		for (Move move : movelist) {
			this.board.doMove(move);
		}

		// this.clockPlayer1 = new Clock();
		// this.clockPlayer2 = new Clock();
	}

	/**
	 * Constructs a new GameBoard with the provided fen String as the positions.
	 *
	 * @param fen The fen String that provides the customs formation.
	 */
	public Game(String fen) {
		this.board = new Board();
		this.board.loadFromFen(fen);

		this.movelist = new MoveList();
		//this.sp = new SpielFrame();

		// this.clockPlayer1 = new Clock();
		// this.clockPlayer2 = new Clock();
	}

	/**
	 * Plays the move on the board and adds it to the movelist
	 *
	 * @param move the move to be played
	 */
	public void playMove(Move move) {
		this.board.doMove(move);
		this.movelist.add(move);
		clock.pressClock();
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
	
	public void undo() {
		this.board.undoMove();
		this.movelist.removeLast();
	}

	public boolean isMate() {
		return board.isMated();
	}

	public boolean isDraw() {
		return board.isDraw();
	}

	public int getActivePlayer() {
		if (board.getSideToMove() == Side.WHITE) {
			return 1;
		}
		return 2;
	}

	/**
	 * Retrieves a list of legal moves originating from the specified square.
	 *
	 * @param square The square from which to retrieve legal moves.
	 *
	 * @return A list of legal moves that originate from the specified square.
	 */
	public List<Move> getLegalMoves(Square square) {
		return this.board.legalMoves().stream().filter(move -> move.getFrom() == square).collect(Collectors.toList());

	}

	public void stopClock() {
		clock.endGame();
	}

	/**
	 * Retrieves a list of all legal moveable squares from the current board state.
	 * 
	 * @return a List of Square objects representing all legal moveable squares.
	 */
	public List<Square> getAllLegalMoveableSquares() {
		return this.board.legalMoves().stream().map(move -> move.getFrom()).distinct().collect(Collectors.toList());
	}

	/**
	 * Retrieves a list of legal moveable squares for a given square.
	 * 
	 * @param square the Square from which to retrieve legal moveable squares
	 * @return a List of Square objects representing the legal moveable squares from
	 *         the specified square.
	 */
	public List<Square> getLegalMoveableSquares(Square square) {
		return this.board.legalMoves().stream().filter(move -> move.getFrom() == square).map(move -> move.getTo())
				.collect(Collectors.toList());
	}

	public String toFEN() {
		board.toString();
		return board.getFen();
	}

	public void setModus(String modus) {
		this.modus = modus;
	}

	public Clock getClock() {
		return this.clock;
	}

	public boolean isZuruecknahme() {
		return zuruecknahme;
	}
	
	public boolean movesNotNull() {
		if(movelist.getLast() != null) {
			return true;
		}
		return false;
	}
	
	public String getFen() {
		return this.board.getFen();
	}
	
	public Move getLastMove() {
		logger.info(this.movelist.getLast().toString());
		return this.movelist.getLast();
	}
}
