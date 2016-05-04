package GameScene;


import WeaponBase.BaseGun;
import WeaponBase.BulletFactory;
import Weapons.Pistol;
import Weapons.RocketLauncher;

public class GameManager
{
	public GameScene gameScene;
	public BulletFactory bulletFactory;

	public BaseGun selectedGun;
	public RocketLauncher rocketLauncher;
	public Pistol pistol;

	int selectedGunNumber = 0;

	GameManager(GameScene mScene)
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
