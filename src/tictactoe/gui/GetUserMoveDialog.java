package tictactoe.gui;
import java.awt.*;
import java.awt.event.*;

import tictactoe.fundamentals.Move;

public class GetUserMoveDialog extends DialogBase {

	private int result = 0;
	
	// 1 == X
	// 2 == O
	// 0 is not a valid player turn value
	private int playerTurn = 1;
	
	private int width = 300;
	private int height = 300;
	private Font font = new Font("Monospaced", Font.BOLD, 130);
	
	// this is a cache of the user's moves
	// 0 == empty; 1 == X; 2 == O;
	private int[][] board = new int[3][3];
	
	public GetUserMoveDialog(Object sem) {
		super(sem);
		setUp();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}
	
	public void resetBoard() {
		this.board = new int[3][3];
		this.playerTurn = 1;
		this.repaint();
	}
	
	/**
	 * This method gets called when the dialogs should paint itself.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g){
		// Be sure to call the super class implementation first
        super.paintComponent(g);
        
        // Now, we can paint our TicTacToe board
		g.setColor(Color.BLACK);
		this.setBackground(Color.WHITE);
		g.setFont(font);
		
        g.clearRect(0, 0, this.width, this.height);
		
		// draw the hash marks
		g.drawLine(this.width/3, 0, this.width/3, this.height);
		g.drawLine(2*this.width/3, 0, 2*this.width/3, this.height);
		g.drawLine(0, this.height/3, this.width, this.height/3);
		g.drawLine(0, 2*this.height/3, this.width, 2*this.height/3);
		
		// fill the board with X's and O's
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				drawTurn(row, col, g);
			}
		}
    }
	
	private void drawTurn(int row, int col, Graphics g) {
		int dx = this.width / 3;
		int dy = this.height / 3;
		
	    if (board[row][col] != 0) {
	    	String turn = board[row][col] == 1 ? "X" : "O";
		    g.drawString(turn, col * dx + 5, row * dy + dy - 5);
	    }
	}
	
	/**
	 * This method gets called when the AI has decided to make its
	 * move. Since it cannot click on the board, we must call this
	 * method explicitly to show the AI's move.
	 * We will always assume that 
	 * @param move The Move object representing the location and value.
	 */
	public void showAIMove(Move move) {
		// the move's value should match whose turn it is
		if (move.getValue() != playerTurn) {
			throw new IllegalArgumentException("Invalid Move!");
		}
		
		// udpate the board with the move made
		board[move.getRow()][move.getCol()] = move.getValue();
		// swap from 1 to 2, and 2 to 1
		playerTurn = playerTurn % 2 + 1;
		
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
		return this.result;
	}
	
	/**
	 * Create components for this dialog.
	 * Set up LayoutManager.
	 * Create and hook up event handlers.
	 * Set visibility to false until this dialog is to be shown.
	 */
	private void setUp() {	
		createEventHandlers();
		
		// default to not showing
		this.setVisible(false);
	}
	
	/**
	 * Set up all the event handlers for our components.
	 */
	private void createEventHandlers() {
		
		// A mouse listener requires a full interface with lots of methods.
		// The MouseListener interface has five methods.
		// To get around having implement all, we use the MouseAdapter class 
		// and override just the one method we're interested in.
		this.addMouseListener(new MouseAdapter() {
			@Override
	        public void mousePressed(MouseEvent me) { 
	            onMouseClicked(me); 
	        } 
	    }); 
	}    
	
	private void onMouseClicked(MouseEvent me) {
		// get the coordinates.
		int col = me.getX() / (this.width / 3);
		int row = me.getY() / (this.height / 3);
		
		// the move is calculated. 
		this.result = row*3 + col;

		// validate empty move before notifying main
		if (board[row][col] == 0) {
			board[row][col] = playerTurn;
			// swap from 1 to 2, and 2 to 1
			playerTurn = playerTurn % 2 + 1;
			
			this.repaint();
			this.notifyMain();
		}
		
		// else if not a valid move, we could display or do something.
		
	}
}
