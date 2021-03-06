package Weapons;

import DataStore.GunStatData;
import Enums.Enums;
import GameScene.GameManager;
import Misc.Log;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.NormalBullet;
import heshmat.MainActivity;

public class NormalGun extends BaseGun
{
	public NormalGun(MainActivity a, GameManager gm, int posID, GunSorter.GunType gunType) {
		super(a, gm);

		this.gunType = gunType;
		loadResources("gfx/weapons/" + posID + "/", null);
	}

	@Override
	public void shoot()
	{
		super.shoot();

//		Log.e("NormalGun.java", "shoot : isShootingEnabled = " + isShootingEnabled + " & isReloading = " + isReloading);
//		Log.e("NormalGun.java", "shoot : isShootingEnabled = " + isShootingEnabled + " & isReloading = " + isReloading);
		if(!isShootingEnabled || isReloading)
			return;

		NormalBullet p = gameManager.bulletFactory.getNormalBullet(this, gunType);

		p.damage = getBulletDamage();
		p.hitPoint = getBulletHP();
		p.bulletTexture = bulletTexture;

		p.shoot(getShootingX(), getShootingY(), image.getRotation());
//		Log.e("NormalGun.java", "SHOOT");

		isShootingEnabled = false;
	}

}