
public abstract class AIBase extends Player {

	public AIBase(GameUserInterface gui, int num) {
		super(gui, num);
	}

	/**
	 * Some algorithms, like Cups, needs to have an opportunity
	 * to learn.
	 */
	public void learn() {
	}
}
