package de.mannheim.th.chess.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

import de.mannheim.th.chess.utl.Clock;

/**
 * Ist die zentrale Klasse für ein einzelnes Spiel. Ist praktisch die zentrale
 * Steuerung davon.
 */
public class Game {

  private Board board;
  private Clock clock;

  private MoveList movelist;

  /**
   * Conststructs a new standard GameBoard.
   */
  public Game() {
    this.board = new Board();

    this.movelist = new MoveList();

    clock = new Clock("blitz");
    clock.start();

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
    return this.board.legalMoves().stream()
        .filter(move -> move.getFrom() == square)
        .collect(Collectors.toList());

  }
  
  public void stopClock() {
	  clock.endGame();
  }
  
  public boolean isPromotionMove(Move move) {
	  return ((move.getTo().getRank().equals(Rank.RANK_8) || move.getTo().getRank().equals(Rank.RANK_1)) &&
			    (board.getPiece(move.getFrom()) == Piece.BLACK_PAWN || board.getPiece(move.getFrom()) == Piece.WHITE_PAWN));  
	  }

  /**
   * Retrieves a list of all legal moveable squares from the current board state.
   * 
   * @return a List of Square objects representing all legal moveable squares.
   */
  public List<Square> getAllLegalMoveableSquares() {
    return this.board.legalMoves().stream()
        .map(move -> move.getFrom())
        .distinct()
        .collect(Collectors.toList());
  }

  /**
   * Retrieves a list of legal moveable squares for a given square.
   * 
   * @param square the Square from which to retrieve legal moveable squares
   * @return a List of Square objects representing the legal moveable squares
   *         from the specified square.
   */
  public List<Square> getLegalMoveableSquares(Square square) {
    return this.board.legalMoves().stream()
        .filter(move -> move.getFrom() == square)
        .map(move -> move.getTo())
        .collect(Collectors.toList());
  }
  
  public void doPromotionMove(int piece, Square origin, Square destination) {
	  System.out.println(piece);
	  Piece promotedTo;
	  switch(piece) {
	  case 7:
		  promotedTo = Piece.BLACK_KNIGHT;
		  break;
	  case 4:
		  promotedTo = Piece.BLACK_QUEEN;
		  break;
	  case 5:
		  promotedTo = Piece.BLACK_ROOK;
		  break;
	  case 6:
		  promotedTo = Piece.BLACK_BISHOP;
		  break;
	  case 3:
		  promotedTo = Piece.WHITE_KNIGHT;
		  break;
	  case 0:
		  promotedTo = Piece.WHITE_QUEEN;
		  break;
	  case 1:
		  promotedTo = Piece.WHITE_ROOK;
		  break;
	  case 2:
		  promotedTo = Piece.WHITE_BISHOP;
		  break;
		  default:
			  promotedTo = Piece.WHITE_QUEEN;
	  }
	  Move promotionMove = new Move(origin, destination, promotedTo);
	  board.doMove(promotionMove);
	  movelist.add(promotionMove);
  }

  public String toFEN() {
    board.toString();
    return board.getFen();
  }
}
