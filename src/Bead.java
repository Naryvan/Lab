import java.awt.Color;
import java.util.Random;
import javax.xml.crypto.XMLCryptoContext;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Bead extends GOval {
	
	private int currentAngle;
	private int deltaX; // speed of the bead relatively to the X axis (left to right)
	private int deltaY; // speed of the bead relatively to the Y axis (up to down)
	private final int COLLISIONS_DETECTION_PRECISION = 20; // represents the number of points which will be 
	// considered while detecting collisions with other objects. SHOULD BE A DIVISOR OF 360
	private boolean didLoose = false;
	private GraphicsProgram window;
	
	private final int WORLD_WIDHT;
	private final int WORLD_HEIGHT;
	
	private final static int DELTA_X_LOWER_BOUND = 2;
	private final static int DELTA_X_UPPER_BOUND = 5;
	private final static int DELTA_Y_LOWER_BOUND = 2;
	private final static int DELTA_Y_UPPER_BOUND = 5;

	public Bead(int xCoord, int yCoord, int radius, GraphicsProgram window) {
		super(xCoord, yCoord, radius * 2, radius * 2);
		this.window = window;
		this.WORLD_WIDHT = window.getWidth();
		this.WORLD_HEIGHT = window.getHeight();
		this.setFilled(true);
		this.setFillColor(new Color(26, 10, 51));
		RandomGenerator gen = RandomGenerator.getInstance();
		deltaX = gen.nextInt(DELTA_X_LOWER_BOUND, DELTA_X_UPPER_BOUND) * (gen.nextBoolean() ? (-1) : 1);
		// deltaX might be negative, but its absolute value remains in range of defined constants
		deltaY = gen.nextInt(DELTA_Y_LOWER_BOUND, DELTA_Y_UPPER_BOUND);
	}
	
	public void moveBead() {
		this.move(deltaX, deltaY);
	}
	
	public boolean collidesWith(ArkanoidObject anotherObject) {
		int xCenter = ((int)getX() + (int)(getWidth() / 2));
		int yCenter = ((int)getY() + (int)(getHeight() / 2));
		
		for (int i = 0; i < COLLISIONS_DETECTION_PRECISION; i++) {
			int currentAngleProcessed = (int)(360 / COLLISIONS_DETECTION_PRECISION * i);
			int currentXprocessed = (int)(GMath.cosDegrees(currentAngleProcessed) * getWidth() / 2);
			int currentYprocessed = -(int)(GMath.sinDegrees(currentAngleProcessed) * getHeight() / 2);
			// minus here because of Y-inverted java coordinates system
			if (anotherObject.contains(xCenter + currentXprocessed, yCenter + currentYprocessed))
				return true;
		}
		return false;
	}
	
	public ArkanoidObject collidesWith() {
		int xCenter = ((int)getX() + (int)(getWidth() / 2));
		int yCenter = ((int)getY() + (int)(getHeight() / 2));
		
		for (int i = 0; i < COLLISIONS_DETECTION_PRECISION; i++) {
			int currentAngleProcessed = (int)(360 / COLLISIONS_DETECTION_PRECISION * i);
			int currentXprocessed = (int)(GMath.cosDegrees(currentAngleProcessed) * getWidth() / 2);
			int currentYprocessed = -(int)(GMath.sinDegrees(currentAngleProcessed) * getHeight() / 2);
			// minus here because of Y-inverted java coordinates system
			GObject objectCollided = window.getElementAt(xCenter + currentXprocessed + 1,
					yCenter + currentYprocessed + 1);
			if (objectCollided != null)
				return (ArkanoidObject)objectCollided;
		}
		
		return null;
	}
	
	
	public void bounceFromRectangle(GRect rect) {
		boolean collidesWidthHorizontalBound = false;
		boolean collidesWidthVerticalBound = false;
		
		int xCoord = (int)rect.getX();
		int yCoord = (int)rect.getY();
		for (int x = xCoord; x <= xCoord + (int)rect.getWidth(); x++) {
			if (this.contains(x, yCoord) || this.contains(x, yCoord + (int)rect.getHeight())) {
				collidesWidthHorizontalBound = true;
			}
		}
		for (int y = yCoord; y <= yCoord + (int)rect.getHeight(); y++) {
			if (this.contains(xCoord, y) || this.contains(xCoord + (int)rect.getWidth(), y)) {
				collidesWidthVerticalBound = true;
			}
		}
		if (collidesWidthHorizontalBound && collidesWidthVerticalBound) {
			int temp = deltaX;
			deltaX = -deltaY;
			deltaY = -temp;
		} else if (collidesWidthHorizontalBound) {
			deltaY = -deltaY;
		} else if (collidesWidthVerticalBound) {
			deltaX = -deltaX;
		}
	}
	
	public void bounceIfCollidesWithWorldBounds() {
		if (getX() + getWidth() > WORLD_WIDHT || getX() < 0) {
			deltaX = -deltaX;
		}
		if (getY() < 0){
			deltaY = -deltaY;
		}
		if (getY() + getHeight() > WORLD_HEIGHT) {
			didLoose = true;
		}
	}
	
	public void bounceFromPaddleIfCollides(Paddle paddle) {
		if (this.collidesWith(paddle)) {
			deltaY = - deltaY;
		}
	}
}