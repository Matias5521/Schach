package de.mannheim.th.chess.ui;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

public class PGNLoaderFrame extends JFrame {
  private PgnHolder pgn;
  private File selectedFile;
  private List<Game> games;
  private DefaultListModel<String> gameListModel;
  private JPanel contentPane;
  private JList<String> gameList;

  public PGNLoaderFrame(MainFrame mf) {
    setResizable(true);
    setAlwaysOnTop(true);
    setTitle("Schach");
    setBounds(100, 100, 500, 500);

    contentPane = new JPanel();
    setContentPane(contentPane);

    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    JButton fileSelectButton = new JButton("Select File");
    fileSelectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();
        }
      }
    });
    contentPane.add(fileSelectButton);

    JButton loadPgnButton = new JButton("Load file");
    loadPgnButton.addActionListener(e -> loadFile());
    contentPane.add(loadPgnButton);

    gameListModel = new DefaultListModel<>();

    gameList = new JList<>(gameListModel);
    gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    gameList.setVisibleRowCount(5);

    JScrollPane scrollPane = new JScrollPane(gameList);

    contentPane.add(scrollPane);

    JButton startGameButton = new JButton("Starte Spiel");
    startGameButton.addActionListener(e -> {
      int index = gameList.getSelectedIndex();
      de.mannheim.th.chess.domain.Game game = new de.mannheim.th.chess.domain.Game(games.get(index).getHalfMoves());

      mf.setGame(game);
      mf.startGame();
    });
    contentPane.add(startGameButton);

    this.setVisible(true);

  }

  private void loadFile() {
    if (this.selectedFile != null) {
      pgn = new PgnHolder(this.selectedFile.getAbsolutePath());
      try {
        pgn.loadPgn();
        games = pgn.getGames();
        int i = 0;
        for (Game game : games) {
          gameListModel.addElement(i++ + "");
        }
        gameList.revalidate();
      } catch (Exception e) {
        // TODO: handle exception
      }

    }
  }

}
