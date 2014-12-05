package mx.itesm.gcrusher.Layers;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.view.MotionEvent;

import mx.itesm.gcrusher.Characters.Yalel;
import mx.itesm.gcrusher.R;


public class Layer_HUD extends CCLayer {	
	private CGSize SIZE = CCDirector.sharedDirector().displaySize();
	private CCSprite pauseButton;
	private float pause_startX,pause_endX,pause_startY, pause_endY ;
	private float fx = SIZE.width /800,fy = SIZE.height / 480;
	
	public Layer_HUD(Yalel yalel){

		pauseButton = CCSprite.sprite("button_pause.png");
		pauseButton.setPosition(SIZE.width/20,(SIZE.height-SIZE.height/10));
		pauseButton.setScaleX(fx);
		pauseButton.setScale(fy);
		
		pause_startX= pauseButton.getPosition().x-pauseButton.getAnchorPointInPixels().x;
		pause_endX= pauseButton.getPosition().x+pauseButton.getAnchorPointInPixels().x;
		pause_startY= pauseButton.getPosition().y-pauseButton.getAnchorPointInPixels().y;
		pause_endY = pauseButton.getPosition().y + pauseButton.getAnchorPointInPixels().y;		
		
		
		/*pause sound*/
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().preloadEffect(contexto, R.raw.pause);
		addChild(pauseButton);				
		setIsTouchEnabled(true);
	}


	public boolean ccTouchesBegan(MotionEvent event){
		
		//touch position
		float x = event.getX();
		float y = event.getY();
		//point with position (0.0 in (n.0))
		CGPoint tempPosition = CGPoint.make(x, y);
		//point converted to normal scale
		CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition);
				
		float touchX = position.x;
		float touchY = position.y;
		/*pause button limits*/
		
		
		//if touch is on pause image
		if (touchX >= pause_startX && touchX <= pause_endX && touchY >= pause_startY && touchY <= pause_endY){						
			
			if (Layer_controls.level==1){
				Layer_tooltip.background.setVisible(false);
				Layer_tooltip.jump.setVisible(false);
				Layer_tooltip.shoot.setVisible(false);
				Layer_tooltip.swipe.setVisible(false);
			}
			
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.pause);
			Layer_pause_menu.resumeButton.setVisible(true);		
			Layer_pause_menu.backButton.setVisible(true);
			Layer_pause_menu.repeatButton.setVisible(true);
			Layer_pause_menu.background.setVisible(true);
			Layer_pause_menu.effectButton.setVisible(true);
			Layer_pause_menu.soundButton.setVisible(true);
			CCDirector.sharedDirector().pause();	
			SoundEngine.sharedEngine().pauseSound();
			
		}
		return true;
	}
	
	

}
