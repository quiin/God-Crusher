package mx.itesm.gcrusher.Items;

public class InfiniteAmmo extends PowerUp {
		
	public Weapon gun;
	public final int INFINITE = 999999999;
	
	public InfiniteAmmo(Weapon toApply){
		super();
		this.gun=toApply;
		this.gun.bullets=INFINITE;		
	}
	
	public Weapon getGun(){
		return this.gun;
	}
	
	public void setWeapon (Weapon w){
		this.gun=w;
	}
	
	
	
	

}
