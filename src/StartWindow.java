import java.awt.Color;
import acm.graphics.*;

public class StartWindow extends GCompound {

	public StartWindow(int width, int xPos, int yPos) {
		int height = width;
		
		GRoundRect rect = new GRoundRect(0, 0, width, height, 10);
		rect.setFilled(true);
		rect.setFillColor(Color.WHITE);
		add(rect);
		
		GLabel gameLabel = new GLabel("Arkanoid");
		gameLabel.setFont("Arial-" + width / 6);
		gameLabel.setLocation((width - gameLabel.getWidth()) / 2, gameLabel.getHeight() + height / 10);
		gameLabel.setColor(Color.RED);
		add(gameLabel);
		
		GLine separator = new GLine(0, height / 2, width, height / 2);
		add(separator);
		
		GLabel authorsLabel = new GLabel("Автори:");
		authorsLabel.setFont("Arial-" + width / 12);
		authorsLabel.setLocation((width - authorsLabel.getWidth()) / 2, authorsLabel.getHeight() + height / 2);
		add(authorsLabel);
		
		GLabel author1 = new GLabel("Козак Данило");
		author1.setFont("Arial-" + width / 12);
		author1.setLocation((width - author1.getWidth()) / 2, author1.getHeight() + height / 20 * 13);
		add(author1);
		
		GLabel author2 = new GLabel("Стасюк Ілля");
		author2.setFont("Arial-" + width / 12);
		author2.setLocation((width - author2.getWidth()) / 2, author2.getHeight() + height / 20 * 16);
		add(author2);
		
		this.setLocation(xPos, yPos);
	}
	
}
