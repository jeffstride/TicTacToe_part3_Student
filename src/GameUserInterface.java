

public interface GameUserInterface {

	public int getPlayerCount();
	public Move getUserMove(int playerNum);
	public void showAIMove(Move move);
	public boolean askPlayAgain(String winner);
}
