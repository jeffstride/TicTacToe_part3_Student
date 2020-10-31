
import java.awt.FlowLayout;

import javax.swing.*;
public class SwingUI implements GameUserInterface {
	
	//static GraphicsConfiguration gc;
	
	private JFrame frame;
	private Object semaphore;
	
	private PlayerCountDialog dialogPlayerCount;
	private GetUserMoveDialog dialogUserMove;
	
	public SwingUI(Object semaphore){
		
		// we need this to make our API appear synchronous
		this.semaphore = semaphore;
		
	}
	
	/**
	 * This method will allow us to create all of our UI on the EDT
	 * (Event Dispatch Thread). This is to assure that all events and UI
	 * interactions happen on the correct thread. Yes, it is possible that
	 * we could get everything to work correctly without this. BUT! But,
	 * it is highly recommended to use this method when creating UI.
	 */
	public static void startUIOnEventDispatchThread(SwingUI ex) {
		// Question: What bad things would happen if we don't do this?
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	  			ex.createFrame();
	      }
	    });
	    
		// while the EventDispatchThread gets user input, wait()
		// this code runs on the Main thread
		synchronized (ex.semaphore ) {
			try {
				ex.semaphore.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	  }
	
	public void createFrame() {
		frame = new JFrame("Tic Tac Toe");
		
		// without setting this, our program won't exit when the frame is closed.
		// This appears to only be important when we invokeLater() on this
		// EventDispatchThread.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dialogPlayerCount = new PlayerCountDialog(semaphore);
		frame.add(dialogPlayerCount);
		
		dialogUserMove = new GetUserMoveDialog(semaphore);
		frame.add(dialogUserMove);
		
		frame.setLayout(new FlowLayout());		
		frame.setSize(600, 400);
		frame.setVisible(true);
		
		// tell the main thread that we are done creating our dialogs
		synchronized (semaphore) {
			semaphore.notify();
		}
	}


	private int getDialogResult(DialogBase dlg) {
		// invokeLater
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  dlg.setVisible(true);
		      }
		    });
		
		// while the EventDispatchThread gets user input, wait()
		// this code runs on the Main thread
		synchronized (semaphore ) {
			try {
				semaphore.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return dlg.getResult();
	}

	@Override
	public int getPlayerCount() {
		return getDialogResult(dialogPlayerCount);
	}

	@Override
	public Move getUserMove(int playerNum) {
		int move = getDialogResult(dialogUserMove);
		return new Move(move / 3, move % 3, playerNum);
	}

	@Override
	public void showAIMove(Move move) {
		// invokeLater
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  dialogUserMove.setVisible(true);
		    	  dialogUserMove.showAIMove(move);
		      }
		    });	
	}

	@Override
	public boolean askPlayAgain(String winner) {
		int answer = JOptionPane.showConfirmDialog(this.frame, "Do you want to play again?", 
				winner + " won", JOptionPane.YES_NO_OPTION);
		
		dialogUserMove.resetBoard();
		
		return answer == JOptionPane.YES_OPTION;
	}

}