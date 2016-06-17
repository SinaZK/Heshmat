package Sorter;

import com.badlogic.gdx.Game;

import GameScene.GameManager;
import SceneManager.SceneManager;
import WeaponBase.BaseGun;
import Weapons.Pistol;
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
					2,//  Rocket Launcher


			};//tayin konande tartib ine! gunPosByType daghighan bayad be hamun tartibe too file ha bian va bara jabajeyayi too garageScene (nabayad) avaz she meghdaesh

	public static GunType gunPosByType [] =
			{
					null,
					GunType.Pistol,
					GunType.RocketLauncher,
			};

	public enum  GunType
	{
		Pistol, RocketLauncher
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
		GunType gunType = gunPosByType[selectedGun];

		switch (gunType)
		{
			case RocketLauncher:
				return new RocketLauncher(gameManager.gameScene.act, gameManager);
			case Pistol:
				return new Pistol(gameManager.gameScene.act, gameManager);
		}

		return null;
	}

}
