/*
 * File: HealthBar
 * 
 * Shows how much additional balls player has 
 */

import java.awt.Color;

import acm.graphics.*;

public class HealthBar extends ArkanoidObject {
	
	/** Visual representations of lives */
	GOval firstLife;
	GOval secondLife;
	GOval thirdLife;
	GOval fourthLife;
	
	/** Nummber of additional balls player has */
	int livesLeft;
	
	public HealthBar(int radius) {
		super(Arkanoid.MISC);
		firstLife = new GOval(0, 0, radius * 2, radius * 2);
		firstLife.setFilled(true);
		firstLife.setFillColor(new Color(26, 10, 51));
		
		secondLife = new GOval(radius * 4, 0, radius * 2, radius * 2);
		secondLife.setFilled(true);
		secondLife.setFillColor(new Color(26, 10, 51));
		
		thirdLife = new GOval(radius * 8, 0, radius * 2, radius * 2);
		thirdLife.setFilled(true);
		thirdLife.setFillColor(new Color(26, 10, 51));
		
		fourthLife = new GOval(radius * 12, 0, radius * 2, radius * 2);
		fourthLife.setFilled(true);
		fourthLife.setFillColor(new Color(26, 10, 51));
		
		add(firstLife);
		add(secondLife);
		add(thirdLife);
		
		livesLeft = 3;
	}
	
	/**
	 * Decreases aditional balls by one
	 * If no balls left - returns true
	 * 
	 * @return true if no balls left, else false
	 */
	public boolean removeLife() {
		switch(livesLeft) {
		case 3:
			livesLeft--;
			remove(thirdLife);
			return false;
		case 2:
			livesLeft--;
			remove(secondLife);
			return false;
		case 1:
			livesLeft--;
			remove(firstLife);
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * Adds aditional ball, max - 4
	 */
	public void addLife() {
		switch(livesLeft) {
		case 3:
			livesLeft++;
			add(fourthLife);
			return;
		case 2:
			livesLeft++;
			add(thirdLife);
			return;
		case 1:
			livesLeft++;
			add(secondLife);
			return;
		case 0:
			livesLeft++;
			add(firstLife);
		default:
			return;
		}
	}
	
}
