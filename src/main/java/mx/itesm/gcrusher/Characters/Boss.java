package mx.itesm.gcrusher.Characters;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import java.lang.*;

public class Boss extends Character {
	
	private int attackPattern;
	private int level;
	private boolean isOnStage;
	private boolean isAttacking;
	public int type;
	
	public Boss(){
		super();
	}
	
	/* 1 = Micantlecutli, 2 = Anubis, 3 = Cthulhu */
	public Boss(int type, double lifes){
		this.lifes=lifes;
		// constructor for each type of enemy, capable of handling n number of different enemies
		this.type = type;
		if (type == 1){
			this.setLevel(type-1);
			this.setSprite("characters/boss_mexico.png");
			this.setAttackPattern(1);
		} else 
			if (type == 2) {
			this.setLevel(type-1);
			this.setSprite("characters/boss_egypt.png"); // to change
			this.setAttackPattern(2);
		} else if (type == 3) {
			this.setLevel(type-1);
			this.setSprite("characters/boss_rlyeh.png"); // to change
			this.setAttackPattern(3);
		} 
		else {
			this.setSprite("warning_others/target.png"); // DO NOT change
//			this.setAttackPattern(0);
		}
	}
	
	private void setSprite(String image) {
		CGSize size = CCDirector.sharedDirector().displaySize();
		float fx = size.width/800;
		float fy = size.height/480;
//		float bf = 3.0f;
		this.sprite = CCSprite.sprite(image);
		this.sprite.setScaleX(fx);
		this.sprite.setScaleY(fy);
	}
	
	public void setAttackPattern(int i){
		this.attackPattern = i;
	}
	
	public int getAttackPattern(){
		return this.attackPattern;
	}

	public boolean isOnStage() {
		return isOnStage;
	}

	public void setOnStage(boolean isOnStage) {
		this.isOnStage = isOnStage;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
		
}
