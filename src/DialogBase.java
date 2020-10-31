import javax.swing.*;

public abstract class DialogBase extends JPanel {
	
	private Object semaphore;
	
	public DialogBase(Object semaphore) {
		this.semaphore = semaphore;
	}
	
	public abstract int getResult();
	
	protected void notifyMain() {
		// hide ourselves when we are done
		// this.setVisible(false);
		
		// allow call thread to start again
		synchronized (semaphore) {
			semaphore.notify();
		}
	}
}
