/*
 * File: Paddle.java
 * 
 * Paddle that player controls
 */

import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;

public class Paddle extends ArkanoidObject {

	/** Visual parts of the paddle */
	private GRect rectangle;
	private GOval leftOval;
	private GOval rightOval;
	
<<<<<<< HEAD
	private final int INITIAL_WIDTH;
=======
	/** Paddle dimensions */
>>>>>>> 102beb729eda11efe2d1b793ade81997e2827501
	private int width;
	private int height;
	
	public Paddle(int width, int height) {
		super(Arkanoid.PADDLE);
		this.width = width;
		INITIAL_WIDTH = width;
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
	
	/**
	 * Moves paddle to the location of mouse,
	 * stops if out of bounds
	 * 
	 * @param e - mouse
	 */
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
	
	public void extendPaddle() {
		remove(leftOval);
		remove(rectangle);
		remove(rightOval);
		width = (int)(width * 1.4);
		
		rectangle = new GRect(width * 0.1, 0, width * 1.2, height);
		rectangle.setFilled(true);
		rectangle.setFillColor(Color.GRAY);
		
		leftOval = new GOval(0, 0, width * 0.2, height);
		leftOval.setFilled(true);
		leftOval.setFillColor(Color.RED);
		
		rightOval = new GOval(width * 1.2, 0, width * 0.2, height);
		rightOval.setFilled(true);
		rightOval.setFillColor(Color.RED);
		
		add(leftOval);
		add(rightOval);
		add(rectangle);
	}
	
	public void normalPaddle() {
		remove(leftOval);
		remove(rectangle);
		remove(rightOval);
		width = INITIAL_WIDTH;
		
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
	
	public void shortenPaddle() {
		remove(leftOval);
		remove(rectangle);
		remove(rightOval);
		width = (int)(width * 0.8);
		
		rectangle = new GRect(width * 0.1, 0, width * 0.6, height);
		rectangle.setFilled(true);
		rectangle.setFillColor(Color.GRAY);
		
		leftOval = new GOval(0, 0, width * 0.2, height);
		leftOval.setFilled(true);
		leftOval.setFillColor(Color.RED);
		
		rightOval = new GOval(width * 0.6, 0, width * 0.2, height);
		rightOval.setFilled(true);
		rightOval.setFillColor(Color.RED);
		
		add(leftOval);
		add(rightOval);
		add(rectangle);
	}
	
}
