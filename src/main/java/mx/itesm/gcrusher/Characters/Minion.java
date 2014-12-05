package mx.itesm.gcrusher.Characters;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public  class Minion extends Character {
	
	protected double damage;
	
	public Minion(int level){
		super();
		setLifes(1.5);
		setSprite(level);
	}

	public void resetLifes() {
		setLifes(1.5);
		
	}
	
	public void setSprite(int level){
		
		if (level==1)
			this.sprite = CCSprite.sprite("snake_1.png");
		else
			if (level==2)
				this.sprite = CCSprite.sprite("medusa_1.png");
			else
				if (level==3)
					this.sprite = CCSprite.sprite("mummy_1.png");
		
		CGSize size = CCDirector.sharedDirector().displaySize();
		float fx = size.width/800;
		float fy = size.height/480;		
		this.sprite.setScaleX(fx);
		this.sprite.setScaleY(fy);
		
	}
	
	
}
