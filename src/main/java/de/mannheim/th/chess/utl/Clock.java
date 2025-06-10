package de.mannheim.th.chess.utl;

/**
 * Zeigt die Zeitangabe während eines Spiels eines Spielers an.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Clock extends Thread implements Runnable {
	private volatile boolean whiteToMove = true;
	private volatile boolean gameHasFinished = false;
	private int minutes;
	
	public Clock(String mode) {
		setMode(mode);
		
	}
	
	public void pressClock() {
		whiteToMove = !whiteToMove;
	}
	
	public void endGame() {
		gameHasFinished = true;
	}
	
	public void run() {
		JFrame clockFrame = new JFrame("Clock");
		Container pane = clockFrame.getContentPane();
		pane.setBackground(Color.BLACK);
		clockFrame.setBounds(1000, 500, 10000, 10000);
		clockFrame.setBackground(Color.BLACK);
		clockFrame.setLayout(new BorderLayout());
		var clock1 = new JLabel(minutes + ":00");
		clock1.setForeground(Color.WHITE);
		clock1.setFont(new Font("Arial", Font.BOLD, 50));
		var clock2 = new JLabel(minutes + ":00");
		clock2.setFont(new Font("Arial", Font.BOLD, 50));
		clock2.setForeground(Color.WHITE);
		var middleSpace = new JTextArea("	");
		middleSpace.setBackground(Color.BLACK);
		middleSpace.setEditable(false);
		pane.add(middleSpace, BorderLayout.CENTER);
		
		var min1 = new AtomicInteger(minutes);
		var sec1 = new AtomicInteger(0);
		var min2 = new AtomicInteger(minutes);
		var sec2 = new AtomicInteger(0);
		pane.add(clock1, BorderLayout.LINE_START);
		pane.add(middleSpace, BorderLayout.CENTER);
		pane.add(clock2, BorderLayout.LINE_END);
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
		Clock st = new Clock("blitz");
		st.start();
	}
	
	private void setMode(String mode) {
		switch(mode) {
		case "blitz":
			minutes = 5;
			break;
		case "rapid":
			minutes = 10;
			break;
		case "classic":
			minutes = 120;
			break;
		}
	}

}