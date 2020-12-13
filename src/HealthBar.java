import java.awt.Color;

import acm.graphics.*;

public class HealthBar extends ArkanoidObject {
	
	GOval firstLife;
	GOval secondLife;
	GOval thirdLife;
	GOval fourthLife;
	
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
		add(fourthLife);
		
		livesLeft = 3;
	}
	
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
