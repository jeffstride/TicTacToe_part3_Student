package tictactoe.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * This class presents a dialog to the user asking what type
 * of AI algorithm to use.
 * 
 * @author Jeff
 *
 */
public class AIAlgorithmDialog extends DialogBase {

	private int result = 0;
	
	/**
	 * The constructor for the dialog will call the super constructor
	 * to establish the semaphore for thread synchronization. It then
	 * calls the setup method to create all the internal components.
	 * @param sem The object used to synchronize threads.
	 */
	public AIAlgorithmDialog(Object sem) {
		super(sem);
		setUp();
	}
	
	/**
	 * This adds all the components to the dialog for display.
	 * The student will need to hook up event handlers to
	 * know when the user's input is complete.
	 * 
	 */
	private void setUp() {
		// TODO: choose and set layout manager
		
		// TODO: create and add components to this pane
		
		// TODO: hook up event handlers
		
		// default to not showing
		this.setVisible(false);
	}
	
	/**
	 * @return
	 *    0 = GameTree
	 *    1 = Cups
	 *    2 = ...
	 *    3 = ...
	 */
	public int getResult() {
		return result;
	}
	
}
