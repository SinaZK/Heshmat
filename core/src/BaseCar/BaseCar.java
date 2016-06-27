package BaseCar;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import java.util.ArrayList;

import DataStore.CarStatData;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.GameSceneInput;
import HUD.DrivingHUD;
import Misc.Log;
import Physics.SizakBody;
import WeaponBase.BaseBullet;
import heshmat.MainActivity;

public abstract class BaseCar
{
	public enum CarType
	{
		MotorCycle, NormalCar, NoRoof
	}

	public MainActivity act;
	public GameScene gameScene;
	public GameManager gameManager;
	public SizakBody body;

	public ArrayList<CarSlot> slots = new ArrayList<CarSlot>();

	public float hitpoint;
	public float rotationFlipTorque;

	public int wheelNum;
	public boolean[] isWheelDrive;
	public WheelJoint [] wheelJoints;
	public float []wheelTorque;
	public float []wheelSpeed;

	public CarType carType;
	public Float CAR_FORWARDS_TORQUE;
	public Float CAR_FORWARDS_SPEED;
	public Float CAR_BRAKE_TORQUE;
	public Float CAR_REVERSE_TORQUE;
	public Float CAR_REVERSE_SPEED;
	public Float CAR_DOWN_FORCE;
	public Float CAR_BRAKE_FORCE;
	public boolean isDead;

	public float gunPosX, gunPosY;

	public GameSceneInput gameSceneInput;
	public DrivingHUD HUD;
	public CarStatData carStatData;

	public BaseCar(GameManager gm, CarStatData carStatData)
	{
		gameManager = gm;
		gameScene = gm.gameScene;
		act = gm.gameScene.act;
		gameSceneInput = gm.gameScene.gameSceneInput;
		HUD = gameScene.drivingModeHUD;
		this.carStatData = carStatData;
	}

	public void draw(Batch spriteBatch)
	{
		body.draw(spriteBatch);

		for(int i = 0;i < slots.size();i++)
			slots.get(i).draw(spriteBatch);
	}

	public abstract void gas(float rate);

	public abstract void brake(float rate);

	public abstract void relax();
//			wheelJoint[i].enableMotor(true);
//			wheelJoint[i].setMaxMotorTorque(0.05f);
//			wheelJoint[i].setMotorSpeed(0);

	public void run(boolean isGas, boolean isBrake, float rate)
	{
		if(!isLabeled)
		{
			label();
		}

		if(gameManager.levelManager.levelMode == GameScene.LevelMode.Shooting)
			shouldStop = true;

		if(shouldStop)
		{
			if(isStopped())
				shouldStop = false;

			brake(1);
			return;
		}
		if(isGas)
			gas(1f);

		if(isBrake)
			brake(1f);

		if(!isGas && !isBrake)
			relax();

		for(int i = 0;i < slots.size();i++)
			slots.get(i).run();
	}

	boolean shouldStop;
	public void stop()
	{
		shouldStop = true;
	}

	public boolean isStopped()
	{
		for(int i = 0;i < body.bodies.size();i++)
		{
			if(!isBodyStopped(body.bodies.get(i).getmBody()))
				return  false;
		}

		return true;
	}

	public boolean isBodyStopped(Body body)
	{
		return body.getLinearVelocity().len2() < 0.1f;
	}

	public void dispose(){}

	boolean isLabeled = false;
	public void label()
	{
		body.setCar();
		isLabeled = true;
	}

	public void addSlot(CarSlot slot)
	{
		slots.add(slot);
	}

	public void setFromCarModel(SizakCarModel carModel)
	{
		if(carModel == null)
			return;

		for(int i = 0;i < carModel.slots.size();i++)
		{
			CarSlot slot = carModel.slots.get(i);

			slot.createOnGameScene(gameScene.world, this);
			addSlot(slot);
		}
	}

	float firstPosX, firstPosY;
	public void setFirstPos(float x, float y)
	{
		firstPosX = x;
		firstPosY = y;
		body.setCenterPosition(x, y);
	}

	public void reset()
	{
//		Log.e("BaseCar.java", "Reset");
		body.setCenterPosition(firstPosX, firstPosY);
		body.setAllBodiesV(0, 0);
	}

	public void hitByBullet(String bulletData)
	{
		int bulletID = BaseBullet.getBulletID(bulletData);
		damage(gameManager.bulletFactory.bullets.get(bulletID).damage);
	}

	private void damage(float damage)
	{
		hitpoint -= damage;
		Log.e("BaseCar.java", "Hit damage = " + damage);
	}
}
