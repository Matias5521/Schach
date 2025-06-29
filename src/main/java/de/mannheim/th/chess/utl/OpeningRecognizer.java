package de.mannheim.th.chess.utl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;

import com.github.bhlangonijr.chesslib.move.MoveList;

public class OpeningRecognizer {
	private static class Opening {
		String name;
		String moves;
		
		Opening(String name, String  moves) {
			this.name = name;
			this.moves = moves;
		}
	}
	
	private static List<Opening> openingList = new ArrayList<>();
	
	public static void loadOpenings() throws IOException {
		BufferedReader openingReader = new BufferedReader(new FileReader("src/main/resources/openings.pgn"));
		StringBuilder openingName = new StringBuilder();
		String moves = null;
		String line;
		while ((line = openingReader.readLine()) != null) {
			if ((line.startsWith("[Site") && openingName.toString().equals("")) || line.equals("") ) {
				continue;
			}
			if (line.startsWith("[Site")) {
				openingList.add(new Opening(openingName.toString(), moves));
				moves = null;
				openingName.delete(0, openingName.length());
				continue;
			}
			if (line.startsWith("[White")) {
				openingName.append(line.split("\"")[1].trim());
				continue;
			}
			if (line.startsWith("[Black")) {
				openingName.append(":").append(line.split("\"")[1].trim());
				continue;
			}
			moves = line;
			
		}
		openingReader.close();
	}
	
	public static String compareOpening(MoveList moves, String openingBefore) {
		for (Opening o: openingList) {
			if (o.moves.equals(moves.toSanWithMoveNumbers().trim())) {
				return o.name;
					
			}
		}
		return openingBefore;
		
	}

}
