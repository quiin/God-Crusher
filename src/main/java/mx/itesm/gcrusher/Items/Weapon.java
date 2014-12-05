package mx.itesm.gcrusher.Items;



public  class Weapon extends Item {
		
	protected int bullets;	
	protected int type;
	protected double damage;
	protected int infiniteTime;
	public static int baseAmmo;
	
	public Weapon(){
		super();
	}
			
	
	public int getBullets(){
		return this.bullets;
	}
	
	public void setBullets(int bullets){
		this.bullets=bullets;
	}
	
	public int getType(){
		return this.type;
	}
	
	public double getDamage(){
		return this.damage;		
	}
	
	public void setDamage(double damage){
		this.damage = damage;
	}
	
	public String toString(){
		switch (this.type) {
		case 0:
			return "handgun";
		case 2:
			return "rifle";
		case 1: 
			return "shotgun";
		case 3: 
			return "basooka";
			
		default:
			return "wtf";
		}
	}
	
	public int getInfiniteTime(){
		return this.infiniteTime;
	}
		
}
