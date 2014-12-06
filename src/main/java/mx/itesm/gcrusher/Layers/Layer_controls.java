package mx.itesm.gcrusher.Layers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Stack;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

import mx.itesm.gcrusher.Activities.LevelSelection;
import mx.itesm.gcrusher.Activities.SceneCreator;
import mx.itesm.gcrusher.Activities.Win;
import mx.itesm.gcrusher.Characters.Boss;
import mx.itesm.gcrusher.Characters.Minion;
import mx.itesm.gcrusher.Characters.Yalel;
import mx.itesm.gcrusher.Items.Ammo;
import mx.itesm.gcrusher.Items.Bazooka;
import mx.itesm.gcrusher.Items.Bullet;
import mx.itesm.gcrusher.Items.Item;
import mx.itesm.gcrusher.Items.Obstacle;
import mx.itesm.gcrusher.Items.PowerUp;
import mx.itesm.gcrusher.Items.Rifle;
import mx.itesm.gcrusher.Items.Shotgun;
import mx.itesm.gcrusher.Items.Weapon;
import mx.itesm.gcrusher.R;

public class Layer_controls extends CCLayer {
	
	/*COCOS - 2D objects declaration*/
	private CCSprite jumpButton;
	private CCSprite shootButton;
	private CCSprite bullet;
	private CCSprite explosion;
	private CCSprite boss_explosion;
	private CCSprite token;
	
	private CGPoint startTouch;
	private CGPoint endTouch;
	private CCLabel label_score;
	private CCLabel label_ammo;


	/*float declarations*/
	private float weaponFactor;
	private float itemFactor;
	private float fx;
	private float fy;
	

	/*in-game objects declaration*/
	private Weapon levelWeapon;
	private Item itemToSpawn;
	private Item pickedUp;

	/*data structure declaration*/
	private ArrayList<Obstacle> platforms;
	private ArrayList<Minion> minions ;
	private ArrayList<Item> items ;
	private Stack<CCSprite> lifes;

	/*int declarations*/
	private int firstTouch;
	private int multiplier;
	private int numPlatform;
	private int jumpNumber=0;
	public static  int level;
		

	/*boolean declarations*/
	private boolean onPlatform;
	private boolean onFloor;
	private boolean blast;
	private boolean lastMovingDone;
	
	
	/*RECIEN AGREGADOS*/
	 
	CCSprite target = CCSprite.sprite("warning_others/target.png");
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	int iter; // iterator for bullets
	int boss_bullet_numb; // number of bullets		 

	private Yalel yalel;
    boolean bossSpawned;
	Boss boss;	
	
	/* MINIONS, PLATAFORMAS, ITEMS  & TOKENS*/
	private int MAX_MINIONS = 0, MAX_PLATFORMS = 0, TOKENS_FOR_BOSS =0;
	
	/*CONSTANTS DECLARATION*/
	private static final CGSize SIZE = CCDirector.sharedDirector().displaySize();
	private static final CGPoint FLOOR_POSITION = CGPoint.make(SIZE.width/6f,SIZE.height*2/7);
    private static final int MAX_JUMPS = 2;
	private static final float MINION_MIN = SIZE.height * .25f, MINION_MAX= SIZE.height /2f, ADVANCE_MINION = SIZE.width / 100f;
	private static final float PLATFORM_MAX_Y= SIZE.height * .75f, PLATFOTM_MIN_Y = SIZE.height /2f, ADVANCE_PLATFORM = SIZE.width / 200f;
	private static final float NORMAL_BULLET_1=1,NORMAL_BULLET_2=4,BOSS_BAZOOKA=3,BOSS_BASH=2;
	private static final float PLATFORM_SEPARATION = SIZE.width ,MINION_SEPARATION = SIZE.width/5;
		
	
	/*CLASS VARIABLES*/
	
	public static int final_score=0;
	
	/*TAG DECLARATION*/
	private static final int HEART_TAG = 50, HALF_HEART_TAG =51, JUMP_TAG = 60, BULLET_TAG=20, DAMAGE_TAG = 99,NO_AMMO_TAG = 300, MOVE_BULLET_TAG = 120;
	private static final int YALEL_SPRITE_TAG = 42;


	/* INITIALIZATION SECTION */
	public Layer_controls(Yalel yalel,int level) {
		
		Layer_controls.level = level;		
		/*initialize yalel*/		
		this.yalel = new Yalel(yalel);
		this.yalel.getSprite().setPosition(FLOOR_POSITION);

		// SET DIFFICULTY ACCORDING TO LEVEL
				if (level == 1){
					MAX_MINIONS = 2;
					MAX_PLATFORMS = 4;
					TOKENS_FOR_BOSS = 0;
					
				} else if (level == 2){
					MAX_MINIONS = 4;
					MAX_PLATFORMS = 3;
					TOKENS_FOR_BOSS = 6;
					
				} else if (level == 3){
					MAX_MINIONS = 5;
					MAX_PLATFORMS =2 ;
					TOKENS_FOR_BOSS=8;
				}
		
		/*initialize control variables*/		
		multiplier = 1;
		firstTouch=0;	

		/*init ui & in-game objects*/
		initScalingFactor();		
		initGameObects();				
		initLabels();
		initButtons();
		initSounds();

		
		/* get random factor for weapon & item spawn */		
		weaponFactor = getRandomFactor();
		itemFactor = getRandomFactor();


		/* spawn everything */
		spawnBoss();
		spawnPlatforms();
		spawnMinions();
		spawnWeapon();
		spawnItems();
		spawnToken();

		/*GUI visible*/
		addChild(jumpButton);
		addChild(shootButton);
		addChild(label_score);
		addChild(label_ammo);
		
		
		animation_token();
		animation_yalel(yalel.getSprite().getPosition());

		updateLifes();
		schedule("updateScore", .1f);
		updateAmmo(yalel.getGun().getBullets());
		schedule("moveObjects", .01f);

		setIsTouchEnabled(true);

	}	
	public void initSounds() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().preloadEffect(contexto, R.raw.wilhelm_scream_1);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.rifle_shoot);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.shotgun_shoot);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.bazooka_shoot);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.bazooka_shoot);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.handgun_shoot);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.minion_stump);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.pickup_health);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.pickup_item);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.pickup_powerup);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.pickup_weapon);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.damage);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.explosion);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.jump);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.unavaiable);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.no);
		SoundEngine.sharedEngine().preloadSound(contexto, R.raw.keyboard_cat2);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.boss_defeat);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.hurt);
		SoundEngine.sharedEngine().preloadEffect(contexto,R.raw.rainbow);		
	}
	public void initButtons() {
		/*jump button*/
		jumpButton = CCSprite.sprite("buttons/button_jump.png");
		jumpButton.setScaleX(fx*2);
		jumpButton.setScaleY(fy*2);
		jumpButton.setPosition(SIZE.width / 10, SIZE.height / 8);

		/*shoot button*/
		shootButton = CCSprite.sprite("buttons/button_shoot.png");
		shootButton.setScaleX(fx*2);
		shootButton.setScaleY(fy*2);		
		shootButton.setPosition(SIZE.width - SIZE.width / 10, SIZE.height / 8);

	}
	public void initLabels() {
		/*score label*/
		label_score = (CCLabel.makeLabel("" + yalel.getScore(), "fonts/shadows.ttf", 32));
		label_score.setPosition(SIZE.width - (SIZE.width / 10),(SIZE.height - SIZE.height / 20));
		label_score.setScaleX(fx*2);
		label_score.setScaleY(fy*2);
		
		/*ammo label*/
		label_ammo = CCLabel.makeLabel("a", "fonts/shadows.ttf", 32);
		ccColor3B black = ccColor3B.ccBLACK;
		/*si es nivel 1, letras negras*/
		if (level==1){
			this.label_ammo.setColor(black);
			this.label_score.setColor(black);
		}
		
		label_ammo.setPosition(SIZE.width / 4, SIZE.height - SIZE.height / 10);
		label_ammo.setScaleX(fx*2);
		label_ammo.setScaleY(fy*2);
		
	}
	public void initGameObects() {
		pickedUp = new Item();
		lifes = new Stack<CCSprite>();
		platforms = new ArrayList<Obstacle>();
		minions =new ArrayList<Minion>();
		items = new ArrayList<Item>();
		token = CCSprite.sprite("animations/coin/coin_1.png");
		token.setScaleX(fx);
		token.setScaleY(fy);
		fillLists();
	}
	public void fillLists() {
		/* fill platforms */
		for (int i = 0; i < MAX_PLATFORMS; i++) {
			Obstacle obs = new Obstacle(level);
			platforms.add(obs);
		}
		/* fill minions */
		for (int i = 0; i < MAX_MINIONS; i++) {
			Minion min = new Minion(Layer_controls.level);
			this.minions.add(min);
		}

		/* fill items */		
		items.add(new PowerUp(0, "items/power-ups/infinite.png"));
		items.add(new PowerUp(1, "items/power-ups/multiplier.png"));
		items.add(new Item("items/lifes/heart_complete.png", 2, 1));
		items.add(new Item("items/lifes/heart_half.png", 3, .5f));
		items.add(new Ammo(4, 20, "items/ammo/ammo_shotgun.png"));
		items.add(new Ammo(5, 30, "items/ammo/ammo_rifle.png"));
		items.add(new Ammo(6, 3, "items/ammo/ammo_bazooka.png"));

	}
	public void initScalingFactor() {		
		fx = SIZE.width * .6f / 800;
		fy = SIZE.height * .6f / 480;

	}


	/*UPDATE SECTION*/	
	private void updateAmmo(int bullets) {

		/*if current gun is handgun or infiniteAmmo is Active -> label is infinite*/
		if (yalel.getGun().getType() == 0	|| (pickedUp instanceof PowerUp && ((PowerUp) pickedUp).isactive())) {
			label_ammo.setString("AMMO: INFINITE");

		} else {
			/*update label with remaining bullets*/
			if (bullets < 0)
				bullets = 0;
			label_ammo.setString("AMMO: " + bullets);
		}

	}
	public void updateScore(float f) {
			
		int actualScore = yalel.getScore();

		yalel.setScore(actualScore + 1 * multiplier);
		label_score.setString(actualScore + "");

	}
	public void updateLifes() {
		if (yalel.getLifes() > 0) {
			double actual_lifes = yalel.getLifes();
			
			/*# of heart sprites*/
			double number_sprites = (long) Math.floor(yalel.getLifes() + 0.5d); 

			// removes n heart sprites
			for (int i = 0; i < number_sprites; i++) {
				removeChildByTag(HEART_TAG, true);
				removeChildByTag(HALF_HEART_TAG, true);
			}

			// insert and display lifes
			for (int i = 0; i < (int) actual_lifes; i++) {
				if (this.lifes.isEmpty()) {
					CCSprite complete_heart = CCSprite.sprite("items/lifes/heart_complete.png");
					complete_heart.setScaleX(fx);
					complete_heart.setScaleY(fy);
					complete_heart.setTag(HEART_TAG);
					complete_heart.setPosition(SIZE.width - (SIZE.width / 20),	(SIZE.height - SIZE.height / 8));
					addChild(complete_heart);
					lifes.push(complete_heart);
				} else {
					CGPoint last_position;
					CCSprite complete_heart = CCSprite.sprite("items/lifes/heart_complete.png");
					complete_heart.setScaleX(fx);
					complete_heart.setScaleY(fy);
					complete_heart.setTag(HEART_TAG);
					last_position = lifes.peek().getPosition();
					float image_width = 2*lifes.peek().getBoundingBox().size.width;
					complete_heart.setPosition(last_position.x - image_width,last_position.y);
					addChild(complete_heart);
					lifes.push(complete_heart);
				}
			}

			String heartsToAdd = actual_lifes + "";
			if (heartsToAdd.contains(".5")) {// if there's a decimal: add half
				// heart
				CGPoint last_position;
				CCSprite half_heart = CCSprite.sprite("items/lifes/heart_half.png");
				half_heart.setScaleX(fx);
				half_heart.setScaleY(fy);
				half_heart.setTag(HALF_HEART_TAG);
				if (lifes.isEmpty()) {
					half_heart.setPosition(SIZE.width - (SIZE.width / 20),	(SIZE.height - SIZE.height / 8));
					addChild(half_heart);
					lifes.push(half_heart);
				} else {
					last_position = lifes.peek().getPosition();
					float image_width = 2* lifes.peek().getBoundingBox().size.width;
					half_heart.setPosition(last_position.x - image_width,last_position.y);
					addChild(half_heart);
					lifes.push(half_heart);
				}
			}

			lifes.clear();

		} 
		/*YALEL IS KILL*/
		if (yalel.getLifes() <= 0) {			
			removeChildByTag(HEART_TAG, true);
			removeChildByTag(HALF_HEART_TAG, true);
									
			CCDirector.sharedDirector().pause();
			
			/*actualiza el highscore*/
			updateHighScore();
			
			yalel.resetLifes();
			
			/*pause  sounds*/									
			SoundEngine.sharedEngine().realesSound(R.raw.mexico_background);
			
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playSound(contexto, R.raw.no, false);
			
			/*game over screen*/
			Layer_pause_menu.gameOver();
			Layer_pause_menu.backButton.setPosition(SIZE.width-SIZE.width/5,SIZE.height-SIZE.height/2.5f);
			Layer_pause_menu.repeatButton.setPosition(Layer_pause_menu.backButton.getPosition().x,Layer_pause_menu.backButton.getPosition().y-SIZE.height/2.5f);
			Layer_pause_menu.backButton.setVisible(true);
			Layer_pause_menu.repeatButton.setVisible(true);

		}

	}

	/* SPAWN SECTION */
	public void spawnBoss() {			
		
		switch (Layer_controls.level) {
		
		case 1:
			boss = new Boss(level, 10);
//			Log.d("YALEL", "SPAWNING BOSS 1");
			break;
		case 2:			
			boss = new Boss(level, 10);
//			Log.d("YALEL", "SPAWNING BOSS 2: "+boss.lifes);
			break;
		case 3:
			boss = new Boss(level, 100);
//			Log.d("YALEL", "SPAWNING BOSS 3");
			break;
			
		default:			
			break;
		}
		
//		Log.d("YALEL", "BOSS LIFE: "+boss.getLifes());
		boss.getSprite().setPosition(SIZE.width*2f, SIZE.height*2f);
		addChild(boss.getSprite());
		

	}
	public void spawnPlatforms() {
		
		for (int i=0;i<platforms.size();i++) {
			Obstacle plat = platforms.get(i);
			// min-max for heights
			
			// plat.getSprite().setPosition(size.width+size.width*(float)Math.random(),size.height/3+size.height*(float)Math.random());			
			if (i==0){
				plat.getSprite().setPosition(1.5f*SIZE.width,(float) (PLATFOTM_MIN_Y+ Math.random() * (PLATFORM_MAX_Y - PLATFOTM_MIN_Y) + 1));
			}else{
				if (i<MAX_PLATFORMS-1){
					plat.getSprite().setPosition(platforms.get(i-1).getSprite().getPosition().x+PLATFORM_SEPARATION,(float) (PLATFOTM_MIN_Y+ Math.random() * (PLATFORM_MAX_Y - PLATFOTM_MIN_Y) + 1));
				}
				
				if (i==MAX_PLATFORMS-1){
					plat.getSprite().setPosition(platforms.get(MAX_PLATFORMS-2).getSprite().getPosition().x+PLATFORM_SEPARATION,(float) (PLATFOTM_MIN_Y+ Math.random() * (PLATFORM_MAX_Y - PLATFOTM_MIN_Y) + 1));
				}
			}
			
			
			
			addChild(plat.getSprite());

		}

	}
	public void spawnMinions() {
		
		// SET DIFFICULTY
		
		for (int i=0;i<MAX_MINIONS;i++){
			Minion minion = minions.get(i);
			
			if (i==0){
				minion.getSprite().setPosition(1.5f*SIZE.width,(float) (MINION_MIN+ Math.random() * (MINION_MAX- MINION_MIN) + 1));
			}else{
				if (i<MAX_MINIONS-1){
					minion.getSprite().setPosition(minions.get(i-1).getSprite().getPosition().x+MINION_SEPARATION,(float) (MINION_MIN+ Math.random() * (MINION_MAX- MINION_MIN) + 1));
				}
				
				if (i==MAX_MINIONS-1){
					minion.getSprite().setPosition(minions.get(MAX_MINIONS-2).getSprite().getPosition().x+MINION_SEPARATION,(float) (MINION_MIN+ Math.random() * (MINION_MAX- MINION_MIN) + 1));
				}
			}
			animation_minions(minion);
			addChild(minion.getSprite());
		}
//		for (Minion minion : this.minions) {			
//			minion.getSprite().setPosition(size.width + size.width * (float) Math.random(),	(float) (MINION_MIN + Math.random() * (MINION_MAX - MINION_MIN) + 1));
//			animateGroundMinions(minion);
//			addChild(minion.getSprite());
//		}

	}
	public void spawnWeapon() {

		this.weaponFactor = getRandomFactor()+yalel.getScore();
		Log.d("YALEL", "WEAPON FACTOR: "+weaponFactor);
		
		switch (level) {
		case 1:
            this.levelWeapon = new Shotgun();
			break;
		case 2:
			Rifle rifle = new Rifle();
			this.levelWeapon=rifle;
			break;
		case 3:
			Bazooka baz = new Bazooka();
			this.levelWeapon=baz;
			break;

		default:
			break;
		}
		
		levelWeapon.getSprite().setPosition(1.5f * SIZE.width, FLOOR_POSITION.y);
		addChild(levelWeapon.getSprite());
	}
	public void spawnItems() {
		/* random factor */
		this.itemFactor = yalel.getScore() + yalel.getScore() / 2+ getRandomFactor() % 100;
		
		if (level==1) {
		
			int itemChoosed = (int) this.itemFactor % 5;
			this.itemToSpawn = items.get(itemChoosed);
			itemToSpawn.getSprite().setPosition(1.5f * SIZE.width,	FLOOR_POSITION.y);
			addChild(itemToSpawn.getSprite());
		} else 
			if (level==2) {
				int itemChoosed = (int) this.itemFactor % 6;
				this.itemToSpawn = items.get(itemChoosed);
				itemToSpawn.getSprite().setPosition(1.5f * SIZE.width,	FLOOR_POSITION.y);
				addChild(itemToSpawn.getSprite());
		} else 
			if (level==3) {
				int itemChoosed = (int) this.itemFactor % 7;
				this.itemToSpawn = items.get(itemChoosed);
				itemToSpawn.getSprite().setPosition(1.5f * SIZE.width,	FLOOR_POSITION.y);
				addChild(itemToSpawn.getSprite());
		}
	}
    public void spawnToken(){
    	token.setPosition(2f*SIZE.width,FLOOR_POSITION.y);
    }


	/* MOVE SECTION -> COLLISION DETECTION */
	public void moveBoss(){		
		
		CGPoint stagePosition = CGPoint.make(SIZE.width* 4/5f, SIZE.height/3f);
		CGPoint upperBound = CGPoint.make(SIZE.width*4/5f, SIZE.height*2/3f);
		
		/*if boss is not visible, bring it to stage*/
		if (!boss.isOnStage()){	
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.rainbow);
			CCAction moveDown = CCMoveTo.action(1.5f,stagePosition);
			boss.getSprite().runAction(moveDown);
			boss.setOnStage(true);	
		} else { 			
			if (boss.getSprite().getPosition().y==stagePosition.y){
				CCAction moveUp = CCMoveTo.action(1.5f,upperBound);
				boss.getSprite().runAction(moveUp);
			}else if (boss.getSprite().getPosition().y==upperBound.y){
				CCAction moveDown = CCMoveTo.action(1.5f, stagePosition);
				boss.getSprite().runAction(moveDown);
			}
			// attacking!
			if (!boss.isAttacking()){
				Random rando = new Random();
				boss.setAttacking(true);
				bossAttack(rando.nextInt(5));
			}
			// check bullet collision with Yalel
			for (Bullet bullet_boss : bullets){
				
				if ((bullet_boss.getType() == 2) && CGRect.intersects(bullet_boss.getSprite().getBoundingBox(), bullet.getBoundingBox())){
					CGPoint collisionPoint = bullet.getPosition();
					bullet.stopAllActions();
					bullet.setPosition(-200,-200);
					removeChild(bullet, true);
					
					if (yalel.getGun() instanceof Bazooka){					
						animationBazooka(collisionPoint);
					}
					
					
					if (boss.getLifes() >0){
						SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.hurt);
						boss.setLifes(boss.getLifes() -yalel.getGun().getDamage());
//						Log.d("BOSS","VIDA: "+boss.getLifes());
						
					}else{

						/*BOSS IS KILL*/
						yalel.resetTokens();
						updateHighScore();
						boss.setAlive(false);
						/*STOP SOUND*/
						if (level ==1)
							SoundEngine.sharedEngine().realesSound(R.raw.mexico_background);
						if (level ==2)
							SoundEngine.sharedEngine().realesSound(R.raw.egypt_background);
						if (level == 3)
							SoundEngine.sharedEngine().realesSound(R.raw.rlyeh_background);
						if ((int)Math.random()*10 ==4){
                            SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.wilhelm_scream_1);
						}else{
							SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.boss_defeat);
						}
						
						CCFadeOut fade = CCFadeOut.action(3f);				
						CCCallFuncN remove = CCCallFuncN.action(this, "removeBoss");
						CCSequence seq = CCSequence.actions(fade, remove);
						boss.getSprite().runAction(seq);
//						stopAllActions();
//						unscheduleAllSelectors();
						
						int level = boss.getLevel();
						LevelSelection.state[level] = true;
						SceneCreator.yalel = new Yalel(this.yalel);
						
					}	
				}
				
				if (CGRect.intersects(bullet_boss.getSprite().getBoundingBox(), yalel.getSprite().getBoundingBox())){
					/* damage indicator */
					if (bullet_boss.getType() != 3){
						CCSprite damageSprite = CCSprite.sprite("warning_others/damage.png");
						damageSprite.setScaleX(SIZE.width/ damageSprite.getContentSize().width);
						damageSprite.setScaleY(SIZE.height/ damageSprite.getContentSize().height);
						damageSprite.setPosition(SIZE.width / 2,SIZE.height / 2);
						damageSprite.setTag(DAMAGE_TAG);
						addChild(damageSprite);
																		
					}
					
					
					/*decrement yalels lifes*/
					double actualLifes = yalel.getLifes();
					if (bullet_boss.getType() == NORMAL_BULLET_1 || bullet_boss.getType() == NORMAL_BULLET_2){
						// Handgun bullet
						Context contexto = CCDirector.sharedDirector().getActivity();
						SoundEngine.sharedEngine().playEffect(contexto, R.raw.damage);
						yalel.setLifes(actualLifes - .5);						
						bullets.remove(bullet_boss);
						removeChild(bullet_boss.getSprite(), true);
						updateLifes();
					} else if (bullet_boss.getType() == BOSS_BASH){
						// Boss bullet
						bullets.remove(bullet_boss);
						removeChild(bullet, true);
						Context contexto = CCDirector.sharedDirector().getActivity();
						SoundEngine.sharedEngine().playEffect(contexto, R.raw.damage);
						yalel.setLifes(actualLifes - .5);												
						updateLifes();
					} 
//					
					schedule("warning_damageDone", .1f);
				}
			}
			
			/* COLISION EXPLOSION Y YALEL*/ 
			if (boss_explosion!=null){
				if (CGRect.intersects(yalel.getSprite().getBoundingBox(), boss_explosion.getBoundingBox()) && !blast){

					Context contexto = CCDirector.sharedDirector().getActivity();
					SoundEngine.sharedEngine().playEffect(contexto, R.raw.damage);

					yalel.setLifes(yalel.getLifes()-.5);
					updateLifes();

					CCSprite damageSprite = CCSprite.sprite("warning_others/damage.png");
					damageSprite.setScaleX(SIZE.width/ damageSprite.getContentSize().width);
					damageSprite.setScaleY(SIZE.height/ damageSprite.getContentSize().height);
					damageSprite.setPosition(SIZE.width / 2,SIZE.height / 2);
					damageSprite.setTag(DAMAGE_TAG);
					addChild(damageSprite);

					schedule("warni	ng_damageDone", .1f);
					blast = true;
				}
			}

			/*COLISION BALA YALEL Y BOSS*/
			if (CGRect.intersects(bullet.getBoundingBox(), boss.getSprite().getBoundingBox()) && boss.getSprite().getVisible()){
				CGPoint collisionPoint = bullet.getPosition();
				bullet.stopAllActions();
				bullet.setPosition(-200,-200);
				removeChild(bullet, true);

				if (yalel.getGun() instanceof Bazooka){
					animationBazooka(collisionPoint);
				}


				if (boss.getLifes() >0){
					SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.hurt);
					boss.setLifes(boss.getLifes() -yalel.getGun().getDamage());
//					Log.d("BOSS","VIDA: "+boss.getLifes());

				}else{

					/*BOSS IS KILL*/
					yalel.resetTokens();
					updateHighScore();
					boss.setAlive(false);
					/*STOP SOUND*/
					if (level ==1)
						SoundEngine.sharedEngine().realesSound(R.raw.mexico_background);
					if (level ==2)
						SoundEngine.sharedEngine().realesSound(R.raw.egypt_background);
					if (level == 3)
						SoundEngine.sharedEngine().realesSound(R.raw.rlyeh_background);
					if ((int)Math.random()*10 ==4){
                        SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.wilhelm_scream_1);
					}else{
						SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.boss_defeat);
					}

					CCFadeOut fade = CCFadeOut.action(3f);
					CCCallFuncN remove = CCCallFuncN.action(this, "removeBoss");
					CCSequence seq = CCSequence.actions(fade, remove);
					boss.getSprite().runAction(seq);
//					stopAllActions();
//					unscheduleAllSelectors();

					int level = boss.getLevel();
					LevelSelection.state[level] = true;
					SceneCreator.yalel = new Yalel(this.yalel);

				}
			}



		}

	}
	public void moveObjects(float f) {

		if (isOnFloor()){
			this.jumpNumber =0;
		}
		 /*MOVE TOKEN*/
		if (yalel.getTokens()<TOKENS_FOR_BOSS){
			moveToken();
		}

		/* MOVE PLATFORMS */
		if (!platforms.isEmpty())
			movePlatforms();


		/* MOVE WEAPON */
		if (yalel.getScore() > weaponFactor + 2)
			moveWeapon();

		/* MOVE ITEM */
		moveItem();

		/* MOVE BOSS */
		if (yalel.getTokens()==TOKENS_FOR_BOSS && boss.isAlive()) { // MUST IMPLEMENT TOKENS!!!
			moveBoss();
			bossSpawned=true;
		}

		/* MOVE MINIONS */
		if (!lastMovingDone)
			moveMinions();

		/*LAST MINION MOVE*/
		if (bossSpawned)
			moveLastMinion();



	}
	public void moveLastMinion() {
		for (int i=0;i<minions.size();i++) {
			if (i==minions.size()-1)
				lastMovingDone=true;

			Minion minion = minions.get(i);
			CGPoint position = minion.getSprite().getPosition();
			position.x -= ADVANCE_MINION;
			minion.getSprite().setPosition(position);

			/* delete minions */
			if (position.x < -SIZE.width / 2f) {
				minions.remove(minion);
				removeChild(minion.getSprite(), true);

			}

			if (this.explosion!=null){
				if (CGRect.intersects(this.explosion.getBoundingBox(), minion.getSprite().getBoundingBox() )){
					yalel.setScore(yalel.getScore() + 50 * multiplier);
					minions.remove(minion);
					removeChild(minion.getSprite(), true);
				}
			}

			if (this.bullet!=null){
				/* colision disparo y minions */
				if (CGRect.intersects(bullet.getBoundingBox(), minion.getSprite().getBoundingBox())) {

					double minionLifes = minion.getLifes();
					int gunType = yalel.getGun().getType();
					/* do damage to minion */
					minion.setLifes(minionLifes - yalel.getGun().getDamage());

					switch (gunType) {/* COMPORTAMIENTO DE CADA ARMA */
					case 0:/* handgun */
						bullet.stopAction(MOVE_BULLET_TAG);/* stop moving the bullet */
						bullet.setPosition(-200, -200);
						removeChildByTag(BULLET_TAG, true);
						break;

					default:
						break;
					}
					/* if minion dies -> delete it */
					if (minion.getLifes() <= 0) {
						yalel.setScore(yalel.getScore() + 50 * multiplier);
						minions.remove(minion);
						removeChild(minion.getSprite(), true);
					}

				}
			}

			/* colision yalel y minions */
			if (CGRect.intersects(yalel.getSprite().getBoundingBox(), minion.getSprite().getBoundingBox())) {

				/* yalel is ON the minion */
				if (yalel.getSprite().getPosition().y > minion.getSprite().getPosition().y+ minion.getSprite().getContentSize().height / 2) {
					Context contexto = CCDirector.sharedDirector().getActivity();
					SoundEngine.sharedEngine().playEffect(contexto, R.raw.minion_stump);
					/* respawn minion */
					yalel.setScore(yalel.getScore() + 50 * multiplier);
					minions.remove(minion);
					removeChild(minion.getSprite(), true);

					/* yalel jumps */
					CCAction jump = CCJumpTo.action(1, FLOOR_POSITION,SIZE.getHeight() / 5, 1);
					jump.setTag(JUMP_TAG);
					yalel.getSprite().runAction(jump);
					this.jumpNumber -=1;

				} else {
					/* if yalel is still alive */
					if (yalel.getLifes() > 0) {

						Context contexto = CCDirector.sharedDirector().getActivity();
						SoundEngine.sharedEngine().playEffect(contexto, R.raw.damage);

						/* damage indicator */
						CCSprite damageSprite = CCSprite.sprite("warning_others/damage.png");
						damageSprite.setScaleX(SIZE.width/ damageSprite.getContentSize().width);
						damageSprite.setScaleY(SIZE.height/ damageSprite.getContentSize().height);
						damageSprite.setPosition(SIZE.width / 2,SIZE.height / 2);
						damageSprite.setTag(DAMAGE_TAG);
						addChild(damageSprite);

						/* respawnear al minion */
						minions.remove(minion);
						removeChild(minion.getSprite(), true);

						/* decrement yalels lifes */
						double actualLifes = yalel.getLifes();
						yalel.setLifes(actualLifes - .5);
						updateLifes();

						schedule("warning_damageDone", .1f);
					}
				}
			}
		}

	}
	public void moveItem() {
		float advanceItem = SIZE.width / 450;
		/* move the item */
		if (yalel.getScore() > this.itemFactor) {
			CGPoint position = itemToSpawn.getSprite().getPosition();
			position.x -= advanceItem;
			itemToSpawn.getSprite().setPosition(position);

			/* if item is off limits */
			if (position.x < -SIZE.width / 2f) {
				removeChild(itemToSpawn.getSprite(), true);
				this.itemToSpawn = null;
				spawnItems();
			}

			/* pick item up */
			if (CGRect.intersects(yalel.getSprite().getBoundingBox(), itemToSpawn
					.getSprite().getBoundingBox())) {
				this.pickedUp = itemToSpawn;

				useItem();
				removeChild(itemToSpawn.getSprite(), true);
				// itemToSpawn=null;
				spawnItems();
			}
		}

	}
	public void moveWeapon() {
		float advanceWeapon = SIZE.width / 550f;

		CGPoint position = levelWeapon.getSprite().getPosition();
		position.x -= advanceWeapon;
		levelWeapon.getSprite().setPosition(position);

		/* pick item up */
		if (CGRect.intersects(this.yalel.getSprite().getBoundingBox(), this.levelWeapon.getSprite().getBoundingBox())) {

			if (level==1){
				Layer_tooltip.swipe.setVisible(true);
			}

			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_weapon);
			this.levelWeapon.setPicked(true);
			this.yalel.addWeapon(levelWeapon);

			levelWeapon.getSprite().setPosition(-200, -200);/* avoid bugs */
			removeChild(levelWeapon.getSprite(), true);
			this.yalel.setScore(yalel.getScore() + 50);

			Log.d("YALEL", ""+this.yalel.getWeapons());
		}


	}
	public void moveMinions() {

		for (int i=0;i<MAX_MINIONS;i++) {

			Minion minion = minions.get(i);
			CGPoint position = minion.getSprite().getPosition();
			position.x -= ADVANCE_MINION;
			minion.getSprite().setPosition(position);

			/* respawn minions */
			if (position.x < -SIZE.width / 2f) {
//				minion.getSprite().setPosition(SIZE.width + SIZE.width * (float) Math.random(),	(float) (MINION_MIN + Math.random() * (MINION_MAX - MINION_MIN) + 1));
				minion.getSprite().setPosition(1.5f * SIZE.width + i * MINION_SEPARATION, (float) (MINION_MIN + Math.random() * (MINION_MAX - MINION_MIN) + 1));
			}

			if (this.explosion!=null){
				if (CGRect.intersects(this.explosion.getBoundingBox(), minion.getSprite().getBoundingBox() )){
					yalel.setScore(yalel.getScore() + 50 * multiplier);
					this.minions.remove(minion);
					minion.resetLifes();
					CCCallFuncN damage_minion = CCCallFuncN.action(this,"animation_damage");
					minion.getSprite().runAction(damage_minion);
				}
			}

			if (this.bullet!=null){
				/* colision disparo y minions */
				if (CGRect.intersects(this.bullet.getBoundingBox(), minion.getSprite().getBoundingBox())) {

					double minionLifes = minion.getLifes();
					int gunType = yalel.getGun().getType();
					/* do damage to minion */
					minion.setLifes(minionLifes - yalel.getGun().getDamage());

					switch (gunType) {/* COMPORTAMIENTO DE CADA ARMA */
					case 0:/* handgun */
						this.bullet.stopAction(MOVE_BULLET_TAG);/* stop moving the bullet */
						this.bullet.setPosition(-200, -200);
						removeChildByTag(BULLET_TAG, true);
						break;

					default:
						break;
					}
					/* if minion dies -> respawn it */
					if (minion.getLifes() <= 0) {
						yalel.setScore(yalel.getScore() + 50 * multiplier);
						this.minions.remove(minion);
						minion.resetLifes();
						CCCallFuncN damage_minion = CCCallFuncN.action(this,"animation_damage");
						minion.getSprite().runAction(damage_minion);
					}

				}
			}

			/* colision yalel y minions */
			if (CGRect.intersects(yalel.getSprite().getBoundingBox(), minion.getSprite().getBoundingBox())) {

				/* yalel is ON the minion */
				if (yalel.getSprite().getPosition().y > minion.getSprite().getPosition().y+ minion.getSprite().getContentSize().height / 2) {
					Context contexto = CCDirector.sharedDirector().getActivity();
					SoundEngine.sharedEngine().playEffect(contexto, R.raw.minion_stump);
					/* respawn minion */
					yalel.setScore(yalel.getScore() + 50 * multiplier);
					this.minions.remove(minion);
					minion.resetLifes();
					CCCallFuncN damage_minion = CCCallFuncN.action(this,"animation_damage");
					minion.getSprite().runAction(damage_minion);

					/* yalel jumps */
					CCAction jump = CCJumpTo.action(1, FLOOR_POSITION,SIZE.getHeight() / 5, 1);
					jump.setTag(JUMP_TAG);
					yalel.getSprite().runAction(jump);
					this.jumpNumber -=1;

				} else {
					/* if yalel is still alive */
					if (yalel.getLifes() > 0) {

						Context contexto = CCDirector.sharedDirector().getActivity();
						SoundEngine.sharedEngine().playEffect(contexto, R.raw.damage);

						/* damage indicator */
						CCSprite damageSprite = CCSprite.sprite("warning_others/damage.png");
						damageSprite.setScaleX(SIZE.width/ damageSprite.getContentSize().width);
						damageSprite.setScaleY(SIZE.height/ damageSprite.getContentSize().height);
						damageSprite.setPosition(SIZE.width / 2,SIZE.height / 2);
						damageSprite.setTag(DAMAGE_TAG);
						addChild(damageSprite);

						/* respawnear al minion */
						minion.getSprite().setPosition(SIZE.width + SIZE.width* (float) Math.random(),(float) (MINION_MIN + Math.random()* (MINION_MAX - MINION_MIN) + 1));

						/* decrement yalels lifes */
						double actualLifes = yalel.getLifes();
						yalel.setLifes(actualLifes - .5);
						updateLifes();

						schedule("warning_damageDone", .1f);
					}
				}
			}
		}

	}
	public void movePlatforms() {

		float platformTolerance = platforms.get(0).getSprite().getBoundingBox().size.height/10;

		for (int i=0;i<platforms.size();i++) {

			Obstacle plat = platforms.get(i);
			CGPoint position = platforms.get(i).getSprite().getPosition();
			position.x -= ADVANCE_PLATFORM;
			plat.getSprite().setPosition(position);

			/* if platform is off limits */
			if (position.x < -SIZE.width / 2f) {
				if (i==0){

					plat.getSprite().setPosition(platforms.get(MAX_PLATFORMS-1).getSprite().getPosition().x+PLATFORM_SEPARATION,(float) (PLATFOTM_MIN_Y + Math.random() * (PLATFORM_MAX_Y- PLATFOTM_MIN_Y) + 1));
				}else{
					plat.getSprite().setPosition(platforms.get(i-1).getSprite().getPosition().x+PLATFORM_SEPARATION,(float) (PLATFOTM_MIN_Y+ Math.random() * (PLATFORM_MAX_Y - PLATFOTM_MIN_Y) + 1));

				}
			}


			/* platform jump detection */
			if (CGRect.intersects(yalel.getSprite().getBoundingBox(), platforms.get(i).getSprite().getBoundingBox())) {

				CGPoint newPosition = CGPoint.make(yalel.getSprite().getPosition().x, platforms.get(i).getSprite().getPosition().y+platforms.get(i).getSprite().getBoundingBox().size.height+10*platformTolerance);

				/*yalel esta arriba*/
				if((yalel.getSprite().getPosition().y- yalel.getSprite().getBoundingBox().size.height/2) + yalel.getSprite().getBoundingBox().size.height/3> plat.getSprite().getPosition().y+plat.getSprite().getBoundingBox().size.height/2){
					this.jumpNumber =jumpNumber-1;
					this.numPlatform =i;
					yalel.getSprite().stopAction(JUMP_TAG);
					onPlatform=true;
					yalel.getSprite().setPosition(newPosition);


				}
			}else
				/* fall from platform detection */
				if (onPlatform) {
					if (numPlatform==i){
						/*contorno izquierdo de yalel es mayor al contorno derecho de la plataforma*/
						if (yalel.getSprite().getPosition().x- yalel.getSprite().getBoundingBox().size.width/2
                                > platforms.get(i).getSprite().getPosition().x+platforms.get(i).getSprite().getBoundingBox().size.width/2
								&& yalel.getSprite().getPosition().x- yalel.getSprite().getBoundingBox().size.width/2
                                < platforms.get(i).getSprite().getPosition().x+platforms.get(i).getSprite().getBoundingBox().size.width+1.5*ADVANCE_PLATFORM){
							onPlatform=false;
							CCMoveTo jumpDown = CCMoveTo.action(.5f, FLOOR_POSITION);
							yalel.getSprite().runAction(jumpDown);
							this.numPlatform -=1;
						}

					}
				}

		}

	}
	public void moveToken(){
		CGPoint position = token.getPosition();
		position.x -= ADVANCE_PLATFORM;
		token.setPosition(position);

		/* if token is off limits */
		if (position.x < -SIZE.width / 2f){
			spawnToken();
		}

		/*collision detection*/
		if (CGRect.intersects(yalel.getSprite().getBoundingBox(), token.getBoundingBox())){
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_weapon);
			yalel.setTokens(yalel.getTokens()+1);
			spawnToken();
		}
	}


	/* TOUCH DETECTION SECTION */
	public boolean ccTouchesBegan(MotionEvent event) {

		float jump_startX = jumpButton.getPosition().x- jumpButton.getBoundingBox().size.width;
		float jump_endX = jumpButton.getPosition().x	+ jumpButton.getBoundingBox().size.width;
		float jump_startY = jumpButton.getPosition().y- jumpButton.getBoundingBox().size.height;
		float jump_endY = jumpButton.getPosition().y+ jumpButton.getBoundingBox().size.height;

		/* shoot button definition & limits */
		float shoot_startX = shootButton.getPosition().x	- shootButton.getBoundingBox().size.width;
		float shoot_endX = shootButton.getPosition().x+ shootButton.getBoundingBox().size.width;
		float shoot_startY = shootButton.getPosition().y	- shootButton.getBoundingBox().size.height;
		float shoot_endY = shootButton.getPosition().y+ shootButton.getBoundingBox().size.height;

		if (event.getPointerCount()>=1){
			if (event.getPointerCount()==1){
				// touch position
				float x = event.getX(0);
				float y = event.getY(0);

				CGPoint tempPosition = CGPoint.make(x, y);

				CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition);

				float touchX_1 = position.x;
				float touchY_1 = position.y;


				// if touch 1 is on jump button
				if ((touchX_1 >= jump_startX && touchX_1 <= jump_endX) && (touchY_1 >= jump_startY && touchY_1 <= jump_endY)) {

					if (level==1){
						Layer_tooltip.jump.setVisible(false);
						Layer_tooltip.jumpPressed=true;
						if (Layer_tooltip.jumpPressed && Layer_tooltip.shootPressed){
							Layer_tooltip.background.setVisible(false);
						}
					}

					CCScaleTo scaleDown = CCScaleTo.action(.09f,fx/2);
					CCScaleTo scaleUp = CCScaleTo.action(.09f,fx*2);
					CCSequence pressButton = CCSequence.actions(scaleDown, scaleUp);
					this.jumpButton.runAction(pressButton);
					Context contexto = CCDirector.sharedDirector().getActivity();
					SoundEngine.sharedEngine().playEffect(contexto, R.raw.jump);
					jump();
					return true;
				}


				// if touch 1 is on shoot button
				if ((touchX_1 >= shoot_startX && touchX_1 <= shoot_endX)&& (touchY_1 >= shoot_startY && touchY_1 <= shoot_endY)&& !CCDirector.sharedDirector().getIsPaused()) {

					if (level==1){
						Layer_tooltip.shoot.setVisible(false);
						Layer_tooltip.shootPressed=true;
						if (Layer_tooltip.jumpPressed && Layer_tooltip.shootPressed){
							Layer_tooltip.background.setVisible(false);
						}
					}

					CCScaleTo scaleDown = CCScaleTo.action(.09f,fx/2);
					CCScaleTo scaleUp = CCScaleTo.action(.09f,fx*2);
					CCSequence pressButton = CCSequence.actions(scaleDown, scaleUp);
					this.shootButton.runAction(pressButton);
					shoot();
					return true;
				}

				return false;
			}else
				if (event.getPointerCount()>=1){

					// touch position
					float x = event.getX(0);
					float y = event.getY(0);

					float x2 = event.getX(1);
					float y2 = event.getY(1);

					CGPoint tempPosition = CGPoint.ccp(x, y),tempPosition2 = CGPoint.ccp(x2, y2);
					CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition),position2 = CCDirector.sharedDirector().convertToGL(tempPosition2);



					float touchX_1 = position.x;
					float touchY_1 = position.y;

					float touchX_2 = position2.x;
					float touchY_2 = position2.y;

					/*touch  2on shoot button*/
					if ((touchX_2 >= shoot_startX && touchX_2 <= shoot_endX) && (touchY_2 >= shoot_startY && touchY_2 <= shoot_endY)){
						shoot();


						/*touch 1 on jump button*/
						if ((touchX_1 >= jump_startX && touchX_1 <= jump_endX) && (touchY_1 >= jump_startY && touchY_1 <= jump_endY)){
							jump();
							return true;
						}
					}

					/*touch 1 on shoot button*/
					if ((touchX_1 >= shoot_startX && touchX_1 <= shoot_endX) && (touchY_1 >= shoot_startY && touchY_1 <= shoot_endY)){
						shoot();

						/*touch 2 on jump button*/
						if ((touchX_2 >= jump_startX && touchX_2 <= jump_endX) && (touchY_2 >= jump_startY && touchY_2 <= jump_endY)){
							jump();
							return true;
						}
					}

				}

		}
		return false;
	}
	public boolean ccTouchesMoved(MotionEvent event) {
		// touch position
		float x = event.getX();

		float y = event.getY();
		// point with position (0.0 in (n.0))
		CGPoint tempPosition = CGPoint.make(x, y);
		// point converted to normal scale
		CGPoint position = CCDirector.sharedDirector()	.convertToGL(tempPosition);
		if (this.firstTouch == 0) {
			this.startTouch = position;
		}
		this.firstTouch=1;
		return false;

	}
	public boolean ccTouchesEnded(MotionEvent event) {

		float jump_endX = jumpButton.getPosition().x	+ jumpButton.getBoundingBox().size.width/2;

		/* shoot button definition & limits */
		float shoot_startX = shootButton.getPosition().x	- shootButton.getBoundingBox().size.width/2;

		this.firstTouch=0;

		// touch position
		float x = event.getX();
		float y = event.getY();

		// point with position (0.0 in (n.0))
		CGPoint tempPosition = CGPoint.make(x, y);

		// point converted to normal scale
		CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition);
		float touchX = position.x;
		this.endTouch = position;


		if (this.startTouch != null && this.endTouch != null) {
			/*
			 * GUNS INDEX
			 *  handgun index=0
			 *  shotgun index=1
			 *  rifle   index=2
			 *  bazooka index=3*/

			if ((this.startTouch.y > this.endTouch.y) // SWIPE DOWN
					&& (touchX > jump_endX) // not the jump button
					&& (touchX < shoot_startX) // not the shoot button
					&& ((this.yalel.getSprite().getPosition().x == FLOOR_POSITION.x && this.yalel.getSprite().getPosition().y == FLOOR_POSITION.y)|| onPlatform)
					&& !CCDirector.sharedDirector().getIsPaused()) { // not on
				// pause

				if (level==1){
					Layer_tooltip.swipe.setVisible(false);
				}


				int index = this.yalel.getGun().getType(); // index of actual gun
				int maxGun = -1;

				Log.d("GUN", "INDEX: "+index);
				for (Weapon w : yalel.getWeapons()) {
					if (w.isPicked())
						if (w.getType() > maxGun)
							maxGun = w.getType();
				}

				if (index == maxGun) {
					index = 0;
					changeWeapon(index);
				} else if (index < 3) {
					index += 1;
					Log.d("GUN", "FIRST CHANGE: "+index);
					if (!yalel.getWeapons().get(index).isPicked()){
						index+=1;
						Log.d("GUN", "SECOND CHANGE: "+index);
					}
					if (index <=3){
						if (!this.yalel.getWeapons().get(index).isPicked()){
							index+=1;
							Log.d("GUN", "THIRD CHANGE: "+index);
						}
					}


					changeWeapon(index);
				}
				return true;
			} else {
				if ((this.startTouch.y < this.endTouch.y) // SWIPE UP
						&& (touchX > jump_endX) // not the jump button
						&& (touchX < shoot_startX) // not the shoot button
						&& ((this.yalel.getSprite().getPosition().x == FLOOR_POSITION.x && this.yalel.getSprite().getPosition().y == FLOOR_POSITION.y)|| onPlatform)
						&& !CCDirector.sharedDirector().getIsPaused()) { // not
					// on
					// pause

					int index = this.yalel.getGun().getType();
					int maxGun = index;
					for (Weapon w : yalel.getWeapons()) {
						if (w.isPicked()) {
							if (w.getType() > maxGun)
								maxGun = w.getType();
						}
					}


					if (index == 0) {
						index = maxGun;
						changeWeapon(index);
					} else if (index <= 3) {
						index -= 1;
						if (!this.yalel.getWeapons().get(index).isPicked()){
							index-=1;
						}
						if (index <=3){
							if (!this.yalel.getWeapons().get(index).isPicked()){
								index-=1;
								Log.d("GUN", "THIRD CHANGE: "+index);
							}
						}


						changeWeapon(index);
					}
					return true;
				}
			}
		}
		return true;
	}

	/*BANNER SECTION*/
	public void warning_damageDone(float f) {
		removeChildByTag(DAMAGE_TAG, true);/* removes red frame */
		unschedule("warning_damageDone");
	}
	public void warning_noAmmo(float f) {
		removeChildByTag(NO_AMMO_TAG, true);/* removes red frame */
		unschedule("warning_noAmmo");
	}


	/* POWERUP ACTIVATION SECTION */
	public void activateInfiniteAmmo(float f) {
		((PowerUp) pickedUp).setactive(false);
		((PowerUp) pickedUp).setPicked(false);
		unschedule("activateInfiniteAmmo");
		/* updates reference to avoid sincronization problems */
		this.pickedUp = new Item();
		updateAmmo(yalel.getGun().getBullets());

	}
	public void activateScoreMultiplier(float f) {
		// multiplier = 100;
		((PowerUp) pickedUp).setactive(false);
		((PowerUp) pickedUp).setPicked(false);
		unschedule("activateScoreMultiplier");
		this.multiplier=1;
	}

	/* SHOOTING SECTION */
	public void shoot() {
		int avaiableBullets = yalel.getGun().getBullets();
		if (avaiableBullets > 0) {
			/* if pickedUp is infinite ammo, don't dimminish ammo */
			if (pickedUp.getType() != 0 || yalel.getGun().getType() ==0) {
				yalel.shoot(yalel.getGun());
			}

			updateAmmo(yalel.getGun().getBullets());
			int gunType = yalel.getGun().getType();
			switch (gunType) {
			case 0:
				shoot_handgun();
				break;
			case 2:
				shoot_rifle();
				break;
			case 1:
				shoot_shotgun();
				break;
			case 3:
				shoot_bazooka();
				break;

			default:
				break;
			}
		} else {
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.unavaiable);
			updateAmmo(0);
			ccColor3B color = ccColor3B.ccRED;
			CCLabel cantShoot = CCLabel.makeLabel("OUT OF AMMO!", "Arial", 50);
			cantShoot.setColor(color);
			cantShoot.setTag(NO_AMMO_TAG);
			cantShoot.setPosition(SIZE.width / 2, SIZE.height / 2);
			addChild(cantShoot);
			schedule("warning_noAmmo", .5f);
		}
	}
	public void shoot_bazooka() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.bazooka_shoot);
		bullet = CCSprite.sprite("items/bullets/bullet_bazooka.png");
		this.bullet.setTag(BULLET_TAG);
		CGPoint position = yalel.getSprite().getPosition();
		position.x = SIZE.width / 3f;
		this.bullet.setPosition(position);
		/* destination point */
		CGPoint bulletDirection = CGPoint.make(SIZE.width * 4 / 5f,FLOOR_POSITION.y);
		/* rotate the bullet */
		CCAction rotate = CCRotateTo.action(1f, 100f);
		/* move the bullet */
		CCFiniteTimeAction moveBullet = CCJumpTo.action(1f, bulletDirection,SIZE.height / 3, 1);
		/* removes the sprite */
		CCCallFuncN stopShoot = CCCallFuncN.action(this, "stopShoot");
		/* runs nuclear bullet animation */
		CCCallFuncN nuclear_explosion = CCCallFuncN.action(this,"animation_bazooka");
		CCSequence sequnce = CCSequence.actions(moveBullet, stopShoot,nuclear_explosion);

		this.bullet.runAction(rotate);
		this.bullet.runAction(sequnce);
		addChild(this.bullet);
		updateAmmo(yalel.getGun().getBullets());

	}
	public void shoot_shotgun() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.shotgun_shoot);
		updateAmmo(yalel.getGun().getBullets());
		animation_shotgun();

	}
	public void shoot_rifle() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.rifle_shoot);
		bullet = CCSprite.sprite("items/bullets/bullet_rifle.png");
		this.bullet.setTag(BULLET_TAG);
		CGPoint position = yalel.getSprite().getPosition();
		position.x = SIZE.width / 3f;
		this.bullet.setPosition(position);
		CGPoint bulletDirection = CGPoint.make(2 * SIZE.width, position.y);
		CCFiniteTimeAction moveBullet = CCMoveTo.action(.2f, bulletDirection);
		moveBullet.setTag(MOVE_BULLET_TAG);
		this.bullet.runAction(moveBullet);
		addChild(this.bullet);
		updateAmmo(yalel.getGun().getBullets());

	}
	public void shoot_handgun() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.handgun_shoot);
		bullet = CCSprite.sprite("items/bullets/bullet_hg.png");
		this.bullet.setTag(BULLET_TAG);
		CGPoint position = yalel.getSprite().getPosition();
		position.x = SIZE.width / 3f;
		this.bullet.setPosition(position);
		CGPoint bulletDirection = CGPoint.make(2 * SIZE.width, position.y);
		CCFiniteTimeAction moveBullet = CCMoveTo.action(.2f, bulletDirection);
		moveBullet.setTag(MOVE_BULLET_TAG);
		this.bullet.runAction(moveBullet);
		addChild(this.bullet);
		updateAmmo(yalel.getGun().getBullets());

	}
	public void stopShoot(Object obj) {
		removeChild((CCSprite) obj, true);/* removes the sprite */
		((CCSprite) obj).setPosition(-200f, -200f);/*
		 * places the position off
		 * the screen to avoid bugs
		 */
	}

	/* UTILITY SECTION */
	public void jump() {
        Log.e("JUMP NUMBER",jumpNumber+"");
		this.jumpNumber =jumpNumber + 1;
		this.onPlatform=false;
		CCAction jump = CCJumpTo.action(1, FLOOR_POSITION, SIZE.getHeight() / 2,1);
		jump.setTag(JUMP_TAG);
		yalel.getSprite().runAction(jump);

	}
	public void changeWeapon(int index) {
		CGPoint position = yalel.getSprite().getPosition();
		this.yalel.changeWeapon(index);
		updateAmmo(yalel.getGun().getBullets());
		animation_yalel(position);
	}
	public void useItem() {
		Context contexto = CCDirector.sharedDirector().getActivity();
		Ammo ammo;
		int type = pickedUp.getType();

		switch (type) {
		case 0:/* pickedUp is infinite ammo */
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_powerup);
			((PowerUp) pickedUp).setactive(true);
			for (Weapon w:yalel.getWeapons()){
				w.setBullets(w.getBullets() +w.baseAmmo);
			}
			updateAmmo(yalel.getGun().getBullets());

			schedule("activateInfiniteAmmo", (15f));
			break;
		case 1: /* itemToSpawn is score multiplier */
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_powerup);

			this.multiplier=2;
			// banner: "SCORE MULTIPLIER!" TAG = 301
//			ccColor3B color1 = ccColor3B.ccWHITE;
//			CCLabel cantShoot1 = CCLabel.makeLabel("SCORE MULTIPLIER!",	"Arial", 180);
//			cantShoot1.setColor(color1);
//			cantShoot1.setTag(301);
//			cantShoot1.setPosition(size.width / 2, size.height / 2);
//			addChild(cantShoot1);
//			schedule("banner_ScoreMultiplier", 1f);
			schedule("activateScoreMultiplier", 10f);
			break;

		case 2:
		case 3:
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_health);
			yalel.setLifes(yalel.getLifes() + pickedUp.getLifes());
			updateLifes();
			break;

		case 5: /* itemToSpawn is rifle ammo */
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_item);
			Weapon rifle = yalel.getWeapons().get(2);
			ammo = (Ammo) pickedUp;
			rifle.setBullets(rifle.getBullets() + ammo.getBullets());
			updateAmmo(yalel.getGun().getBullets());
			break;
		case 4:/* itemToSpawn is shotgun ammo */
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_item);
			Weapon shotgun = yalel.getWeapons().get(1);
			ammo = (Ammo) pickedUp;
			shotgun.setBullets(shotgun.getBullets() + ammo.getBullets());
			updateAmmo(yalel.getGun().getBullets());
			break;
		case 6:/* itemToSpawn is bazooka ammo */
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pickup_item);
			Weapon bazooka = yalel.getWeapons().get(3);
			ammo = (Ammo) pickedUp;
			bazooka.setBullets(bazooka.getBullets() + ammo.getBullets());
			updateAmmo(yalel.getGun().getBullets());
			break;

		default:
			break;
		}

	}
	public float getRandomFactor() {
		Random rand = new Random();

		itemFactor = rand.nextFloat() * 1500;

		return this.itemFactor;
	}
	public void respawnMinion(Object obj) {
		Minion minion = new Minion(Layer_controls.level);
		this.minions.add(minion);
		animation_minions(minion);
		addChild(minion.getSprite());
		int i = minions.indexOf(minion);
//		minion.sprite.setPosition(SIZE.width + SIZE.width * (float) Math.random(), (float) (MINION_MIN	+ Math.random() * (MINION_MAX- MINION_MIN) + 1));
		minion.getSprite().setPosition(1.5f * SIZE.width + i * MINION_SEPARATION, (float) (MINION_MIN + Math.random() * (MINION_MAX - MINION_MIN) + 1));
	}
	public void bossAttack(int type){
		/*
		 *  Attack type 1, 4 : Bullet spray
		 */
		if (type == NORMAL_BULLET_1 || type == NORMAL_BULLET_2){
			boss_bullet_numb = 2;
			for (int i = 0; i<boss_bullet_numb; i++){
				Bullet bullet_boss = new Bullet(type, boss);
				bullets.add(bullet_boss);
				addChild(bullet_boss.getSprite());
			}
			int gap = 0;
			iter = 0;
			for (Bullet bullet_boss : bullets){
				bullet_boss.getSprite().setPosition(boss.getSprite().getPosition());
				CGPoint point = CGPoint.make(50, gap);
				CCFiniteTimeAction move = CCMoveTo.action(.8f, point);
				CCCallFunc removeBullet = CCCallFunc.action(this, "removeBullet");
				CCSequence seq = CCSequence.actions(move, removeBullet);
				bullet_boss.getSprite().runAction(seq);
				gap += SIZE.height/boss_bullet_numb;
				iter++;
			}
		} else if (type == BOSS_BASH){
			/*
			 * Attack type 2 : Bash
			 */
			boss_bullet_numb = 1;
			for (int i = 0; i<boss_bullet_numb; i++){
				Bullet bullet_boss = new Bullet(type, boss);
				bullets.add(bullet_boss);
				addChild(bullet_boss.getSprite());
			}
			iter = 0;
			for (Bullet bullet_boss : bullets){
				Random rando = new Random();
				boss.getSprite().setVisible(false);
				bullet_boss.getSprite().setPosition(boss.getSprite().getPosition());
				 /*move the bullet*/
				CGPoint point;
				CCFiniteTimeAction move;
				CCFiniteTimeAction moveH;
				if (rando.nextInt(2) == 0){
					point = CGPoint.make(-SIZE.width*3/7, SIZE.height*1/4);
					moveH = CCMoveTo.action(1f, CGPoint.make(boss.getSprite().getPosition().x, SIZE.height*1/4));
					move = CCMoveTo.action(1.5f, point);
				} else {
					point = CGPoint.make(-SIZE.width*3/7, SIZE.height);
					moveH = CCMoveTo.action(1f, CGPoint.make(boss.getSprite().getPosition().x, SIZE.height));
					move = CCMoveTo.action(1f, point);
				}
				CCCallFunc removeBullet = CCCallFunc.action(this, "removeBullet");
				CCCallFunc restoreBoss = CCCallFunc.action(this, "restoreBoss");
				CCSequence seq = CCSequence.actions(moveH, move, restoreBoss, removeBullet);
				bullet_boss.getSprite().runAction(seq);
				iter++;
			}
		} else if (type == BOSS_BAZOOKA){
			/*
			 * Attack type 3 : Air Missile
			 */
			boss_bullet_numb = 1;
			for (int i = 0; i<boss_bullet_numb; i++){
				Bullet bullet_boss = new Bullet(type, boss);
				bullets.add(bullet_boss);
				addChild(bullet_boss.getSprite());
			}
			iter = 0;
			for (Bullet bullet_boss : bullets){
				bullet_boss.getSprite().setPosition(boss.getSprite().getPosition());
				target.setPosition(yalel.getSprite().getPosition());
				addChild(target);
				/*rotate the bullet*/
				CCAction rotate = CCRotateTo.action(1f,-100f);
				 /*move the bullet*/
				CCFiniteTimeAction move = CCJumpTo.action(1.5f, yalel.getSprite().getPosition(), SIZE.height, 1);
				CCCallFunc removeBullet = CCCallFunc.action(this, "removeBullet");
				bullet_boss.getSprite().runAction(rotate);
				CCCallFunc removeTarget = CCCallFunc.action(this, "removeTarget");
				CCCallFuncN nuclearExplotion = CCCallFuncN.action(this, "animationAirMissile");
				CCSequence seq = CCSequence.actions(move, removeTarget, nuclearExplotion, removeBullet);
				bullet_boss.getSprite().runAction(seq);
				iter++;
			}
		} else {
			/*
			 * DO NOTHING
			 */
			boss.setAttacking(false);
		}
	}
	public void removeBullet(){
		if (iter == boss_bullet_numb){
			for (Bullet bullet_boss : bullets){
				removeChild(bullet_boss.getSprite(), true);
			}
			bullets.removeAll(bullets);
		}
		if (bullets.isEmpty()){
			boss.setAttacking(false);
		}
		blast = false;
	}
	public void removeTarget(){
		target.setPosition(CGPoint.make(-500, -500));
		target.removeChild(target, true);
	}
	public void restoreBoss(){
		boss.getSprite().setVisible(true);
		CCFiniteTimeAction appear = CCFadeIn.action(.3f);
		boss.getSprite().runAction(appear);
	}
	public void removeBoss(Object obj){
		/*removeChild(boss.getSprite(), true);
		Context context = CCDirector.sharedDirector().getActivity();
		CCCallFuncN showWin = CCCallFuncN.action(this, "showWin");
		runAction(showWin);*/
        Intent intencion= new Intent(CCDirector.sharedDirector().getActivity(), Win.class);
        CCDirector.sharedDirector().getActivity().startActivity(intencion);
        CCDirector.sharedDirector().getActivity().finish();
	}

/*
	public void showWin(Object obj){
		Layer_pause_menu.win.setVisible(true);
	}
*/

	public void updateHighScore() {
		final_score = yalel.getScore();
		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		mydate = mydate.substring(0, mydate.length()-5);
		Layer_scores.updateScore(final_score+" "+mydate);
		unschedule("updateScore");
	}



	/* ANIMATION SECTION */
 	public void animation_yalel(CGPoint position) {
		yalel.getSprite().setPosition(position);
		CCAnimation animation = CCAnimation.animation("run");
		CCIntervalAction action;
		switch (yalel.getGun().getType()) {
		/*
		 * runs a different animation
		 * according to the actual gun
		 */
		case 0:

			animation.addFrame("animations/handgun/animation_2_hg.png");
			animation.addFrame("animations/handgun/animation_3_hg.png");
			animation.addFrame("animations/handgun/animation_4_hg.png");
			animation.addFrame("animations/handgun/animation_5_hg.png");
			animation.addFrame("animations/handgun/animation_6_hg.png");
			animation.addFrame("animations/handgun/animation_7_hg.png");
			action = CCAnimate.action(.3f, animation, true);
			yalel.getSprite().runAction(CCRepeatForever.action(action));
			this.removeChildByTag(YALEL_SPRITE_TAG, true);
			yalel.getSprite().setTag(YALEL_SPRITE_TAG);
			addChild(yalel.getSprite());
			break;
		case 1:
			animation.addFrame("animations/rifle/animation_2_rifle.png");
			animation.addFrame("animations/rifle/animation_3_rifle.png");
			animation.addFrame("animations/rifle/animation_4_rifle.png");
			animation.addFrame("animations/rifle/animation_5_rifle.png");
			animation.addFrame("animations/rifle/animation_6_rifle.png");
			animation.addFrame("animations/rifle/animation_7_rifle.png");
			action = CCAnimate.action(.3f, animation, true);
			yalel.getSprite().runAction(CCRepeatForever.action(action));
			this.removeChildByTag(YALEL_SPRITE_TAG, true);
			yalel.getSprite().setTag(YALEL_SPRITE_TAG);
			addChild(yalel.getSprite());
			break;
		case 2:
			animation.addFrame("animations/shotgun/animation_2_sg.png");
			animation.addFrame("animations/shotgun/animation_3_sg.png");
			animation.addFrame("animations/shotgun/animation_4_sg.png");
			animation.addFrame("animations/shotgun/animation_5_sg.png");
			animation.addFrame("animations/shotgun/animation_6_sg.png");
			animation.addFrame("animations/shotgun/animation_7_sg.png");
			action = CCAnimate.action(.3f, animation, true);
			yalel.getSprite().runAction(CCRepeatForever.action(action));
			this.removeChildByTag(YALEL_SPRITE_TAG, true);
			yalel.getSprite().setTag(YALEL_SPRITE_TAG);
			addChild(yalel.getSprite());
			break;
		case 3:
			animation.addFrame("animations/bazooka/animation_2_bazooka.png");
			animation.addFrame("animations/bazooka/animation_3_bazooka.png");
			animation.addFrame("animations/bazooka/animation_4_bazooka.png");
			animation.addFrame("animations/bazooka/animation_5_bazooka.png");
			animation.addFrame("animations/bazooka/animation_6_bazooka.png");
			animation.addFrame("animations/bazooka/animation_7_bazooka.png");
			action = CCAnimate.action(.3f, animation, true);
			yalel.getSprite().runAction(CCRepeatForever.action(action));
			this.removeChildByTag(YALEL_SPRITE_TAG, true);
			yalel.getSprite().setTag(YALEL_SPRITE_TAG);
			addChild(yalel.getSprite());
			break;
		default:// should never happen
			animation.addFrame("animations/solo/animation_2.png");
			animation.addFrame("animations/solo/animation_3.png");
			animation.addFrame("animations/solo/animation_4.png");
			action = CCAnimate.action(.3f, animation, true);
			yalel.getSprite().runAction(CCRepeatForever.action(action));
			this.removeChildByTag(YALEL_SPRITE_TAG, true);
			yalel.getSprite().setTag(YALEL_SPRITE_TAG);
			addChild(yalel.getSprite());
			break;
		}

	}
	public void animation_minions(Minion minion) {
		CCAnimation moving = CCAnimation.animation("move");
		if (level==1){
		moving.addFrame("animations/snake/snake_2.png");
		moving.addFrame("animations/snake/snake_3.png");
		CCAnimate action = CCAnimate.action(.5f, moving, true);
		minion.getSprite().runAction(CCRepeatForever.action(action));
		}else
			if (level==2){
				moving.addFrame("animations/mummy/mummy_2.png");
				moving.addFrame("animations/mummy/mummy_3.png");
				moving.addFrame("animations/mummy/mummy_4.png");
				moving.addFrame("animations/mummy/mummy_5.png");
				CCAnimate action = CCAnimate.action(.5f, moving, true);
				minion.getSprite().runAction(CCRepeatForever.action(action));
			}else
				if (level==3){
					moving.addFrame("animations/medusa/medusa_2.png");
					moving.addFrame("animations/medusa/medusa_3.png");
					moving.addFrame("animations/medusa/medusa_4.png");
					moving.addFrame("animations/medusa/medusa_5.png");
					CCAnimate action = CCAnimate.action(.5f, moving, true);
					minion.getSprite().runAction(CCRepeatForever.action(action));

				}
	}
	public void animation_bazooka(Object obj) {
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.explosion);

		explosion = CCSprite.sprite("animations/bazooka/explosion/explotion_missile_1.png");
		explosion.setPosition(SIZE.width * 4 / 5f, FLOOR_POSITION.y);
		explosion.setScaleX(5f * fx);
		explosion.setScaleY(5f * fy);
		CCAnimation animation = CCAnimation.animation("nuclear_explosion");
		CCIntervalAction nuclear_explosion;
		animation.addFrame("animations/bazooka/explosion/explotion_missile_2.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_3.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_4.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_5.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_6.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_7.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_8.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_9.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_10.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_11.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_12.png");

		nuclear_explosion = CCAnimate.action(.5f, animation, true);
		CCCallFuncN stopShoot = CCCallFuncN.action(this, "stopShoot");
		CCSequence sequence = CCSequence.actions(nuclear_explosion, stopShoot);
		explosion.runAction(sequence);
		addChild(explosion);
	}
	public void animation_damage(Object obj) {
		CCSprite minion = (CCSprite) obj;
		CCFadeOut fadeout = CCFadeOut.action(.2f);
		CCScaleTo scale = CCScaleTo.action(.2f, 0);
		minion.runAction(scale);
		CCCallFuncN respawn = CCCallFuncN.action(this, "respawnMinion");
		CCSequence sequence = CCSequence.actions(fadeout, respawn);
		minion.runAction(sequence);

	}
	public void animation_shotgun() {
		this.bullet = CCSprite.sprite("animations/shotgun/explosion/explotion_1.png");
		this.bullet.setScaleX(2 * fx);
		this.bullet.setScaleY(3 * fy);
		this.bullet.setTag(BULLET_TAG);
		CGPoint position = yalel.getSprite().getPosition();
		position.x = SIZE.width / 3f;
		this.bullet.setPosition(position);
		CCAnimation animation = CCAnimation.animation("shoot");
		CCIntervalAction shoot;
		animation.addFrame("animations/shotgun/explosion/explotion_2.png");
		animation.addFrame("animations/shotgun/explosion/explotion_3.png");
		animation.addFrame("animations/shotgun/explosion/explotion_4.png");
		animation.addFrame("animations/shotgun/explosion/explotion_5.png");
		animation.addFrame("animations/shotgun/explosion/explotion_6.png");
		animation.addFrame("animations/shotgun/explosion/explotion_7.png");
		animation.addFrame("animations/shotgun/explosion/explotion_8.png");
		animation.addFrame("animations/shotgun/explosion/explotion_9.png");
		animation.addFrame("animations/shotgun/explosion/explotion_10.png");
		animation.addFrame("animations/shotgun/explosion/explotion_11.png");
		shoot = CCAnimate.action(.5f, animation, true);
		CCCallFuncN endShoot = CCCallFuncN.action(this, "stopShoot");
		CCSequence sequence = CCSequence.actions(shoot, endShoot);
		this.bullet.runAction(sequence);
		addChild(this.bullet);

	}
	public void animationAirMissile(Object obj){
		boss_explosion = CCSprite.sprite("animations/bazooka/explosion/explotion_missile_1.png");
		boss_explosion.setPosition(bullets.get(0).getSprite().getPosition());
		boss_explosion.setScaleX(4*fx);
		boss_explosion.setScaleY(4*fy);
		CCAnimation animation = CCAnimation.animation("nuclear_explosion");
		CCIntervalAction nuclear_explosion;
		animation.addFrame("animations/bazooka/explosion/explotion_missile_2.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_3.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_4.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_5.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_6.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_7.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_8.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_9.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_10.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_11.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_12.png");

		nuclear_explosion = CCAnimate.action(.5f, animation, true);
		CCCallFuncN stopShoot = CCCallFuncN.action(this, "stopShoot");
		CCSequence sequence = CCSequence.actions(nuclear_explosion, stopShoot);
		boss_explosion.runAction(sequence);
		addChild(boss_explosion);
	}
	public void animation_token() {
		CCAnimation animation = CCAnimation.animation("moveCoin");
		CCIntervalAction action;
		animation.addFrame("animations/coin/coin_2.png");
		animation.addFrame("animations/coin/coin_3.png");
		animation.addFrame("animations/coin/coin_4.png");
		animation.addFrame("animations/coin/coin_5.png");
		animation.addFrame("animations/coin/coin_6.png");
		animation.addFrame("animations/coin/coin_7.png");
		animation.addFrame("animations/coin/coin_8.png");
		animation.addFrame("animations/coin/coin_9.png");
		animation.addFrame("animations/coin/coin_10.png");
		action = CCAnimate.action(.3f, animation, true);
		token.runAction(CCRepeatForever.action(action));
		addChild(token);

	}
	public void animationBazooka(CGPoint position){
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().playEffect(contexto, R.raw.explosion);

		position.x+= boss.getSprite().getBoundingBox().size.width/2;

		explosion = CCSprite.sprite("animations/bazooka/explosion/explotion_missile_1.png");
		explosion.setPosition(position);
		explosion.setScaleX(5f * fx);
		explosion.setScaleY(5f * fy);
		CCAnimation animation = CCAnimation.animation("nuclear_explosion");
		CCIntervalAction nuclear_explosion;
		animation.addFrame("animations/bazooka/explosion/explotion_missile_2.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_3.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_4.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_5.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_6.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_7.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_8.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_9.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_10.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_11.png");
		animation.addFrame("animations/bazooka/explosion/explotion_missile_12.png");

		nuclear_explosion = CCAnimate.action(.5f, animation, true);
		CCCallFuncN stopShoot = CCCallFuncN.action(this, "stopShoot");
		CCSequence sequence = CCSequence.actions(nuclear_explosion, stopShoot);
		explosion.runAction(sequence);
		addChild(explosion);
	}
	public boolean isOnFloor() {
		if (yalel.getSprite().getPosition().y == FLOOR_POSITION.y){
		onFloor = true;
		}else
			onFloor = false;
		return onFloor;
	}	

}
