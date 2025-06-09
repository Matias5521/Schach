package de.mannheim.th.chess.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

public class MainFrame extends JFrame {
	
	private ArrayList<SpielFrame> spiele = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {

		setBackground(Color.LIGHT_GRAY);
		setResizable(true);
		setAlwaysOnTop(true);
		setTitle("Schach");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(Box.createVerticalStrut(10));

		JLabel lblNewLabel = new JLabel("Schach");
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
				
				SpielFrame sp = new SpielFrame();
				spiele.add(sp);
				
			}
			
		});
		contentPane.add(btnNewButton);

		contentPane.add(Box.createVerticalStrut(15));

		JButton btnNewButton_1 = new JButton("Vergangenes Spiel laden");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                JFileChooser dateiWaehler = new JFileChooser();
                JFrame jfFile = new JFrame();
                int auswahl = dateiWaehler.showOpenDialog(jfFile); 

                if (auswahl == JFileChooser.APPROVE_OPTION) {
                    File ausgewaehlteDatei = dateiWaehler.getSelectedFile();
                    JOptionPane.showMessageDialog(jfFile,"Gewählte Datei:\n" + ausgewaehlteDatei.getAbsolutePath());
                    
                    //Uebergabe zu Logik zum extrahieren der Daten
                }
            }
			
		});
		
		contentPane.add(btnNewButton_1);

		contentPane.add(Box.createVerticalStrut(15));

		JButton btnNewButton_2 = new JButton("Spiel beenden");
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
