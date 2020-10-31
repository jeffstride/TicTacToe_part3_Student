import java.util.*;

public class GameTreeAI extends AIBase {
	
	private GameTreeAI(GameUserInterface gui, int num) {
		super(gui, num);
	}

	public static Player getPlayer(GameUserInterface gui, int num) {
		return new GameTreeAI(gui, num);
	}
	
	public String toString() {
		return "Game Tree AI";
	}
	
	public void learn() {
		// TODO: Student must fill in this code
	}
	
	public Move getMove(Board board) {
		// TODO: Student must fill in this code
			
		// once we get our move from our Game Tree
		// we must show the move and return it
		//getGui().showAIMove(move);
		
		// TODO: Student should replace this return statement
		return new Move(0,0,1);

	}

}
