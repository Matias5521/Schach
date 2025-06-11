package de.mannheim.th.chess.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
