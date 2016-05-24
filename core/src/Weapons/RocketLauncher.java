package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Bullets.RocketBullet;
import Entity.AnimatedSprite;
import Entity.Entity;
import GameScene.GameManager;
import HUD.ShootingHUD;
import Misc.Log;
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
		rateOfFire = 1.5f;
	}
	
	@Override
	public void shoot()
	{
		super.shoot();

		if(isShootingEnabled == false)
			return;

//		Log.e("RocketLauncher.java", "shooting and : " + isShootingEnabled);
		RocketBullet r = gameManager.bulletFactory.getRocketBullet();
		r.shoot(getShootingX(), getShootingY(), image.getRotation());

		isShootingEnabled = false;
//		Log.e("BaseGun.java", "isShootingEnabled = " + isShootingEnabled);
	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/rl.png", act.sceneManager.gameScene.disposeTextureArray));
	}

	@Override
	public void draw(Batch batch)
	{
		super.draw(batch);
	}
}
