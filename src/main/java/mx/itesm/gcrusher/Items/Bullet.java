package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import mx.itesm.gcrusher.Characters.Boss;

public class Bullet {

	private int type;
	private boolean isOnStage = false;
	private CCSprite sprite;
	private Boss boss;
	
	public Bullet(int type, Boss boss){
		this.type = type;
		
		if (type == 1 || type == 4){
			// Handgun bullet
			this.setSprite("bullet_hg_boss.png");
		} else if (type == 2){
			// Boss bullet
			this.setBoss(boss);
			this.setSprite("2");
		} else if (type == 3){
			// Air Missile bullet
			this.setSprite("bullet_bazooka_boss.png");
		}
	}
	
	public void setSprite(String image) {
		if (image.equals("2")){
			CGSize size = CCDirector.sharedDirector().displaySize();
			if (boss.type == 1){
				this.sprite = CCSprite.sprite("boss_mexico.png");	
			} else if (boss.type == 2){
				this.sprite = CCSprite.sprite("boss_egypt.png");
			} else if (boss.type == 3){
				this.sprite = CCSprite.sprite("boss_rlyeh.png");
			}
			float fx = size.width/800;
			float fy = size.height/480;
			this.sprite.setScaleX(fx);
			this.sprite.setScaleY(fy);	
		} else {
			CGSize size = CCDirector.sharedDirector().displaySize();
			float fx = size.width/800;
			float fy = size.height/480;
//			float bf = 3.0f;
			this.sprite = CCSprite.sprite(image);
			this.sprite.setScaleX(fx);
			this.sprite.setScaleY(fy);	
		}
	}
	
	public CCNode getSprite(){
		return this.sprite;
	}
	
	public void setOnStage(boolean stage){
		this.isOnStage = stage;
	}
	
	public boolean isOnStage(){
		return this.isOnStage;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

}
