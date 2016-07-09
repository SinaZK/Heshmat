package Entity.BuyButtons;

import com.badlogic.gdx.graphics.g2d.Batch;

import DataStore.GunStatData;
import Dialog.DialogManager;
import Entity.Button;
import Enums.Enums;
import Misc.Log;
import Scene.Garage.GarageScene;
import Scene.Garage.GunSelectEntity;


/**
 * Created by sinazk on 6/17/16.
 * 07:29
 * a button for buy & unlocking Guns
 */
public class GunBuyButton extends Button
{
	DialogManager dialogManager;
	GunSelectEntity gunSelectEntity;
	GarageScene garageScene;
	GunStatData gunStatData;
	long price;

	boolean isWaitingForBuy;

	public GunBuyButton(final GarageScene garageScene, GunSelectEntity gunSelectEntity, final long price)
	{
		super(garageScene.BuyButtonTexture1, garageScene.BuyButtonTexture2);

		this.garageScene = garageScene;
		this.gunSelectEntity = gunSelectEntity;
		this.gunStatData = gunSelectEntity.gunStatData;
		this.dialogManager = this.garageScene.mSceneManager.dialogManager;
		this.price = price;

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				dialogManager.addBuyDialog(GunBuyButton.this.garageScene.DX, GunBuyButton.this.garageScene.DY, GunBuyButton.this.price);
				isWaitingForBuy = true;
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(gunStatData.lockStat == Enums.LOCKSTAT.LOCK)
		{
			super.draw(batch, parentAlpha);
			garageScene.act.font22.draw(batch, "" + price, getX() + 50, getY() + 35);
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
			gunStatData.lockStat = Enums.LOCKSTAT.UNLOCK;
			garageScene.act.saveGunDatas();
			dialogManager.popQ();
		}
	}
}
