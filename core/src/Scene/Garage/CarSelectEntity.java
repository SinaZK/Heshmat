package Scene.Garage;

import com.badlogic.gdx.graphics.g2d.Batch;

import DataStore.CarStatData;
import BaseCar.SizakCarModel;
import Entity.BuyButtons.CarBuyButton;
import Misc.Log;
import Sorter.CarSorter;

/**
 * Created by sinazk on 6/7/16.
 * 19:54
 */
public class CarSelectEntity
{
	GarageScene garageScene;
	CarSelectorTab carSelectorTab;

	public CarStatData carStatData;
	public SizakCarModel sizakCarModel;
	CarBuyButton carBuyButton;
	public int carID;//the position of this in the garageScene //1Base

	public CarSelectEntity(CarSelectorTab carSelectorTab, CarStatData carStatData, int id)
	{
		this.carSelectorTab = carSelectorTab;
		garageScene = carSelectorTab.garageScene;
		this.carStatData = carStatData;
		carID = id;

		sizakCarModel = new SizakCarModel(garageScene.act, this);
		sizakCarModel.loadFromCarFile(garageScene, "gfx/car/" + CarSorter.carPos[carID] + "/car.car", garageScene.disposeTextureArray);
		sizakCarModel.initUpgradeButtons(carStatData);

		carBuyButton = new CarBuyButton(garageScene, this, sizakCarModel.price, id);
		carSelectorTab.attachChild(carBuyButton);
	}

	public void setPosition(float x, float y)
	{
		sizakCarModel.setPosition(x, y);
		carBuyButton.setPosition(x + (sizakCarModel.carShowSprite.getWidth() - carBuyButton.getWidth()) / 2, y - 20 - carBuyButton.getHeight());
	}

	public void run()
	{

	}

	public void draw(Batch batch)
	{
		batch.begin();
		sizakCarModel.drawOnGarageScene(batch);
		batch.end();
	}

	public void select()
	{
		carSelectorTab.carUpgradeHUD.getActors().clear();

		for(int i = 0;i < sizakCarModel.upgradeButtons.size();i++)
			carSelectorTab.carUpgradeHUD.addActor(sizakCarModel.upgradeButtons.get(i));
	}

}
