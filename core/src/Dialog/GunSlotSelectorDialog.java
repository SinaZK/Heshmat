package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import Entity.CarGunSlotSelectorButton;
import Misc.Log;
import Scene.Garage.GarageScene;
import SceneManager.SceneManager;
import Sorter.GunSlotSorter;

/**
 * Created by sinazk on 6/10/16.
 * 03:04
 * a dialog for selecting which GunSlot selected! at GarageScene
 */
public class GunSlotSelectorDialog extends Dialog
{
	GarageScene garageScene;

	ArrayList<CarGunSlotSelectorButton> [][] selectorButtons = new ArrayList[SceneManager.CAR_NUM + 1][5];//1base

	public GunSlotSelectorDialog(DialogManager dialogManager)
	{
		super(dialogManager);
	}

	public void loadResources()
	{
	}

	public void create(GarageScene garageScene, int carID, int slotID, ArrayList <String> availableGunSlots)
	{
		this.garageScene = garageScene;
		backSprite = new Sprite(dialogManager.backGroundTexture);

		scene.getActors().clear();
		super.create();

		if(selectorButtons[carID][slotID] == null)
			selectorButtons[carID][slotID] = new ArrayList<CarGunSlotSelectorButton>();

		Log.e("GunSlotSelectorDialog.java", "1selectorButtons sz = " + selectorButtons[carID][slotID].size());

		if(selectorButtons[carID][slotID].size() != 0)
		{
			for(int i = 0;i < selectorButtons[carID][slotID].size();i++)
			{
				selectorButtons[carID][slotID].get(i).setCarID(carID, slotID);
				scene.addActor(selectorButtons[carID][slotID].get(i));
			}

			return;
		}

		float DX = garageScene.DX;
		float DY = garageScene.DY;
		int padding = 50;
		int width = 50, height = 200;
		int startX = (SceneManager.WORLD_X - (SceneManager.GUN_SLOT_NUM * width) - (SceneManager.GUN_SLOT_NUM - 1) * padding) / 2;

		for(int i = 0;i < availableGunSlots.size();i++)
		{
			CarGunSlotSelectorButton button = new CarGunSlotSelectorButton(garageScene, this, carID,
					GunSlotSorter.getGunSLotID(availableGunSlots.get(i)), slotID,
					garageScene.carSelectorTab.carSelectEntities[carID].sizakCarModel.slots.get(slotID).slotPrices.get(i));

			Log.e("GunSlotSelectorDialog.java", "passing price : " + garageScene.carSelectorTab.carSelectEntities[carID].sizakCarModel.slots.get(slotID).slotPrices.get(i));

			button.setCarID(carID, slotID);
			button.setSize(width, height);
			button.setPosition(startX + (i - 1) * (width + padding), 200);

			selectorButtons[carID][slotID].add(button);
			scene.addActor(button);
		}

		Log.e("GunSlotSelectorDialog.java", "2selectorButtons sz = " + selectorButtons[carID][slotID].size());

	}

}
