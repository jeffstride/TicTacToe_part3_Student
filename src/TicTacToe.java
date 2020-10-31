import java.util.*;

public class TicTacToe {
	
	private GameUserInterface gui;
	public static boolean useGameTree = true;
	
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		Scanner console = new Scanner(System.in);
		
		System.out.print("GUI or console? (G/C): ");
		boolean useGUI = console.nextLine().toLowerCase().startsWith("g");
		
		// let's use our GameManager object as the semaphore
		SwingUI gui = new SwingUI(game);
		
		if (useGUI) {
			game.setGameInterface(gui);
			
			// start up the UI on the EVT
			SwingUI.startUIOnEventDispatchThread(gui);
			
			// TODO: Delete this once game play works
			gui.waitForNotifications();
		} else {
			
			game.setGameInterface(new ConsoleUI(game));
		}
		
		// TODO: Once the Student is ready to start playing
		// the game, call: game.play();
		// game.play();


		console.close();
	}
	
	public void setGameInterface(GameUserInterface gui) {
		this.gui = gui;
	}
	
	/**
	 * This plays games until the user(s) don't want to play anymore.
	 * It starts off by asking how many players. If there is only
	 * one play, it then asks for the type of AI Algorithm to use.
	 * Furthermore, with an AI, it creates the AI class and allows
	 * the AI to learn (synchronously) before starting the play.
	 * 
	 * When playing an AI, the AI always goes first for now (is X).
	 * The human player will always be O. This is for simplification
	 * of the code.
	 * 
	 * The management of the game is one the main thread. All GUI
	 * is handled on the Event Dispatch Thread. Dialogs use a semaphore
	 * to synchronize user input back to the main thread which waits
	 * for user input.
	 */
	public void play() {
		
		// get player count
		int playerCount = gui.getPlayerCount();
		
		// ask for the type of AI, if we have a 1-player game
		if (playerCount == 1) {
			useGameTree = (gui.getAIAlgorithm() == 0);
		}
		
		// start with two human Players
		Player players[] = { new Player(gui, 1), new Player(gui, 2) } ;
		
		// EXTENSIONS: Here are some possible things one could do:
		// 1) ask for count of games to train
		// 2) show a progress bar in the UI
		// 3) Do UI to get who goes first (AI or Human)
		// 4) Get Human Player Names in GUI and Console
		if (playerCount == 1) {
			// Create the correct AI by using a static factory method.
			// This factory pattern isn't truly necessary.
			Player ai = (useGameTree ? GameTreeAI.getPlayer(gui, 1) : CupsAI.getPlayer(gui, 1));
			
			// Cast our Player type to an AIBase so that we can have
			// access to the learn() method
			AIBase learningAI = (AIBase) ai;
			learningAI.learn();
			
			// put the AI player into our array
			players[0] = ai;
		}
		
		boolean keepPlaying = true;
		
		while (keepPlaying) {
			String winner = playGame(players);
			keepPlaying = gui.askPlayAgain(winner);
		}
		
		System.out.println("End of Session. Thanks for playing.");
		System.exit(0);
	}
	
	/**
	 * Plays a game with the given players and stick count
	 * @param players The players involved in the game with their own GUI
	 * @return The player that one.
	 */
	public static String playGame(Player[] players) {
		boolean gameOver = false;
		int playerNum = 0;
		Board board = new Board();
		int count = 0;
		int winnerNum = 0;
		while (!gameOver) {
			Move move = players[playerNum].getMove(board);
			board.doMove(move);
			
			// keep track of the count of moves made
			count++;
			
			// go to the next player
			playerNum = (playerNum + 1) % 2;
			
			winnerNum = board.findWinner();
			gameOver = (count == 9 || winnerNum != 0);
		}
		
		String winner;
		
		if (winnerNum == 0) {
			winner = "STALEMATE: Nobody";
		} else {
			// give winning message
			winner = players[winnerNum-1].toString();
		}
		
		// inform players of who won
		players[0].tellPlayerResult(winnerNum);
		players[1].tellPlayerResult(winnerNum);
		
		return winner;
	}

}
