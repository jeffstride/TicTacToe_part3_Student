package tictactoe.console;
import java.util.Scanner;

import tictactoe.fundamentals.Board;
import tictactoe.fundamentals.GameUserInterface;
import tictactoe.fundamentals.Move;

/**
 * This class is simple. It prints to the console window and gets
 * input from the user via the console window. It is the responsibility
 * of this class to keep track of the moves made and to assure that
 * attempted moves are valid. It does this by creating a Board object.
 * There is no way to reset the board in the middle of the game.
 * <p>
 * 
 * Students may choose to update the implementation of this class.
 * If any public method is added, it should be added to the interface
 * GameUserInterface as well, and added to the SwingUI class, too.
 * 
 * @author Jeff
 *
 */
public class ConsoleUI implements GameUserInterface {

	/**
	 * We have one and only one Scanner for user input.
	 */
	public static Scanner console = new Scanner(System.in);	

	private Board board;
	
	/**
	 * The constructor will initialize to a new board.
	 */
	public ConsoleUI() {
		this.board = new Board();
	}
	
	/**
	 * Gets the count of players for this game.
	 * @return 1 or 2. 1 player ==> an AI is the second player.
	 */
	@Override
	public int getPlayerCount() {
		return getInteger("How many players? (1-2) ", 1, 2);
	}
	
	/**
	 * Gets a valid move number from the user. 
	 * Moves are 1-9 and fit into the board as follows:
	 *  1 | 2 | 3  <br>
	 *  ---------  <br>
	 *  4 | 5 | 6  <br>
	 *  ---------  <br>
	 *  7 | 8 | 9  <p>
	 *  This method also updates the local copy of a board
	 *  and will print the board after the move is made.
	 *  It may optionally print the board before the move is made.
	 *  It may optionally present help to the user.
	 *  @param playerNum The player number, 1 or 2. This is used
	 *  to appropriately prompt the correct player to make the move.
	 *  @return a valid Move object is return representing the move made.
	 */
	@Override
	public Move getUserMove(int playerNum) {
		Move m;
		do {
			int move = getInteger("Player " + playerNum + ": Enter your move? (1-9) ", 1, 9);
			// user enters 1-9. We keep tack of moves 0-8. Subtract 1
			move--;	
			
			// create our move object and update our local board
			m = new Move(move/3, move%3, playerNum);
		} while (!board.isValidMove(m));

		board.doMove(m);
		
		System.out.println("Board = " + board);
		
		return m;
	}
	
	/**
	 * Prompts the user for an integer. It will continually re-prompt
	 * the user until a valid integer is entered.
	 * @param prompt The prompt to the user.
	 * @param min The Min value allowed as an answer (inclusive).
	 * @param max The Max value allowed as an answer (inclusive)
	 * @return an integer within the bounds set.
	 */
	private int getInteger(String prompt, int min, int max) {
		int answer = min - 1;
		while (answer < min || answer > max) {
			System.out.print(prompt);
			String line = console.nextLine();
			try {
				answer = Integer.parseInt(line);
			} catch  (NumberFormatException e) {
			}
		}
		return answer;
	}
	
	/**
	 * This method should display the full board after applying
	 * the Move. This method gets called when the AI has decided to make its
	 * move. Since the AI cannot click on the board, we must call this
	 * method explicitly to show the AI's move. 
	 * @param move The Move object representing the location and value.
	 */
	@Override
	public void showAIMove(Move move) {
		board.doMove(move);
		System.out.println("   AI selects " + move);
		System.out.println("Board = " + board);
	}

	/**
	 * This displays the winner of the last game and asks the user 
	 * if he/she wants to play again.
	 * @param winner The name of the play that won the game.
	 * @return true if the user wants to play again.
	 */
	@Override
	public boolean askPlayAgain(String winner) {
		System.out.println("The winner of the game was: " + winner);
		System.out.print("Do you want to play again? ");
		
		// reset our board before we play again
		board = new Board();
		return console.nextLine().toLowerCase().startsWith("y");
	}

	/**
	 * Asks the user for the type of AI to use.
	 * @return
	 *    0 = GameTree
	 *    1 = Cups
	 *    2 = ...
	 *    3 = ...
	 */
	@Override
	public int getAIAlgorithm() {
		System.out.print("GameTree or Cups? (G/C): ");
		return console.nextLine().toLowerCase().startsWith("g") ? 0 : 1;
	}

}
