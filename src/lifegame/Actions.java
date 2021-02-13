package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		       JButton newGameButton) {
		
		this.board = board;
		this.boardView = boardView;
		this.autoButton = autoButton;
		this.stopButton = stopButton;
		this.nextButton = nextButton;
		this.undoButton = undoButton;
		this.newGameButton = newGameButton;
		
		this.stopButton.setEnabled(false);
		this.undoButton.setEnabled(false);
		
		timer = new Timer();
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
			if(board.pastBoards.size() == 0) {
				undoButton.setEnabled(false);
			}
			
			timer.scheduleAtFixedRate(new MyTimerTask(), 500, 500);
		}
		else if(e.getSource() == stopButton) {
			toggleStateOfButtons();
		}
		else if(e.getSource() == nextButton) {
			board.next();
			undoButton.setEnabled(true);
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
	}
	
	private void toggleStateOfButtons() {
		autoButton.setEnabled(!autoButton.isEnabled());
		stopButton.setEnabled(!stopButton.isEnabled());
		nextButton.setEnabled(!stopButton.isEnabled());
		undoButton.setEnabled(!undoButton.isEnabled());
		newGameButton.setEnabled(!newGameButton.isEnabled());
	}
	
	public synchronized void closeThread() {
		timer.cancel();
	}
}
