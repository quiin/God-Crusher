package mx.itesm.gcrusher.Items;

public class Scoremultiplier extends PowerUp {
		
	
	public double multiplier;

	public Scoremultiplier(double multiplier){
		super();
		if (multiplier > 0) this.multiplier = 2;
	}
		
	
	
	
	public void setMultipliyer(double multiplier){
		this.multiplier = multiplier;
	}
	
	public double getmultiplier(){
		return this.multiplier;
	}

}
