import java.awt.Color;
import java.util.Random;
import javax.xml.crypto.XMLCryptoContext;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acm.util.SoundClip;

public class Bead extends ArkanoidObject {
	
	private int currentAngle;
	public double speedModulus = 4;
	private final int COLLISIONS_DETECTION_PRECISION = 180; // represents the number of points which will be 
	// considered while detecting collisions with other objects. SHOULD BE A DIVISOR OF 360
	private Arkanoid window;
	
	private final int WORLD_WIDHT;
	private final int WORLD_HEIGHT;

	public Bead(int xCoord, int yCoord, int radius, Arkanoid window) {
		super(Arkanoid.BEAD);
		GOval bead = new GOval(radius * 2, radius * 2);
		this.window = window;
		this.WORLD_WIDHT = window.getWidth();
		this.WORLD_HEIGHT = window.getHeight();
		bead.setFilled(true);
		bead.setFillColor(new Color(26, 10, 51));
		add(bead);
		RandomGenerator gen = RandomGenerator.getInstance();
		currentAngle = (gen.nextInt(-135, -45));
		this.setLocation(xCoord, yCoord);
	}
	
	public void moveBead() {
		this.movePolar(speedModulus, currentAngle);
	}
	
	public boolean collidesWith(GObject anotherObject) {
		int xCenter = ((int)getX() + (int)(getWidth() / 2));
		int yCenter = ((int)getY() + (int)(getHeight() / 2));
		
		for (int i = 0; i < COLLISIONS_DETECTION_PRECISION; i++) {
			int currentAngleProcessed = (int)(360 / COLLISIONS_DETECTION_PRECISION * i);
			int currentXprocessed = (int)(Math.cos(Math.toRadians(currentAngleProcessed)) * getWidth() / 2);
			int currentYprocessed = -(int)(Math.sin(Math.toRadians(currentAngleProcessed)) * getHeight() / 2);
			// minus here because of Y-inverted java coordinates system
			if (window.getElementAt(xCenter + currentXprocessed, yCenter + currentYprocessed) == anotherObject)
				return true;
		}
		return false;
	}
	
	public ArkanoidObject collidesWith() {
		int xCenter = ((int)getX() + (int)(getWidth() / 2));
		int yCenter = ((int)getY() + (int)(getHeight() / 2));
		
		for (int i = 0; i < COLLISIONS_DETECTION_PRECISION; i++) {
			int currentAngleProcessed = (int)(360 / COLLISIONS_DETECTION_PRECISION * i);

			int currentXprocessed = (int)(Math.cos(Math.toRadians(currentAngleProcessed)) * getWidth() / 2);
			int currentYprocessed = -(int)(Math.sin(Math.toRadians(currentAngleProcessed)) * getHeight() / 2);
			// minus here because of Y-inverted java coordinates system
			GObject objectCollided = window.getElementAt(xCenter + currentXprocessed + 1,
					yCenter + currentYprocessed + 1);
			if (objectCollided != null)
				return (ArkanoidObject)objectCollided;
		}
		
		return null;
	}
	
	
	public boolean bounceFromRectangle(GRect rect) {
		playBounceFromBlock();
		
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
			if (this.contains(xCoord, y) || this.contains(xCoord + (int)rect.getWidth() - 1, y)) {
				collidesWidthVerticalBound = true;
			}
		}
		
		
		if (collidesWidthHorizontalBound && collidesWidthVerticalBound) {
			currentAngle = currentAngle > 0 ? currentAngle - 180 : currentAngle + 180;
		} else if (collidesWidthVerticalBound) {
			currentAngle = currentAngle > 0 ? 180 - currentAngle : -180 - currentAngle;
		} else if (collidesWidthHorizontalBound) {
			currentAngle = -currentAngle;
			return true;
		}  
		return false;
	}
	
	public boolean bounceIfCollidesWithWorldBounds() {
		if (getX() + getWidth() >= WORLD_WIDHT || getX() <= 0) {
			currentAngle = currentAngle > 0 ? 180 - currentAngle : -180 - currentAngle;
			BounceFromWall();
			return true;
		}
		if (getY() <= 0){
			currentAngle = -currentAngle;
			BounceFromWall();
			return true;
		}
		if (getY() + getHeight() > WORLD_HEIGHT) {
			//currentAngle = RandomGenerator.getInstance().nextInt(20, 160);
			playFallOutOfBounds();
			window.processGameOver();
			return true;
		}
		return false;
	}
	
	public boolean bounceFromPaddleIfCollides(Paddle paddle) {
		int width = (int)paddle.getWidth();
		if (this.collidesWith(paddle)) {
			playBounceFromPaddle();
			for (int i = 1; i <= 6; i++) {
				for (int j = ((int)(width / 6)) * (i - 1); j < ((int)(width / 6)) * i; j++) {
					if (this.contains(paddle.getX() + j, paddle.getY()) || 
							this.contains(paddle.getX() + j, (int)(paddle.getY() + paddle.getHeight() / 2))
							|| this.contains(paddle.getX() + j, (int)(paddle.getY() + paddle.getHeight()))) {
						currentAngle = 160 - (20 * i);
						return true;
					}
				}
			}
		}
		return false;
	}

	private void playBounceFromBlock() {
		SoundClip bounceSound = new SoundClip("sounds/bounce_from_block.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
	private void playBounceFromPaddle() {
		SoundClip bounceSound = new SoundClip("sounds/bounce_from_paddle.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
	private void BounceFromWall() {
		SoundClip bounceSound = new SoundClip("sounds/bounce_from_wall.wav");
		bounceSound.setVolume(0.01);
		bounceSound.play();
	}
	
	private void playFallOutOfBounds() {
		SoundClip bounceSound = new SoundClip("sounds/fall_out_of_bounds.wav");
		bounceSound.setVolume(0.1);
		bounceSound.play();
	}
	
}