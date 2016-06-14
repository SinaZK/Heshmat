package Sorter;

import BaseCar.CarLoader;
import SceneManager.SceneManager;
import WeaponBase.BaseGun;
import Weapons.GunSlots.Dooshka;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/10/16.
 * 00:55
 */
public class GunSlotSorter
{
	public static String [] gunSlotNames =
			{
					"NULL", "DOOSHKA"
			};
	public static BaseGun getGunSlot(MainActivity act, int id)
	{
		switch (id)
		{
			case 1:
				return new Dooshka(act);

		}

		return null;
	}

	public static int getGunSLotID(String gunSlotName)
	{
		for(int i = 1;i <= SceneManager.GUN_SLOT_NUM;i++)
			if(gunSlotName.equals(gunSlotNames[i]))
				return i;

		return -1;
	}
}
