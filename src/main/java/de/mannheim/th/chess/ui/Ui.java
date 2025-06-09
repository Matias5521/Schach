package de.mannheim.th.chess.ui;

import java.awt.EventQueue;
import java.util.ArrayList;

import de.mannheim.th.chess.utl.GameReader;

/**
 * Zeigt das Main-Menü der App an.
 */
public class Ui{
	
	private ArrayList<GameWindow> gamewindows = new ArrayList<>();
	private GameReader reader = new GameReader();
	private MainFrame mf;
	
	public Ui() {
		mf = new MainFrame();
		mf.setVisible(true);
		
	}
}