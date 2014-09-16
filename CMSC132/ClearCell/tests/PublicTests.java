package tests;
import junit.framework.TestCase;
import java.util.Random;
import model.*;

public class PublicTests extends TestCase {
	public void testGoodFaithAttempt() {
		StringBuffer resultBuffer = new StringBuffer();
		int rows=9, cols=12;
		GameModel model = new ClearCellGameModel(rows, cols, new Random(1L));
		resultBuffer.append("Initial board\n");
		BoardCell[][] board = model.getBoard();
		for (int row=0; row<board.length; row++) {
			for (int col=0; col<board[row].length; col++)
				resultBuffer.append(board[row][col].getName());
			resultBuffer.append("\n");
		}
		
		model.nextAnimationStep();
		resultBuffer.append("Board after animation step\n" + model);
		resultBuffer.append("MaxRows: " + model.getMaxRows());
		resultBuffer.append("\nMaxCols: " + model.getMaxCols() + "\n");
		assertTrue(TestingSupport.correctResults("pubGoodFaithAttempt.txt", resultBuffer.toString()));
	}
	
	public void testAnimationSteps() {
		StringBuffer resultBuffer = new StringBuffer();
		int rows=8, cols=16;
		GameModel model = new ClearCellGameModel(rows, cols, new Random(1L));
		resultBuffer.append("Initial board\n" + model);
		for (int i=0; i<rows/2; i++)
			model.nextAnimationStep();
		resultBuffer.append("\nBoard after first set of animation steps\n" + model);
		resultBuffer.append("\nGame Over: " + model.isGameOver() + "\n");
		
		for (int i=0; i<rows/2; i++)
			model.nextAnimationStep();
		resultBuffer.append("\nBoard after second set of animation steps\n" + model);
		resultBuffer.append("\nGame Over: " + model.isGameOver() + "\n");
		
		for (int i=0; i<rows/2; i++)
			model.nextAnimationStep();
		resultBuffer.append("\nBoard after third set of animation steps\n" + model);
		resultBuffer.append("\nGame Over: " + model.isGameOver() + "\n");
		
		assertTrue(TestingSupport.correctResults("pubAnimationSteps.txt", resultBuffer.toString()));
	}
	
	public void testNonRandomProcessCell() {
		int rows=7, cols=7;
		StringBuffer resultBuffer = new StringBuffer();
		
		GameModel model = new ClearCellGameModel(rows, cols, null);
		BoardCell[][] testBoard = new BoardCell[rows][cols];
		for (int row=0; row<testBoard.length; row++)
			for (int col=0; col<testBoard[row].length; col++)
				model.setBoardCell(row, col, BoardCell.RED);
		
		appendInfo(resultBuffer, "Initial Board", model);

		model.processCell(3, 3);
		appendInfo(resultBuffer, "After processCell(3, 3)", model);

		model.setBoardCell(6, 3, BoardCell.YELLOW);
		appendInfo(resultBuffer, "After Setting cell 6, 3 yellow", model);

		model.processCell(5, 3);
		appendInfo(resultBuffer, "After processCell(5, 3)", model);
		
		model.processCell(6, 6);
		appendInfo(resultBuffer, "After processCell(6, 6)", model);
		
		model.processCell(2, 2);
		appendInfo(resultBuffer, "After processCell(2, 2)", model);
		
		assertTrue(TestingSupport.correctResults("pubNonRandomProcessCell.txt", resultBuffer.toString()));
	}
	
	public void testNonRandomCollapse() {
		int rows=7, cols=7;
		StringBuffer resultBuffer = new StringBuffer();
		
		GameModel model = new ClearCellGameModel(rows, cols, null);
		BoardCell[][] testBoard = new BoardCell[rows][cols];
		for (int row=0; row<testBoard.length; row++)
			for (int col=0; col<testBoard[row].length; col++)
				if (row % 2 == 0)
					model.setBoardCell(row, col, BoardCell.RED);
				else
					model.setBoardCell(row, col, BoardCell.YELLOW);
		appendInfo(resultBuffer, "Initial Board", model);

		model.processCell(2, 3);
		appendInfo(resultBuffer, "After processCell(2, 3)", model);
		
		for (int i=0; i<cols; i++)
			model.processCell(1, i);
		appendInfo(resultBuffer, "After processCell for every cell in second row", model);

		assertTrue(TestingSupport.correctResults("pubNonRandomCollapse.txt", resultBuffer.toString()));
	}
	
	public void testProcessCell() {
		StringBuffer resultBuffer = new StringBuffer();
		int rows=9, cols=12;
		
		GameModel model = new ClearCellGameModel(rows, cols, new Random(1L));
		appendInfo(resultBuffer, "Initial Board", model);		
		
		for (int i=0; i<rows/2; i++)
			model.nextAnimationStep();
		appendInfo(resultBuffer, "After animation steps", model);		
		
		model.processCell(1, 5);
		appendInfo(resultBuffer, "After processCell(1, 5)", model);

		model.processCell(0, 0);
		appendInfo(resultBuffer, "After processCell(0, 0)", model);

		model.processCell(3, 11);
		appendInfo(resultBuffer, "After processCell(3, 11)", model);
		
		model.processCell(1, 1);
		appendInfo(resultBuffer, "After processCell(1, 1)", model);

		model.processCell(rows-1, cols-1);
		appendInfo(resultBuffer, "After processCell(rows-1, cols-1)", model);

		assertTrue(TestingSupport.correctResults("pubProcessCell.txt", resultBuffer.toString()));
	}
	
	public void testCollapsingRow() {
		StringBuffer resultBuffer = new StringBuffer();
		int rows=9, cols=12;
		GameModel model = new ClearCellGameModel(rows, cols, new Random(1L));

		for (int i=1; i<=5; i++)
			model.nextAnimationStep();
		appendInfo(resultBuffer, "After animation steps", model);		
		
		for (int i=0; i<cols; i++) {
			model.processCell(2, i);
			appendInfo(resultBuffer, "After processCell(2, " + i + ")", model);
		}
		
		assertTrue(TestingSupport.correctResults("pubCollapsingRow.txt", resultBuffer.toString()));
	}
	
	private void appendInfo(StringBuffer resultBuffer, String message,
						    GameModel model) {
		resultBuffer.append(message + "\n" + model);
		resultBuffer.append("Game Over: " + model.isGameOver() + "\n");
		resultBuffer.append("Score: " + model.getScore() + "\n\n");		
	}
}