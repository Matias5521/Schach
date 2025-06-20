package de.mannheim.th.chess.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;
import de.mannheim.th.chess.controller.ButtonMovePieceListener;
import de.mannheim.th.chess.controller.ButtonSelectPieceListener;
import de.mannheim.th.chess.controller.ButtonToNormalListener;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Color;
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
  private Game game;

  private BoardMode mode;
  private Square selectedSquare;

  public enum BoardMode {
    normal, pieceSelected, finished
  }

  /**
   * Create the frame.
   */
  public SpielFrame() {

    game = new Game();
    mode = BoardMode.normal;

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    panelRechts.setBackground(Color.LIGHT_GRAY);

    // JSplitPane horizontal (linke und rechte Hälfte)
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLinks, panelRechts);
    splitPane.setResizeWeight(0.70);
    splitPane.setDividerSize(5);
    splitPane.setEnabled(false);

    contentPane.add(splitPane, BorderLayout.CENTER);
    setVisible(true);
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

    // // Bild laden und Cursor im gesamten Frame setzen
    // Image image = Toolkit.getDefaultToolkit().getImage(pfad);
    // Image scaled = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    // Cursor figurCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaled,
    // new Point(0, 0),
    // "figurCursor");
    // setCursor(figurCursor);

    // }else
    // {
    //
    // // wenn gerade Figur ausgewählt wird...
    // buttonChoosed = (JButton) e.getSource();
    // symbolChoosed = belegungen.get(buttonChoosed);
    // // System.out.println(symbolChoosed+" wurde gewählt.");
    // // setzt cursor auf spielfigur für die animation
    // String pfad = "src/main/resources/" + (int) symbolChoosed.toCharArray()[2] +
    // ".png";
    //
    // // Bild laden und Cursor im gesamten Frame setzen
    // Image image = Toolkit.getDefaultToolkit().getImage(pfad);
    // Image scaled = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    // Cursor figurCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaled,
    // new Point(0, 0),
    // "figurCursor");
    // setCursor(figurCursor);
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
        logger.info("Helles Feld erstellt." + i);
        b.setBackground(new Color(90, 90, 90));
      } else {
        logger.info("Dunkles Feld erstellt." + i);
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
        s.addActionListener(new ButtonToNormalListener(this)); // cancel action

        selectables = game.getLegalMoveableSquares(selectedSquare);

        for (

        Square square : selectables) {
          JButton b = buttons.get(mirrowedGrid(square.ordinal()));
          final Move move = new Move(selectedSquare, square);
          b.setEnabled(true);
          b.setBackground(new Color(230, 100, 100));
          b.addActionListener(new ButtonMovePieceListener(this, this.game, move));
        }

<<<<<<< HEAD
			for (Square square : selectables) {
				JButton b = buttons.get(mirrowedGrid(square.ordinal()));
				final Move move = new Move(selectedSquare, square);
				b.setEnabled(true);
				b.setBackground(new Color(230, 100, 100));
				for (ActionListener al : b.getActionListeners()) {
				    b.removeActionListener(al);
				}
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(game.isPromotionMove(move)) {
							game.doPromotionMove(showPromotion(), selectedSquare, square);
							
						} else {
							game.playMove(move);
						}
						if (game.isDraw()) {
							game.stopClock();
							mode = BoardMode.finished;
							showDraw();
						} else if (game.isMate()) {
							game.stopClock();
							mode = BoardMode.finished;
							showWin(game.getActivePlayer());
						}
						mode = BoardMode.normal;
						setCursor(null);
						erstelleBrett();
					}
				});
			}
=======
        break;
>>>>>>> branch 'buttonActions' of https://gitty.informatik.hs-mannheim.de/3020772/Schach.git

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

<<<<<<< HEAD
        JLabel jl = new JLabel(String.format("%d - %d", player / 2, player % 2));
        jl.setBounds(50, 30, 200, 25);
        jl.setFont(new Font("Tahoma", Font.BOLD, 20));
        frame.add(jl);
        frame.setVisible(true);
	}
	
	private int showPromotion() {
	    final int[] result = {-1};

	    
	    JDialog dialog = new JDialog(this, "Wähle eine Figur", true);
	    dialog.setLayout(new GridLayout(2, 2));
	    dialog.setSize(300, 200);

	    int[] pictures = {81, 82, 66, 78, 113, 114, 98, 110};
	    

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
	
=======
    JLabel jl = new JLabel("1/2 - 1/2");
    jl.setBounds(50, 30, 200, 25);
    jl.setFont(new Font("Tahoma", Font.BOLD, 20));
    frame.add(jl);
    frame.setVisible(true);

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
>>>>>>> branch 'buttonActions' of https://gitty.informatik.hs-mannheim.de/3020772/Schach.git

}
