package mx.itesm.gcrusher.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import org.cocos2d.sound.SoundEngine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mx.itesm.gcrusher.Layers.Layer_controls;
import mx.itesm.gcrusher.Layers.Layer_scores;
import mx.itesm.gcrusher.R;

/**
 * Created by quiin on 12/5/14.
 */
public class Win extends Activity{

    private TextView yourLabel,yourScore,highLabel,highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscrean window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SoundEngine.sharedEngine().playSound(this, R.raw.keyboard_cat2,false);
        setContentView(R.layout.activity_win);
        yourLabel = (TextView)findViewById(R.id.lbl_score);
        highLabel = (TextView)findViewById(R.id.lbl_highest);
        yourScore = (TextView)findViewById(R.id.nbr_score);
        highScore = (TextView)findViewById(R.id.nbr_highest);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shadows.ttf");
        yourLabel.setTypeface(typeface);
        highLabel.setTypeface(typeface);
        yourScore.setTypeface(typeface);
        highScore.setTypeface(typeface);
        yourScore.setText(""+ Layer_controls.final_score);

        ArrayList<String>scores = MainMenu.score;
        scoreCompare c = new scoreCompare();
        Collections.sort(scores, Collections.reverseOrder(c));
        int max =0;
        for(int i=0;i<3;i++){
            int currPoints = Integer.parseInt(scores.get(i).split(" ")[0]);
            if (currPoints>max)
                max = currPoints;
        }
        highScore.setText(""+max);
    }

    @Override
    public void onBackPressed() {
        SoundEngine.sharedEngine().realesAllSounds();
        SoundEngine.purgeSharedEngine();
        Intent intent= new Intent(this, LevelSelection.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SoundEngine.sharedEngine().realesAllEffects();
        SoundEngine.purgeSharedEngine();
    }

    public void next(View v){
        SoundEngine.sharedEngine().realesAllSounds();
        SoundEngine.purgeSharedEngine();
        Intent intent= new Intent(this, LevelSelection.class);
        startActivity(intent);
    }
    
    
    public void retry(View v){
        int level = Layer_controls.level;
        Intent intent;
        SoundEngine.sharedEngine().realesAllSounds();
        SoundEngine.purgeSharedEngine();
        switch (level){
            case 1:
                intent= new Intent(this, LevelMexico.class);
                startActivity(intent);        
                break;
            case 2:
                intent= new Intent(this, LevelEgypt.class);
                startActivity(intent);
                break;
            case 3:
                intent= new Intent(this, LevelRlyeh.class);
                startActivity(intent);
                break;
            default:
                intent= new Intent(this, LevelSelection.class);
                startActivity(intent);
                break;
        }
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundEngine.sharedEngine().realesAllEffects();
        SoundEngine.purgeSharedEngine();
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

