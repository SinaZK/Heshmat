package Sorter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import BaseCar.BaseCar;
import BaseCar.CarLoader;
import GameScene.GameManager;
import SceneManager.SceneManager;
import WeaponBase.BaseGun;

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


			};

	public static int getFromGunPos(int id)
	{
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			if(gunPos[i] == id)
				return i;

		return -1;
	}

	public static BaseGun createSelectedGun(int pos)
	{
		int selectedGun = gunPos[pos];

		switch (selectedGun)
		{
			case 1:

			case 2:
		}

		return null;
	}

}
