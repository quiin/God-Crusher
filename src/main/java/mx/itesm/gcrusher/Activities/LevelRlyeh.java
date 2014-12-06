package mx.itesm.gcrusher.Activities;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Window;
import android.view.WindowManager;

public class LevelRlyeh extends Activity {

	private CCGLSurfaceView glCcglSurfaceView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		//fullscrean window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//setContentView(R.layout.activity_pantalla_juego);
		glCcglSurfaceView  = new CCGLSurfaceView(this);
		setContentView(glCcglSurfaceView);	
		
	}
	
	@Override
	protected void onStart() {		
		super.onStart();
		CCDirector.sharedDirector().attachInView(glCcglSurfaceView);
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		CCDirector.sharedDirector().setDisplayFPS(true);
		CCDirector.sharedDirector().setAnimationInterval(1.0/60);
		CCScene scene = SceneCreator.createScene_rlyeh();
		scene.setTag(35);
		CCDirector.sharedDirector().runWithScene(scene);		
	}
	
	@Override
	protected void onPause() { 
		super.onPause();				
		CCDirector.sharedDirector().pause();		
		SoundEngine.sharedEngine().pauseSound();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		CCDirector.sharedDirector().end();		
	}
	
	@Override
	public void onBackPressed() {		
		Intent intencion= new Intent(this,LevelSelection.class);
		startActivity(intencion);
		this.finish();
	}
	
	@Override
	protected void onResume() {	
		super.onResume();	
		CCDirector.sharedDirector().resume();		
	}


	

}
