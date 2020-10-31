import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class PlayerCountDialog extends DialogBase {

	private int result = 0;
	
	public PlayerCountDialog(Object sem) {
		super(sem);
		setUp();
	}
	
	private void setUp() {
		
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
