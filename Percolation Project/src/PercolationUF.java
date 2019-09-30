
public class PercolationUF implements IPercolate {
	
	protected boolean[][] myGrid; 
	protected int myOpenCount; 
	private final int VTOP;
	private final int VBOTTOM; 
	IUnionFind myFinder; 
	
	public PercolationUF(int size, IUnionFind finder) {
		myGrid = new boolean[size][size]; 
		myFinder = finder; finder.initialize(size * size + 2);
		VTOP = size * size; 
		VBOTTOM = size * size + 1; 
	}
	/**
	 * checks to be sure the cell is not already open, 
	 * and then marks the cell as open and connects with open neighbors.
	 * @param row and @param col is the cell's position
	 */
	@Override
	public void open(int row, int col) {
		if(row >= myGrid.length || col >= myGrid.length || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if(isOpen(row,col)) return;
		int cell = (row * myGrid.length) + col; 
		int neighbor = 0; 
		int newRow = 0;
		int newCol = 0;
		myGrid[row][col] = true; 
		myOpenCount++; 
		
		int[] rowDelta = {-1, 1, 0, 0};
		int[] colDelta = {0, 0, -1, 1};
		if(row == 0) myFinder.union(cell, VTOP);
		if(row == myGrid.length - 1) myFinder.union(cell, VBOTTOM);
		for(int k = 0; k < 4; k++) {
			newRow = row + rowDelta[k];
			newCol = col + colDelta[k]; 
			if(newRow >= 0 && newRow < myGrid.length && newCol >= 0 && newCol < myGrid.length) {
			if(myGrid[newRow][newCol] == true) {
				neighbor = (newRow * myGrid.length) + newCol;
				myFinder.union(cell, neighbor);
				}
			}
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * Determines whether a cell is open or closed 
	 * @param row and @param col is the cell's position 
	 * @return boolean returns true if the cell is open  
	 * and false if the cell is closed
	 */
	@Override
	public boolean isOpen(int row, int col) {
		if(row >= myGrid.length || col >= myGrid.length || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col]; 
		// TODO Auto-generated method stub
	}
	/**
	 * Determines whether a set of connected cells should be full.
	 * If a set is connected to VTOP then the set is marked as full
	 * @param row and @param col is the cell's position 
	 * @return boolean returns false when the cell is not connected to VTOP
	 * and true when it is
	 */
	@Override
	public boolean isFull(int row, int col) {
		if(row >= myGrid.length || col >= myGrid.length || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		int cell = (row * myGrid.length) + col; 
		return myFinder.connected(cell, VTOP); 
		// TODO Auto-generated method stub
	}
	/**
	 * Determines whether the system percolates or not.
	 * If VTOP and VBOTTOM are connected then the systems percolates
	 * @return boolean returns true if VTOP and VBOTTOM are connected 
	 * and false if they aren't
	 */
	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP, VBOTTOM); 
		// TODO Auto-generated method stub
	}
	/**
	 * Returns how many open sites there are
	 * @return int returns myOpenCount which contains 
	 * the number of open sites
	 */
	@Override
	public int numberOfOpenSites() {
		// TODO Auto-generated method stub
		return myOpenCount;
	}

}
