package Weapons;

import DataStore.GunStatData;
import GameScene.GameManager;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.NormalBullet;
import heshmat.MainActivity;

public class NormalGun extends BaseGun
{
	public NormalGun(MainActivity a, GameManager gm, int posID, GunSorter.GunType gunType) {
		super(a, gm);

		loadResources("gfx/weapons/" + posID + "/", null);
		this.gunType = gunType;
	}

	@Override
	public void shoot()
	{
		super.shoot();

		if(!isShootingEnabled || isReloading)
			return;

		NormalBullet p = gameManager.bulletFactory.getNormalBullet(this, gunType);
		p.shoot(getShootingX(), getShootingY(), image.getRotation());

		isShootingEnabled = false;
	}

}