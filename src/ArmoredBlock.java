/*
 * File: ArmoredBlock.java
 * 
 * Blocks that can be hit one additional time before disappearing
 */

import java.awt.Color;
import acm.graphics.*;

public class ArmoredBlock extends Block {

	/** true if hasn't been hit */
	private boolean solid;
	
	public ArmoredBlock(int width, int height) {
		super(width, height, Color.LIGHT_GRAY);
		solid = true;
	}
	
	/**
	 * Checks if block has been hit already
	 * If has been - return true
	 * If not - soild is set to false and visualt representaion of damage is shown
	 * 
	 * 
	 * @return true if can be destroyed, false if can't
	 */
	public boolean tryToDestroy() {
		if(!solid) {
			return true;
		}
		else {
			solid = false;
			add(new GLine(getWidth() / 10, getHeight() / 10, getWidth() - getWidth() / 10, getHeight() - getHeight() / 10));
			add(new GLine(getWidth() / 10, getHeight() - getHeight() / 10, getWidth() - getWidth() / 10, getHeight() / 10));
			return false;
		}
	}
	
}
