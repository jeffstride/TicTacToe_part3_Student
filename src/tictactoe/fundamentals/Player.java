package tictactoe.fundamentals;

/**
 * This is an abstraction for a Player. It will leverage the
 * GameUserInterface to get user moves. 
 * 
 * @author Jeff
 *
 */
public class Player {
	
	private int playerNum;
	private GameUserInterface gui;
	
	public Player(GameUserInterface gui, int num) {
		this.gui = gui;
		this.playerNum = num;
	}
	
	public int getPlayerNum() {
		return this.playerNum;
	}
	
	public String toString() {
		return "Player " + this.playerNum;
	}
	
	public GameUserInterface getGui() {
		return this.gui;
	}
	
	/**
	 * This method is intended to allow any derived AI class
	 * to learn. When an AI loses or wins, it would presumably
	 * learn from that experience.
	 * This method could potentially interact with the GUI to
	 * display some winning message. However, that is
	 * accomplished in the askPlayAgain(winner) method.
	 * (Bad design? Perhaps.)
	 * @param winner integer value of the winning player (0, 1, 2)
	 *   0 == stale mate (no winner)
	 *   1 == X won the game
	 *   2 == O won the game
	 */
	public void tellPlayerResult(int winner) {
	}
	
	public Move getMove(Board board) {
		int answer = 1;
		if (gui != null) {
			return gui.getUserMove(playerNum);
		}

		System.out.println("ERROR. Should have a gui!");
		return new Move(answer / 3, answer % 3, this.getPlayerNum());
	}
}
