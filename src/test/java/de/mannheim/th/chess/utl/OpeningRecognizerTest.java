package de.mannheim.th.chess.utl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;



class OpeningRecognizerTest {

	@BeforeAll
	static void prepareOpenings() throws IOException {
		OpeningRecognizer.loadOpenings();
	}
	
	@Test
	void test() {
		Move m = new Move(Square.E2, Square.E4);
		MoveList moves = new MoveList();
		moves.add(m);
		assertEquals(OpeningRecognizer.compareOpening(moves, "Unknown"), "King's pawn Opening");
		
	}

}
