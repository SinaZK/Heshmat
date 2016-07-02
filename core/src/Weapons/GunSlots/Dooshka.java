package Weapons.GunSlots;

import Entity.Button;
import GameScene.GameScene;
import HUD.ShootingHUD;
import Misc.Log;
import Misc.TextureHelper;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.NormalBullet;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/10/16.
 * 00:58
 */
public class Dooshka extends BaseGun
{
	public Dooshka(MainActivity a)
	{
		super(a, null);

		loadResources("gfx/car/slots/gun/dooshka/", act.sceneManager.currentBaseScene.disposeTextureArray);//loading in garageScene

		Log.e("Dooshka.java", "creating dooshka");
	}

	@Override
	public void shoot()
	{
		super.shoot();

		if(!isShootingEnabled || isReloading)
			return;

		NormalBullet p = gameManager.bulletFactory.getNormalBullet(this, GunSorter.GunType.Pistol);
		p.shoot(getShootingX(), getShootingY(), image.getRotation());

		isShootingEnabled = false;
	}

	@Override
	public void slotGunInitOnAttachCar()
	{
		super.slotGunInitOnAttachCar();

		GameScene gameScene = act.sceneManager.gameScene;

		Button testButton = new Button(TextureHelper.loadTexture("gfx/scene/game/restart1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/restart2.png", gameScene.disposeTextureArray));
		testButton.setPosition(200, 300);
		testButton.setSize(70, 70);
		gameScene.drivingModeHUD.addActor(testButton);

		testButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				shoot();
				Log.e("Dooshka.java", "HOLAAAAAAAAAAA");
			}
		});
	}
}
