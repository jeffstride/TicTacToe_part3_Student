import java.awt.*;
import java.awt.event.*;

public class GetUserMoveDialog extends DialogBase {

	private int result = 0;
	
	// 1 = X
	// 2 = O
	// 0 would be invalid
	private int playerTurn = 1;
	
	private int width = 300;
	private int height = 300;
	private Font font = new Font("Monospaced", Font.BOLD, 130);
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
	
	public void paintComponent(Graphics g){  
        super.paintComponent(g);
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
	
	public void showAIMove(Move move) {
		// udpate and redraw the board
		board[move.getRow()][move.getCol()] = playerTurn;
		// swap from 1 to 2, and 2 to 1
		playerTurn = playerTurn % 2 + 1;
		this.repaint();
	}
	
	public int getResult() {
		return this.result;
	}
	
	private void setUp() {	
		createEventHandlers();
		
		// default to not showing
		this.setVisible(false);
	}
	
	/**
	 * Set up all the event handlers for our components.
	 */
	private void createEventHandlers() {
		
		// a mouse listener requires a full interface with lots of methods.
		// to get around having implement all, we use the MouseAdapter class 
		// and override just the one method we're interested in.
		this.addMouseListener(new MouseAdapter() { 
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
		
		// not a valid move. Could display something.
		
	}
}
