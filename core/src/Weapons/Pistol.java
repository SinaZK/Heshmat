package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Entity.Entity;
import Misc.TextureHelper;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

public class Pistol extends BaseGun
{
	public Pistol(MainActivity a) {
		super(a);

		loadResources();

		setPosition(0, 0);
	}

	@Override
	public void run() {

	}

	@Override
	public void shoot() {

	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/pistol.png", act.sceneManager.gameScene.disposeTextureArray));
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("Tag", "pistol TouchDown");
		return super.touchDown(screenX, screenY, pointer, button);
	}
}