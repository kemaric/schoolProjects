package model;

import java.awt.*;
import java.util.Random;

/**
 * This enumerated type represents a board cell.  A board cell has a
 * color (based on Color) and a name (e.g., "R").  
 * @Kemari Legg Dept of Computer Science, UMCP
 */

public enum BoardCell {
	RED(Color.RED, "R"), 
	GREEN(Color.GREEN, "G"),
	BLUE(Color.BLUE, "B"),
	YELLOW(Color.YELLOW, "Y"),
	EMPTY(Color.WHITE, ".");
		
	private final Color color;
	private final String name;
	private static int totalColors = BoardCell.values().length;

	private BoardCell(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public static int getTotalColors() {
		return totalColors;
	}

	/** Generates a random BoardCell using the specified Random object.
	 * @param random
	 * @return random BoardCell
	 */
	
	public static BoardCell getNonEmptyRandomBoardCell(Random random) {
		int target = random.nextInt(totalColors);
		for (BoardCell boardCell : BoardCell.values()) {
			if (boardCell == BoardCell.EMPTY)
				return BoardCell.RED;
			if (target == boardCell.ordinal())
				return boardCell;
		}
		throw new IllegalArgumentException("Invalid random number generated");
	}
}