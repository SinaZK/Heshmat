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
					2,//  Magnum
					3,//  UZI
					4,//  AK47
					5,//  SNIPER
					6,//  Rocket Launcher

			};//tayin konande tartib ine! gunPosByType daghighan bayad be hamun tartibe too file ha bian va bara jabajeyayi too garageScene (nabayad) avaz she meghdaresh

	public static GunType gunPosByType [] =
			{
					null,
					GunType.Pistol,
					GunType.Magnum,
					GunType.UZI,
					GunType.AK47,
					GunType.SNIPER,
					GunType.RocketLauncher,
			};

	public static String [] BulletBodyStrings =
			{
					"NULL",
					"Pistol",
					"Magnum",
					"UZI",
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
		Pistol, Magnum, RocketLauncher, UZI, AK47, SNIPER,

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
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.Pistol);
			case Magnum:
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.Magnum);
			case UZI:
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.UZI);
			case AK47:
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.AK47);
			case SNIPER:
				return new NormalGun(gameManager.gameScene.act, gameManager, selectedGun, GunType.SNIPER);
			case RocketLauncher:
				return new RocketLauncher(gameManager.gameScene.act, gameManager);
		}

		return null;
	}

	public static BaseGun createEnemyGun(GameManager gameManager)
	{
		return new NormalGun(gameManager.gameScene.act, gameManager, 1, GunType.NormalEnemyGun);
	}

}
