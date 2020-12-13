import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acm.util.SoundClip;

public class Bonus extends ArkanoidObject {

	final double BONUS_PROBABILITY = 0.0001;
	final int BONUS_FALLING_SPEED = 3;
	private int radius;
	public boolean bonusActive = false;
	public boolean bonusFalling = false;
	public GOval bonusBead;
	private Bead bead;
	private Paddle paddle;
	private BonusType bonusType;
	private Arkanoid window;
	
	public Bonus(int radius, Paddle paddle, Bead bead, Arkanoid window) {
		super(Arkanoid.BONUS);
		this.radius = radius;
		this.paddle = paddle;
		this.bead = bead;
		this.window = window;
	}

	public void addBonus() {
		RandomGenerator gen = RandomGenerator.getInstance();
		boolean isTrue = gen.nextBoolean(BONUS_PROBABILITY);
		if (isTrue) {
			bonusBead = new GOval(gen.nextInt(20, Arkanoid.WINDOW_WIDTH - 20), 20, radius * 2, radius * 2);
			bonusBead.setFilled(true);
			bonusBead.setFillColor(RandomGenerator.getInstance().nextColor());
			window.add(bonusBead);
			bonusFalling = true;
			bonusType = chooseBonusType();
		}
	}
	
	public void moveBonus() {
		bonusBead.move(0, BONUS_FALLING_SPEED);
	}
	
	public boolean checkForCollisionsWithPaddle() {
		if (this.collidesWith() == paddle) {
			playBonusApplySound();
			window.remove(bonusBead);
			bonusFalling = false;
			activateBonus();
			return true;
		}
		return false;
	}
	
	private void activateBonus() {
		bonusActive = true;
		switch (bonusType) {
		case BEAD_ACCELERATE:
			bead.speedModulus += 2;
			break;
		case BEAD_DECELERATE:
			bead.speedModulus -= 2;
		default:
			break;
		}
	}
	
	public void desactivateBonus() {
		bonusActive = false;
		switch (bonusType) {
		case BONUS_LIFE:
			window.healthBar.addBonusLife(radius);
			break;
		case BEAD_ACCELERATE:
			bead.speedModulus--;
			break;
		case BEAD_DECELERATE:
			bead.speedModulus++;
		default:
			break;
		}
	}
	
	public GObject collidesWith() {
		int xCenter = ((int)bonusBead.getX() + (int)(bonusBead.getWidth() / 2));
		int yCenter = ((int)bonusBead.getY() + (int)(bonusBead.getHeight() / 2));
		
		for (int i = 0; i < 36; i++) {
			int currentAngleProcessed = (int)(10 * i);

			int currentXprocessed = (int)(Math.cos(Math.toRadians(currentAngleProcessed)) * bonusBead.getWidth() / 2);
			int currentYprocessed = -(int)(Math.sin(Math.toRadians(currentAngleProcessed)) * bonusBead.getHeight() / 2);
			// minus here because of Y-inverted java coordinates system
			GObject objectCollided = window.getElementAt(xCenter + currentXprocessed + 1,
					yCenter + currentYprocessed + 1);
			if (objectCollided != null)
				return objectCollided;
		}
		
		return null;
	}
	
	private void playBonusApplySound() {
		SoundClip bounceSound = new SoundClip("sounds/bonus.wav");
		bounceSound.setVolume(0.01);
		bounceSound.play();
	}
	
	public void checkForOutOfBounds() {
		if (bonusBead.getY() > window.getHeight()) {
			bonusBead = null;
			bonusActive = false;
			bonusFalling = false;
		}
	}
	
	private BonusType chooseBonusType() {
		RandomGenerator gen = RandomGenerator.getInstance();
		int value = gen.nextInt(0, 10);
		if (value < 5)
			return BonusType.BONUS_LIFE;
		else if (value < 9)
			return BonusType.BEAD_DECELERATE;
		else 
			return BonusType.BEAD_ACCELERATE;
	}
		
}
