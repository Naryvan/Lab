import java.awt.Color;
import acm.graphics.*;

public class Block extends ArkanoidObject {
	
	private static int colorIndex = 1;
	
	private GRect block;
	
	public Block(int width, int height) {
		super(Arkanoid.BLOCK);
		block = new GRect(width, height);
		block.setFilled(true);
		block.setFillColor(getNextColor());
		add(block);
	}
	
	public Block(int width, int height, Color color) {
		super(Arkanoid.BLOCK);
		block = new GRect(width, height);
		block.setFilled(true);
		block.setFillColor(color);
		add(block);
	}
	
	private Color getNextColor() {
		Color color;
		
		switch(colorIndex) {
		case 1:
			color = Color.RED;
			break;
		case 2:
			color = Color.GREEN;
			break;
		case 3: 
			color = Color.BLUE;
			break;
		case 4:
			color = Color.ORANGE;
			break;
		default: 
			color = Color.RED;
			break;
		}
		
		colorIndex++;
		if(colorIndex > 4) {
			colorIndex = 1;
		}
		
		return color;
	}
	
	public GRect getRect() {
		return block;
	}
	
	
	
}
