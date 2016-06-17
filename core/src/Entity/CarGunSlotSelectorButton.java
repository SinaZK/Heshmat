package Entity;

import com.badlogic.gdx.graphics.g2d.Batch;

import DataStore.CarStatData;
import Dialog.GunSlotSelectorDialog;
import Enums.Enums;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/10/16.
 * 03:42
 * this button for adding to GunSlotSelectorDialog
 * it differs to GunSlotButton
 */
public class CarGunSlotSelectorButton extends Button
{
	GunSlotSelectorDialog dialog;
	GarageScene garageScene;
	int gunID;
	int carID;
	int carSlotID;
	long price;
	CarStatData carStatData;

	public boolean isWaitingForBuyDialog;

	public CarGunSlotSelectorButton(final GarageScene garageScene1, GunSlotSelectorDialog dialog1, int carId, final int gunId, int carSlotId, final long price)
	{
		super(garageScene1.carSelectorTab.gunSlots[gunId].showSprite.getTexture(), garageScene1.carSelectorTab.gunSlots[gunId].showSprite.getTexture());

//		Log.e("CarGunSlotSelectorButton.java", "Price = " + price);
		this.carSlotID = carSlotId;
		this.price = price;
		this.dialog = dialog1;
		this.garageScene = garageScene1;
		this.gunID = gunId;
		this.carID = carId;
		act = garageScene.act;
		carStatData = CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData;

		setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{

				if(CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData.gunSlotLockStats[carSlotID][gunID] == Enums.LOCKSTAT.LOCK)
				{
					isWaitingForBuyDialog = true;
					dialog.dialogManager.addBuyDialog(garageScene.DX, garageScene.DY, CarGunSlotSelectorButton.this.price);
				}
				else
				{
					CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].sizakCarModel.slots.get(carSlotID).selectedGunSlot = gunID;
					CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData.selectedGunSLots[carSlotID] = gunID;
					act.saveCarDatas();
					dialog.dialogManager.popQ();
				}
			}
		});
	}

	public void doOnBuyFinished()
	{
		dialog.dialogManager.buyDialog.isFinished = false;
		isWaitingForBuyDialog = false;

//		Log.e("CarGunSlotSelectorButton.java", "Buy completed: carID = " + carID + "SlotID = " + carSlotID + " gunID = " + gunID);

		if(dialog.dialogManager.buyDialog.isBought)
		{
			CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].sizakCarModel.slots.get(carSlotID).selectedGunSlot = gunID;
			CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData.selectedGunSLots[carSlotID] = gunID;
			CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData.gunSlotLockStats[carSlotID][gunID] = Enums.LOCKSTAT.UNLOCK;
			act.saveCarDatas();
			dialog.dialogManager.popQ();
			dialog.dialogManager.popQ();
		}
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);

		if(isWaitingForBuyDialog)
			if(dialog.dialogManager.buyDialog.isFinished)
			{
				doOnBuyFinished();
			}
	}

	public void setCarID(int carID, int carSlotID)
	{
		this.carID = carID;
		this.carSlotID = carSlotID;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(CarGunSlotSelectorButton.this.garageScene.carSelectorTab.carSelectEntities[carID].carStatData.gunSlotLockStats[carSlotID][gunID] == Enums.LOCKSTAT.LOCK)
			super.draw(batch, 0.5f);
		else
			super.draw(batch, 1);
	}

	//	public CarGunSlotSelectorButton(int id)
//	{
//		super(normalTex, clickedTex);
//	}
}
