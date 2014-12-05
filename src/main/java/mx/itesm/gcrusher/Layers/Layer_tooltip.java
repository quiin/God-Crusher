package mx.itesm.gcrusher.Layers;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class Layer_tooltip extends CCLayer {

	public static  CCSprite swipe,background;
	public static CCLabel shoot,jump;
	public static boolean shootPressed,jumpPressed,swipeDone;
	private CGSize SIZE = CCDirector.sharedDirector().displaySize();
	float fx = SIZE.width /800,fy = SIZE.height / 480;
	private CGPoint original = CGPoint.make(SIZE.width/2,SIZE.height/2);
	
	
	public Layer_tooltip(){
		background = CCSprite.sprite("background_pause.png");
		background.setScaleX(SIZE.width/background.getContentSize().width);
		background.setScaleY(SIZE.height/background.getContentSize().height);
		background.setPosition(SIZE.width/2,SIZE.height/2);
		background.setVisible(true);
		
		swipe = CCSprite.sprite("tooltip_swipe.png");
		swipe.setScaleX(fx);
		swipe.setScaleY(fy);
		swipe.setPosition(SIZE.width/2,SIZE.height/2);
		swipe.setVisible(false);
		
		jump = CCLabel.makeLabel("Jump", "fuentes/shadows.ttf", 40);
		jump.setScaleX(fx);
		jump.setScaleY(fy);
		jump.setPosition(SIZE.width / 10, SIZE.height / 3.5f);
		jump.setVisible(true);
		
		
		shoot = CCLabel.makeLabel("Shoot", "fuentes/shadows.ttf", 40);
		shoot.setScaleX(fx);
		shoot.setScaleY(fy);
		shoot.setPosition(SIZE.width - SIZE.width / 10, SIZE.height / 3.5f);
		shoot.setVisible(true);
				
		
		addChild(background);
		addChild(jump);
		addChild(shoot);
		addChild(swipe);
		
		animateSwipe();
	}


	public void animateSwipe() {
				
		CCMoveTo moveDown = CCMoveTo.action(2f, CGPoint.make(swipe.getPosition().x, SIZE.height/10f));
		CCCallFuncN resetPosition = CCCallFuncN.action(this, "resetPosition");
		CCSequence seq = CCSequence.actions(moveDown, resetPosition);
		swipe.runAction(CCRepeatForever.action(seq));
	}
	
	public void resetPosition(Object obj){
		((CCSprite)obj).setPosition(original);
	}
	
}
