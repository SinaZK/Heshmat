package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import Bullets.PistolBullet;
import Entity.Entity;
import GameScene.GameManager;
import Misc.Log;
import Misc.TextureHelper;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

public class Pistol extends BaseGun
{
	public Pistol(MainActivity a, GameManager gm) {
		super(a, gm);

		loadResources();

		setPosition(100, 250);
		shootingPoint.set(169, 100);
		rateOfFire = 5;
	}

	@Override
	public void shoot()
	{
		super.shoot();

		if(!isShootingEnabled)
			return;

		PistolBullet p = gameManager.bulletFactory.getPistolBullet();
		p.shoot(getShootingX(), getShootingY(), image.getRotation());

		isShootingEnabled = false;
	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/pistol.png", act.sceneManager.gameScene.disposeTextureArray));
	}

}