package mx.itesm.gcrusher.Characters;

import org.cocos2d.nodes.CCSprite;

public  class Character {
	
	protected double lifes;	
	protected boolean alive;
	protected CCSprite sprite;
	
	public Character(){		
		this.alive=true;
	}
	
	public double getLifes(){
		return this.lifes;	
	}
	
	public void setLifes(double lifes){
		this.lifes=lifes;
	}
	
	public void addLifes(double extraLifes){
		this.lifes += extraLifes;
	}
	
	public boolean isAlive(){		
		return this.alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public CCSprite getSprite(){
		return this.sprite;
	}
	public void setSprite(CCSprite sprite){
		this.sprite=sprite;
	}
	
}
