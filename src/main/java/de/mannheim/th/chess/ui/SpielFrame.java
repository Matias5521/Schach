package de.mannheim.th.chess.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Red;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.File;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Square;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielFrame extends JFrame {

  private static final Logger logger = LogManager.getLogger(App.class);

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private ArrayList<JButton> buttons = new ArrayList<>();
  private List<Integer> clickableButtons = new ArrayList<>();
  private HashMap<JButton, Integer> positions = new HashMap<>();
  private HashMap<JButton, String> belegungen = new HashMap<>();
  private HashMap<JButton, Color> farben = new HashMap<>();
  private JPanel panelLinks, panelRechts;
  private Game game;
  private String symbolChoosed;
  private JButton buttonChoosed;
  private boolean playerWhite = true;
  private boolean moveFinished = false;

  private BoardMode mode;
  private Square selectedSquare;

  enum BoardMode {
    normal,
    pieceSelected,
  }

  /**
   * Launch the application. Die Main-Methode für den WindowBuilder.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          SpielFrame frame = new SpielFrame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
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

    JPanel contentPane = new JPanel();
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

  /**
   * Erstellt alle Buttons und fügt sie dem Frame hinzu.
   */
  private void erstelleBrett() {

    clearButtons();
    setDefaultBackground();

    List<Square> selectables;

    switch (this.mode) {
      case BoardMode.normal:
        selectables = game.getAllLegalMoveableSquares();

        System.out.println(selectables);

        for (Square square : selectables) {
          final Square currentSquare = square; // ActionListener need it to be final
          JButton b = buttons.get(63 - square.ordinal());
          b.setEnabled(true);
          b.setBackground(Color.green);
          b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              System.out.println("switch to selected");
              mode = BoardMode.pieceSelected;
              selectedSquare = currentSquare;
              erstelleBrett();
            }
          });
        }

        break;

      case BoardMode.pieceSelected:
        JButton s = buttons.get(63 - selectedSquare.ordinal());
        s.setEnabled(true);
        s.setBackground(Color.orange);
        s.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            mode = BoardMode.normal;
            selectedSquare = null;
            erstelleBrett();
          }
        }); // cancel action

        selectables = game.getLegalMoveableSquares(selectedSquare);

        for (Square square : selectables) {
          JButton b = buttons.get(63 - square.ordinal());
          b.setEnabled(true);
          b.setBackground(Color.RED);
          b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              System.out.println("switch to normal");
              mode = BoardMode.normal;
              erstelleBrett();
            }
          });
        }

        break;
      default:
        break;

    }

    for (JButton b : this.buttons) {
      panelLinks.add(b);
    }

    // int i = 0;i<64;i++)
    // {
    //
    // // wenn gerade Figur ausgewählt wird...
    // buttonChoosed = (JButton) e.getSource();
    // symbolChoosed = belegungen.get(buttonChoosed);
    //
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
    //
    // // filtert möglichen Züge heraus
    // int position = positions.get(buttonChoosed);
    //
    // clickableButtons = game
    // .getLegalMoves(
    // Square.encode(Rank.allRanks[7 - position / 8], File.allFiles[position % 8]))
    // .stream().peek(System.out::println).map(m ->
    // m.getTo()).peek(System.out::println)
    // .map(s -> 56 - s.getRank().ordinal() * 8 + s.getFile().ordinal())
    // .collect(Collectors.toList());
    //
    // // filtert mögliche Züge und nicht mögliche Züge in eine Map aus Listen
    // Map<Boolean, List<JButton>> buttonsSeperated = buttons.stream()
    // .collect(Collectors.partitioningBy(b ->
    // clickableButtons.contains(buttons.indexOf(b))));
    //
    // for (Boolean list : buttonsSeperated.keySet()) {
    //
    // if (list) {
    // // alle möglichen felder rot markieren
    // for (JButton b : positions.keySet()) {
    // // wenn button ein möglicher zug ist
    // if (clickableButtons.contains(positions.get(b))) {
    // farben.put(b, b.getBackground()); // damit sich gemerkt werden kann welches
    // feld welche farbe vorher
    // // hatte
    // b.setBackground(new Color(230, 100, 100));
    //
    // }
    // }
    //
    // } else {
    // // den rest der buttons ausser die möglichen züge deaktivieren
    // List<JButton> andere = buttonsSeperated.get(list);
    //
    // for (JButton b : andere) {
    // if (!belegungen.get(b).split("-")[0].equals("w")) {
    // b.setEnabled(false);
    // }
    // }
    // }
    // }
    //
    // // alle weisen squares deaktivieren, damit dannach klar ist wer dran ist
    // for (JButton b : belegungen.keySet()) {
    // if (belegungen.get(b).split("-")[0].equals("b")) {
    // b.setEnabled(false);
    // }
    // }
    //
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
    //
    // // filtert möglichen Züge heraus
    // int position = positions.get(buttonChoosed);
    // List<Integer> clickableButtons = new ArrayList<>();
    // clickableButtons = game
    // .getLegalMoves(
    // Square.encode(Rank.allRanks[7 - position / 8], File.allFiles[position % 8]))
    // .stream().peek(System.out::println).map(m ->
    // m.getTo()).peek(System.out::println)
    // .map(s -> 56 - s.getRank().ordinal() * 8 + s.getFile().ordinal())
    // .collect(Collectors.toList());
    //
    // for (JButton b : positions.keySet()) {
    // // wenn button ein möglicher zug ist
    // if (clickableButtons.contains(positions.get(b))) {
    // b.setBackground(new Color(230, 100, 100));
    // }
    // }
    //
    // // alle schwarzen squares deaktivieren, damit dannach klar ist wer dran ist
    // for (JButton b : belegungen.keySet()) {
    // if (belegungen.get(b).split("-")[0].equals("w")) {
    // b.setEnabled(false);
    // }
    // }
    //
    // }
    //
    // // alle anderen Buttons nicht ckickbar zu machen
    //
    // // Button Icon zurücksetzen
    //
    // // Buttonposition merken (in MoveListe oder so)
    //
    // // wenn Button platzierd werden soll...
    //
    // // neuen Button in Moveliste eintragen
    //
    // // Icon ändern
    //
    // // Modus auf auswählen setzen und spielerwechsel markieren
    //
    // // spielerwechsel
    // if(moveFinished)playerWhite=!playerWhite;
    //
    // }
    //
    // });

    // panelLinks.add(b);
    // buttons.add(b);
    // positions.put(b, i);
    //
    // }

    ladeBrett();

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
        buttons.get(i).setEnabled(false); // erstmal deaktivieren, damit weiß beginnen kann
      }
      buttons.get(i).setIcon(new ImageIcon("src/main/resources/" + (int) fen[j] + ".png"));

      i++;

    }
  }

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

}
