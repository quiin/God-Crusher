
package mx.itesm.gcrusher.Activities;


import org.cocos2d.layers.CCScene;


import android.app.Activity;

import mx.itesm.gcrusher.Characters.Yalel;
import mx.itesm.gcrusher.Layers.Layer_HUD;
import mx.itesm.gcrusher.Layers.Layer_background;
import mx.itesm.gcrusher.Layers.Layer_controls;
import mx.itesm.gcrusher.Layers.Layer_instructions;
import mx.itesm.gcrusher.Layers.Layer_pause_menu;
import mx.itesm.gcrusher.Layers.Layer_scores;
import mx.itesm.gcrusher.Layers.Layer_tooltip;

public class SceneCreator extends Activity{
	

	 public static Yalel yalel;
	 

	 public static  CCScene createScene_mexico(){		 		
            yalel = new Yalel();
            CCScene sceneMexico = CCScene.node();
            Layer_background background = new Layer_background("backgrounds/Background_lvl1_1.jpg", "backgrounds/Background_lvl1_2.jpg");
            Layer_HUD hud = new Layer_HUD(yalel);
            Layer_controls controls = new Layer_controls(yalel,1);
            Layer_tooltip tip = new Layer_tooltip();
            Layer_pause_menu pause= new Layer_pause_menu();
            sceneMexico.addChild(background);
            sceneMexico.addChild(controls);
            sceneMexico.addChild(hud);
            sceneMexico.addChild(tip);
            sceneMexico.addChild(pause);
            return sceneMexico;
	}
	 
	 public static  CCScene createScene_egypt(){			 	
			CCScene sceneEgypt = CCScene.node();						
			Layer_background background = new Layer_background("backgrounds/Background_lvl2_1.jpg", "backgrounds/Background_lvl2_2.jpg");
			Layer_HUD hud = new Layer_HUD(yalel);
			Layer_controls controls = new Layer_controls(yalel,2);
			Layer_pause_menu pause= new Layer_pause_menu();								
			sceneEgypt.addChild(background);						
			sceneEgypt.addChild(controls);
			sceneEgypt.addChild(hud);
			sceneEgypt.addChild(pause);				
			return sceneEgypt;
		}
	 
	 public static  CCScene createScene_rlyeh(){				
			CCScene sceneRlyeh = CCScene.node();						
			Layer_background background = new Layer_background("backgrounds/Background_lvl3_1.jpg", "backgrounds/Background_lvl3_2.jpg");
			Layer_HUD hud = new Layer_HUD(yalel);
			Layer_controls controls = new Layer_controls(yalel,3);
			Layer_pause_menu pause= new Layer_pause_menu();					
			sceneRlyeh.addChild(background);										
			sceneRlyeh.addChild(controls);
			sceneRlyeh.addChild(hud);
			sceneRlyeh.addChild(pause);					
			return sceneRlyeh;
		}
	
	 public static CCScene createScene_highScores(){
		 CCScene scene = CCScene.node();
		 		
		 Layer_scores scores = new Layer_scores();
		 scene.addChild(scores);		 
		 return scene;		 
	 }
	
	 public static CCScene createScene_instructions(){
		 CCScene scene = CCScene.node();
		 Layer_instructions in = new Layer_instructions();
		 scene.addChild(in);
		 return scene;
	 }
}









	



