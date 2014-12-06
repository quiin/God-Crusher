package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCSprite;

public class Rifle extends Weapon {

		
	
	public Rifle(){
		super();
		this.sprite= CCSprite.sprite("items/weapons/weapon_rifle.png");
		this.bullets = 40;
		this.type = 2;
		this.infiniteTime=15;
		this.baseAmmo=10;
		setDamage(2);
	}
	
	
	
	
	
	


}
