package Entity;

import com.badlogic.gdx.graphics.g2d.Batch;

import BaseCar.CarSlot;
import DataStore.CarStatData;
import Dialog.DialogManager;
import Enums.Enums;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/6/16.
 * 02:48
 * A button for garageScene to choose which slot for the car! :))
 */
public class GunSlotButton extends Button
{
	GarageScene garageScene;
	DialogManager dialogManager;
	CarSlot slot;
	CarStatData carStatData;

	public GunSlotButton(final GarageScene garageScene, final CarSlot slott, CarStatData carStatData1)
	{
		super(garageScene.GunSlotButtonTexture1, garageScene.GunSlotButtonTexture2);
		this.garageScene = garageScene;
		this.slot = slott;
		this.carStatData = carStatData1;

		setPosition(slot.startX, slot.startY);
		setSize(slot.width, slot.height);

		dialogManager = garageScene.mSceneManager.dialogManager;

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				if(carStatData.lockStat == Enums.LOCKSTAT.LOCK)
					return;
				dialogManager.addGunSlotSelectorDialog(GunSlotButton.this.garageScene, slot.carID, slot.slotID, GunSlotButton.this.slot.availableGunSlots);
			}
		});
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);

		setPosition(slot.startX, slot.startY);
		setSize(slot.width, slot.height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(slot.selectedGunSlot == 0)
			super.draw(batch, parentAlpha);
		else
		{
			garageScene.carSelectorTab.gunSlots[slot.selectedGunSlot].setPosition(getX(), getY());
			garageScene.carSelectorTab.gunSlots[slot.selectedGunSlot].draw(batch);
		}
	}
}
