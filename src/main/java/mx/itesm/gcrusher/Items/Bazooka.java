package mx.itesm.gcrusher.Items;

import org.cocos2d.nodes.CCSprite;

public class Bazooka extends Weapon {
	
	
	

	public Bazooka() {
		super();
		this.sprite= CCSprite.sprite("weapon_bazooka.png");
		this.bullets = 5;
		this.type = 3;
		this.infiniteTime=4;
		this.baseAmmo=2;
		setDamage(20);
	}
	
	

}
