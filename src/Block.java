import java.awt.Color;
import acm.graphics.*;

public class Block extends ArkanoidObject {
	
	private static int colorIndex = 1;
	
	private GRoundRect block;
	
	private int width;
	private int height;
	
	public Block(int width, int height) {
		super(Arkanoid.BLOCK);
		this.width = width;
		this.height = height;
		block = new GRoundRect(0, 0, width, height, 5);
		block.setFilled(true);
		block.setFillColor(getNextColor());
		add(block);
	}
	
	public Block(int width, int height, Color color) {
		super(Arkanoid.BLOCK);
		block = new GRoundRect(width, height);
		block.setFilled(true);
		block.setFillColor(color);
		add(block);
	}
	
	private Color getNextColor() {
		Color color;
		
		switch(colorIndex) {
		case 1:
			color = new Color(88, 61, 114);
			break;
		case 2:
			color = new Color(159, 95, 128);
			break;
		case 3: 
			color = new Color(255, 186, 147);
			break;
		case 4:
			color = new Color(255, 142, 113);
			break;
		default: 
			color = new Color(88, 61, 114);
			break;
		}
		
		colorIndex++;
		if(colorIndex > 4) {
			colorIndex = 1;
		}
		
		return color;
	}
	
	public GRect getRect() {
		GRect rect = new GRect(getX(), getY(), getWidth(), getHeight());
		rect.setFilled(true);
		return rect;
	}	
	
	public boolean tryToDestroy() {
		return true;
	}
	
}
