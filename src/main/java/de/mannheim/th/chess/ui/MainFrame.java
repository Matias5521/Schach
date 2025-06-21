package de.mannheim.th.chess.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mannheim.th.chess.App;
import de.mannheim.th.chess.domain.Game;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

public class MainFrame extends JFrame {
	
	private static final Logger logger = LogManager.getLogger(App.class);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		setBackground(Color.LIGHT_GRAY);
		setResizable(true);
		setAlwaysOnTop(true);
		setTitle("Schach");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(Box.createVerticalStrut(10));

		JLabel lblNewLabel = new JLabel("Schach");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 60));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);

		contentPane.add(Box.createVerticalStrut(10));

		JLabel lblNewLabel_1 = new JLabel("by Dominik, Marius und Matias");
		lblNewLabel_1.setFont(new Font("Calibri", Font.ITALIC, 24));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);

		contentPane.add(Box.createVerticalStrut(75));

		JButton btnNewButton = new JButton("Neues Spiel starten");
		
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ModeSelectionFrame ms = new ModeSelectionFrame();

			}

		});
		contentPane.add(btnNewButton);

		contentPane.add(Box.createVerticalStrut(15));

		JButton btnNewButton_2 = new JButton("App beenden");
		
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		contentPane.add(btnNewButton_2);
		setVisible(true);
	}
}
