package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;



public class Item {
	protected boolean picked;
	protected CCSprite sprite;
	protected int type;
	protected float lifes;
	
	public Item(){
		this.picked=false; 	
		this.type=-1;		
		this.lifes=0;
	}
	
	public Item(String spriteName,int type,float lifes){
		this.picked=false;
		this.type=type;
		this.lifes=lifes;
		setSprite(spriteName);
	}
	
	
	
	public boolean isPicked(){
		return this.picked;
	}
	
	public void setPicked(boolean picked){
		this.picked = picked;
	}

    public void setSprite(CCSprite sprite) {
        this.sprite = sprite;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getLifes() {
        return lifes;
    }

    public void setLifes(float lifes) {
        this.lifes = lifes;
    }

    public CCSprite getSprite(){
		return this.sprite;
	}
	
	public void setSprite(String spriteName){
		this.sprite=CCSprite.sprite(spriteName);
		CGSize size = CCDirector.sharedDirector().displaySize();
		float fx = size.width / 800, fy = size.height / 480;
		this.getSprite().setScaleX(fx);
		this.getSprite().setScaleY(fy);
	}
	
	public int getType(){
		return this.type;
	}
	
	
}
