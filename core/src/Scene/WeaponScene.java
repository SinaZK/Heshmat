package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import SceneManager.SceneManager;

public class WeaponScene extends BaseScene
{
	SceneManager mSceneManager;
	public WeaponScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


	public float DX;
	public float DY;
	String add = "gfx/scene/main/";

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
	}

	@Override
	public void create()
	{
	}
	
	@Override
	public void run() 
	{
	}

	@Override
	public void dispose() 
	{
		super.dispose();
	}
}
