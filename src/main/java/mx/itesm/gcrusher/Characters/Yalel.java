package mx.itesm.gcrusher.Characters;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import android.util.Log;

import mx.itesm.gcrusher.Items.Handgun;
import mx.itesm.gcrusher.Items.Weapon;

public class Yalel extends Character{

	private ArrayList<Weapon> guns ; /*avaible guns*/
	private Weapon gun;
	private int score;
	private int tokens;
	private CGSize size = CCDirector.sharedDirector().displaySize();
	private float fx = size.width*.6f/800;
	private float fy = size.height*.6f/480;


	/* GUNS INDEX
	 * index=0: handgun
	 * index=1: rifle
	 * index=2: shotgun
	 * index=3: basooka*/

	public Yalel(){		
		super();
		this.score=0;
		this.setSprite("animation_1_hg.png");		


		this.guns = new ArrayList<Weapon>(4);			

		Handgun hg = new Handgun();
		hg.setPicked(true);
		guns.add(hg);
		for (int i=1;i<4;i++){
			Handgun hag = new Handgun();
			hag.setPicked(false);
			guns.add(hag);
		}

		/*MUST REMOVE THIS*/
//		Shotgun shotgun = new Shotgun();
//		this.addWeapon(shotgun);	
//		Rifle rifle = new Rifle();
//		this.addWeapon(rifle);				
//		Bazooka bazooka = new Bazooka();
//		this.addWeapon(bazooka);
		/*********************/



		this.setGun(guns.get(0)); /*deault gun: handgun*/
		this.lifes= 3;		

	}

	public Yalel(Yalel yalel) {		
		this.alive=yalel.isAlive();
		this.gun=yalel.getGun();
		this.guns=yalel.getWeapons();
		this.lifes=yalel.getLifes();
		this.score=yalel.getScore();
		this.tokens=yalel.getTokens();		
		this.sprite=yalel.getSprite();

	}

	public void setSprite(String string) {
		this.sprite = CCSprite.sprite(string);
		this.sprite.setScaleX(fx);
		this.sprite.setScaleY(fy);
		this.sprite.setPosition(size.width/6f,size.height*2/7);

	}

	public int getScore(){
		return this.score;
	}

	public void setScore(int score){
		this.score=score;
	}

	public void addScore(int score){
		this.score+=score;
	}

	public void addWeapon(Weapon gun){ 
		int type = gun.getType();
		if (guns.get(type) instanceof Handgun || type ==guns.get(type).getType()){
			guns.remove(type);
			this.guns.add(type, gun);
		}else{
			this.guns.add(type, gun);
		}


		gun.setPicked(true);


	}

	public void removeWeapon(Weapon gun){
		this.guns.set(this.guns.indexOf(gun),new Handgun());
	}

	public void shoot(Weapon w){/*decrease weapon ammo*/
		int type = w.getType();
		switch (type) {		
		case 3:
			w.setBullets(w.getBullets()-1);/*if w = basooka*/
			break;
		case 2:
			w.setBullets(w.getBullets()-1);/*if w = rifle*/
			break;
		case 1:
			w.setBullets(w.getBullets()-1);/*if w = shotgun*/
			break;
		default: /*ERROR*/
			Log.w("INDEX OUT OF BOUNDS", "WEAPON TYPE = "+type);
			break;
		}
	}

	public void changeWeapon(int index){		
		switch (index) {	
		case 0:
			if (guns.get(0).isPicked()){
				this.setSprite(CCSprite.sprite("animation_1_hg.png"));
				this.sprite.setScaleX(fx);
				this.sprite.setScaleY(fy);
				this.setGun(guns.get(index));
				Log.d("YALEL", "CHANGED WEAPON TO: "+index);
			}
			break;
		case 2:
			if (guns.get(2).isPicked()){
				this.setSprite(CCSprite.sprite("animation_1_rifle.png"));
				this.sprite.setScaleX(fx);
				this.sprite.setScaleY(fy);
				this.setGun(guns.get(index));
				Log.d("YALEL", "CHANGED WEAPON TO: "+index);
			}
			break;
		case 1:
			if (guns.get(1).isPicked()){
				this.setSprite(CCSprite.sprite("animation_1_sg.png"));
				this.sprite.setScaleX(fx);
				this.sprite.setScaleY(fy);
				this.setGun(guns.get(index));
				Log.d("YALEL", "CHANGED WEAPON TO: "+index);
			}
			break;
		case 3:
			if (guns.get(3).isPicked()){
				this.setSprite(CCSprite.sprite("animation_1_bazooka.png"));
				this.sprite.setScaleX(fx);
				this.sprite.setScaleY(fy);
				this.setGun(guns.get(index));
				Log.d("YALEL", "CHANGED WEAPON TO: "+index);
			}
			break;

		}
	}

	public Weapon getGun() {
		return gun;
	}

	public ArrayList<Weapon> getWeapons(){
		return this.guns;
	}

	public void setGun(Weapon gun) {
		this.gun = gun;
	}

	public int getTokens() {
		return tokens;
	}

	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	public void resetTokens(){
		this.tokens=0;
	}

	public void resetLifes() {
		this.lifes=3;

	}

	public void setWeapons(ArrayList<Weapon> ws){
		this.guns=ws;
	}

	public String toString(){
		return "Score: "+score+" Gun: "+getGun()+" Weapons: "+ getWeapons()+" Lifes: "+lifes+" Tokens: "+tokens;

	}
}
