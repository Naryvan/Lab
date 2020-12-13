/*
 * File: Arkanoid.java
 * 
 * This class is the main class of the Arkanoid game
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;

public class Arkanoid extends GraphicsProgram {

	/** Object type definitions for better readability */
	public static final int PADDLE = 1;
	public static final int BLOCK = 2;
	public static final int BONUS = 3;
	public static final int BEAD = 4;
	public static final int MISC = 5;	
	public static final int START = 1;
	public static final int BEAD_APPEARED = 2;
	public static final int BEAD_MOVES = 3;
	public static final int END_SCREEN = 4;
	
	/** music in game */
	private SoundClip soundtrack;
	
	/** Window dimensions */
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	
	/** Paddle dimensions */
	private static final int PADDLE_WIDTH = 80;
	private static final int PADDLE_HEIGHT = 15;
	
	/** Distance between paddle and bottom edge of window */
	private static final int PADDLE_Y_OFFSET = 50;
	
	/** Number of bricks */
	private static final int BRICKS_PER_ROW = 8;
	private static final int BRICK_ROWS = 10;
	
	/** Bricks dimensions */
	private static final int BRICK_WIDTH = WINDOW_WIDTH / (BRICK_ROWS + 2);
	private static final int BRICK_HEIGHT = WINDOW_HEIGHT / BRICK_ROWS / 4;
	
	/** Distance between top brick row and top edge of window */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Distance between bricks and side edges of window */
	private static final int BRICK_X_OFFSET = BRICK_WIDTH;
	
	/** Radius of bead */
	private static final int BEAD_RADIUS = 7;


	private static final int BONUS_DURATION = 10;


	/** Delay between iterations */
	private static final int DELAY = 5;
	
	private static final int LEVELS_NUMBER = 3;
	
	
	/** Ball currently in game */
	private Bead bead;
	
	/** Paddle that player controls */
	private Paddle paddle;

	private Bonus bonus;
	
	/** Shows number of lives left */
	public HealthBar healthBar;	
	
	/** Popup that shows game name and authors */
	private GCompound startWindow;
	
	/** Popup that shows result of the game */
	private GCompound endWindow;
	
	
	/** Numbers of blocks left to destroy */
	private int blocksCount;
	private int blocksAtStartOfBonus;
	
	/** Current state of the game */
	private int gameState;
	
	/** Current block formation */
	private int currentLevel;
	
	/** Intitalizes game components */
	public void init() {
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		gameState = START;
		startMusic();
		addMouseListeners();
		currentLevel = 1;
		newGame();
	}
	
	/** 
	 * Initializes new game
	 */
	public void newGame() {
		removeAll();
		add(new Background());
		addBlocks();
		addPaddle();
		addHealthBar();
		showStartScreen();
	}
	
	/** 
	 * All game processes are called form here 
	 */
	public void run() {
		while(true) {
			if(gameState == BEAD_MOVES) {
				if(bead != null) {
					checkForCollisions();
					moveBead();
					processBonus();
				}
				else {
					addBead();
					gameState = BEAD_APPEARED;
				}
			}		
			pause(DELAY);		
		}
	}
	
	/**
	 * Processes mouse clicks
	 */
	public void mouseClicked(MouseEvent e) {
		if(gameState == START) {
			remove(startWindow);
			addBead();
			gameState = BEAD_APPEARED;
			return;
		}
		else if(gameState == BEAD_APPEARED) {
			addBonus();
			gameState = BEAD_MOVES;
		}
		else if(gameState == END_SCREEN) {
			remove(endWindow);
			startMusic();
			gameState = START;
			newGame();
		}
	}
	
	/**
	 * Processes mouse movement, make paddle follow the mouse
	 */
	public void mouseMoved(MouseEvent e) {
		if(gameState == BEAD_APPEARED || gameState == BEAD_MOVES) {
			paddle.movePaddle(e);
		}
	}
	
	/**
	 * Checks for collisions between objects and performs required actions
	 */
	private void checkForCollisions() {
		if (bead.bounceFromPaddleIfCollides(paddle))
			return;
		if (bead.bounceIfCollidesWithWorldBounds())
			return;
		if (bead.collidesWith() instanceof ArkanoidObject) {
			ArkanoidObject collidedObject = (ArkanoidObject) bead.collidesWith();
			if(collidedObject != null && collidedObject.getType() == BLOCK) {
				bead.bounceFromRectangle(((Block)collidedObject).getRect());
				if(((Block)collidedObject).tryToDestroy()) {
					destroyBlock(collidedObject);
					remove(collidedObject);
				}
				return;
			}
		}
		bead.bounceIfCollidesWithWorldBounds();
	}
	
	/**
	 * Initializes paddle
	 */
	private void addPaddle() {
		paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(paddle, (WINDOW_WIDTH - PADDLE_WIDTH) / 2 , WINDOW_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}
	
	/**
	 * Initializes bead
	 */
	public void addBead() {
		bead = new Bead(WINDOW_WIDTH / 2 - BEAD_RADIUS, WINDOW_HEIGHT / 2 - BEAD_RADIUS, BEAD_RADIUS, this);
		add(bead);
		bead.sendToBack();
		bead.sendForward();
	}
	
	/**
	 * Moves beed on field
	 */
	private void moveBead() {
		if(bead != null) {
			bead.moveBead();
		}
	}
	
	/**
	 * Initializes blocks
	 */
	private void addBlocks() {
		blocksCount = 0;
		drawBlocks(currentLevel);
	}
	
	/**
	 * Draws different block patterns
	 * 
	 * @param level - Current level
	 */
	private void drawBlocks(int level) {
		Block.resetColor();
		switch(level) {
		case 1:
			for(int i = 0; i < BRICK_ROWS; i++) {
				for(int j = 0; j < BRICKS_PER_ROW; j++) {
					add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
					blocksCount++;
				}
				add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + BRICKS_PER_ROW * BRICK_HEIGHT);
				blocksCount++;
			}
			break;
		case 2:
			for(int i = 0; i < BRICK_ROWS + 1; i++) {
				if(i % 2 == 0) {
					for(int j = 0; j < BRICKS_PER_ROW; j += 4) {
						add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_HEIGHT + BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_HEIGHT * 2 + BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						blocksCount += 3;
					}
				}
				else {
					for(int j = 2; j < BRICKS_PER_ROW; j += 4) {
						add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_HEIGHT + BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET / 2 + i * BRICK_WIDTH, BRICK_HEIGHT * 2 + BRICK_Y_OFFSET + j * BRICK_HEIGHT);
						blocksCount += 3;
					}
				}
			}
			break;
		case 3:
			for(int j = 0; j < BRICKS_PER_ROW + 2; j++) {
				add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), 0, BRICK_Y_OFFSET - BRICK_HEIGHT + j * BRICK_HEIGHT);
				add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + BRICK_ROWS * BRICK_WIDTH, BRICK_Y_OFFSET - BRICK_HEIGHT + j * BRICK_HEIGHT);
				blocksCount += 2;
			}
			for(int i = 0; i < BRICK_ROWS; i++) {
				add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET - BRICK_HEIGHT);
				blocksCount++;
				for(int j = 0; j < BRICKS_PER_ROW; j++) {
					add(new Block(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT);
					blocksCount++;
				}
				add(new ArmoredBlock(BRICK_WIDTH, BRICK_HEIGHT), BRICK_X_OFFSET + i * BRICK_WIDTH, BRICK_Y_OFFSET + BRICKS_PER_ROW * BRICK_HEIGHT);
				blocksCount++;
			}
		}		
	}
	
	private void nextLevel() {
		if(++currentLevel > LEVELS_NUMBER) {
			currentLevel = 1;
		}
	}
	
	/**
	 * Initializes health bar
	 */
	private void addHealthBar() {
		healthBar = new HealthBar(BEAD_RADIUS);
		healthBar.setLocation(BEAD_RADIUS * 2, WINDOW_HEIGHT - BEAD_RADIUS * 4);
		add(healthBar);
	}
	
	private void addBonus() {
		bonus = new Bonus(BEAD_RADIUS, paddle, bead, this);
	}
	
	private void processBonus() {
		if (!bonus.bonusActive && !bonus.bonusFalling) {
			bonus.addBonus();
		} else if (bonus.bonusFalling) {
			bonus.moveBonus();
			if (bonus.checkForCollisionsWithPaddle()) {
				initBonusCounter();
			}
			bonus.checkForOutOfBounds();
		} else if (bonus.bonusActive) {
			if (!countBlocksForBonus()) {
				bonus.desactivateBonus();
			}
		}
	}
	
	private void initBonusCounter() {
		blocksAtStartOfBonus = blocksCount;
	}
	
	private boolean countBlocksForBonus() {
		if (blocksAtStartOfBonus - BONUS_DURATION > blocksCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * Destoys block and checks for victory conditions
	 * 
	 * @param block - block to destroy
	 */
	private void destroyBlock(ArkanoidObject block) {
		remove(block);
		blocksCount--;
		
		if(blocksCount == 0) {
			playVictory();
			gameState = END_SCREEN;
			nextLevel();
			showEndScreen("Ви перемогли!");
		}
	}
	
	/**
	 * Shows window that has information about game and its authors
	 */
	private void showStartScreen() {
		startWindow = new StartWindow(WINDOW_WIDTH / 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 3);
		add(startWindow);
	}
	
	/**
	 * Shows window that displays message to the player
	 * 
	 * @param message - message to display
	 */
	private void showEndScreen(String message) {
		soundtrack.stop();
		endWindow = new EndWindow(WINDOW_WIDTH / 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2 - WINDOW_HEIGHT / 8, message);
		add(endWindow);
	}
	
	/**
	 * Removes bead from field and decreases life count. If no lives left, end the game
	 */
	public void processGameOver() {
		remove(bead);
		bead = null;
		if(healthBar.removeLife()) {
			playLose();
			gameState = END_SCREEN;
			showEndScreen("Ви програли!");
		} else {
			if (bonus.bonusBead != null)
				remove(bonus.bonusBead);
		}
	}
	
	private void startMusic() {
		soundtrack = new SoundClip("sounds/soundtrack.wav");
		soundtrack.setVolume(0.005);
		soundtrack.loop();
	}
	
	/**
	 * Plays victory sound
	 */
	private void playVictory() {
		SoundClip bounceSound = new SoundClip("sounds/victory.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
	/**
	 * Plays lose sound
	 */
	private void playLose() {
		SoundClip bounceSound = new SoundClip("sounds/lose.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
}
