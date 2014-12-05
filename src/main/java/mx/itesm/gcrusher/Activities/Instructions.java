package mx.itesm.gcrusher.Activities;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class Instructions extends Activity {
	
//	MediaPlayer mp;
	private CCGLSurfaceView glCcglSurfaceView;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//fullscrean window				
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
		CCScene scene = SceneCreator.createScene_instructions();		
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//fullscrean window
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_instructions);
//		
////		mp = MediaPlayer.create(this, R.raw.button_select);
//	}
	
	
//	public void showControls(View v)
//	{
////		mp.start();
//		Intent intencion=new Intent(this,InstructionsControls.class);
//		startActivity(intencion);
//		
//		
//	}
//	public void showgameplay(View v)
//	{
//		mp.start();
//		Intent intencion=new Intent(this,Gameplay.class);
//		startActivity(intencion);
//	}
//	
	

}
