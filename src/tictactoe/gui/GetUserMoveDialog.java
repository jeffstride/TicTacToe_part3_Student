package tictactoe.gui;
import java.awt.*;
import java.awt.event.*;

import tictactoe.fundamentals.Move;

public class GetUserMoveDialog extends DialogBase {

	private int result = 0;
	
	public GetUserMoveDialog(Object sem) {
		super(sem);
		setUp();
	}
	
	/*
	 * Student may want something like this. Hint hint.
	 *
	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}
	*/
	
	/**
	 * This method gets called when the dialogs should paint itself.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g){
		// Be sure to call the super class implementation first
        super.paintComponent(g);
        
        // TODO: paint our TicTacToe board
		// TODO: draw the hash marks
		// TODO: fill the board with X's and O's
    }
	
	/**
	 * This method gets called when the AI has decided to make its
	 * move. Since it cannot click on the board, we must call this
	 * method explicitly to show the AI's move.
	 * @param move The Move object representing the location and value.
	 */
	public void showAIMove(Move move) {
		// VALIDATE: the move's value should match whose turn it is
		
		// TODO: udpate the board with the move made
		
		// Redraw the board by triggering a repaint
		this.repaint();
	}
	
	/**
	 * Reset the board to the beginning of the game. Set the player's
	 * turn to be X. Repaint the board.
	 */
	public void resetBoard() {
		// TODO: Reset the board.
		// TODO: Reset the turn to X
		
		// Redraw the board by triggering a repaint
		this.repaint();
	}
	
	/**
	 *  The main thread can access the user's move by requesting
	 *  the result value.
	 *  @return The result of the location of the last click.
	 *     0-8 are valid values.
	 */
	public int getResult() {
		// This needs to be set in the event handler
		return this.result;
	}
	
	/**
	 * Create components for this dialog.
	 * Set up LayoutManager.
	 * Create and hook up event handlers.
	 * Set visibility to false until this dialog is to be shown.
	 */
	private void setUp() {
		// TODO: choose and set layout manager
		
		// TODO: create and add components to this pane (if any)
		
		// TODO: hook up event handlers
		
		// default to not showing
		this.setVisible(false);
	}
}
