package tictactoe.gui;

import java.awt.FlowLayout;

import javax.swing.*;

import tictactoe.fundamentals.GameUserInterface;
import tictactoe.fundamentals.Move;

/**
 * This class is responsible for graphically implementing all the methods
 * in the GameUserInterface. There will be dialogs for each interactions
 * with the user. Once the user interaction is completed, the dialog
 * will notify the main thread via the semaphore.
 * 
 * @author jstride
 *
 */
public class SwingUI implements GameUserInterface {
	
	//static GraphicsConfiguration gc;
	
	private JFrame frame;
	private Object semaphore;
	
	private PlayerCountDialog dialogPlayerCount;
	private GetUserMoveDialog dialogUserMove;
	private AIAlgorithmDialog dialogAIAlgorithm;
	
	/**
	 * This is a reference to the last dialog shown. This allows
	 * us to wait for the result of the last dialog shown and then
	 * to get the result and display it.
	 */
	private DialogBase lastDialog;
	
	/**
	 * This enables us to see if we are running in a multi-threaded
	 * environment or not. We save of the name of the EDT thread.
	 */
	private String nameOfEDT; 
	
	/**
	 * The constructor for the SwingUI object.
	 * @param semaphore 
	 *      Enables synchronization with the main thread.
	 *      This object will be notified once the user has made
	 *      his/her selection.
	 */
	public SwingUI(Object semaphore){
		
		// we need this to make our API appear synchronous
		this.semaphore = semaphore;
		lastDialog = null;
		
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
		// get the name of this EDT thread
		nameOfEDT = Thread.currentThread().getName();
		
		frame = new JFrame("Tic Tac Toe");
		
		// without setting this, our program won't exit when the frame is closed.
		// This appears to only be important when we invokeLater() on this
		// EventDispatchThread.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dialogPlayerCount = new PlayerCountDialog(semaphore);
		frame.add(dialogPlayerCount);
		
		dialogUserMove = new GetUserMoveDialog(semaphore);
		frame.add(dialogUserMove);
		
		dialogAIAlgorithm = new AIAlgorithmDialog(semaphore);
		frame.add(dialogAIAlgorithm);
		
		frame.setLayout(new FlowLayout());		
		frame.setSize(600, 400);
		
		// let's add some menu options to help us trigger dialogs
		// even before we have a working game.
		JMenuBar bar = new JMenuBar();
		JMenu dialogs = new JMenu("Dialogs");
		dialogs.setMnemonic('D');
		JMenuItem item = new JMenuItem("Get Player Count", 'C');
		item.addActionListener(e -> getPlayerCount());
		dialogs.add(item);
		item = new JMenuItem("Get User Move", 'M');
		item.addActionListener(e -> getUserMove(1));
		dialogs.add(item);
		item = new JMenuItem("Pick AI Algorithm", 'A');
		item.addActionListener(e -> getAIAlgorithm());
		dialogs.add(item);
		bar.add(dialogs);
		frame.setJMenuBar(bar);
		
		// last, show our dialog (make it visible)
		frame.setVisible(true);
		
		// tell the main thread that we are done creating our dialogs
		synchronized (semaphore) {
			semaphore.notify();
		}
	}

	private int getDialogResult(DialogBase dlg) {
		// remember the last dialog we wanted a result for
		lastDialog = dlg;
		
		// invokeLater
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  if (dlg != dialogUserMove) {
		    		  dialogUserMove.setVisible(false);
		    	  }
		    	  dlg.setVisible(true);
		      }
		    });
		
		// When dialogs get triggered by the menu system, then
		// this code will be running on the EventDispatchThread.
		// In which case, we won't want thread synchronization.
		String name = Thread.currentThread().getName();
		if (name.equals(nameOfEDT)) {
			// if we are on the EDT, then don't wait to be notified
			// by the dialog. That will cause a deadlock.
			// Just exit now.
			return 0;
		}
		
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
		// invokeLater to assure that the dialog
		// is run on the EDT thread.
		// This lambda represents the Runnable interface, run() method.
		SwingUtilities.invokeLater( () -> {
		   dialogUserMove.setVisible(true);
		   dialogUserMove.showAIMove(move);
		});	
	}

	/**
	 * This displays the winner of the last game.
	 * It asks the user if he/she wants to play again.
	 * We simplify this by using a modal, popup, but we
	 * could extend this to be a modeless dialog as well.
	 * @param winner The name of the player that won the game.
	 * @return boolean true if the user wants to play again.
	 */
	@Override
	public boolean askPlayAgain(String winner) {
		int answer = JOptionPane.showConfirmDialog(this.frame, "Do you want to play again?", 
				winner + " won", JOptionPane.YES_NO_OPTION);
		
		dialogUserMove.resetBoard();
		
		return answer == JOptionPane.YES_OPTION;
	}

	/**
	 * Asks the user for the type of AI to use.
	 * @return
	 *    0 = GameTree
	 *    1 = Cups
	 *    2 = ...
	 *    3 = ...
	 */
	@Override
	public int getAIAlgorithm() {
		return getDialogResult(dialogAIAlgorithm);
	}

	/**
	 * This will wait for the user interaction to be completed by
	 * whatever dialog is being displayed. Once the thread is notified,
	 * this thread will display the result.
	 * The main thread will call this in lieu of actually playing
	 * the game. Don't call this method if you want the main thread
	 * to do anything.
	 */
	public void waitForNotifications() {
		while (true) {
			synchronized (semaphore ) {
				try {
					semaphore.wait();
					int result = lastDialog.getResult();
					JOptionPane.showMessageDialog(null, "Result = " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}