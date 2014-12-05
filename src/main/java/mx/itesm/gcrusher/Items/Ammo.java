package mx.itesm.gcrusher.Items;



public class Ammo extends Item{
	
	int bullets;
	
		
	public Ammo(int type,int i, String string) {
		super();
		this.bullets=i;
		setSprite(string);
		this.type=type;
		
	}
	
	public int getBullets(){
		return this.bullets;
	}
}
