package de.mannheim.th.chess.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.bhlangonijr.chesslib.Board;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;

import java.awt.EventQueue;
import java.awt.Font;

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
	private HashMap<JButton, String> belegungen = new HashMap<>();
	private JPanel panelLinks, panelRechts;
	private Game game;
	private String symbolChoosed;

	/**
	 * Launch the application.
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

	private void erstelleBrett() {
		for (int i = 0; i < 64; i++) {
			JButton b = new JButton();
			b.setFocusPainted(false);
			b.setFont(new Font("Arial", Font.PLAIN, 30));

			if ((i / 8 + i % 8) % 2 == 0) {
				logger.info("Helles Feld erstellt." + i);
				b.setBackground(new Color(90, 90, 90));
			} else {
				logger.info("Dunkles Feld erstellt." + i);
				b.setBackground(new Color(65, 65, 65));
			}

			b.setForeground(Color.WHITE);
			b.setBorderPainted(false);
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					//wenn gerade Figur ausgewählt wird...
					
					JButton clickedButton = (JButton) e.getSource();
					symbolChoosed = belegungen.get(clickedButton);
					//System.out.println(symbolChoosed+" wurde gewählt.");
					//setzt cursor auf spielfigur für die animation
			        String pfad = "src/main/resources/" + (int) symbolChoosed.toCharArray()[2] + ".png";

			        // Bild laden und Cursor im gesamten Frame setzen
			        Image image = Toolkit.getDefaultToolkit().getImage(pfad);
			        Image scaled = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			        Cursor figurCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaled, new Point(0, 0), "figurCursor");

			        //wenn richtiger spieler dran:
			        
			        setCursor(figurCursor);
			        
			        //Button Icon zurücksetzen
			        
			        //Buttonposition merken (in MoveListe oder so)
			        
			        //wenn Button platzierd werden soll...
			        
			        //neuen Button in Moveliste eintragen
			        
			        //Icon ändern 
			        
			        //Modus auf auswählen setzen und spielerwechsel markieren
			 
			        
			        
			        
				}

			});

			panelLinks.add(b);
			buttons.add(b);
		}
		
		game = new Game();
		ladeBrett();
	}

	/**
	 * holt sich FEN-Zeichenkette und extrahiert daraus die Positionen der Figuren
	 */
	public void ladeBrett() {
		//System.out.println(game.toFEN());
		
		char[] fen = game.toFEN().replaceAll("/", "").split(" ")[0].toCharArray();
		int i = 0;
		for (int j = 0; j < fen.length; j++) {
			if (Character.isDigit(fen[j])) {
				i += Character.getNumericValue(fen[j]);
				belegungen.put(buttons.get(i),"n-n"); //n steht fuer ein leeres Feld
				
				continue;
			}
			if(fen[j] >= 65 && fen[j] <= 90) { //ein Großbuchstabe
				belegungen.put(buttons.get(i), "w-"+fen[j]);
			}else if(fen[j] >= 97 && fen[j] <= 122) { //ein Kleinbuchstabe
				belegungen.put(buttons.get(i), "b-"+fen[j]);
			}
			buttons.get(i).setIcon(new ImageIcon("src/main/resources/" + (int) fen[j] + ".png"));
			//System.out.println(fen[j]);
			i++;

		}
		
		//System.out.println(belegungen.toString());
	}
}
