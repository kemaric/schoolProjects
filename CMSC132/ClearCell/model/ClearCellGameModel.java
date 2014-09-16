package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game. 
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one where 
 * every cell corresponds to BoardCell.EMPTY.
 * 
 * @Kemari Legg of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {

	private Random randomNum; // random object to be used with coming up with random color
	private int score; // number of spaces on the board that were turned white
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 */
	public ClearCellGameModel(int maxRows, int maxCols, Random random) {
		super(maxRows, maxCols); /* Yes, we need this call */
		randomNum = random;
		score = 0;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1)
	 * is different from empty row. Returns true if the row is not empty and false otherwise.
	 */
	public boolean isGameOver() {
		
		return isRowEmpty((board.length-1))? false: true;
	}

	/**
	 * This returns the score of the game which is determined by the number of cells processed and turned white
	 * by the user. 
	 */
	public int getScore() {
		return score;
	}

	/**
	 * This is used to check if the given row in the parameter is empty. It checks to see if each cell is empty
	 * and if they are all empty, the method returns false.
	 * 
	 *  @param row
	 */
	private boolean isRowEmpty(int row){
		boolean rowEmpty = false;
		int emptyColCount = 0;
		for(int col = 0; col < getMaxCols(); col++){
			if(getBoardCell(row, col) == BoardCell.EMPTY){
				emptyColCount++;
			}
			// this checks and keep track the number of empty cells in the given row
		}
		if(emptyColCount == getMaxCols()){  
			rowEmpty = true;
		}
		// checks to see if the the number of empty cells found is equal to the length of the columns.
		return rowEmpty;
	}
	/**
	 * This method will attempt to insert a row of random BoardCell objects
	 * if the last board row (row with index board.length -1) corresponds to
	 * the empty row;  Otherwise no operation will take place. If isGameOver() returns false,
	 * the method carries on to insert the random row of BoardCell objects.
	 */
	public void nextAnimationStep() {
		if(this.isGameOver() == false){
			for(int row = getMaxRows()-2; row >= 0; row--){
				for(int col = 0; col < getMaxCols(); col++){
					if(getBoardCell(row, col) != BoardCell.EMPTY){
						setBoardCell(row+1, col, this.getBoardCell(row, col));
					}
					/*
					 * This loop goes through the board from two rows above the last row to the top
					 * and shifts the BoardCell objects down the board one by one and eventually 
					 * leaves row 0 blank.
					 */
					if(row == 0){
						setBoardCell(row, col, BoardCell.getNonEmptyRandomBoardCell(randomNum));
					}
					// when the loop reaches row 0, random BoardCell objects are added to it's columns
					
				}
			}
		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY any cells immediately surrounding the
	 * cell at position [rowIndex][colIndex] if and only if those surrounding 
	 * cells have the same color as the selected cell.  The selected cell will
	 * also be turned into a BoardCell.EMPTY.  If after processing the surrounding
	 * cells any rows in the board are empty then those rows will collapse, moving non-empty
	 * rows upward. If [rowIndex][colIndex] corresponds to an empty cell no 
	 * action will take place. 
	 * @throws IllegalArgumentException with message "Invalid row index" for invalid row
	 * or "Invalid column index" for invalid column.  We check for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {
		int up = rowIndex-1;
		int down = rowIndex+1;
		int left = colIndex-1;
		int right = colIndex+1;

		if(rowIndex > getMaxCols() || rowIndex < 0){  // checker for invalid row
			throw new IllegalArgumentException("Invalid row index");
		}
		if(colIndex > getMaxCols() || colIndex < 0){  // checker for invalid column 
			throw new IllegalArgumentException("Invalid column index");
		}

		if(board[rowIndex][colIndex] != BoardCell.EMPTY){
			if(up >= 0){
				if(board[up][colIndex] == board[rowIndex][colIndex]){ 
					board[up][colIndex] = BoardCell.EMPTY;
					//  sets the cell above the target cell to EMPTY if it matches the parameter
					score++;
				}
				if(left >= 0){
					if(board[up][left] == board[rowIndex][colIndex]){ 
						board[up][left] = BoardCell.EMPTY;
						//  sets the cell above and to the left of the target cell to EMPTY if it matches the parameter

						score++;
					}
				}
				if(right < getMaxCols()){
					if(board[up][right] == board[rowIndex][colIndex]){ 
						board[up][right] = BoardCell.EMPTY;
						//  sets the cell above and to the right of the target cell to EMPTY if it matches the parameter

						score++;
					}

				}
			}
			// This block covers the up directions to the target cell and increments the score when cell made EMPTY

			if(down < getMaxRows()){
				if(board[down][colIndex] == board[rowIndex][colIndex]){ 
					board[down][colIndex] = BoardCell.EMPTY;
					//  sets the cell bellow the target cell to EMPTY if it matches the parameter

					score++;
				}
				if(left >= 0){
					if(board[down][left] == board[rowIndex][colIndex]){ 
						board[down][left] = BoardCell.EMPTY;
						// sets the cell bellow and to the left of the target cell to EMPTY if it matches the parameter

						score++;
					}
				}
				if(right < getMaxCols()){
					if(board[down][right] == board[rowIndex][colIndex]){ 
						board[down][right] = BoardCell.EMPTY;
						// sets the cell bellow and to the right of the target cell to EMPTY if it matches the parameter

						score++;
					}	
				}
			}

			// this block covers the down directions corresponding with the target cell

			if(left > 0){
				if(board[rowIndex][left] == board[rowIndex][colIndex]){ 
					board[rowIndex][left] = BoardCell.EMPTY;
					// sets the cell to the left of the target cell to EMPTY if it matches the parameter

					score++;
				}
			}
			if(right < getMaxCols()){
				if(board[rowIndex][right] == board[rowIndex][colIndex]){ 
					board[rowIndex][right] = BoardCell.EMPTY;
					// sets the cell to the right of the target cell to EMPTY if it matches the parameter
					score++;
				}
			}
			if(board[rowIndex][colIndex] == board[rowIndex][colIndex]){ 
				board[rowIndex][colIndex] = BoardCell.EMPTY;
				// sets the target cell to EMPTY if it matches the parameter

				score++;
			}
			// this block covers the left and right directions corresponding to the target cell
		}
		for(int row = getMaxRows()-2; row > 0; row--){
			if(isRowEmpty(row) == true){
				for(int breakPoint = row; breakPoint < getMaxRows()-1; breakPoint++){
					for(int col = 0; col < getMaxCols(); col++){
						board[breakPoint][col] = board[breakPoint+1][col];
						board[breakPoint+1][col] = BoardCell.EMPTY;
					}
				}
			}
		}
		/*
		 * This loop block loops through the rows of the board from two above the last getMaxRows() to row 0.
		 * It them checks each row by calling the isRowEmpty method to see if the row is empty and if it is,
		 * the row position is then made the starting point of another loop that goes from that row till getMaxRows()-1.
		 * Then another loop loops though each column and shift the position of the cells up and makes the lower cells
		 * empty.
		 */
	}
}


