package GameScene;


import Enemy.Pigeon;
import EnemyBase.EnemyFactory;
import Entity.HPBarSprite;
import Misc.Log;
import WeaponBase.BaseGun;
import WeaponBase.BulletFactory;
import Weapons.Pistol;
import Weapons.RocketLauncher;

public class GameManager
{
	public GameScene gameScene;
	public BulletFactory bulletFactory;
	public EnemyFactory enemyFactory;

	public BaseGun selectedGun;
	public RocketLauncher rocketLauncher;
	public Pistol pistol;

	public HPBarSprite hpBarSprite;

	int selectedGunNumber = 0;

	GameManager(GameScene mScene)
	{
		gameScene = mScene;
		initGuns();
		selectedGun = pistol;
		loadResources();
		bulletFactory = new BulletFactory(gameScene.act, gameScene);
		enemyFactory = new EnemyFactory(gameScene.act, gameScene);
		hpBarSprite = new HPBarSprite("gfx/hpbar.png", 7, 1, gameScene.disposeTextureArray);
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
		enemyFactory.run();
	}

	public void draw()
	{
		selectedGun.draw(gameScene.getBatch());
		enemyFactory.draw(gameScene.getBatch());
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
