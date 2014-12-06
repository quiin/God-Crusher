package mx.itesm.gcrusher.Layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.content.Context;

import mx.itesm.gcrusher.R;

public class Layer_background extends CCLayer {
	CCSprite background_1;
	CCSprite background_2 ;
	CGSize size = CCDirector.sharedDirector().displaySize();
	float advance = size.width/200f;
	
	public Layer_background(String bg_1,String bg_2){		
		background_1 = CCSprite.sprite(bg_1);
		background_2 = CCSprite.sprite(bg_2);
		
		background_1.setScaleX(size.width/background_1.getContentSize().width);
		background_2.setScaleX(size.width/background_2.getContentSize().width);
		
		background_1.setScaleY(size.height/background_1.getContentSize().height);
		background_2.setScaleY(size.height/background_2.getContentSize().height);
		
		background_1.setPosition(CGPoint.make(size.width/2f, size.height/2f));
		background_2.setPosition(CGPoint.make(size.width*3/2f, size.height/2f));
		
		Context contexto = CCDirector.sharedDirector().getActivity();
		
		if (bg_1.equalsIgnoreCase("backgrounds/Background_lvl1_1.jpg")){
			SoundEngine.sharedEngine().playSound(contexto, R.raw.mexico_background, true);
		}
		
		if (bg_1.equalsIgnoreCase("backgrounds/Background_lvl2_1.jpg")){
			SoundEngine.sharedEngine().playSound(contexto, R.raw.egypt_background, true);
		}
		if (bg_1.equalsIgnoreCase("backgrounds/Background_lvl3_1.jpg")){
			SoundEngine.sharedEngine().playSound(contexto, R.raw.rlyeh_background, true);
		}
		
		
		addChild(background_1);			
		addChild(background_2);		
		schedule("moveBackground",0.01f);
		
	}
	
	public void moveBackground(float dt){		
		CGSize size = CCDirector.sharedDirector().displaySize();				
		CGPoint position1 = background_1.getPosition();
		// movement back 1
		position1.x -= advance;
		background_1.setPosition(position1);				
		// movement back 2
		CGPoint position2 = background_2.getPosition();
		position2.x -= advance;

		background_2.setPosition(position2);
		

		if (position2.x < -size.width/2f){
			background_2.setPosition(size.width+size.width/2f-advance,size.height/2);

		}
		
		if (position1.x < -size.width/2f){
			background_1.setPosition(size.width+size.width/2f-advance,size.height/2f);

		}
							
	}
	
	
	
}
