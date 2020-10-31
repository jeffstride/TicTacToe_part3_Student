
import java.awt.FlowLayout;

import javax.swing.*;

public class SwingUI implements GameUserInterface {
	
	//static GraphicsConfiguration gc;
	
	private JFrame frame;
	private Object semaphore;
	
	private PlayerCountDialog dialogPlayerCount;
	private GetUserMoveDialog dialogUserMove;
	private AIAlgorithmDialog dialogAIAlgorithm;
	private DialogBase lastDialog;
	
	private String nameOfEDT; 
	
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