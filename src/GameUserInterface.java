

public interface GameUserInterface {

	public int getPlayerCount();
	public Move getUserMove(int playerNum);
	public void showAIMove(Move move);
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
