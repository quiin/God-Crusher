package mx.itesm.gcrusher.Activities;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Highscores extends Activity {
	
	private CCGLSurfaceView glCcglSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//fullscreen window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	
//		setContentView(R.layout.activity_highscores);
		
		glCcglSurfaceView  = new CCGLSurfaceView(this);
		setContentView(glCcglSurfaceView);
		
	}
	
	
	protected void onStart() {		
		super.onStart();
		CCDirector.sharedDirector().attachInView(glCcglSurfaceView);
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		CCDirector.sharedDirector().setAnimationInterval(1.0/60);
		CCScene scene = SceneCreator.createScene_highScores();		
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	
	


}
