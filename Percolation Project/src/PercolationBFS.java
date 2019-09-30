import java.util.*;

public class PercolationBFS extends PercolationDFSFast{
	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationBFS(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	/**
	 * changes dfs to use a queue and the BFS method
	 * @param row and @param col is the location of the cell
	 */
	@Override 
	public void dfs(int row, int col) {
		
		int size = myGrid.length;
		int value = 0; 
		int[] rowDelta = {-1, 1, 0, 0};
		int[] colDelta = {0, 0, -1, 1};
		
		Queue<Integer> q = new LinkedList<>();
		myGrid[row][col] = FULL; 
		q.add((row * size) + col);
		
		while(q.size() != 0) {
			value = q.remove(); 
			for(int k = 0; k < 4; k++) {
				col = (value % size) + colDelta[k]; 
				row = ((value - (value % size)) / size) + rowDelta[k]; 
				if(inBounds(row, col) && isOpen(row,col) && myGrid[row][col] != FULL) {
					q.add((row * size) + col); 
					myGrid[row][col] = FULL;
				}
			}
		}
	}

}
