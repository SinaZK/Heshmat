package Entity.GunUpgradeButtons;

import com.badlogic.gdx.graphics.Texture;

import DataStore.CarStatData;
import DataStore.GunStatData;
import Entity.Button;
import Scene.Garage.GarageScene;
import Scene.Garage.GunSelectorTab;

/**
 * Created by sinazk on 6/6/16.
 * 07:16
 * A button for upgrading cars in garageScene!
 */
public class GunUpgradeButton extends Button
{
	GarageScene garageScene;
	GunSelectorTab gunSelectorTab;
	GunStatData gunStatData;
	public GunUpgradeButton(GunSelectorTab gunSelectorTab, Texture normalTex, Texture clickedTex)
	{
		super(normalTex, clickedTex);

		this.gunSelectorTab = gunSelectorTab;
		this.garageScene = gunSelectorTab.garageScene;
	}

	public void setGunStatData(GunStatData gunStatData)
	{
		this.gunStatData = gunStatData;
	}
}
