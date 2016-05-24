package GameScene;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import PhysicsFactory.PhysicsConstant;
import WeaponBase.BaseGun;
import Weapons.Pistol;
import Weapons.RocketLauncher;

/**
 * Created by sinazk on 5/23/16.
 * -
 */

public class GunManager
{
	GameScene gameScene;
	GameManager gameManager;

	public RocketLauncher rocketLauncher;
	public Pistol pistol;

	public static int MAX_GUNS = 2;
	int selectedGunNumber = 0;
	ArrayList<BaseGun> guns = new ArrayList<BaseGun>();

	public GunManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
	}

	public void create()
	{
		initGuns();
	}

	public void run()
	{
		if(gameManager.levelManager.levelMode == GameScene.LevelMode.Shooting)
			getSelectedGun().run();
		else
			getSelectedGun().isTouched = false;
	}

	public void draw(Batch batch)
	{
		if(gameManager.levelManager.levelMode == GameScene.LevelMode.Shooting)
			getSelectedGun().draw(gameScene.getBatch());
	}

	public void initGuns()
	{
		rocketLauncher = new RocketLauncher(gameScene.act, gameManager);
		pistol = new Pistol(gameScene.act, gameManager);

		guns.add(rocketLauncher);
		guns.add(pistol);
	}

	public void swapGun()
	{
		selectedGunNumber++;
		selectedGunNumber %= MAX_GUNS;

		gameScene.setInput();
	}

	public void setInput(InputMultiplexer inputMultiplexer)
	{
		inputMultiplexer.addProcessor(getSelectedGun());
	}

	public BaseGun getSelectedGun()
	{
		return guns.get(selectedGunNumber);
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	public void reposition(ShootingMode shootingMode)
	{
		for(int i = 0;i < MAX_GUNS;i++)
		{
			guns.get(i).rePosition(shootingMode.firstCarX * ratio, shootingMode.firstCarY * ratio);
		}
	}
}
