package BaseCar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import Entity.GunSlotButton;
import Misc.BodyStrings;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Scene.Garage.GarageScene;
import Sorter.GunSlotSorter;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/6/16.
 * 02:08
 * An addable slot to cars, it can be gun or nitro or ..
 */
public class CarSlot
{
	MainActivity activity;
	public ArrayList<String> availableGunSlots = new ArrayList<String>();

	BaseCar car;
	public float startX, startY, width, height;
	public float firstStartX, firstStartY;
	BaseGun gun;
	CzakBody body;
	GunSlotButton slotButton;
	public SlotType slotType;
	public int selectedGunSlot;//1Base
	public String attachedCarBodyName;

	public int carID;//in garageScene Car Selector Section
	public int slotID;//the position in SizakCarModel.slots

	public CarSlot(MainActivity activity)
	{
		this.activity = activity;
	}

	public void set(float sX, float sY, float w, float h)
	{
		startX = sX;
		startY = sY;
		width = w;
		height = h;
	}

	public void set(GarageScene garageScene, float sX, float sY, float w, float h, SlotType type, int selectedGunSlot, float firstSpriteX, float firstSpriteY,
					SizakCarModel carModel)
	{
		startX = sX;
		startY = sY;
		width = w;
		height = h;
		slotType = type;
		firstStartX = sX - firstSpriteX;
		firstStartY = sY - firstSpriteY;
		this.selectedGunSlot = selectedGunSlot;

		slotButton = new GunSlotButton(garageScene, this);
		garageScene.attachChild(slotButton);
	}

//	public void set(float sX, float sY, float w, float h, SlotType type)
//	{
//		startX = sX;
//		startY = sY;
//		width = w;
//		height = h;
//		slotType = type;
//	}

	public void setParentPositionID(int carID, int slotID)
	{
		this.carID = carID;
		this.slotID = slotID;
	}

	public void createOnGameScene(World world, BaseCar baseCar)
	{
		car = baseCar;


		if(slotType == SlotType.GUN)
			if(selectedGunSlot != 0)
			{
				gun = GunSlotSorter.getGunSlot(activity, selectedGunSlot);
				assert gun != null;
				gun.slotGunInitOnAttachCar();


				body = new CzakBody();
				Vector2 pos = getOnGameScenePos();
				body.setBody(PhysicsFactory.createBoxBody(world, pos.x, pos.y,
						gun.image.getWidth(), gun.image.getHeight(), BodyDef.BodyType.DynamicBody, gun.bodyDensity, gun.bodyElasticity, gun.bodyFriction));
				body.setUserData(BodyStrings.GUN_STRING);
				body.addSprite(gun.image);

				car.body.addBodyWithWeld(body, attachedCarBodyName, world);
			}
	}

	public void run()
	{
		if(slotType == SlotType.GUN && gun != null)
		{
			gun.run();
			gun.setPosition(gun.image.getX(), gun.image.getY());
		}
	}

	public Vector2 getOnGameScenePos()
	{
		float rat = PhysicsConstant.PIXEL_TO_METER;
		float carPosX = car.body.bodies.get(0).getmBody().getWorldCenter().x * rat - car.body.bodies.get(0).getmSprite().get(0).getWidth() / 2;
		float carPosY = car.body.bodies.get(0).getmBody().getWorldCenter().y * rat - car.body.bodies.get(0).getmSprite().get(0).getHeight() / 2;

		Vector2 ret = new Vector2(carPosX + firstStartX, carPosY + firstStartY);
		return ret;
	}

	public Vector2 getOnGameSceneWorldPos()
	{
		float rat = PhysicsConstant.PIXEL_TO_METER;
		float carPosX = car.body.bodies.get(0).getmSprite().get(0).getX();
		float carPosY = car.body.bodies.get(0).getmSprite().get(0).getY();

		Vector2 ret = new Vector2( (carPosX + firstStartX) / rat, (carPosY + firstStartY) / rat);
		return ret;
	}

	public void draw(Batch batch)
	{
		if(slotType == SlotType.GUN && selectedGunSlot != 0)
			gun.draw(batch);
	}

	public void setGun(BaseGun Gun)
	{
		gun = Gun;
		gun.setPosition(startX, startY);
	}

	public enum SlotType
	{
		GUN, ENGINE,
	}
}
