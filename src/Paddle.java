import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;

public class Paddle extends ArkanoidObject {

	private GRect rectangle;
	private GOval leftOval;
	private GOval rightOval;
	
	private int width;
	private int height;
	
	public Paddle(int width, int height) {
		super(Arkanoid.PADDLE);
		this.width = width;
		this.height = height;
		
		rectangle = new GRect(width * 0.1, 0, width * 0.8, height);
		rectangle.setFilled(true);
		rectangle.setFillColor(Color.GRAY);
		
		leftOval = new GOval(0, 0, width * 0.2, height);
		leftOval.setFilled(true);
		leftOval.setFillColor(Color.RED);
		
		rightOval = new GOval(width * 0.8, 0, width * 0.2, height);
		rightOval.setFilled(true);
		rightOval.setFillColor(Color.RED);
		
		add(leftOval);
		add(rightOval);
		add(rectangle);
	}
	
	public void movePaddle(MouseEvent e) {
		double xPos = e.getX() - width / 2;
		
		if(xPos < 0) {
			xPos = 0;
		}
		else if(xPos + width > Arkanoid.WINDOW_WIDTH) {
			xPos = Arkanoid.WINDOW_WIDTH - width;
		}
		
		this.setLocation(xPos, this.getY());
	}
	
}
