/*
 * File: ArkanoidObject
 * 
 * Main class for all game objects
 */

import acm.graphics.*;

public class ArkanoidObject extends GCompound {
	
	private int type;
	
	public ArkanoidObject(int type) {
		this.type = type; 
	}
	
	public int getType() {
		return type;
	}
	
}
