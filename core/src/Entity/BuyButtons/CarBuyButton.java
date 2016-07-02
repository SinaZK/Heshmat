package Entity.BuyButtons;

import com.badlogic.gdx.graphics.g2d.Batch;

import DataStore.CarStatData;
import Dialog.DialogManager;
import Entity.Button;
import Enums.Enums;
import Misc.Log;
import Scene.Garage.CarSelectEntity;
import Scene.Garage.GarageScene;


/**
 * Created by sinazk on 6/17/16.
 * 07:04
 * a button for buy & unlocking Cars
 */
public class CarBuyButton extends Button
{
	DialogManager dialogManager;
	CarSelectEntity carSelectEntity;
	GarageScene garageScene;
	CarStatData carStatData;
	long price;

	boolean isWaitingForBuy;

	public CarBuyButton(final GarageScene garageScene, CarSelectEntity carSelectEntity, final long price)
	{
		super(garageScene.BuyButtonTexture1, garageScene.BuyButtonTexture2);

		this.garageScene = garageScene;
		this.carSelectEntity = carSelectEntity;
		this.carStatData = carSelectEntity.carStatData;
		this.dialogManager = this.garageScene.mSceneManager.dialogManager;
		this.price = price;

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				dialogManager.addBuyDialog(CarBuyButton.this.garageScene.DX, CarBuyButton.this.garageScene.DY, CarBuyButton.this.price);
				isWaitingForBuy = true;
			}
		});

		setSize(40, 40);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(carStatData.lockStat == Enums.LOCKSTAT.LOCK)
		{
			garageScene.act.font22.draw(batch, "price = " + price, getX(), getY());
			super.draw(batch, parentAlpha);
		}
		else
			setVisible(false);
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);

		if(isWaitingForBuy)
			if(dialogManager.buyDialog.isFinished)
				doOnBuyFinished();
	}

	public void doOnBuyFinished()
	{
		isWaitingForBuy = false;
		dialogManager.buyDialog.isFinished = false;

		if(dialogManager.buyDialog.isBought)
		{
			carStatData.lockStat = Enums.LOCKSTAT.UNLOCK;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}
}