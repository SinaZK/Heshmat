package Entity;

import Dialog.GunSlotSelectorDialog;
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

	public CarGunSlotSelectorButton(final GarageScene garageScene, GunSlotSelectorDialog dialog1, int id)
	{
		super(garageScene.gunSlots[id].showSprite.getTexture(), garageScene.gunSlots[id].showSprite.getTexture());

		this.dialog = dialog1;
		this.garageScene = garageScene;
		this.gunID = id;
		act = garageScene.act;

		setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				CarGunSlotSelectorButton.this.garageScene.carSelectEntities[carID].sizakCarModel.slots.get(carSlotID).selectedGunSlot = gunID;
				act.carStatDatas[carID].selectedGunSLots[carSlotID] = gunID;
				act.saveCarDatas();
				dialog.dialogManager.popQ();
			}
		});
	}

	public void setCarID(int carID, int carSlotID)
	{
		this.carID = carID;
		this.carSlotID = carSlotID;
	}

//	public CarGunSlotSelectorButton(int id)
//	{
//		super(normalTex, clickedTex);
//	}
}
