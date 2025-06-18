package de.mannheim.th.chess.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class GameTest {

  @Test
  void getLegalMovesTest() {
    Game game = new Game();
    List<Move> list;

    list = game.getLegalMoves(Square.B1);
    assertEquals("b1a3", list.getFirst().toString());

    list = game.getLegalMoves(Square.A2);
    assertEquals("a2a3", list.getFirst().toString());
    assertEquals("a2a4", list.getLast().toString());

  }

  @Test
  void getLegalMoveableSquaresTest() {
    Game game = new Game();
    Square square = Square.A2;

    List<Square> controllList = Arrays.asList(Square.A3, Square.A4);
    assertEquals(controllList, game.getLegalMoveableSquares(square));

    game = new Game("k7/8/8/8/8/8/8/K6N w - - 0 1");
    square = Square.H1;
    controllList = Arrays.asList(Square.F2, Square.G3);
    assertEquals(controllList, game.getLegalMoveableSquares(square));
  }

  @Test
  void getAllLegalMoveableSquaresTest() {
    Game game = new Game();

    List<Square> controllList = Arrays.asList(Square.A2, Square.B2, Square.C2, Square.D2, Square.E2, Square.F2,
        Square.G2, Square.H2, Square.B1, Square.G1);
    assertEquals(controllList, game.getAllLegalMoveableSquares());

    game = new Game("k7/8/8/8/8/8/8/K6N w - - 0 1");
    controllList = Arrays.asList(Square.H1, Square.A1);
    assertEquals(controllList, game.getAllLegalMoveableSquares());
  }
}
