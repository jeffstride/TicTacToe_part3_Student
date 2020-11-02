package tictactoe.gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * This class presents a dialog to the user asking how many
 * players are to play the game.
 * 
 * @author Jeff
 *
 */
public class PlayerCountDialog extends DialogBase {

	private int result = 0;
	
	/**
	 * The constructor for the dialog will call the super constructor
	 * to establish the semaphore for thread synchronization. It then
	 * calls the setup method to create all the internal components.
	 * @param sem The object used to synchronize threads.
	 */
	public PlayerCountDialog(Object sem) {
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
		JLabel label = new JLabel("How many human players? ");
		this.setLayout(new FlowLayout());
		
		//label.setSize(150, 40);
		//label.setVisible(true);
		
		JButton btnOne = new JButton("1 Player vs AI");
		JButton btnTwo = new JButton("2 Players - NO AI");

		btnOne.addActionListener(this::clickOne);
		btnTwo.addActionListener(this::clickTwo);
		
		this.add(label);
		this.add(btnOne);
		this.add(btnTwo);
		
		// default to not showing
		this.setVisible(false);
	}
	
	private void clickOne(ActionEvent e) {
		notifyMain(1);
	}
	
	private void clickTwo(ActionEvent e) {
		notifyMain(2);
	}
	
	public int getResult() {
		return result;
	}
	
	private void notifyMain(int result) {
		this.result = result;
		notifyMain();
		// hide ourselves when we are done
		this.setVisible(false);
	}
	
}
