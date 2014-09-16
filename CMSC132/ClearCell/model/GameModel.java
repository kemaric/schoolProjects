package model;

/**
 * This class represents the logic of a game where a
 * board is updated on each step of the game animation.
 * The board can also be updated by selecting a board cell.
 * This class is also an abstract class with four abstract 
 * methods which are isGameOver(), getScore(), nextAnimationStep(),
 * and processCell() .
 * 
 * @Kemari Legg Dept of Computer Science, UMCP
 */

public abstract class GameModel {
	protected BoardCell[][] board; // board for which the game is played on
	private int rowsOnBoard; // number of rows on the board
	private int colsOnBoard; // number of columns on the board
	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * @param maxRows
	 * @param maxCols
	 */
	public GameModel(int maxRows, int maxCols) {
		rowsOnBoard = maxRows;
		colsOnBoard = maxCols;
		board = new BoardCell[rowsOnBoard][colsOnBoard];
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length; col++){
				board[row][col] = BoardCell.EMPTY;
			}
		}
	}

	/**
	 * This method returns the number of rows on the board.
	 * 
	 * @return number of rows
	 */
	public int getMaxRows() {
		return rowsOnBoard;
	}

	/**
	 * This method returns the number of columns on the board.
	 * 
	 * @return number of columns
	 */
	public int getMaxCols() {
		return colsOnBoard;
	}
	
	/**
	 * This method returns a reference to the board which is a two dimensional array.
	 * 
	 * @return reference to board
	 */
	public BoardCell[][] getBoard() {
		return board;
	}

	/**
	 * This method sets the given row and column on the board a boardCell that is in the parameter.
	 * If the method is passed a BoardCell color, that position will have that color object.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 * @param boardCell
	 */
	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		board[rowIndex][colIndex] = boardCell;
	}

	/**
	 * This method returns a reference to the board which is a two dimensional array.
	 * 
	 * @return reference to board
	 */
	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}

	/** Provides a string representation of the board 
	 * 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Board(Rows: " + board.length + ", Cols: " + board[0].length + ")\n");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				buffer.append(board[row][col].getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	/**
	 * The game is over when the last board row (row with index board.length -1)
	 * is different from empty row. Returns true if the row is not empty and false otherwise.
	 */
	public abstract boolean isGameOver();

	/**
	 * This returns the score of the game which is determined by the number of cells processed and turned white
	 * by the user. 
	 */
	public abstract int getScore();

	/**
	 * Advances the animation one step. To be implemented in the ClearCellGameModel class. 
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board
	 * state and the selected cell.
	 * @param rowIndex 
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}