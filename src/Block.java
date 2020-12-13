/*
 * File: Block.java
 * 
 * Class of all blocks on field
 */

import java.awt.Color;
import acm.graphics.*;

public class Block extends ArkanoidObject {
	
	/** Index of current color */
	private static int colorIndex = 1;
	
	/** Visual part of the block */
	private GRoundRect block;
	
	/**
	 * Block, one of many
	 * 
	 * @param width - block width
	 * @param height - block height
	 */
	public Block(int width, int height) {
		super(Arkanoid.BLOCK);
		block = new GRoundRect(0, 0, width, height, 5);
		block.setFilled(true);
		block.setFillColor(getNextColor());
		add(block);
	}
	
	/**
	 * Block, one of many
	 * 
	 * @param width - block width
	 * @param height - block height
	 * @param color - block color
	 */
	public Block(int width, int height, Color color) {
		super(Arkanoid.BLOCK);
		block = new GRoundRect(width, height);
		block.setFilled(true);
		block.setFillColor(color);
		add(block);
	}
	
	/**
	 * Returns next avalilable color
	 * Increases colorIndex by 1
	 * 
	 * @return next color
	 */
	private Color getNextColor() {
		Color color;
		
		switch(colorIndex) {
		case 1:
		case 2:
			color = new Color(88, 61, 114);
			break;
		case 3:
		case 4:
			color = new Color(159, 95, 128);
			break;
		case 5:
		case 6:
			color = new Color(255, 186, 147);
			break;
		case 7:
		case 8:
			color = new Color(255, 142, 113);
			break;
		default: 
			color = new Color(88, 61, 114);
			break;
		}
		
		colorIndex++;
		if(colorIndex > 8) {
			colorIndex = 1;
		}
		
		return color;
	}
	
	/**
	 * Returns rectangle that has same dimensions as block
	 * 
	 * @return - rectangle
	 */
	public GRect getRect() {
		GRect rect = new GRect(getX(), getY(), getWidth(), getHeight());
		rect.setFilled(true);
		return rect;
	}	
	
	/**
	 * Checks if block can be destroyed
	 * 
	 * @return true if can be destroyed, false if can't
	 */
	public boolean tryToDestroy() {
		return true;
	}
	
	public static void resetColor() {
		colorIndex = 1;
	}
	
}
