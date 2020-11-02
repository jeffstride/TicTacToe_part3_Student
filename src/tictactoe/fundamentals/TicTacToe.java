package tictactoe.fundamentals;

import java.util.*;
import tictactoe.AI.AIBase;
import tictactoe.AI.CupsAI;
import tictactoe.AI.GameTreeAI;
import tictactoe.console.ConsoleUI;
import tictactoe.gui.SwingUI;

/**
 * This class is responsible for creating the necessary class to run
 * the TicTacToe game. It will first ask if we are to be GUI or console
 * based and then it will create the appropriate user interface class:
 * ConsoleUI or SwingUI.<p>
 * 
 * This class will also go through all the steps to play the game.
 * It will create an AI Player, if needed, and allow the AI to learn.
 * It will ask the Player objects for their moves, make the move to
 * the board, and end the game when the board says the game is over.
 * 
 * @author jstride
 *
 */
public class TicTacToe {
	
	private GameUserInterface gui;
	public static boolean useGameTree = true;
	
	private static final int GAME_MODE_GUI = 0;
	private static final int GAME_MODE_DIALOGS = 1;
	private static final int GAME_MODE_CONSOLE = 2;
	
	/**
	 * Main will ask via the console if we are GUI or console.
	 * It will then create an instance of the correct user interface
	 * class, an instance of this TicTacToe class, and connect
	 * the two together. It then starts playing.
	 * @param args
	 */
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();

		int mode = game.getMode();
		
		if (mode != GAME_MODE_CONSOLE) {
			// let's use our GameManager object as the semaphore
			SwingUI gui = new SwingUI(game);
			game.setGameInterface(gui);
			
			// start up the UI on the EDT
			SwingUI.startUIOnEventDispatchThread(gui);
			
			// If we want to just test our dialogs, then
			// let's have the main thread just wait for notifications
			// until the user quits the app.
			if (mode == GAME_MODE_DIALOGS) {
				gui.waitForNotifications();
			}
		} else {
			// Play the games using the console window as the UI
			game.setGameInterface(new ConsoleUI());
		}
		
		// if we aren't in our test dialogs mode, play the game(s)!!
		if (mode != GAME_MODE_DIALOGS) {
			game.play();
			
			System.out.println("End of Session. Thanks for playing.");
			// kill all threads and end the program
			System.exit(0);
		}
	}
	
	/**
	 * Asks the user for the mode to run the program in.
	 * @return GAME_MODE_value
	 *    0 == GUI
	 *    1 == Test Dialogs
	 *    2 == Console
	 */
	private int getMode() {
		Scanner console = ConsoleUI.console;
		
		// Get the mode to run the code.
		// Loop until we get a good mode
		char mode = '\0';
		while (mode == '\0') {
			System.out.print("GUI, Console or Dialog mode? (G/C/D): ");
			String input =console.nextLine().toLowerCase();
			if (input.length() > 0) {
				mode = input.charAt(0);
			}
		}
		
		int result;
		switch (mode) {
			case 'g':
				result = GAME_MODE_GUI;
				break;
			case 'd':
				result = GAME_MODE_DIALOGS;
				break;
			default:
				result = GAME_MODE_CONSOLE;
				break;
		}

		return result;
	}
	
	private void setGameInterface(GameUserInterface gui) {
		this.gui = gui;
	}
	
	/**
	 * This plays games until the user(s) don't want to play anymore.
	 * It starts off by asking how many players. If there is only
	 * one player, it then asks for the type of AI Algorithm to use.
	 * Furthermore, with an AI, it creates the AI class and allows
	 * the AI to learn (synchronously) before starting the play.
	 * 
	 * When playing an AI, the AI always goes first for now (is X).
	 * The human player will always be O. This is for simplification
	 * of the code.
	 * 
	 * The management of the game is on the main thread. All GUI
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
	}
	
	/**
	 * Plays a game with the given players.
	 * @param players The players involved in the game with their own GUI
	 * @return The name of the player that won the game.
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
			playerNum = 1 - playerNum; // (playerNum + 1) % 2;
			
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
