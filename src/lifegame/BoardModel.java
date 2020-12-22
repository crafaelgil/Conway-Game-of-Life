package lifegame;
import java.util.*;

public class BoardModel {
	private int cols;
    private int rows;
    private boolean[][] cells;
    private ArrayList<BoardListener> listeners;
	private int[][] numberOfNeighbours;
	private ArrayList<boolean[][]> pastBoards;
    
    public BoardModel(int c, int r) {
        cols = c;
        rows = r; 
        cells = new boolean[rows][cols];
        numberOfNeighbours = new int[rows][cols];
        listeners = new ArrayList<BoardListener>();
        pastBoards = new ArrayList<boolean[][]>();
        initializeNumberOfBoard();
    }
    
    private void initializeNumberOfBoard() {
    	for(int i = 0; i < cols; i++) {
	     	for(int j = 0; j < rows; j++) {
	     		numberOfNeighbours[j][i] = 0;
	     	}
	     }
    }
    
    public void printForDebug() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (cells[i][j] == true) {
					System.out.print("*");
				}
				else {
					System.out.print(".");
				}
			}
			System.out.println("");
		}
		System.out.println("");
    }
    
    public  synchronized void changeCellState(int x, int y) {
    	cells[y][x] = !cells[y][x];
    	this.fireUpdate();
    }
    
    public void addListeners(BoardListener listener) {
    	listeners.add(listener);
    }
    
    private void fireUpdate() {
    	for(BoardListener listener: listeners) {
    		listener.updated(this);
    	}
    }
    
    public synchronized void next() {
    	countCellsAliveForEachCell();
    	modifyCells();
    	saveBoard();
    }
    
    private void modifyCells() {
    	for (int x = 0; x < cols; x++) {
    		for (int y = 0; y < rows; y++) {
    			if(isCellAlive(x,y) && (numberOfNeighbours[y][x] == 2 || numberOfNeighbours[y][x] == 3)) {
    				cells[y][x] = true;
    			}
    			else if(isCellAlive(x,y) == true && numberOfNeighbours[y][x] > 3) {
    				cells[y][x] = false;
    			}
    			else if(!isCellAlive(x,y) && numberOfNeighbours[y][x] == 3) {
    				cells[y][x] = true;
    	    	}
    			else {
    				cells[y][x] = false;
    			}
        	}
    	}
    }
    
    public boolean isCellAlive(int x, int y) {
    	return cells[y][x];
    }
    
    private void saveBoard() {
    	boolean[][] boardCopied = new boolean[rows][cols];
    	
    	for(int x = 0; x < cols; x++) {
    		for (int y = 0; y < rows; y++) {
    			boardCopied[y][x] = cells[y][x];
    		}
    	}
    	
    	pastBoards.add(boardCopied);
    }
    
    private void countCellsAliveForEachCell() {
	 	initializeNumberOfBoard();
    	
    	for(int x = 0; x < cols; x++) {
    		for(int y = 0; y < rows; y++) {
    			if (x > 0 && cells[y][x-1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (x < cols-1 && cells[y][x+1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (y > 0 && cells[y-1][x] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (y < rows-1 && cells[y+1][x] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (x > 0 &&  y > 0 && cells[y-1][x-1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (x > 0 && y < rows-1 && cells[y+1][x-1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (x < cols-1 && y < rows-1 && cells[y+1][x+1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    	    	if (x < cols-1 && y > 0 && cells[y-1][x+1] == true) {
    	    		numberOfNeighbours[y][x]++;
    	    	}
    		}
    	}
    }
    
    public void printPastBoards(boolean[][] pastBoard) {
    	for (int i = 0; i < cols; i++) {
    		for (int j = 0; j < rows; j++) {
    			if (pastBoard[i][j] == true) {
					System.out.print("*");
				}
				else {
					System.out.print(".");
				}
        	}
    		System.out.println("");
    	}
    	System.out.println("");
    }
    
    public synchronized void undo() {
    	pastBoards.remove(pastBoards.size()-1);
    	cells = pastBoards.get(pastBoards.size()-1);
    }
    
    public boolean isUndoable() {
    	return pastBoards.size() > 1;
    }
    
    public int getCols() {
        return cols;
    }
    
    public int getRows() {
        return rows;
    }
}
