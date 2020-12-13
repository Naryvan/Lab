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
	public static final int MISC = 5;
	
	public static final int START = 1;
	public static final int BEAD_APPEARED = 2;
	public static final int BEAD_MOVES = 3;
	public static final int END_SCREEN = 4;
	
	
	private SoundClip soundtrack;
	
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
	private static final int BRICK_ROWS = 10;
	
	//Bricks dimensions
	private static final int BRICK_WIDTH = WINDOW_WIDTH / (BRICK_ROWS + 2);
	private static final int BRICK_HEIGHT = WINDOW_HEIGHT / BRICK_ROWS / 4;
	
	//Distance top brick row and top edge of window
	private static final int BRICK_Y_OFFSET = 70;
	
	private static final int BRICK_X_OFFSET = BRICK_WIDTH;
	
	//Radius of bead
	private static final int BEAD_RADIUS = 7;

	private static final int DELAY = 5;
	
	
	private Bead bead;
	private Paddle paddle;
	private HealthBar healthBar;
	
	private GCompound startWindow;
	private GCompound endWindow;
	
	private int blocksCount;
	
	private int gameState;
	
	public void init() {
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		gameState = START;
		soundtrack = new SoundClip("sounds/soundtrack.wav");
		soundtrack.setVolume(0.005);
		soundtrack.loop();
		newGame();
		addMouseListeners();
	}
	
	public void newGame() {
		removeAll();
		addBlocks();
		addPaddle();
		addHealthBar();
		showStartScreen();
	}
	
	public void run() {
		while(true) {
			if(gameState == BEAD_MOVES) {
				if(bead != null) {
					checkForCollisions();
					moveBead();
				}
				else {
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
			soundtrack.setVolume(0.01);
			addBead();
			gameState = BEAD_APPEARED;
			return;
		}
		else if(gameState == BEAD_APPEARED) {
			gameState = BEAD_MOVES;
		}
		else if(gameState == END_SCREEN) {
			remove(endWindow);
			gameState = START;
			newGame();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(gameState == BEAD_APPEARED || gameState == BEAD_MOVES) {
			paddle.movePaddle(e);
		}
	}
	
	private void checkForCollisions() {
		if (bead.bounceFromPaddleIfCollides(paddle))
			return;
		if (bead.bounceIfCollidesWithWorldBounds())
			return;
		ArkanoidObject collidedObject = (ArkanoidObject) bead.collidesWith();
		if(collidedObject != null && collidedObject.getType() == BLOCK) {
			bead.bounceFromRectangle(((Block)collidedObject).getRect());
			if(((Block)collidedObject).tryToDestroy()) {
				destroyBlock(collidedObject);
				remove(collidedObject);
			}
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
	
	private void moveBead() {
		if(bead != null) {
			bead.moveBead();
		}
	}
	
	private void addBlocks() {
		blocksCount = 0;
		for(int i = 0; i < BRICK_ROWS; i++) {
			for(int j = 0; j < BRICKS_PER_ROW; j++) {
				add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
				blocksCount++;
			}
			add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + BRICKS_PER_ROW * BRICK_HEIGHT);
			blocksCount++;
		}
	}
	
	private void addHealthBar() {
		healthBar = new HealthBar(BEAD_RADIUS);
		healthBar.setLocation(BEAD_RADIUS * 2, WINDOW_HEIGHT - BEAD_RADIUS * 4);
		add(healthBar);
	}
	
	private void destroyBlock(ArkanoidObject block) {
		remove(block);
		blocksCount--;
		
		if(blocksCount == 0) {
			playVictory();
			gameState = END_SCREEN;
			showEndScreen("Ви перемогли!");
		}
	}
	
	private void showStartScreen() {
		startWindow = new StartWindow(WINDOW_WIDTH / 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 3);
		add(startWindow);
	}
	
	private void showEndScreen(String message) {
		soundtrack.setVolume(0.005);
		endWindow = new EndWindow(WINDOW_WIDTH / 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2 - WINDOW_HEIGHT / 8, message);
		add(endWindow);
	}
	
	public void processGameOver() {
		remove(bead);
		bead = null;
		if(healthBar.removeLife()) {
			playLose();
			gameState = END_SCREEN;
			showEndScreen("Ви програли!");
		}
	}
	
	private void playVictory() {
		SoundClip bounceSound = new SoundClip("sounds/victory.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
	private void playLose() {
		SoundClip bounceSound = new SoundClip("sounds/lose.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
}
