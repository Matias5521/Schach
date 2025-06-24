package de.mannheim.th.chess.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.utl.Clock;
import de.mannheim.th.chess.controller.ButtonAufgebenListener;
import de.mannheim.th.chess.controller.ButtonFileSaverListener;
import de.mannheim.th.chess.controller.ButtonMovePieceListener;
import de.mannheim.th.chess.controller.ButtonSelectPieceListener;
import de.mannheim.th.chess.controller.ButtonToNormalListener;
import de.mannheim.th.chess.controller.ButtonUndoMoveListener;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.GridLayout;

public class SpielFrame extends JFrame {

	private static final Logger logger = LogManager.getLogger(App.class);

	private static final long serialVersionUID = 1L;
	private ArrayList<JButton> buttons = new ArrayList<>();
	private HashMap<JButton, String> belegungen = new HashMap<>();
	private JPanel panelLinks, panelRechts, contentPane;
	private JButton undo, undo2;
	private JTextArea ausgabe;
	private Game game;
	private Clock clock;
	private ArrayList<String> anzeigeMoves = new ArrayList<String>();

	private BoardMode mode;
	private Square selectedSquare;

	public enum BoardMode {
		normal, pieceSelected, finished
	}

	/**
	 * Create the frame.
	 */
	public SpielFrame(Game game) {

		this.game = game;
		this.clock = game.getClock();
		this.clock.start();

		mode = BoardMode.normal;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		setTitle("Schach");
		setAlwaysOnTop(true);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// Linkes Panel mit GridLayout 8x8 für Schachbrett
		panelLinks = new JPanel(new GridLayout(8, 8));

		erstelleBrett();

		// Rechtes Panel für Steuerung oder zusätzliche Eingaben
		panelRechts = new JPanel();
		panelRechts.setBackground(new Color(90, 90, 90));
		panelRechts.setLayout(new BoxLayout(panelRechts, BoxLayout.Y_AXIS));

		// Panel für alle Eingaben von Player 2
		panelRechts.add(getUiPlayerTwo());

		// Panel für Statistikanzeigen
		panelRechts.add(getUiStatistik());

		// Panel für alle Eingaben von Player 1
		panelRechts.add(getUiPlayerOne());

		// JSplitPane horizontal (linke und rechte Hälfte)
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLinks, panelRechts);
		splitPane.setResizeWeight(0.75);
		splitPane.setBackground(Color.BLACK);
		splitPane.setDividerSize(1);
		splitPane.setEnabled(false);

		contentPane.add(splitPane, BorderLayout.CENTER);

		setVisible(true);
	}

	/**
	 * Erstellt alle Buttons und fügt sie dem Frame hinzu.
	 */
	public void erstelleBrett() {

		this.clearButtons();
		this.setDefaultBackground();
		this.setButtonsActions();

		ladeBrett();

		panelLinks.revalidate();
		panelLinks.repaint();

	}

	private int mirrowedGrid(int i) {
		return 63 - (((i / 8) * 8) + (7 - i % 8));
	}

	/**
	 * holt sich FEN-Zeichenkette und extrahiert daraus die Positionen der Figuren
	 */
	private void ladeBrett() {
		// System.out.println(game.toFEN());

		char[] fen = game.toFEN().replaceAll("/", "").split(" ")[0].toCharArray();
		int i = 0;
		for (int j = 0; j < fen.length; j++) {
			if (Character.isDigit(fen[j])) {
				int leerfelder = Character.getNumericValue(fen[j]);
				for (int k = 0; k < leerfelder; k++) {
					belegungen.put(buttons.get(i), "n-n");
					// buttons.get(i).setEnabled(false); // erstmal deaktivieren, weil leere Felder
					// nicht ckickbar sein sollten.
					i++;
				}
				continue;
			} else if (fen[j] >= 65 && fen[j] <= 90) { // ein Großbuchstabe, also
				belegungen.put(buttons.get(i), "w-" + fen[j]);
			} else if (fen[j] >= 97 && fen[j] <= 122) { // ein Kleinbuchstabe, also
				belegungen.put(buttons.get(i), "b-" + fen[j]);
				// buttons.get(i).setEnabled(false); // erstmal deaktivieren, damit weiß
				// beginnen kann
			}
			buttons.get(i).setIcon(new ImageIcon("src/main/resources/" + (int) fen[j] + ".png"));
			buttons.get(i).setDisabledIcon(new ImageIcon("src/main/resources/" + (int) fen[j] + ".png"));

			i++;

		}
	}

	/**
	 * Clears the existing buttons from the button list, panellinks and fills them
	 * with new blank ones.
	 */
	private void clearButtons() {

		buttons.clear();
		panelLinks.removeAll();

		for (int i = 0; i < 64; i++) {
			JButton b = new JButton();

			b.setEnabled(false);

			// style
			b.setFocusPainted(false);
			b.setFont(new Font("Arial", Font.PLAIN, 30));
			b.setForeground(Color.WHITE);
			b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			b.setName(i + "");

			buttons.add(b);
		}
	}

	/**
	 * Sets the default background color for the buttons in the grid.
	 */
	private void setDefaultBackground() {
		for (int i = 0; i < 64; i++) {
			JButton b = buttons.get(i);
			if ((i / 8 + i % 8) % 2 == 0) {
				// logger.info("Helles Feld erstellt." + i);
				b.setBackground(new Color(90, 90, 90));
			} else {
				// logger.info("Dunkles Feld erstellt." + i);
				b.setBackground(new Color(65, 65, 65));
			}
		}
	}

	/*
	 * Switches the button actions depending on the boardmode
	 */
	private void setButtonsActions() {

		List<Square> selectables;

		switch (this.mode) {
		case BoardMode.normal:

			selectables = game.getAllLegalMoveableSquares();

			for (Square square : selectables) {
				JButton b = buttons.get(mirrowedGrid(square.ordinal()));
				b.setEnabled(true);
				// b.setBackground(Color.green);
				b.addActionListener(new ButtonSelectPieceListener(this, square));
			}

			break;

		case BoardMode.pieceSelected:

			JButton s = buttons.get(mirrowedGrid(selectedSquare.ordinal()));
			s.setEnabled(true);
			s.setBackground(new Color(165, 42, 42));
			s.addActionListener(new ButtonToNormalListener(this));

			selectables = game.getLegalMoveableSquares(selectedSquare);

			for (Square square : selectables) {
				JButton b = buttons.get(mirrowedGrid(square.ordinal()));
				final Move move = new Move(selectedSquare, square);
				b.setEnabled(true);
				b.setBackground(new Color(230, 100, 100));
				b.addActionListener(new ButtonMovePieceListener(this, this.game, move));
			}
			break;
		case finished:
			clearButtons();
			break;
		default:
			break;

		}

		for (JButton b : buttons) {
			panelLinks.add(b);
		}
	}

	public void showDraw() {
		JFrame frame = new JFrame("Result");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setLayout(null);

		// JLabel jl = new JLabel(String.format("%d - %d", player / 2, player % 2));
		// jl.setBounds(50, 30, 200, 25);
		// jl.setFont(new Font("Tahoma", Font.BOLD, 20));
		// frame.add(jl);
		// frame.setVisible(true);
	}

	public void showWin(int player) {
		JFrame frame = new JFrame("Result");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setLayout(null);

		JLabel jl = new JLabel(String.format("%d - %d", player / 2, player % 2));
		jl.setBounds(50, 30, 200, 25);
		jl.setFont(new Font("Tahoma", Font.BOLD, 20));
		frame.add(jl);
		frame.setVisible(true);
	}

	public int showPromotion() {
		final int[] result = { -1 };

		JDialog dialog = new JDialog(this, "Wähle eine Figur", true);
		dialog.setLayout(new GridLayout(2, 2));
		dialog.setSize(300, 200);

		int[] pictures = { 81, 82, 66, 78, 113, 114, 98, 110 };

		for (int i = 0; i < 4; i++) {
			int index = (game.getActivePlayer() - 1) * 4 + i;
			JButton jb = new JButton();
			jb.setIcon(new ImageIcon("src/main/resources/" + pictures[index] + ".png"));
			int selectedPiece = index;
			jb.addActionListener(e -> {
				System.out.println("Test");
				result[0] = selectedPiece;
				dialog.dispose();
			});
			dialog.add(jb);
		}

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		return result[0];
	}

	private JPanel getUiPlayerTwo() {

		JPanel playerTwo = new JPanel();
		playerTwo.setBackground(new Color(90, 90, 90));
		playerTwo.setLayout(new BoxLayout(playerTwo, BoxLayout.Y_AXIS));

		playerTwo.add(Box.createVerticalStrut(15));

		JLabel pl2 = new JLabel("Player 2:");
		pl2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		pl2.setFont(new Font("Calibri", Font.BOLD, 35));
		pl2.setForeground(Color.BLACK);
		pl2.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerTwo.add(pl2);

		playerTwo.add(Box.createVerticalStrut(10));

		JLabel clock1 = clock.getClock2();
		playerTwo.add(clock1);

		playerTwo.add(Box.createVerticalStrut(10));

		// Button zurücknahme und aufgeben für Player 2
		JPanel aufgebenUndo = new JPanel();
		aufgebenUndo.setBackground(new Color(90, 90, 90));
		aufgebenUndo.setLayout(new BoxLayout(aufgebenUndo, BoxLayout.X_AXIS));

		if (game.isZuruecknahme()) {
			undo = new JButton("Zug zurücknehmen");
			undo.setBackground(Color.LIGHT_GRAY);
			undo.setForeground(Color.BLACK);
			undo.setFont(new Font("Tahoma", Font.BOLD, 16));
			undo.setAlignmentX(Component.CENTER_ALIGNMENT);
			aufgebenUndo.add(undo);

			// Button-Listener
			undo.addActionListener(new ButtonUndoMoveListener(this, this.game));
		}

		aufgebenUndo.add(Box.createHorizontalStrut(10));

		JButton aufgeben = new JButton("Aufgeben");
		aufgeben.setBackground(Color.LIGHT_GRAY);
		aufgeben.setForeground(Color.BLACK);
		aufgeben.setFont(new Font("Tahoma", Font.BOLD, 16));
		aufgeben.setAlignmentX(Component.CENTER_ALIGNMENT);
		aufgebenUndo.add(aufgeben);

		// Button-Listener
		aufgeben.addActionListener(new ButtonAufgebenListener());

		aufgebenUndo.add(Box.createHorizontalStrut(10));

		JButton safe = new JButton("Spielstand sichern");
		safe.setBackground(Color.LIGHT_GRAY);
		safe.setForeground(Color.BLACK);
		safe.setFont(new Font("Tahoma", Font.BOLD, 16));
		safe.setAlignmentX(Component.CENTER_ALIGNMENT);
		aufgebenUndo.add(safe);

		// Button-Listener
		safe.addActionListener(new ButtonFileSaverListener(this, this.game));

		playerTwo.add(aufgebenUndo);

		playerTwo.add(Box.createVerticalStrut(10));

		return playerTwo;
	}

	private JPanel getUiStatistik() {

		JPanel statistik = new JPanel();
		statistik.setBackground(new Color(90, 90, 90));
		statistik.setLayout(new BoxLayout(statistik, BoxLayout.Y_AXIS));

		ausgabe = new JTextArea();
		ausgabe.setEditable(false);
		ausgabe.setBackground(new Color(75, 75, 75));
		ausgabe.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		ausgabe.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		ausgabe.setForeground(Color.BLACK);
		ausgabe.setText("\n   Bisherige Züge:\n");

		JScrollPane scrollPane = new JScrollPane(ausgabe);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		statistik.add(scrollPane);

		return statistik;
	}

	public void aktualisiereAusgabe() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n    Bisherige Züge:\n");

		MoveList l = game.getMoveList();
		anzeigeMoves.add("     " + game.getUnicodeFromMove(l.getLast()) + ": " + l.getLast().toString() + "\n");

		for (String line : anzeigeMoves) {
			sb.append(line);
		}

		ausgabe.setText(sb.toString());
	}

	public void deleteLastAusgabe() {
		String[] zeilen = ausgabe.getText().split("\n");

		// es müssen immer mind 5 Zeilen existieren, dass also 1 Zug löschbar ist
		if (zeilen.length <= 2)
			return;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < zeilen.length - 1; i++) {
			sb.append(zeilen[i]).append("\n");
		}

		ausgabe.setText(sb.toString());
	}

	private JPanel getUiPlayerOne() {

		JPanel playerOne = new JPanel();
		playerOne.setBackground(new Color(90, 90, 90));
		playerOne.setLayout(new BoxLayout(playerOne, BoxLayout.Y_AXIS));

		playerOne.add(Box.createVerticalStrut(10));

		// Button zurücknahme und aufgeben für Player 1
		JPanel aufgebenUndo = new JPanel();
		aufgebenUndo.setBackground(new Color(90, 90, 90));
		aufgebenUndo.setLayout(new BoxLayout(aufgebenUndo, BoxLayout.X_AXIS));

		if (game.isZuruecknahme()) {
			undo2 = new JButton("Zug zurücknehmen");
			undo2.setBackground(Color.LIGHT_GRAY);
			undo2.setForeground(Color.BLACK);
			undo2.setFont(new Font("Tahoma", Font.BOLD, 16));
			undo2.setAlignmentX(Component.CENTER_ALIGNMENT);
			aufgebenUndo.add(undo2);

			// Button-Listener
			undo2.addActionListener(new ButtonUndoMoveListener(this, this.game));

		}

		aufgebenUndo.add(Box.createHorizontalStrut(10));

		JButton aufgeben = new JButton("Aufgeben");
		aufgeben.setBackground(Color.LIGHT_GRAY);
		aufgeben.setForeground(Color.BLACK);
		aufgeben.setFont(new Font("Tahoma", Font.BOLD, 16));
		aufgeben.setAlignmentX(Component.CENTER_ALIGNMENT);
		aufgebenUndo.add(aufgeben);

		// Button-Listener
		aufgeben.addActionListener(new ButtonAufgebenListener());

		aufgebenUndo.add(Box.createHorizontalStrut(10));

		JButton safe = new JButton("Spielstand sichern");
		safe.setBackground(Color.LIGHT_GRAY);
		safe.setForeground(Color.BLACK);
		safe.setFont(new Font("Tahoma", Font.BOLD, 16));
		safe.setAlignmentX(Component.CENTER_ALIGNMENT);
		aufgebenUndo.add(safe);

		// Button-Listener
		safe.addActionListener(new ButtonFileSaverListener(this, this.game));

		playerOne.add(aufgebenUndo);

		playerOne.add(Box.createVerticalStrut(15));

		JLabel clock1 = clock.getClock1();
		playerOne.add(clock1);

		playerOne.add(Box.createVerticalStrut(10));

		JLabel pl2 = new JLabel("Player 1:");
		pl2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		pl2.setFont(new Font("Calibri", Font.BOLD, 35));
		pl2.setForeground(Color.BLACK);
		pl2.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerOne.add(pl2);

		return playerOne;
	}

	public void setBoardMode(BoardMode bm) {
		this.mode = bm;
	}

	public void setSelectedSquare(Square sq) {
		this.selectedSquare = sq;
	}

	public HashMap<JButton, String> getBelegung() {
		return this.belegungen;
	}

	public JButton getUndo() {
		return undo;
	}

	public JButton getUndo2() {
		return undo2;
	}

	public BoardMode getMode() {
		return mode;
	}

	public Clock getClock() {
		return clock;
	}

}
