package de.mannheim.th.chess;

import de.mannheim.th.chess.ui.Ui;

/**
 * Eine einfache Schach App mithilfe von {@linkplain https://github.com/bhlangonijr/chesslib} entwickelt.
 * @author Matias Mas Viehl, Dominik Stuck und Marius Guendel
 * @version 0.0.1
 */
public class App {
	
	private static Ui userinterface;
	/**
	 * Main-Methode.
	 * @param args
	 */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        userinterface = new Ui();
    }
}
