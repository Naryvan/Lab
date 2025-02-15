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
	GOval bonusLife;
	
	/** Number of additional balls player has */
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
		
		add(firstLife);
		add(secondLife);
		add(thirdLife);
		
		livesLeft = 3;
	}
	
	/**
	 * Decreases additional balls by one
	 * If no balls left - returns true
	 * 
	 * @return true if no balls left, else false
	 */
	public boolean removeLife() {
		if (bonusLife != null ) {
			removeBonusLife();
			if (livesLeft == 0)
				return true;
			else return false;
		} else {
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
	}
		
	public void addBonusLife(int radius) {
		if (bonusLife == null) {
			bonusLife = new GOval(radius * 12, 0, radius * 2, radius * 2);
			bonusLife.setFilled(true);
			bonusLife.setFillColor(new Color(123, 0, 255));
			add(bonusLife);
			livesLeft++;
		}
	}
	
	public void removeBonusLife() {
		remove(bonusLife);
		bonusLife = null;
		livesLeft--;
	}
	
}
