package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCSprite;



public class Shotgun extends Weapon {
	
	
	
	public Shotgun(){
		super();
		this.sprite = CCSprite.sprite("items/weapons/weapon_shotgun.png");
		this.infiniteTime=20;
		this.bullets = 30;
		this.type = 1;
		setDamage(1);
		this.baseAmmo=5;
	}
	
	

	

}
