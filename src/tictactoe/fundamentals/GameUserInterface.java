package tictactoe.fundamentals;
public interface GameUserInterface {

	/**
	 * Ask the user for how many players to play the game.
	 * 
	 * @return 1 or 2
	 */
	public int getPlayerCount();
	
	/**
	 * Gets the move that the player wants to make.
	 * @param playerNum The player number whose turn it is.
	 * @return The Move object representing the move made.
	 */
	public Move getUserMove(int playerNum);
	
	/**
	 * This method should display the full board after applying
	 * the Move. This method gets called when the AI has decided to make its
	 * move. Since the AI cannot click on the board, we must call this
	 * method explicitly to show the AI's move. 
	 * @param move The Move object representing the location and value.
	 */
	public void showAIMove(Move move);
	
	/**
	 * This displays the winner of the last game and asks the user 
	 * if he/she wants to play again.
	 * @param winner The name of the play that won the game.
	 * @return true if the user wants to play again.
	 */
	public boolean askPlayAgain(String winner);
	
	/**
	 * Asks the user for the type of AI to use.
	 * @return
	 *    0 = GameTree
	 *    1 = Cups
	 *    2 = ...
	 *    3 = ...
	 */
	public int getAIAlgorithm();
}
