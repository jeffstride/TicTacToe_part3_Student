
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
	
	public void tellPlayerResult(int winner) {
	}
	
	// TODO: probably should have an AIBase where this would go
	public void learn() {
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
