package mx.itesm.gcrusher.Activities;

import java.util.ArrayList;

import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import mx.itesm.gcrusher.R;

public class MainMenu extends Activity {	
	private MediaPlayer mp;	
	public static ArrayList<String> score = new ArrayList<String>();
	public static boolean soundEnabled = true ,effectsEnabled = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			
		setContentView(R.layout.activity_main_menu);

		
		/*SOUND*/
		mp = MediaPlayer.create(this, R.raw.button_select);								
		
		
		SharedPreferences pref = getSharedPreferences("scores", Context.MODE_PRIVATE);
	      if(score.isEmpty()){
	              score.add(pref.getString("primero", "0 "+"MM DD YY HH MM"));
	              score.add(pref.getString("segundo", "0 "+"MM DD YY HH MM"));
	              score.add(pref.getString("tercero", "0 "+"MM DD YY HH MM"));
	      }
					
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences pref = getSharedPreferences("scores", Context.MODE_PRIVATE);
		 if(score.isEmpty()){
             score.add(pref.getString("primero", ""));
             score.add(pref.getString("segundo", ""));
             score.add(pref.getString("tercero", ""));
     }
	}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences pref = getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("primero", score.get(2));
        editor.putString("segundo", score.get(1));
        editor.putString("tercero", score.get(0));
        editor.commit();
	}
	
	public void tootleEffects(View v){
		if (effectsEnabled){
			SoundEngine.sharedEngine().setEffectsVolume(0f);
			effectsEnabled=false;
		}else{
			mp.start();
			effectsEnabled=true;
			SoundEngine.sharedEngine().setEffectsVolume(1f);
		}
	}
	
	public void toogleSound(View v){
		mp.start();
		if (soundEnabled){
			SoundEngine.sharedEngine().setSoundVolume(0f);
			soundEnabled=false;		
		}else{			
			soundEnabled=true;
			SoundEngine.sharedEngine().setSoundVolume(1f);
		}
				
	}

	public void showInstructions(View v)//SHOW INSTRUCTIONS
	{	
		if (effectsEnabled)
			mp.start();
		Intent intencion=new Intent(this,Instructions.class );
		startActivity(intencion);
		
	}
	
	public void showAbout(View v)//SHOW INSTRUCTIONS
	{		
		if (effectsEnabled)
			mp.start();
		Intent intencion=new Intent(this,About.class );		
		startActivity(intencion);
		
		
	}

	public void showHighscores(View v)//SHOW INSTRUCTIONS
	{	if (effectsEnabled)
			mp.start();		
		Intent intencion=new Intent(this,Highscores.class );
		startActivity(intencion);		
		
	}
	
	public void showLevels(View v)//SHOW INSTRUCTIONS
	{
		if (effectsEnabled)
			mp.start();
		Intent intencion=new Intent(this,LevelSelection.class );
		startActivity(intencion);			
	}
	
	protected void onDestroy() {		
		super.onDestroy();		
		SoundEngine.sharedEngine().realesAllSounds();
		SoundEngine.sharedEngine().realesAllEffects();
	}
	
	


	

}
