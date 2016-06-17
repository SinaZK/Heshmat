package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import Bullets.PistolBullet;
import DataStore.GunStatData;
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

		loadResources("gfx/weapons/1/", null);
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
	public void setUpgrade(GunStatData gunStatData)
	{
	}
}