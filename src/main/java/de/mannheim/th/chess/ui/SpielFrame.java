package de.mannheim.th.chess.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.GridLayout;

public class SpielFrame extends JFrame {
	
	private static final Logger logger = LogManager.getLogger(App.class);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<JButton> buttons = new ArrayList<>();
	private JPanel panelLinks, panelRechts;

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
		setTitle("Spiel");
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
            	logger.info("Helles Feld erstellt."+i);
                b.setBackground(new Color(90, 90, 90));
            } else {
            	logger.info("Dunkles Feld erstellt."+i);
                b.setBackground(new Color(65, 65, 65));
            }
            
            b.setForeground(Color.WHITE);
            b.setBorderPainted(false);

            panelLinks.add(b);
        }
    }

}
