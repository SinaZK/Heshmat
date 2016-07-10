package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Bullets.RocketBullet;
import DataStore.GunStatData;
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

		loadResources("gfx/weapons/6/", null);
	}
	
	@Override
	public void shoot()
	{
		super.shoot();

		if(isShootingEnabled == false || isReloading)
			return;

//		Log.e("RocketLauncher.java", "shooting and : " + isShootingEnabled);
		RocketBullet r = gameManager.bulletFactory.getRocketBullet(this);
		r.shoot(getShootingX(), getShootingY(), image.getRotation());

		isShootingEnabled = false;

		reload();
//		Log.e("BaseGun.java", "isShootingEnabled = " + isShootingEnabled);
	}


}
