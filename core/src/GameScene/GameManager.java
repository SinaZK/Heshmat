package GameScene;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import javax.security.auth.login.LoginException;

import Misc.Log;
import WeaponBase.BaseGun;
import WeaponBase.BulletFactory;
import Weapons.Pistol;
import Weapons.RocketLauncher;

public class GameManager
{
	public GameSceneNormal gameScene;
	public BulletFactory bulletFactory;

	public BaseGun selectedGun;
	RocketLauncher rocketLauncher;
	Pistol pistol;

	int selectedGunNumber = 0;

	GameManager(GameSceneNormal mScene)
	{
		gameScene = mScene;
		initGuns();
		selectedGun = pistol;
		loadResources();
		bulletFactory = new BulletFactory(gameScene.act, gameScene);
	}

	public void loadResources(){}

	public void initGuns()
	{
		rocketLauncher = new RocketLauncher(gameScene.act, this);
		pistol = new Pistol(gameScene.act, this);
	}

	public void run()
	{
		selectedGun.run();
		bulletFactory.run();
	}

	public void draw()
	{
		selectedGun.draw(gameScene.getBatch());
		bulletFactory.draw(gameScene.getBatch());
	}

	public void swapGun()
	{
		selectedGunNumber++;
		selectedGunNumber %= 2;

		switch (selectedGunNumber)
		{
			case 0:
				selectedGun = pistol;
				break;

			case 1:
				selectedGun = rocketLauncher;
				break;
		}

		gameScene.setInput();
	}

}
