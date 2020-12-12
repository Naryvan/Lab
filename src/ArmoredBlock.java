import java.awt.Color;
import acm.graphics.*;

public class ArmoredBlock extends Block {

	private boolean solid;
	
	public ArmoredBlock(int width, int height) {
		super(width, height, Color.LIGHT_GRAY);
		solid = true;
	}
	
	public boolean tryToDestroy() {
		if(!solid) {
			return true;
		}
		else {
			solid = false;
			add(new GLine(getWidth() / 10, getHeight() / 10, getWidth() - getWidth() / 10, getHeight() - getHeight() / 10));
			add(new GLine(getWidth() / 10, getHeight() - getHeight() / 10, getWidth() - getWidth() / 10, getHeight() / 10));
			//add(new GLine(10, 10, 150, 150));
			return false;
		}
	}
	
}
