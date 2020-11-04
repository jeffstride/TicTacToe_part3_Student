package tictactoe.gui;
import javax.swing.*;

/**
 * This class enables polymorphism for the other dialog classes.
 * It implements the notification of the main thread.
 * It enforces that each dialog has a semaphore object
 * and the getResult() method.
 * <p>
 * Do NOT change.
 * 
 * @author Jeff
 *
 */
public abstract class DialogBase extends JPanel {
	
	private Object semaphore;
	
	public DialogBase(Object semaphore) {
		this.semaphore = semaphore;
	}
	
	public abstract int getResult();
	
	protected void notifyMain() {
		// allow call thread to start again
		synchronized (semaphore) {
			semaphore.notify();
		}
	}
}
