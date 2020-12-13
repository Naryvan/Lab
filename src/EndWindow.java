import java.awt.Color;
import acm.graphics.*;

public class EndWindow extends GCompound {

	public EndWindow(int width, int xPos, int yPos, String text) {
		int height = width / 3;
		
		GRoundRect rect = new GRoundRect(0, 0, width, height, 10);
		rect.setFilled(true);
		rect.setFillColor(Color.WHITE);
		add(rect);
		
		GLabel label = new GLabel(text);
		label.setFont("Arial-" + (int)(width * 10 / label.getWidth()));
		label.setLocation((width - label.getWidth()) / 2, height / 2 + label.getHeight() / 5 * 2);
		label.setColor(Color.RED);
		add(label);
		
		this.setLocation(xPos, yPos);
	}
	
}
