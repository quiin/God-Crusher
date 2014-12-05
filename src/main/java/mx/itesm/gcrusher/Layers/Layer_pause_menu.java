package mx.itesm.gcrusher.Layers;


import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;


import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;

import mx.itesm.gcrusher.Activities.LevelSelection;
import mx.itesm.gcrusher.Activities.MainMenu;
import mx.itesm.gcrusher.Activities.SceneCreator;
import mx.itesm.gcrusher.Items.Bazooka;
import mx.itesm.gcrusher.Items.Rifle;
import mx.itesm.gcrusher.Items.Weapon;
import mx.itesm.gcrusher.R;


public class Layer_pause_menu extends CCLayer {
	

	static  CCSprite resumeButton,gameOver, backButton,repeatButton,win,background,effectButton,soundButton; 	
	private CGSize SIZE = CCDirector.sharedDirector().displaySize();
	float fx = SIZE.width /800,fy = SIZE.height / 480;
	
	public Layer_pause_menu(){
		setIsTouchEnabled(true);
		
		background = CCSprite.sprite("background_pause.png");
		background.setScaleX(SIZE.width/background.getContentSize().width);
		background.setScaleY(SIZE.height/background.getContentSize().height);
		background.setPosition(SIZE.width/2,SIZE.height/2);
		background.setVisible(false);
		
		
		gameOver = CCSprite.sprite("game_over.png");
		gameOver.setScaleX(SIZE.width/gameOver.getContentSize().width);
		gameOver.setScaleY(SIZE.height/gameOver.getContentSize().height);
		gameOver.setPosition(SIZE.width/2,SIZE.height/2);
		gameOver.setVisible(false);
		
		win = CCSprite.sprite("win.jpg");
		win.setScaleX(SIZE.width/win.getContentSize().width);
		win.setScaleY(SIZE.height/win.getContentSize().height);
		win.setPosition(SIZE.width/2,SIZE.height/2);
		win.setVisible(false);

		resumeButton = CCSprite.sprite("button_resume.png");
		resumeButton.setVisible(false);
		resumeButton.setPosition(SIZE.width/2,SIZE.height/2);
		resumeButton.setScaleX(fx);
		resumeButton.setScaleY(fy);
		
		backButton = CCSprite.sprite("button_back.png");
		backButton.setVisible(false);
		backButton.setPosition(resumeButton.getPosition().x+SIZE.width/4,SIZE.height/2);
		backButton.setScaleX(fx);
		backButton.setScaleY(fy);
		
		repeatButton = CCSprite.sprite("button_repeat.png");
		repeatButton.setVisible(false);
		repeatButton.setPosition(resumeButton.getPosition().x-SIZE.width/4,SIZE.height/2);
		repeatButton.setScaleX(fx);
		repeatButton.setScaleY(fy);
		
		if (MainMenu.effectsEnabled)
			effectButton = CCSprite.sprite("button_sfx.png");
		else
			effectButton = CCSprite.sprite("button_sfx_pressed.png");
		
		effectButton.setVisible(false);
		effectButton.setPosition(SIZE.width/3,SIZE.height/10);
		effectButton.setScaleX(fx);
		effectButton.setScaleY(fy);
		
		if (MainMenu.soundEnabled)
			soundButton = CCSprite.sprite("button_sound.png");
		else
			soundButton = CCSprite.sprite("button_sound_pressed.png");
		
		soundButton.setVisible(false);
		soundButton.setPosition(SIZE.width - SIZE.width/3,SIZE.height/10);
		soundButton.setScaleX(fx);
		soundButton.setScaleY(fy);
		
		/*PREPARE SOUND EFFECT*/
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().preloadEffect(contexto, R.raw.resume);
		
		addChild(background);
		addChild(resumeButton);		
		addChild(gameOver);
		addChild(backButton);
		addChild(repeatButton);
		addChild(effectButton);
		addChild(soundButton);
		addChild(win);
	}
	
	public boolean ccTouchesEnded(MotionEvent event){
		//touch position
		float x = event.getX();
		float y = event.getY();
		//point with position (0.0 in (n.0))
		CGPoint tempPosition = CGPoint.make(x, y);
		//point converted to normal scale
		CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition);
				
		float touchX = position.x;
		float touchY = position.y;
		
		/*resume button limits*/
		float resume_startX = resumeButton.getPosition().x-resumeButton.getBoundingBox().size.width/2f;
		float resume_endX= resumeButton.getPosition().x+resumeButton.getBoundingBox().size.width/2f;
		float resume_startY = resumeButton.getPosition().y-resumeButton.getBoundingBox().size.height/2f;
		float resume_endY = resumeButton.getPosition().y + resumeButton.getBoundingBox().size.height/2f;
		
		float back_startX = backButton.getPosition().x-backButton.getBoundingBox().size.width/2f;
		float back_endX= backButton.getPosition().x+backButton.getBoundingBox().size.width/2f;
		float back_startY = backButton.getPosition().y-backButton.getBoundingBox().size.height/2f;
		float back_endY = backButton.getPosition().y + backButton.getBoundingBox().size.height/2f;
		
		float repeat_startX = repeatButton.getPosition().x-repeatButton.getBoundingBox().size.width/2f;
		float repeat_endX= repeatButton.getPosition().x+repeatButton.getBoundingBox().size.width/2f;
		float repeat_startY = repeatButton.getPosition().y-repeatButton.getBoundingBox().size.height/2f;
		float repeat_endY = repeatButton.getPosition().y + repeatButton.getBoundingBox().size.height/2f;
		
		float sound_startX = soundButton.getPosition().x-soundButton.getBoundingBox().size.width/2f;
		float sound_endX= soundButton.getPosition().x+soundButton.getBoundingBox().size.width/2f;
		float sound_startY = soundButton.getPosition().y-soundButton.getBoundingBox().size.height/2f;
		float sound_endY = soundButton.getPosition().y + soundButton.getBoundingBox().size.height/2f;
		
		float effect_startX = effectButton.getPosition().x-effectButton.getBoundingBox().size.width/2f;
		float effect_endX= effectButton.getPosition().x+effectButton.getBoundingBox().size.width/2f;
		float effect_startY = effectButton.getPosition().y-effectButton.getBoundingBox().size.height/2f;
		float effect_endY = effectButton.getPosition().y + effectButton.getBoundingBox().size.height/2f;
		
		/*TOUCH ON RESUME*/
		if (touchX >= resume_startX && touchX <= resume_endX && touchY >= resume_startY
				&& touchY <= resume_endY && resumeButton.getVisible()){
			if (Layer_controls.level==1){
				if (!Layer_tooltip.jumpPressed){
					Layer_tooltip.jump.setVisible(true);
					Layer_tooltip.background.setVisible(true);
				}
				if (!Layer_tooltip.shootPressed){
					Layer_tooltip.shoot.setVisible(true);
					Layer_tooltip.background.setVisible(true);
				}														
			}
			
			
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.resume);
			Layer_pause_menu.resumeButton.setVisible(false);
			Layer_pause_menu.backButton.setVisible(false);
			Layer_pause_menu.repeatButton.setVisible(false);
			Layer_pause_menu.background.setVisible(false);
			Layer_pause_menu.effectButton.setVisible(false);
			Layer_pause_menu.soundButton.setVisible(false);
			CCDirector.sharedDirector().resume();
			SoundEngine.sharedEngine().resumeSound();
			return true;
		}
		
		/*TOUCH ON BACK*/
		if (touchX >= back_startX && touchX <= back_endX && touchY >= back_startY
				&& touchY <= back_endY && backButton.getVisible()){
						
			
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.resume);
			Intent intencion= new Intent(CCDirector.sharedDirector().getActivity(), LevelSelection.class);
			CCDirector.sharedDirector().getActivity().startActivity(intencion);
			CCDirector.sharedDirector().getActivity().finish();
			
			return true;
		}
		
		/*TOUCH ON REPEAT*/
		
		if (touchX >= repeat_startX && touchX <= repeat_endX && touchY >= repeat_startY
				&& touchY <= repeat_endY && repeatButton.getVisible()){			
														
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.resume);
			SoundEngine.sharedEngine().realesAllSounds();
			CCDirector.sharedDirector().resume();
			
			ArrayList<Weapon> guns = SceneCreator.yalel.getWeapons();
			Rifle rifle= null;
			Bazooka baz = null;
			
			
			int level = Layer_controls.level;
			if (level==1)		
				CCDirector.sharedDirector().runWithScene(SceneCreator.createScene_mexico());
			else
				if (level==2){					
					for (Weapon w:guns){
						if (w instanceof Rifle){
							rifle=(Rifle) w;
							SceneCreator.yalel.removeWeapon(rifle);
							break;
						}
					}
					
					SceneCreator.yalel.resetLifes();
					CCScene scene = SceneCreator.createScene_egypt();
					CCDirector.sharedDirector().getRunningScene().removeSelf();
					CCDirector.sharedDirector().runWithScene(scene);

					
				}else
					if (level ==3){
						for (Weapon w:guns){
							if (w instanceof Bazooka){
								baz=(Bazooka) w;
								SceneCreator.yalel.removeWeapon(baz);
								break;
							}
						}
						
						SceneCreator.yalel.resetLifes();
						CCDirector.sharedDirector().runWithScene(SceneCreator.createScene_rlyeh());
					}
			
		}
		
		/*TOUCH ON SOUND*/
		if (touchX >= sound_startX && touchX <= sound_endX && touchY >= sound_startY
				&& touchY <= sound_endY && soundButton.getVisible()){
			
			
			if (MainMenu.soundEnabled){
				SoundEngine.sharedEngine().setSoundVolume(0f);
				soundButton.setTexture(CCTextureCache.sharedTextureCache().addImage("button_sound_pressed.png"));
				
				MainMenu.soundEnabled=false;		
			}else{			
				soundButton.setTexture(CCTextureCache.sharedTextureCache().addImage("button_sound.png"));
				MainMenu.soundEnabled=true;
				SoundEngine.sharedEngine().setSoundVolume(1f);
			}
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.button_select);
			
		}
		
		/*TOUCH ON SFX*/
		if (touchX >= effect_startX && touchX <= effect_endX && touchY >= effect_startY
				&& touchY <= effect_endY && effectButton.getVisible()){
			
			if (MainMenu.effectsEnabled){
				effectButton.setTexture(CCTextureCache.sharedTextureCache().addImage("button_sfx_pressed.png"));
				SoundEngine.sharedEngine().setEffectsVolume(0f);
				MainMenu.effectsEnabled=false;
			}else{
				effectButton.setTexture(CCTextureCache.sharedTextureCache().addImage("button_sfx.png"));
				SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.button_select);
				MainMenu.effectsEnabled=true;
				SoundEngine.sharedEngine().setEffectsVolume(1f);
			}
		}
		
		
		
		
		
		return true;
	}

	public static void gameOver() {
		gameOver.setVisible(true);
		
	}
	

	
}
