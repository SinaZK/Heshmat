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

	ArrayList<CarGunSlotSelectorButton> [] selectorButtons = new ArrayList[SceneManager.CAR_NUM + 1];//1base

	public GunSlotSelectorDialog(DialogManager dialogManager)
	{
		super(dialogManager);

		loadResources();
	}

	public void loadResources()
	{

	}

	public void create(GarageScene garageScene, int carID, int slotID, ArrayList <String> availableGunSlots)
	{
		this.garageScene = garageScene;
		backSprite = new Sprite(dialogManager.backGroundTexture);

		if(selectorButtons[carID] == null)
			selectorButtons[carID] = new ArrayList<CarGunSlotSelectorButton>();

		if(selectorButtons[carID].size() != 0)
		{
			for(int i = 0;i < selectorButtons[carID].size();i++)
				selectorButtons[carID].get(i).setCarID(carID, slotID);

			return;
		}

		float DX = garageScene.DX;
		float DY = garageScene.DY;
		int padding = 50;
		int width = 50, height = 200;
		int startX = (SceneManager.WORLD_X - (SceneManager.GUN_SLOT_NUM * width) - (SceneManager.GUN_SLOT_NUM - 1) * padding) / 2;

		for(int i = 0;i < availableGunSlots.size();i++)
		{
			CarGunSlotSelectorButton button = new CarGunSlotSelectorButton(garageScene, this, GunSlotSorter.getGunSLotID(availableGunSlots.get(i)));
			button.setCarID(carID, slotID);
			button.setSize(width, height);
			button.setPosition(startX + (i - 1) * (width + padding), 200);

			attachChild(button);
			selectorButtons[carID].add(button);
		}

	}

}
