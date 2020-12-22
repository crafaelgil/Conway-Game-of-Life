package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

public class Actions implements ActionListener{
	BoardModel board;
	BoardView boardView;
	JButton autoButton;
	JButton stopButton;
	JButton nextButton;
	JButton undoButton;
	JButton newGameButton;
	JMenuItem saveAsMenuItem;
	JMenuItem openFileMenuItem;
	Timer timer;
	JFileChooser fileChooser;
	
	public Actions(BoardModel board,
		   BoardView boardView,
		   JButton autoButton,
				   JButton stopButton,
				   JButton nextButton,
				   JButton undoButton,
				   JButton newGameButton,
				   JMenuItem saveAsMenuItem,
				   JMenuItem openFileMenuItem) {
		
		this.board = board;
		this.boardView = boardView;
		this.autoButton = autoButton;
		this.stopButton = stopButton;
		this.nextButton = nextButton;
		this.undoButton = undoButton;
		this.newGameButton = newGameButton;
		this.saveAsMenuItem = saveAsMenuItem;
		this.openFileMenuItem = openFileMenuItem;
		
		this.stopButton.setEnabled(false);
		timer = new Timer();
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if(stopButton.isEnabled()) {
				board.next();
				boardView.repaint();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == autoButton) {
			toggleStateOfButtons();
			
			timer.scheduleAtFixedRate(new MyTimerTask(), 500, 500);
		}
		else if(e.getSource() == stopButton) {
			toggleStateOfButtons();
		}
		else if(e.getSource() == nextButton) {
			board.next();
			boardView.repaint();
		}
		else if(e.getSource() == undoButton) {
			if(!board.isUndoable()) {
				undoButton.setEnabled(false);
			}
			else {
				undoButton.setEnabled(true);
				board.undo();
				boardView.repaint();
			}
		}
		else if(e.getSource() == newGameButton) {
			Main newGame = new Main();
			newGame.run();
		}
		else if(e.getSource() == saveAsMenuItem) {
			saveNewFile();
		}
		else if(e.getSource() == openFileMenuItem) {
			openExistingFile();
		}
	}
	
	private void toggleStateOfButtons() {
		autoButton.setEnabled(!autoButton.isEnabled());
		stopButton.setEnabled(!stopButton.isEnabled());
		nextButton.setEnabled(!stopButton.isEnabled());
		undoButton.setEnabled(!undoButton.isEnabled());
		newGameButton.setEnabled(!newGameButton.isEnabled());
	}
	
	private void saveNewFile() {
		int returnValue = fileChooser.showSaveDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File("game.bin");
            
            
            System.out.println("Saving: " + file.getName() + ".");
        } else {
        	System.out.println("Save command cancelled by user.");
        }
	}
	
	private void openExistingFile() {
		int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Opening: " + file.getName() + ".");
        } else {
        	System.out.println("Open command cancelled by user.");
        }
	}
	
	public synchronized void closeThread() {
		timer.cancel();
	}
}
