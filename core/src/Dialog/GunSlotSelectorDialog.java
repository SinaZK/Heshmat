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
		backSprite = new Sprite(dialogManager.backGroundTexture);
	}

	public void loadResources()
	{
	}

	public void create(GarageScene garageScene, int carID, int slotID, ArrayList <String> availableGunSlots)
	{
		this.garageScene = garageScene;

		scene.getActors().clear();
		super.create();

		if(selectorButtons[carID][slotID] == null)
			selectorButtons[carID][slotID] = new ArrayList<CarGunSlotSelectorButton>();

		float DX = garageScene.DX;
		float DY = garageScene.DY;
		int padding = 50;
		int width = 50, height = 200;
		int startX = (int) (DX + (SceneManager.WORLD_X - (availableGunSlots.size() * width) - (availableGunSlots.size() - 1) * padding) / 2);
		int endX = startX + availableGunSlots.size() * (width + padding) - padding;

		int edgeW = 20, edgeH = 50;
		int backSpriteWidth = (endX - startX) + 2 * edgeW;

		backSprite.setSize(backSpriteWidth, height + 2 * edgeH);
		backSprite.setPosition(DX + (SceneManager.WORLD_X - backSprite.getWidth()) / 2, DY + (SceneManager.WORLD_Y - backSprite.getHeight()) / 2);

		exitButton.setPosition(backSprite.getX() + backSprite.getWidth() - exitButton.getWidth() - 10, backSprite.getY() + backSprite.getHeight() - exitButton.getHeight() - 10);

		if(selectorButtons[carID][slotID].size() != 0)
		{
			for(int i = 0;i < selectorButtons[carID][slotID].size();i++)
			{
				selectorButtons[carID][slotID].get(i).setCarID(carID, slotID);
				scene.addActor(selectorButtons[carID][slotID].get(i));
			}

			return;
		}


		for(int i = 0;i < availableGunSlots.size();i++)
		{
			CarGunSlotSelectorButton button = new CarGunSlotSelectorButton(garageScene, this, carID,
					GunSlotSorter.getGunSLotID(availableGunSlots.get(i)), slotID,
					garageScene.carSelectorTab.carSelectEntities[carID].sizakCarModel.slots.get(slotID).slotPrices.get(i));

			button.setCarID(carID, slotID);
			button.setSize(width, height);
			button.setPosition(startX + (i) * (width + padding), DY + (SceneManager.WORLD_Y - height) / 2);

			selectorButtons[carID][slotID].add(button);
			scene.addActor(button);
		}


//		Log.e("GunSlotSelectorDialog.java", "lastButtonEnd = " + lastButtonEnd + "startX = " + startX + " and Width = " + backSpriteWidth);
	}

}
