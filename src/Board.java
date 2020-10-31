
import java.util.*;

public class Board {

	// 0 = empty
	// 1 = "X"
	// 2 = "O"
	private int[][] board = new int[3][3];
	
	private List<Move> moves = new LinkedList<>();
	
	public Board() {
		// add all moves to the board
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				moves.add(new Move(row, col));
			}
		}
	}
	
	public String toString() {
		// TODO: make this a fancy looking TEXT board
		return Arrays.deepToString(board);
	}
	
	/**
	 * doMove will place a move into the Board.
	 * 
	 * @param move The move to make
	 * @param value 0 = empty. 1 = "X". 2 = "O"
	 */
	public void doMove(Move move) {
		// TODO: verify that move is valid first
		int index = moves.indexOf(move);
		if (index != -1) {
			// remove the Move from our array of moves
			moves.remove(index);
			// place the value into the board
			board[move.getRow()][move.getCol()] = move.getValue(); 
		}
	}
	
	/**
	 * This will remove the value from the board
	 * and add the move into the list of available moves.
	 * 
	 * @param move The move to remove from the board.
	 */
	public void undoMove(Move move) {
		// clear the board position
		board[move.getRow()][move.getCol()] = 0;
		
		// add the move back into our list
		moves.add(move);
	}
	
	/**
	 * Returns true if the move is valid.
	 * @param move The move to test.
	 * @return true if valid.
	 */
	public boolean isValidMove(Move move) {
		return board[move.getRow()][move.getCol()] == 0;
	}
	
	/**
	 * Gets the value of the board at the specific move location.
	 * 
	 * @param move provides the location of the board to test
	 * @return 0 = empty. 1 = X. 2 = O.
	 */
	public int getBoardValue(Move move) {
		return board[move.getRow()][move.getCol()];
	}
	
	/**
	 * Determines if the board is full of X's and O's.
	 * @return true if the board is full.
	 */
	public boolean isBoardFull() {
		return this.moves.size() == 0;
	}
	
	/**
	 * This gets all the moves. 
	 * We make copies of the moves to avoid clobbering the values.
	 * We adjust the moves before giving out the
	 * list so that they have all the correct X's and Oh values.
	 *
	 * 	0 = empty: no move
	 * 	1 = X value
	 * 	2 = O value
	 * @return List of moves.
	 */
	public Move[] getAllMoves() {
		Move[] result = new Move[this.moves.size()];
		// adjust the move values to X or O
		// X goes with 9 moves. O goes with 8 moves.
		// X goes with odd moves. O goes with event moves.
		int value = (moves.size() % 2 == 1 ? 1 : 2);
		int index = 0;
		for (Move move : moves) {
			result[index++] = new Move(move.getRow(), move.getCol(), value);
		}
		return result;
	}
	
	/**
	 * Search the board for 3 in a row.
	 * 
	 * @return 0 = none. 1 = X won. 2 = O won.
	 */
	public int findWinner() {
		if (isWinner(1))
			return 1;
		if (isWinner(2))
			return 2;
		return 0;
	}
	
	private boolean isWinner(int x) {
		if (board[0][0] == x) {
			if ((board[1][0] == x && board[2][0] == x) || (board[1][1] == x && board[2][2] == x)
					|| (board[0][1] == x && board[0][2] == x)) {
				return true;
			}
		}
		if (board[0][2] == x) {
			if ((board[1][1] == x && board[2][0] == x) || (board[1][2] == x && board[2][2] == x)) {
				return true;
			}
		}

		return ((board[2][0] == x && board[2][1] == x && board[2][2] == x)
				|| (board[0][1] == x && board[1][1] == x && board[2][1] == x)
				|| (board[1][0] == x && board[1][1] == x && board[1][2] == x));
	}
	
}
