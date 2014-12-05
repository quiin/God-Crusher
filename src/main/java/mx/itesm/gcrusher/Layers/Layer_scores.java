package mx.itesm.gcrusher.Layers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.util.Log;

import mx.itesm.gcrusher.Activities.MainMenu;


public class Layer_scores extends CCLayer{
		
	private static CGSize size = CCDirector.sharedDirector().displaySize();
	private CCSprite background;
	private static CCLabel first_rank,first_score,first_date,second_rank,second_score,second_date,third_rank,third_score,third_date,title_rank,title_score,title_date;
	private static final float TITLE_SEPARATION = size.width/5,Y_SEPARATION = size.height/10;
	private float fx,fy;
	 
	
	public Layer_scores(){
		
		fx = size.width / 800;
		fy = size.height/600;
		background = CCSprite.sprite("background_high_scores.jpg");
		background.setPosition(size.width/2,size.height/2);
		background.setScaleX(size.width/background.getContentSize().width);
		background.setScaleY(size.height/background.getContentSize().height);
		
		ccColor3B black = ccColor3B.ccBLACK;
		title_rank = CCLabel.makeLabel("RANK", "fuentes/shadows.ttf", 45);
		title_rank.setPosition(size.width/6,size.height-size.height/3);
		title_rank.setScaleX(fx);
		title_rank.setScaleY(fy);
		title_rank.setColor(black);
		
		title_score = CCLabel.makeLabel("SCORE", "fuentes/shadows.ttf", 45);
		title_score.setPosition(title_rank.getPosition().x+TITLE_SEPARATION,size.height-size.height/3);
		title_score.setScaleX(fx);
		title_score.setScaleY(fy);
		title_score.setColor(black);
		 
		title_date = CCLabel.makeLabel("DATE", "fuentes/shadows.ttf", 45);
		title_date.setPosition(title_score.getPosition().x+1.5f*TITLE_SEPARATION,size.height-size.height/3);
		title_date.setScaleX(fx);
		title_date.setScaleY(fy);
		title_date.setColor(black);
		
		first_rank = CCLabel.makeLabel("1.-", "fuentes/shadows.ttf", 45);
		first_rank.setPosition(title_rank.getPosition().x,title_rank.getPosition().y-Y_SEPARATION);
		first_rank.setScaleX(fx);
		first_rank.setScale(fy);
		
		first_score= CCLabel.makeLabel("0 ", "fuentes/shadows.ttf", 45);
		first_score.setPosition(title_score.getPosition().x,title_score.getPosition().y-Y_SEPARATION);
		first_score.setScaleX(fx);
		first_score.setScale(fy);		
		
		first_date = CCLabel.makeLabel("MM DD YY HH MM", "fuentes/shadows.ttf", 45);
		first_date.setPosition(title_date.getPosition().x+Y_SEPARATION,title_score.getPosition().y-Y_SEPARATION);
		first_date.setScaleX(fx);
		first_date.setScaleY(fy);
		
		second_rank = CCLabel.makeLabel("2.- ", "fuentes/shadows.ttf", 45);
		second_rank.setPosition(first_rank.getPosition().x,first_rank.getPosition().y-Y_SEPARATION);
		second_rank.setScaleX(fx);
		second_rank.setScaleY(fy);
		
		second_score = CCLabel.makeLabel("0 ", "fuentes/shadows.ttf", 45);
		second_score.setPosition(first_score.getPosition().x,first_score.getPosition().y-Y_SEPARATION);
		second_score.setScaleX(fx);
		second_score.setScaleY(fy);
		
		second_date = CCLabel.makeLabel("MM DD YY HH MM", "fuentes/shadows.ttf", 45);
		second_date.setPosition(first_date.getPosition().x,first_date.getPosition().y-Y_SEPARATION);
		second_date.setScaleX(fx);
		second_date.setScaleY(fy);
		
		third_rank = CCLabel.makeLabel("3.- ", "fuentes/shadows.ttf", 45);
		third_rank.setPosition(second_rank.getPosition().x,second_rank.getPosition().y-Y_SEPARATION);
		third_rank.setScaleX(fx);
		third_rank.setScaleY(fy);
		
		third_score = CCLabel.makeLabel("0 ", "fuentes/shadows.ttf", 45);
		third_score.setPosition(second_score.getPosition().x,second_score.getPosition().y-Y_SEPARATION);
		third_score.setScaleX(fx);
		third_score.setScaleY(fy);
		
		third_date = CCLabel.makeLabel("MM DD YY HH MM", "fuentes/shadows.ttf", 45);
		third_date.setPosition(second_date.getPosition().x,second_date.getPosition().y-Y_SEPARATION);
		third_date.setScaleX(fx);
		third_date.setScaleY(fy);
		 
		updateLabels();
		addChild(background);
		addChild(title_rank);
		addChild(title_score);
		addChild(title_date);
		addChild(first_rank);
		addChild(first_score);
		addChild(first_date);
		addChild(second_rank);
		addChild(second_score);
		addChild(second_date);
		addChild(third_rank);
		addChild(third_score);
		addChild(third_date);
				
	}
	
	public static void updateScore(String toUpdate){
		scoreCompare c = new scoreCompare();		
		Log.d("SCORE", "TRYING: "+toUpdate);
		ArrayList<String>scores  = MainMenu.score;
		int points = Integer.parseInt(toUpdate.split(" ")[0]);
		
		Collections.sort(scores,Collections.reverseOrder(c));
		
		Log.d("SCORE", "BEFORE UPDATE");
		for (int i=0;i<3;i++){
			Log.d("SCORE", ""+(i+1)+".- "+scores.get(i));
		}
		
		
		for (int i=0;i<3;i++){
			int currPoints = Integer.parseInt(scores.get(i).split(" ")[0]);
			
			if (points>currPoints){				
				String lastHigh = scores.get(i);
				Log.d("SCORE", "TRY: "+points);
				Log.d("SCORE", "CURRPOINTS: "+currPoints);
				Log.d("SCORE", "LASTHIGH: "+lastHigh+" i= "+i);
				if (i==0){					
					String mid = scores.get(1);
					scores.set(i+1, lastHigh);
					scores.set(i+2, mid);
				}else
					if (i==1)
						scores.set(i+1, lastHigh);
				
				scores.set(i, toUpdate);
				Log.d("SCORE", "UPDATED: "+i);
				break;																		
			}			
		}		
		
		Log.d("SCORE", "AFTER UPDATE");
		for (int i=0;i<3;i++){
			Log.d("SCORE", ""+(i+1)+".- "+scores.get(i));
		}
		MainMenu.score = scores;
		
	}

	private static void updateLabels() {
		ArrayList<String>scores  = MainMenu.score;			
				
		scoreCompare c = new scoreCompare();	
		Collections.sort(scores,Collections.reverseOrder(c));
		
		
		for (int i=0;i<3;i++){
			Log.d("SCORE", ""+(i+1)+".- "+scores.get(i));
		}
		
		for (int i=0;i<3;i++){
			String line = scores.get(i);
			String [] splitted = line.split(" ");
			String score = splitted[0];
			String date= "";
			
			for (int j=0;j<splitted.length;j++){
				if (j!=0){
					date+=splitted[j]+" ";
				}
			}
//			Log.d("SCORE", "SCORE: "+score);
//			Log.d("SCORE", "DATE: "+date);
			if (i==0){
				first_score.setString(score);
				first_date.setString(date);
			}
			if (i==1){
				second_score.setString(score);
				second_date.setString(date);
			}
			
			if (i==2){
				third_score.setString(score);
				third_date.setString(date);
			}
		}
				

		
	}

	static class scoreCompare implements Comparator<String>
    {
        public int compare(String one, String two) {        	
        	int scoreA = Integer.parseInt(one.substring(0, one.indexOf(" ")));
        	int scoreB = Integer.parseInt(two.substring(0, two.indexOf(" ")));
        						
            return Integer.compare(scoreA, scoreB);
        }
    }
}
