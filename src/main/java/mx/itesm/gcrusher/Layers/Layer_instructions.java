package mx.itesm.gcrusher.Layers;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

import mx.itesm.gcrusher.Activities.InstructionsControls;
import mx.itesm.gcrusher.Activities.InstructionsGameplay;
import mx.itesm.gcrusher.R;

public class Layer_instructions extends CCLayer{

	private static CGSize size = CCDirector.sharedDirector().displaySize();
	private CCSprite background;
	private CCLabel controls,gamePlay;
	private float fx,fy;
	
	public Layer_instructions(){
		fx = size.width / 800;
		fy = size.height/600;
		
		background = CCSprite.sprite("backgrounds/background_instructions.jpg");
		background.setPosition(size.width/2,size.height/2);
		
		background.setScaleX(size.width/background.getContentSize().width);
		background.setScaleY(size.height/background.getContentSize().height);
		
		ccColor3B black = ccColor3B.ccBLACK;
		
		controls = CCLabel.makeLabel("Controls", "fonts/shadows.ttf", 40);
		controls.setColor(black);
		controls.setPosition(size.width/4,size.height-size.height/2.5f);
		controls.setScaleX(fx);
		controls.setScaleY(fy);
		
		gamePlay = CCLabel.makeLabel("Gameplay", "fonts/shadows.ttf",40);
		gamePlay.setColor(black);
		gamePlay.setPosition(controls.getPosition().x,controls.getPosition().y-size.height/5);
		gamePlay.setScaleX(fx);
		gamePlay.setScaleY(fy);
		
		Context contexto = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().preloadEffect(contexto, R.raw.button_select);
		
		addChild(background);
		addChild(gamePlay);
		addChild(controls);
		
		setIsTouchEnabled(true);
		
	}
	
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// touch position		
		float x = event.getX();
		float y = event.getY();

		// point with position (0.0 in (n.0))
		CGPoint tempPosition = CGPoint.make(x, y);

		// point converted to normal scale
		CGPoint position = CCDirector.sharedDirector().convertToGL(tempPosition);
		float touchX = position.x,touchY = position.y;		
		
		if (touchX>=gamePlay.getPosition().x-gamePlay.getBoundingBox().size.width/2
			&& touchX<=gamePlay.getPosition().x+gamePlay.getBoundingBox().size.width/2
			&& touchY >=gamePlay.getPosition().y-gamePlay.getBoundingBox().size.height/2
			&& touchY <= gamePlay.getPosition().y+gamePlay.getBoundingBox().size.height/2){
						
			Log.d("YALEL", "CLICK");
			
			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.button_select);
			
			Intent intencion=new Intent(CCDirector.sharedDirector().getActivity(), InstructionsGameplay.class);
			CCDirector.sharedDirector().getActivity().startActivity(intencion);
			Log.d("YALEL", "INIT COMPLETED");
	
			
			return true;
		}
		
		if (touchX>=controls.getPosition().x-controls.getBoundingBox().size.width/2
			&& touchX<=controls.getPosition().x+controls.getBoundingBox().size.width/2
			&& touchY >=controls.getPosition().y-controls.getBoundingBox().size.height/2
			&& touchY <= controls.getPosition().y+controls.getBoundingBox().size.height/2){
			

			Context contexto = CCDirector.sharedDirector().getActivity();
			SoundEngine.sharedEngine().playEffect(contexto, R.raw.button_select);
			
			Intent intencion=new Intent(CCDirector.sharedDirector().getActivity(), InstructionsControls.class);
			CCDirector.sharedDirector().getActivity().startActivity(intencion);		
			
			return true;
			
		}
		
		
		return true;
	}
	

	
	
}
