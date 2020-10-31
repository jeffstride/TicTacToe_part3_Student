import java.util.Scanner;

public class ConsoleUI implements GameUserInterface {

	public static Scanner console = new Scanner(System.in);	

	private Board board = new Board();
	
	public ConsoleUI(Object semaphore) {
		// since all console UI is synchronous, we can ignore the semaphore
		// this.semaphore = semaphore;
	}
	
	@Override
	public int getPlayerCount() {
		return getInteger("How many players? (1-2) ", 1, 2);
	}
	
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

	@Override
	public void showAIMove(Move move) {
		board.doMove(move);
		System.out.println("   AI selects " + move);
		System.out.println("Board = " + board);
	}

	@Override
	public boolean askPlayAgain(String winner) {
		System.out.println("The winner of the game was: " + winner);
		System.out.print("Do you want to play again? ");
		
		// reset our board before we play again
		board = new Board();
		return console.nextLine().toLowerCase().startsWith("y");
	}

}
