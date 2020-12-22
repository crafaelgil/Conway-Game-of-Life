package lifegame;

import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

public class Main implements Runnable {
	BoardModel model = new BoardModel(12,12);
	
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Main());
	}
	
	public void run() {
		String title = "Lifegame";
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(title);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveAsMenuItem = new JMenuItem("Save As");
		JMenuItem openFileMenuItem = new JMenuItem("Open File");
		
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(openFileMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(450, 300));
		frame.setMinimumSize(new Dimension(450,200));
		
		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		base.add(view,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout()); 
		
		JButton nextButton = new JButton("Next");
		JButton undoButton = new JButton("Undo");
		JButton autoButton = new JButton("Auto");
		JButton stopButton = new JButton("Stop");
		JButton newGameButton = new JButton("New Game");
		
		buttonPanel.add(autoButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(newGameButton);
		
		Actions actions = new Actions(model, 
									  view, 
									  autoButton, 
									  stopButton, 
									  nextButton, 
									  undoButton, 
									  newGameButton,
									  saveAsMenuItem,
									  openFileMenuItem);
		
		autoButton.addActionListener(actions);
		stopButton.addActionListener(actions);
		nextButton.addActionListener(actions);
		undoButton.addActionListener(actions);
		newGameButton.addActionListener(actions);
		
		saveAsMenuItem.addActionListener(actions);
		openFileMenuItem.addActionListener(actions);
		
		frame.pack();
		frame.addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent e) {
			   actions.closeThread();
			 }
		});
		frame.setVisible(true);
	}
}
