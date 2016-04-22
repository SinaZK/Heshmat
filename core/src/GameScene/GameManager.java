package GameScene;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import WeaponBase.BaseGun;
import Weapons.Pistol;
import Weapons.RocketLauncher;

public class GameManager
{
	public GameSceneNormal gameScene;

	BaseGun selectedGun;
	RocketLauncher rocketLauncher;
	Pistol pistol;

	int selectedGunNumber = 0;

	GameManager(GameSceneNormal mScene)
	{
		gameScene = mScene;

		initGuns();

		selectedGun = pistol;
	}

	public void loadResources(){}

	public void initGuns()
	{
		rocketLauncher = new RocketLauncher(gameScene.act);
		pistol = new Pistol(gameScene.act);
	}

	public void run()
	{
		selectedGun.run();
	}

	public void draw()
	{
		selectedGun.draw(gameScene.getBatch());
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
