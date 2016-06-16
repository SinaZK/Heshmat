package Entity.GunUpgradeButtons;

import DataStore.CarStatData;
import DataStore.GunStatData;
import Entity.CarUpgradeButtons.CarUpgradeButton;
import Scene.Garage.GarageScene;
import Scene.Garage.GunSelectorTab;

/**
 * Created by sinazk on -/-/16.
 * 06:55
 */
public class FireRateUpgradeButton extends GunUpgradeButton
{
	public FireRateUpgradeButton(GunSelectorTab gunSelectorTab)
	{
		super(gunSelectorTab, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture1, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture2);

	}

	public void setGunStatData(GunStatData gunStatData)
	{
		super.setGunStatData(gunStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				FireRateUpgradeButton.this.gunStatData.fireRateLVL++;
			}
		});
	}
}
