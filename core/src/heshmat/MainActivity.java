package heshmat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import Audio.AudioManager;
import GoogleServices.IGoogleServices;
import PurchaseIAB.purchaseIAB;
import SceneManager.SceneManager;

public class MainActivity extends ApplicationAdapter 
{
	
	public AudioManager audioManager;
	public SceneManager sceneManager;
	
	public IGoogleServices googleServices;
	public purchaseIAB.IABInterface purchaseHelper;

	public MainActivity(IGoogleServices googleServices, purchaseIAB.IABInterface mHelper)
	{
		purchaseHelper = mHelper;
		this.googleServices = googleServices;
	}
	
	@Override
	public void create () 
	{
		Gdx.input.setCatchBackKey(true);

		sceneManager = new SceneManager(this, purchaseHelper);
		sceneManager.setCurrentScene(SceneManager.SCENES.GAME_SCENE);
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sceneManager.run();
	}
}
