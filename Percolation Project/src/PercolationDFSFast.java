
public class PercolationDFSFast extends PercolationDFS{
	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFSFast(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Determines when to call dfs on a cell based on its position and neighbors.
	 * If a cell has full neighbors or is in the top row then dfs will be called
	 * @param row and @param col is the cell's position
	 */
	@Override
	public void updateOnOpen(int row, int col) {

		if(row == 0) dfs(row,col); 
		else if(inBounds(row, col + 1) && (myGrid[row][col + 1] == FULL)) dfs(row,col); 
		else if(inBounds(row - 1, col) && (myGrid[row - 1][col] == FULL)) dfs(row,col);  
		else if(inBounds(row + 1, col) && (myGrid[row + 1][col] == FULL)) dfs(row,col); 
		else if(inBounds(row, col - 1) && (myGrid[row][col - 1] == FULL)) dfs(row,col); 
		
		else return; 
	}
}
