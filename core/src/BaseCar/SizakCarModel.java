package BaseCar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import BaseCar.CarLoader;
import BaseCar.CarSlot;
import DataStore.CarStatData;
import Entity.CarUpgradeButtons.CarUpgradeButton;
import Entity.CarUpgradeButtons.EngineUpgradeButton;
import Entity.CarUpgradeButtons.HitPointUpgradeButton;
import Misc.BodyStrings;
import Misc.Log;
import Physics.SizakBodyModel;
import Scene.Garage.CarSelectEntity;
import Scene.Garage.GarageScene;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/5/16.
 * 23:39
 *
 * It's just a model! just showing sprites at the positions without creating any body. no need to any world.
 */
public class SizakCarModel extends SizakBodyModel
{
	GarageScene garageScene;
	CarSelectEntity carSelectEntity;

	public ArrayList <CarSlot> slots = new ArrayList<CarSlot>();

	public ArrayList <CarUpgradeButton> upgradeButtons = new ArrayList<CarUpgradeButton>();
	public CarStatData carStatData;

	public long price;

	public SizakCarModel(MainActivity activity, CarSelectEntity carSelectEntity)
	{
		super(activity);
		this.carSelectEntity = carSelectEntity;
		carStatData = carSelectEntity.carStatData;
	}

	public void loadFromCarFile(GarageScene garageScene, String path, ArrayList<Texture> disposableArray)
	{
		this.garageScene = garageScene;
		FileHandle f = Gdx.files.internal(path);
		InputStream inputStream = f.read();
		BufferedReader dis = new BufferedReader(new InputStreamReader(inputStream));

		String read;
		try
		{
			read = dis.readLine();//price
			price = Long.valueOf(BodyStrings.getPartOf(read, 1));


			read = dis.readLine();

			loadSpritesFromBodyFile(BodyStrings.getPartOf(read, 1), disposableArray);

			read = dis.readLine();
			int wheelCount = Integer.valueOf(BodyStrings.getPartOf(read, 1));
			for(int i = 0;i < wheelCount;i++)
			{
				dis.readLine();
				dis.readLine();
				dis.readLine();
			}

			read = dis.readLine();
			int slotCount = Integer.parseInt(BodyStrings.getPartOf(read, 1));
			for(int i = 0;i < slotCount;i++)
			{
				slots.add(new CarSlot(activity));
				read = dis.readLine();

				if(BodyStrings.getPartOf(read, 0).equals(CarLoader.GUN_SLOT))
				{
					int slotCT = slots.size() - 1;
					float x1 = Float.parseFloat(BodyStrings.getPartOf(read, 1)) + sprites.get(0).getX();
					float y1 = Float.parseFloat(BodyStrings.getPartOf(read, 2)) + sprites.get(0).getY();
					float w = Float.parseFloat(BodyStrings.getPartOf(read, 3));
					float h = Float.parseFloat(BodyStrings.getPartOf(read, 4));
					String bodyName = BodyStrings.getPartOf(read, 5);

//					Log.e("SizakCarModel.java", "selected = " + carSelectEntity.carStatData.selectedGunSLots[slotCT + 1] + " slotCt = " + slotCT);
					slots.get(slotCT).set(garageScene, x1, y1, w, h, CarSlot.SlotType.GUN, carSelectEntity.carStatData.selectedGunSLots[slotCT],
							sprites.get(0).getX(), sprites.get(0).getY(), this);
					slots.get(slotCT).setParentPositionID(carSelectEntity.carID, slotCT);
					slots.get(slotCT).attachedCarBodyName = bodyName;

					while(true)
					{
						read = dis.readLine();

						if(read.equals(CarLoader.EOF))
							break;

						slots.get(slotCT).availableGunSlots.add(BodyStrings.getPartOf(read, 0));
						slots.get(slotCT).slotPrices.add(Long.valueOf(BodyStrings.getPartOf(read, 1)));

//						Log.e("SizakCarModel.java", "read ct = " + slotCT + " price = " + slots.get(slotCT).slotPrices.get(slots.get(slotCT).slotPrices.size() - 1));
					}//reading availableGunSlots
				}
			}

			dis.readLine();
			dis.readLine();//Upgrades

			while(true)
			{
				read = dis.readLine();

				if(read.equals(CarLoader.EOF))
					break;

				if(read.equals(CarLoader.UPGRADE_ENGINE))
				{
					upgradeButtons.add(new EngineUpgradeButton(garageScene));
				}

				if(read.equals(CarLoader.UPGRADE_HIT_POINT))
				{
					upgradeButtons.add(new HitPointUpgradeButton(garageScene));
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void drawOnGarageScene(Batch batch)
	{
		super.draw(batch);
	}

	@Override
	public void setPosition(float x, float y)
	{
		float xx = sprites.get(0).getX();
		float yy = sprites.get(0).getY();

		super.setPosition(x, y);

		for(int i = 0;i < slots.size();i++)
		{
			float x1 = slots.get(i).startX;
			float y1 = slots.get(i).startY;
			float w = slots.get(i).width;
			float h = slots.get(i).height;

			slots.get(i).set(x1 - xx + x, y1 - yy + y, w, h);
		}
	}

	public void initUpgradeButtons(CarStatData carStatData)
	{
		float startX = garageScene.DX + 80;
		float startY = garageScene.DY + 40;
		float width = 50;
		float height = 50;
		float padding = 20;

		for(int i = 0;i < upgradeButtons.size();i++)
		{
			upgradeButtons.get(i).setCarStatData(carStatData);
			upgradeButtons.get(i).setPosition(startX + (width + padding) * (i - 1), startY);
			upgradeButtons.get(i).setSize(width, height);
		}
	}
}
