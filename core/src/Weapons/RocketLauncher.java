package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Entity.Entity;
import Misc.TextureHelper;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

public class RocketLauncher extends BaseGun
{
	public RocketLauncher(MainActivity a)
	{
		super(a);

		loadResources();

		setPosition(100, 200);
	}
	
	@Override
	public void run()
	{
	}

	@Override
	public void shoot() 
	{
	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/rl.png", act.sceneManager.gameScene.disposeTextureArray));
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Gdx.app.log("Tag", "RocketLauncher TouchUp");
		return super.touchUp(screenX, screenY, pointer, button);
	}
}
