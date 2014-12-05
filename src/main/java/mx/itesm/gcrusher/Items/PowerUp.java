package mx.itesm.gcrusher.Items;



public class PowerUp extends Item {
	
	
	protected boolean active;	
	
	public PowerUp(){
		super();
		this.active = false;		
	}
	
	public PowerUp(int type,String spriteName){
		super();
		this.active=false;
		this.type=type;
		setSprite(spriteName);
	}
	
	public void setactive(boolean active){
		this.active = active;
	}
	
	public boolean isactive(){
		return this.active;
	}
	
	
}
