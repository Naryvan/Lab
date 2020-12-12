import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;

public class Arkanoid extends GraphicsProgram {

	//Object type definitions for better readability
	public static final int PADDLE = 1;
	public static final int BLOCK = 2;
	public static final int BONUS = 3;
	public static final int BEAD = 4;
	
	public static final int START = 1;
	public static final int BEAD_APPEARED = 2;
	public static final int BEAD_MOVES = 3;
	public static final int END_SCREEN = 4;
	
	
	//Window dimensions
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	
	//Paddle dimensions
	private static final int PADDLE_WIDTH = 80;
	private static final int PADDLE_HEIGHT = 15;
	
	//Distance between paddle and bottom edge of window
	private static final int PADDLE_Y_OFFSET = 50;
	
	//Number of bricks
	private static final int BRICKS_PER_ROW = 8;
	private static final int BRICK_ROWS = 12;
	
	//Bricks dimensions
	private static final int BRICK_WIDTH = 50;
	private static final int BRICK_HEIGHT = 20;
	
	//Distance top brick row and top edge of window
	private static final int BRICK_Y_OFFSET = 70;
	
	//Radius of bead
	private static final int BEAD_RADIUS = 10;

	private static final int DELAY = 5;
	
	
	private Bead bead;
	
	private Paddle paddle;
	
	private GCompound startWindow;
	
	private int blocksCount;
	private int livesCount;
	
	private int gameState;
	
	public void init() {
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//addBead();
		addBlocks();
		addPaddle();
		addMouseListeners();
		gameState = START;
		livesCount = 3;
		showStartScreen();
	}
	
	public void run() {
		while(true) {
			if(gameState == BEAD_MOVES) {
				if(bead != null) {
					bead.moveBead();
					checkForCollisions();
				}
				else if(livesCount != 0) {
					addBead();
					gameState = BEAD_APPEARED;
				}
			}		
			pause(DELAY);		
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(gameState == START) {
			remove(startWindow);
			addBead();
			gameState = BEAD_APPEARED;
			return;
		}
		if(gameState == BEAD_APPEARED) {
			gameState = BEAD_MOVES;
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(gameState == BEAD_APPEARED || gameState == BEAD_MOVES) {
			paddle.movePaddle(e);
		}
	}
	
	private void checkForCollisions() {
		bead.bounceFromPaddleIfCollides(paddle);
		
		ArkanoidObject collidedObject = bead.collidesWith();
		if(collidedObject != null && collidedObject.getType() == BLOCK) {
			bead.bounceFromRectangle(((Block)collidedObject).getRect());
			remove(collidedObject);
			blocksCount--;
			return;
		}
		
		bead.bounceIfCollidesWithWorldBounds();
	}
	
	private void addPaddle() {
		paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(paddle, (WINDOW_WIDTH - PADDLE_WIDTH) / 2 , WINDOW_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}
	
	public void addBead() {
		bead = new Bead(WINDOW_WIDTH / 2 - BEAD_RADIUS, WINDOW_HEIGHT / 2 - BEAD_RADIUS, BEAD_RADIUS, this);
		add(bead);
		bead.sendToBack();
	}
	
	private void addBlocks() {
		blocksCount = 0;
		for(int i = 0; i < BRICK_ROWS; i++) {
			for(int j = 0; j < BRICKS_PER_ROW; j++) {
				add(new Block(BRICK_WIDTH, BRICK_HEIGHT), i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
				blocksCount++;
			}
		}
	}
	
	private void showStartScreen() {
		startWindow = new StartWindow(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 3, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 3);
		add(startWindow);
	}
	
	public void processGameOver() {
		remove(bead);
		bead = null;
		livesCount--;
	}
	
}
