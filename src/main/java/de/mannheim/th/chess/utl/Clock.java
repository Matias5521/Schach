package de.mannheim.th.chess.utl;

/**
 * Zeigt die Zeitangabe während eines Spiels eines Spielers an.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Clock extends Thread implements Runnable {
	private volatile boolean whiteToMove = true;
	private volatile boolean gameHasFinished = false;
	private static final Logger clockLogger = LogManager.getLogger(Clock.class);
	private int minutes;
	
	public Clock(String mode) {
		setMode(mode);
		//run();
	}
	
	public void pressClock() {
		whiteToMove = !whiteToMove;
		if (whiteToMove) {
			clockLogger.info("Weiß ist am Zug");
		} else {
			clockLogger.info("Schwarz ist am Zug");
		}
	}
	
	public void endGame() {
		gameHasFinished = true;
	}
	
	public void run() {
		JFrame clockFrame = new JFrame("Clock");
		
		JPanel player1Panel = new JPanel();
		player1Panel.setBackground(Color.BLACK);
		JPanel player2Panel = new JPanel();
		player2Panel.setBackground(Color.BLACK);
		clockFrame.setBounds(1000, 500, 10000, 10000);
		clockFrame.setLayout(new BorderLayout());
		JLabel clock1 = new JLabel(" " + minutes + ":00 ");
		clock1.setForeground(Color.WHITE);
		clock1.setFont(new Font("Arial", Font.BOLD, 50));
		JLabel clock2 = new JLabel(" " + minutes + ":00 ");
		clock2.setForeground(Color.WHITE);
		clock2.setFont(new Font("Arial", Font.BOLD, 50));
		player1Panel.add(clock1);
		player2Panel.add(clock2);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, player1Panel, player2Panel);
		split.setFont(new Font("Arial", Font.BOLD, 50));
		clockFrame.add(split);
		
		var min1 = new AtomicInteger(minutes);
		var sec1 = new AtomicInteger(0);
		var min2 = new AtomicInteger(minutes);
		var sec2 = new AtomicInteger(0);
		clockFrame.pack();
		clockFrame.setVisible(true);

		var t = new Timer(1000, (ae) -> {
	
			if (!gameHasFinished) {	
				
			StringBuilder clockShower = new StringBuilder();
			if (whiteToMove) {
				if (sec1.intValue() == 00) {
					sec1.set(60);
					min1.decrementAndGet();
				}
				if (min1.intValue() < 10) {
					clockShower.append("0");
				}
				clockShower.append(min1.get());
				clockShower.append(":");
				if (sec1.intValue() < 10) {
					clockShower.append("0");
				}
				clockShower.append(sec1.decrementAndGet());
				clock1.setText(clockShower.toString());

			} else {
				if (sec2.intValue() == 00) {
					sec2.set(60);
					min2.decrementAndGet();
				}
				if (min2.intValue() < 10) {
					clockShower.append("0");
				}
				clockShower.append(min2.get());
				clockShower.append(":");
				if (sec2.intValue() < 10) {
					clockShower.append("0");
				}
				clockShower.append(sec2.decrementAndGet());
				clock2.setText(clockShower.toString());
			}
			clockFrame.repaint();
			if ((sec1.intValue() == 0 && min1.intValue() == 0) || (sec2.intValue() == 0 && min2.intValue() == 0)) {
				endGame();
			}
		} });

		t.start();
	}
	public static void main(String[] args) throws InterruptedException {
		Clock st = new Clock("classic");
		st.start();
		st.pressClock();
	}
	
	private void setMode(String mode) {
		switch(mode) {
		case "blitz":
			minutes = 5;
			clockLogger.info("Neue Blitz-Uhr wurde erstellt");
			break;
		case "rapid":
			minutes = 10;
			clockLogger.info("Neue Schnellschach-Uhr wurde erstellt");
			break;
		case "classic":
			minutes = 120;
			clockLogger.info("Neue klassische Schachuhr wurde erstellt");
			break;
		}
	}

}