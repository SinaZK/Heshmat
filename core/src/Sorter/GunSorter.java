package Sorter;

import com.badlogic.gdx.Game;

import GameScene.GameManager;
import Misc.Log;
import SceneManager.SceneManager;
import WeaponBase.BaseBullet;
import WeaponBase.BaseGun;
import Weapons.NormalGun;
import Weapons.RocketLauncher;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/7/16.
 * 19:40
 */
public class GunSorter
{

	public static int gunPos[] =
			{
					0,//   NULL

					1,//  Pistol
					3,//  MP5
					4,//  AK47
					5,//  SNIPER
					2,//  Rocket Launcher

			};//tayin konande tartib ine! gunPosByType daghighan bayad be hamun tartibe too file ha bian va bara jabajeyayi too garageScene (nabayad) avaz she meghdaresh

	public static GunType gunPosByType [] =
			{
					null,
					GunType.Pistol,
					GunType.MP5,
					GunType.AK47,
					GunType.SNIPER,
					GunType.RocketLauncher,
			};

	public static String [] BulletBodyStrings =
			{
					"NULL",
					"Pistol",
					"MP5",
					"AK47",
					"Sniper",
					"Rocket",
			};

	public static String getBulletBodyString(GunType gunType)
	{
		int id = getIDFromGunType(gunType);
		if(id == -1)
			return "NULL";

		return BulletBodyStrings[id];
	}

	public enum  GunType
	{
		Pistol, RocketLauncher, MP5, AK47, SNIPER,

		//GunSlots:
		DOOSHKA,

		//EnemyGuns:
		NormalEnemyGun,
	}

	public static int getFromGunPos(int id)
	{
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			if(gunPos[i] == id)
				return i;

		return -1;
	}

	public static int getIDFromGunType(GunType gunType)
	{
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			if(gunPosByType[i] == gunType)
				return i;

		return -1;
	}

	public static BaseGun createGunByType(GameManager gameManager, GunType gunType)
	{
		return createSelectedGun(gameManager, getIDFromGunType(gunType));
	}

	public static BaseGun createSelectedGun(GameManager gameManager, int pos)
	{
		int selectedGun = gunPos[pos];
		GunType gunType = gunPosByType[pos];

		switch (gunType)
		{
			case Pistol:
//				Log.e("GunSorter.java", "Creating Pistol");
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.Pistol);
			case MP5:
//				Log.e("GunSorter.java", "Creating MP5");
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.MP5);
			case AK47:
//				Log.e("GunSorter.java", "Creating AK47");
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.AK47);
			case SNIPER:
//				Log.e("GunSorter.java", "Creating SNIPER");
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.SNIPER);
			case RocketLauncher:
//				Log.e("GunSorter.java", "Creating ROCKET Launcher");
				return new RocketLauncher(gameManager.gameScene.act, gameManager);
		}

		return null;
	}

	public static BaseGun createEnemyGun(GameManager gameManager)
	{
		return new NormalGun(gameManager.gameScene.act, gameManager, 1, GunType.NormalEnemyGun);
	}

}
