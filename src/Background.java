import acm.graphics.*;

public class Background extends ArkanoidObject {

	public Background() {
		super(Arkanoid.MISC);
		add(new GImage("Background.jpg"));
	}
	
}
