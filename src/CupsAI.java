import java.util.*;

/*
 *  TODO: Student is to implement this class by adding
 *  implementation as well as any additional methods necessary.
 */

// This is the Artificial Intelligence that LEARNS!!
public class CupsAI extends Player {

	private boolean learning = false;
	
	/**
	 * This is a protected constructor which means that only
	 * the CupsAI and derived classes can access it. This allows
	 * us to control the creation of the AI object a bit more.
	 * 
	 * @param gui The GUI for the AI
	 * @param num The player number (1 = X. 2 = O)
	 */
	protected CupsAI(GameUserInterface gui, int num) {
		super(gui, num);
	}
	
	/**
	 * This encapsulate the creation of a Player AI.
	 * 
	 * @param gui The GUI to use when playing
	 * @param num The player number. 1 = X. 2 = O.
	 * @return a Player that has artificial intelligence
	 */
	public static Player getPlayer(GameUserInterface gui, int num) {

		return new CupsAI(gui, num);
	}
	
	/**
	 * The AI should start and/or complete it's learning here.
	 */
	public void learn() {
		// TODO: Student may want to change this implementation.
		
		// create an opponent to play against
		CupsAI[] aiPlayers = { this, new CupsAI(null, 2) };
		
		// temporarily set our learning flag to avoid a bunch of GUI output
		// as we learn.
		this.learning = true;
		
		// let's just play some games and learn
		for (int games = 0; games < 2000; games++) {
			TicTacToe.playGame(aiPlayers);
		}
		
		// reset out learning so that we can see the necessary GUI
		this.learning = false;
	}
	
	public String toString() {
		return "The Artificial Intelligence";
	}
	
	/**
	* This is called at the end of the game. It tells the player
	* who won the game.
	* 
	* @param winner represent who won the game. 1 = X. 2 = 0.
	*/
	public void tellPlayerResult(int winner) {
		
		// TODO: Student needs to implement this.
	}
	
	/**
	 * This is called by the game. An AI should attempt to figure
	 * out a smart move and then display that move
	 */
	public Move getMove(Board board) {
		// TODO: Student needs to implement this to be smart.
		
		// pick a random move from all that are available.
		Move[] moves = board.getAllMoves();
		int rand = (int) (Math.random() * moves.length);
		
		// show the move in the GUI, if we have one
		// and we are not learning
		if (getGui() != null && !this.learning) {
			// show the move we made. We could add other output
			// here to help with Testing and debugging.
			System.out.println(" Move = " + moves[rand]);
			
			getGui().showAIMove(moves[rand]);			
		}
		
		// return the move that the AI decided
		return moves[rand];
	}
}
