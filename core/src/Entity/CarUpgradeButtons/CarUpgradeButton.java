package Entity.CarUpgradeButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import DataStore.CarStatData;
import Entity.*;
import Enums.Enums;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/6/16.
 * 07:16
 * A button for upgrading cars in garageScene!
 */
public class CarUpgradeButton extends Button
{
	GarageScene garageScene;
	CarStatData carStatData;
	public CarUpgradeButton(GarageScene garageScene, Texture normalTex, Texture clickedTex)
	{
		super(normalTex, clickedTex);

		this.garageScene = garageScene;
	}

	public void setCarStatData(CarStatData carStatData)
	{
		this.carStatData = carStatData;
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);

		if(carStatData.lockStat == Enums.LOCKSTAT.LOCK)
			setVisible(false);
		else
			setVisible(true);
	}
}
