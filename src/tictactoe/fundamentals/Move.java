package tictactoe.fundamentals;

/**
 * This is an abstraction for a TicTacToe move.
 * It represents it with rows and column numbers.
 * @author Jeff
 *
 */
public class Move {

	private int row;
	private int col;
	// Value is:
	//   0 == empty
	//   1 == X
	//   2 == O
	private int value;
	
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
		this.value = 0;
	}
	
	public Move(int row, int col, int value) {
		this(row, col);
		this.value = value;
	}
	
	public String toString() {
		return "" + row + ", " + col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean equals(Object obj) {
		Move move = (Move) obj;
		return (move.col == this.col && move.row == this.row);
	}
	
}
