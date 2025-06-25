package de.mannheim.th.chess.ui;

import java.util.List;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.github.bhlangonijr.chesslib.pgn.PgnLoadListener;

public class PGNLoaderFrame extends JFrame {

  private static final Logger logger = LogManager.getLogger(PGNLoaderFrame.class);

  private PgnHolder pgn;
  private File selectedFile;
  private List<Game> games;
  private DefaultListModel<String> gameListModel;
  private JPanel contentPane;
  private JList<String> gameList;
  private JProgressBar progressBar;

  /**
   * Opens the PNGLoaderFrame
   */
  public PGNLoaderFrame(MainFrame mf) {

    // ----- Style -----
    setResizable(true);
    setAlwaysOnTop(true);
    setTitle("PGNLoader");
    setBounds(100, 100, 500, 500);

    // ----- contentPane -----
    contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    setContentPane(contentPane);

    // ----- FileSelector -----
    JButton fileSelectButton = new JButton("Select File");
    fileSelectButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        selectedFile = fileChooser.getSelectedFile();
      }
    });
    contentPane.add(fileSelectButton);

    // ----- LoadButton -----
    JButton loadPgnButton = new JButton("Load file");
    loadPgnButton.addActionListener(e -> loadFile());
    contentPane.add(loadPgnButton);

    // ----- SelectionList -----
    gameListModel = new DefaultListModel<>();
    gameList = new JList<>(gameListModel);
    gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    gameList.setVisibleRowCount(5);
    JScrollPane scrollPane = new JScrollPane(gameList);
    contentPane.add(scrollPane);

    // ----- ProgressBar -----
    progressBar = new JProgressBar(0, 100);
    progressBar.setValue(0);
    progressBar.setStringPainted(true);
    contentPane.add(progressBar);

    // ----- StartButton -----
    JButton startGameButton = new JButton("Starte Spiel");
    startGameButton.addActionListener(e -> {
      int index = gameList.getSelectedIndex();
      de.mannheim.th.chess.domain.Game game = new de.mannheim.th.chess.domain.Game(games.get(index).getHalfMoves());

      mf.setGame(game);
      mf.startView();
    });
    contentPane.add(startGameButton);

    this.setVisible(true);

  }

  private void loadFile() {
    if (this.selectedFile != null) {
      pgn = new PgnHolder(this.selectedFile.getAbsolutePath());

      LoadPGNWorker loadPGNWorker = new LoadPGNWorker();
      loadPGNWorker.addPropertyChangeListener(e -> {
        progressBar.setIndeterminate(true);
      });

      pgn.getListener().add(loadPGNWorker);
      loadPGNWorker.execute();

      gameList.revalidate();
    }
  }

  private class LoadPGNWorker extends SwingWorker<Integer, Integer> implements PgnLoadListener {

    @Override
    protected Integer doInBackground() throws Exception {
      try {
        pgn.loadPgn();
        games = pgn.getGames();
        int totalGames = games.size();
        for (int i = 0; i < totalGames; i++) {
          publish(i);
        }
      } catch (Exception e) {
        logger.info("Could not load pgn file!");
      }
      return pgn.getSize();
    }

    @Override
    protected void process(List<Integer> chunks) {
      for (Integer index : chunks) {
        gameListModel.addElement("Game: " + index);
        setProgress(Math.min(90, index * 100 / games.size()));
      }
    }

    @Override
    protected void done() {
      setProgress(100);
      progressBar.setValue(100);
      progressBar.setIndeterminate(false);
    }

    @Override
    public void notifyProgress(int games) {
      setProgress(Math.min(90, games));
      progressBar.setValue(Math.min(90, games));
    }
  }

}
