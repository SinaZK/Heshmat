package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Bullets.RocketBullet;
import Entity.AnimatedSprite;
import Entity.Entity;
import GameScene.GameManager;
import Misc.TextureHelper;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

public class RocketLauncher extends BaseGun
{
	public RocketLauncher(MainActivity a, GameManager gm)
	{
		super(a, gm);

		loadResources();

		setPosition(100, 200);
		shootingPoint.set(200, 42);

	}
	
	@Override
	public void run()
	{
	}

	@Override
	public void shoot() 
	{
		RocketBullet r = gameManager.bulletFactory.getRocketBullet();
		r.shoot(getShootingX(), getShootingY(), image.getRotation());
	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/rl.png", act.sceneManager.gameScene.disposeTextureArray));
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		shoot();
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public void draw(Batch batch)
	{
		super.draw(batch);
	}
}
