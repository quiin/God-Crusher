package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;


public class Obstacle{
	
	private CCSprite sprite;
	CGSize size = CCDirector.sharedDirector().displaySize();
	float fx = size.width/800;
	float fy = size.height/480;
	
	final double DAMAGE=0.5;
	
	public Obstacle(int level){ //NUNCA DEBE USARSE .-.
		switch (level) {
		case 1:
			this.setSprite(CCSprite.sprite("platform_mexico.png"));
			break;
		case 2:
			this.setSprite(CCSprite.sprite("platform_egypt.png"));
			break;
		case 3:
			this.setSprite(CCSprite.sprite("platform_rlyeh.png"));
			break;
		default:
			this.setSprite(CCSprite.sprite("platform_mexico.png"));
			break;
		}		
		this.sprite.setScaleX(fx);
		this.sprite.setScaleY(fy);
	}
	
	

	public CCSprite getSprite() {
		return sprite;
	}

	public void setSprite(CCSprite sprite) {
		this.sprite = sprite;
	}
	
	
}
