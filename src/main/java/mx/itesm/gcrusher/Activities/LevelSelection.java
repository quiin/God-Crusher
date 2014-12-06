package mx.itesm.gcrusher.Activities;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import mx.itesm.gcrusher.R;

public class LevelSelection extends Activity {
	public static boolean[] state = new boolean[]{false,false,false};
	MediaPlayer mexico,egypt,rlyeh,nope;	
//	static  MediaPlayer mp,nope;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
//		SoundEngine.sharedEngine().realesAllEffects();		
		//full screen window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_levels);

		mexico = MediaPlayer.create(this, R.raw.snake);
		egypt = MediaPlayer.create(this, R.raw.wolf);
		rlyeh = MediaPlayer.create(this, R.raw.laugh);
		nope = MediaPlayer.create(this, R.raw.unavaiable);
		
		View v = this.findViewById(R.id.layout);
		if (!state[0]){
			v.setBackgroundResource(R.drawable.background_level_selection_1);
		}else
			/*primer nivel terminado y segundo no*/
			if (state[0] && !state[1]){
				v.setBackgroundResource(R.drawable.background_level_selection_2);
			}else
				v.setBackgroundResource(R.drawable.background_level_selection_3);
		
	}

    @Override
    public void onBackPressed() {
        Intent intencion= new Intent(this, MainMenu.class);
        startActivity(intencion);
    }

    public void showLevelMexico(View v)//SHOW INSTRUCTIONS
	{	if (MainMenu.effectsEnabled)
		mexico.start();		
		Intent intencion=new Intent(this,LevelMexico.class );
		startActivity(intencion);
		this.finish();
	}
	public void showLevelRlyeh(View v)
	{	
		
		if (state[1]){
			rlyeh.start();
			Intent intencion=new Intent(this,LevelRlyeh.class );
			startActivity(intencion);
		}else
			nope.start();
		
					
	}
	public void showLevelEgypt(View v)
	{	
		if (state[0]){
			egypt.start();
			Intent intencion=new Intent(this,LevelEgypt.class );
			startActivity(intencion);
		}else
			nope.start();
	}
	
		
	

}
