package Scene.Garage;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

import BaseCar.SizakCarModel;
import DataStore.CarStatData;
import DataStore.GunStatData;
import Entity.BuyButtons.GunBuyButton;
import Enums.Enums;
import Misc.Log;
import Sorter.CarSorter;
import WeaponBase.BaseGun;
import WeaponBase.GunModel;

/**
 * Created by sinazk on -/-/16.
 * 05:44
 */
public class GunSelectEntity
{
	GarageScene garageScene;
	GunSelectorTab gunSelectorTab;

	public GunStatData gunStatData;
	public GunModel gunModel;
	public GunBuyButton gunBuyButton;
	public int gunID;//the position of this in the garageScene //1Base

	public GunSelectEntity(GunSelectorTab gunSelectorTab, GunStatData gunStatData, int id)
	{
		this.gunSelectorTab = gunSelectorTab;
		garageScene = gunSelectorTab.garageScene;
		this.gunStatData = gunStatData;
		gunID = id;

		gunModel = new GunModel(gunSelectorTab, id);
		gunModel.initUpgradeButtons(gunStatData);

		gunBuyButton = new GunBuyButton(garageScene, this, gunModel.price);
		this.gunSelectorTab.attachChild(gunBuyButton);
	}

	public void setPosition(float x, float y)
	{
		gunModel.setPosition(x, y);
		gunBuyButton.setPosition(x + (gunModel.showSprite.getWidth() - gunBuyButton.getWidth()) / 2, y - 20 - gunBuyButton.getHeight());
	}

	public void setSize(float w, float h) { gunModel.setSize(w, h);}

	public void run()
	{

	}

	public void draw(Batch batch)
	{
		batch.begin();

		if(gunStatData.lockStat == Enums.LOCKSTAT.LOCK)
			gunModel.showSprite.setAlpha(0.5f);
		else
			gunModel.showSprite.setAlpha(1.0f);

		gunModel.draw(batch);
		batch.end();
	}

	public void select()
	{
		gunSelectorTab.gunUpgradeHUD.getActors().clear();

		for(int i = 0;i < gunModel.upgradeButtons.size();i++)
			gunSelectorTab.gunUpgradeHUD.addActor(gunModel.upgradeButtons.get(i));
	}

}
