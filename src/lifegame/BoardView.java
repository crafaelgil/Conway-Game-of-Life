package lifegame;

//import java.util.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardView extends JPanel implements BoardListener, MouseListener, MouseMotionListener {
	
	private BoardModel boardModel;
	private int cellWidth = 20;
	private int horizontalMargin;
	private int verticalMargin;
	private int correctX;
	private int correctY;
	
	public BoardView(BoardModel model) {
		boardModel = model;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		computeEachCellWidth();
		computeMargins();
		
		super.paint(g);
		
		drawGrid(g);
		fillAliveCells(g);
	}
	
	private void computeEachCellWidth() {
		cellWidth = (int) (Math.min(this.getWidth(), this.getHeight())/boardModel.getCols());
	} 
	
	private void computeMargins() {
		horizontalMargin = (int) ((this.getWidth() - boardModel.getCols() * cellWidth) / 2);
		verticalMargin = (int) ((this.getHeight() - boardModel.getRows() * cellWidth) / 2);
	}
	
	private void drawGrid(Graphics g) {
		drawColumns(g);
		drawRows(g);
	}
	
	private void drawColumns(Graphics g) {
		for(int i = 0; i < boardModel.getCols() + 1; i++) {
			g.drawLine(horizontalMargin + i * cellWidth, verticalMargin, horizontalMargin + i * cellWidth, this.getHeight() - verticalMargin);
		}
	}
	
	private void drawRows(Graphics g) {
		for(int i = 0; i < boardModel.getRows() + 1; i++) {
			g.drawLine(horizontalMargin, verticalMargin + i * cellWidth, this.getWidth() - horizontalMargin, verticalMargin + i * cellWidth);
		}
	}
	
	private void fillAliveCells(Graphics g) {
		for(int x = 0; x < boardModel.getCols(); x++) {
			for(int y = 0; y < boardModel.getRows(); y++) {
				if(boardModel.isCellAlive(x, y)) {
					g.fillRect(horizontalMargin +  x * cellWidth, verticalMargin + y * cellWidth, cellWidth, cellWidth);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(wasPressedInsideBaord(e)) {
			correctX = correctCoordinate(e.getX(), horizontalMargin, cellWidth);
			correctY = correctCoordinate(e.getY(), verticalMargin, cellWidth);
			
			boardModel.changeCellState(correctX, correctY);
			
			this.repaint();
		}
	}
	
	private boolean wasPressedInsideBaord(MouseEvent e) {
		if(e.getX() > horizontalMargin && e.getX() < this.getWidth() - horizontalMargin) {
			if(e.getY() > verticalMargin &&  e.getY() < this.getHeight() - verticalMargin) {
				return true;
			}
		}
		return false;
	}
	
	private int correctCoordinate(int coordinate, int margin, int scale) {
		return (int) (Math.floorDiv(coordinate - margin, scale));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updated(BoardModel m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
