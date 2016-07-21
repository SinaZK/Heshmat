package BaseCar;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import java.util.ArrayList;

import DataStore.CarStatData;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.GameSceneInput;
import HUD.DrivingHUD;
import Misc.BodyStrings;
import Misc.Log;
import Physics.SizakBody;
import PhysicsFactory.PhysicsConstant;
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
	public ArrayList<AttachPart> attachParts = new ArrayList<AttachPart>();


	protected float MAX_HITPOINT;
	public float hitpoint;
	protected float collisionDamageRate;

	public int wheelNum;
	public boolean[] isWheelDrive;
	public WheelJoint[] wheelJoints;
	public float[] wheelTorque;
	public float[] wheelSpeed;

	public CarType carType;
	public float driverX, driverY, driverScale;
	public float rotationForce;
	public boolean isDead;
	public int contactManagerWheelCollisionCount = 0;

	public GameSceneInput gameSceneInput;
	public DrivingHUD HUD;
	public CarStatData carStatData;

	public Sound carSound;

	public BaseCar(GameManager gm, CarStatData carStatData)
	{
		gameManager = gm;
		gameScene = gm.gameScene;
		act = gm.gameScene.act;
		gameSceneInput = gm.gameScene.gameSceneInput;
		HUD = gameScene.drivingModeHUD;
		this.carStatData = carStatData;
	}

	public void create()
	{
	}

	public void draw(Batch spriteBatch)
	{
		drawBody(spriteBatch);
		drawSlots(spriteBatch);
		drawOnHUD(spriteBatch);
	}

	public void drawOnHUD(Batch batch)
	{

	}

	public void drawBody(Batch batch)
	{
		body.draw(batch);
	}

	public void drawSlots(Batch batch)
	{
		for (int i = 0; i < slots.size(); i++)
			slots.get(i).draw(batch);
	}

	public abstract void gas(float rate);

	public abstract void brake(float rate);

	public abstract void relax();
//			wheelJoint[i].enableMotor(true);
//			wheelJoint[i].setMaxMotorTorque(0.05f);
//			wheelJoint[i].setMotorSpeed(0);

	long carSoundID;
	public void loadCarSound(String path)
	{
		if(act.audioManager.IS_MUTE)
			return;

		carSound = Gdx.audio.newSound(Gdx.files.internal(path));

		if(act.settingStatData.isSoundOn)
		{
			carSoundID = carSound.play(0.2f);
			carSound.setLooping(carSoundID, true);
		}
	}

	public void stopSound()
	{
		carSound.stop();
	}

	public boolean isAutoPilot = false;
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		if(isAutoPilot)
		{
			gas(0.6f);
			return;
		}
		if(carSound != null)
			act.audioManager.playCarSound(carSound, carSoundID, getSpeedInMeter(), 20);

		if(groundContact > 0)
			groundContact--;

		if(!isLabeled)
		{
			label();
		}

		if(staticCount > 0)
		{
			staticCount--;
			if(staticCount == 0)
			{
				body.bodies.get(0).setType(BodyDef.BodyType.DynamicBody);
			}
		}

		if(gameManager.levelManager.levelModeEnum != GameScene.LevelModeEnum.Driving)
			shouldStop = true;

		if(shouldStop)
		{
			if(isStopped() && gameScene.gameManager.levelManager.currentLevel.getCurrentPart().mode == GameScene.LevelModeEnum.Driving)
				shouldStop = false;

			if(shouldStop && convertAngleToDegrees() >= -15 && convertAngleToDegrees() <= 15)
			{
				onStop();
				return;
			}
		}


		relax();

		if(isInAir())
			runOnAir(isGas, isBrake);
		else
			runOnGround(isGas, isBrake);

		for (int i = 0; i < slots.size(); i++)
			slots.get(i).run();

		if(gameManager.levelManager.currentLevel.getCurrentPart().isFinished == true &&
				gameManager.levelManager.currentLevel.getCurrentPart().isCameraDone == false)
		{
			onStop();
			return;
		}

//		Log.e("Tag", "Speed = " + getSpeedInMeter());
	}

	public void runOnAir(boolean isGasButtonPressed, boolean isBrakeButtonPressed)
	{
		if(isGasButtonPressed)
		{
			body.bodies.get(0).getmBody().applyTorque(rotationForce, true);
		}

		if(isBrakeButtonPressed)
		{
			body.bodies.get(0).getmBody().applyTorque(-rotationForce, true);
		}
	}

	public void runOnGround(boolean isGas, boolean isBrake)
	{
		float speed = getSpeedInMeter();
		float SWAP_LINE = 3;

		if(speed >= 0)
		{
			if(isGas)
				gas(1);
			else if(isBrake)
			{
				if(speed < SWAP_LINE)
					gas(-0.3f);//goingBackWard
				else
					brake(1);
			}
		} else if(speed < 0)
		{
			if(isBrake)
				gas(-0.3f);
			else if(isGas)
			{
				if(speed > -SWAP_LINE)
				{
					gas(1);
				} else brake(1);
			}
		}
	}

	public float getSpeedInMeter()
	{
		float vX = body.bodies.get(0).getmBody().getLinearVelocity().x;

		if(vX > 0)
			return body.bodies.get(0).getmBody().getLinearVelocity().len();

		return -body.bodies.get(0).getmBody().getLinearVelocity().len();
	}

	public float getSpeedInPixel()
	{
		return getSpeedInMeter() * PhysicsConstant.PIXEL_TO_METER;
	}

	boolean shouldStop;

	public void stop()
	{
		shouldStop = true;
	}

	public boolean isStopped()
	{
		for (int i = 0; i < body.bodies.size(); i++)
		{
			if(!isBodyStopped(body.bodies.get(i).getmBody(), 0.1f))
				return false;
		}

		float angle = convertAngleToDegrees();

		if(angle > 30 || angle < -30)
			return false;

		if(isInAir())
			return false;

		return true;
	}

	public float convertAngleToDegrees()
	{
		float angle = body.bodies.get(0).getmBody().getAngle();
		angle = (float) Math.toDegrees(angle);

		int q = (int) angle / 360;
		float q360 = q * 360;
		angle -= q360;

		return angle;
	}

	public float convertAngleToRadians()
	{
		return (float) Math.toRadians(convertAngleToDegrees());
	}

	public void onStop()
	{
		brake(1);
	}

	public boolean isBodyStopped(Body body, float rat)
	{
		return body.getLinearVelocity().len2() < rat;
	}

	public void dispose()
	{
		carSound.stop();
		carSound.dispose();
	}

	boolean isLabeled = false;

	public void label()
	{
		for (int i = 1; i < body.bodies.size(); i++)
			body.bodies.get(i).setUserData(BodyStrings.CAR_WHEEL_STRING + " " + i);

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

		for (int i = 0; i < carModel.slots.size(); i++)
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

	public int staticCount = 0;

	public void reset()
	{
		isAutoPilot = false;
		groundContact = GROUND_MAX;

		contactManagerWheelCollisionCount = 0;
		body.bodies.get(0).setType(BodyDef.BodyType.StaticBody);
		staticCount = 30;
		body.setAllBodiesV(0, 0);
		body.setCenterPosition(firstPosX, firstPosY, 0);

		hitpoint = getMaxHitPoint();
	}

	public void hitByGround(Contact contact, String CarBodyString, boolean isBegin, boolean isEnd)
	{
		if(isBegin)
			contactManagerWheelCollisionCount++;
		if(isEnd)
			contactManagerWheelCollisionCount--;
	}

	public float airCounter = 0;
	float AirLimitTime = 0.1f;

	public boolean isInAir()
	{
		if(contactManagerWheelCollisionCount <= 0)
		{
			contactManagerWheelCollisionCount = 0;
			airCounter += gameScene.getDeltaTime();

			if(airCounter >= AirLimitTime)
				return true;
		} else airCounter = 0;

		return false;
	}

	public boolean isWheel(String s)
	{
		return BodyStrings.getPartOf(s, 1).equals(BodyStrings.CAR_WHEEL_STRING);
	}

	public void hitByBullet(String bulletData)
	{
		int bulletID = BaseBullet.getBulletID(bulletData);
		damage(gameManager.bulletFactory.bullets.get(bulletID).damage);
	}

	public void hitByDrivingEnemy(Contact contact)
	{
		float intence = gameScene.GSCM.getContactIntense(contact);

		damage(intence);
//		Log.e("DrivingModeEn
	}

	public void damage(float damage)
	{
		hitpoint -= damage;
//		Log.e("BaseCar.java", "Hit damage = " + damage + " hp = " + hitpoint);
	}

	public float getXInPixel()
	{
		return body.bodies.get(0).getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;
	}

	public float getYInPixel()
	{
		return body.bodies.get(0).getmBody().getWorldCenter().y * PhysicsConstant.PIXEL_TO_METER;
	}


	int GROUND_MAX = 10;
	int groundContact = GROUND_MAX;

	public void handleWithGround(Contact contact)
	{
		if(groundContact > 0)
		{
			contact.setEnabled(false);
		}
	}

	public static float COLLISION_PERCENT = 10;
	public static float ENGINE_PERCENT = 10;
	public static float HIT_POINT_PERCENT = 10;

	public float getCollisionDamageRate()
	{
		return collisionDamageRate * calculatePercent(carStatData.collisionDamageLVL, COLLISION_PERCENT);
	}

	public float getMaxHitPoint()
	{
		return MAX_HITPOINT * calculatePercent(carStatData.hitPointLVL, HIT_POINT_PERCENT);
	}

	public float getWheelSpeed(float speed)
	{
		return speed * calculatePercent(carStatData.engineLVL, ENGINE_PERCENT);
	}

	public float getWheelMaxTorque(float torque)
	{
		return torque * calculatePercent(carStatData.engineLVL, ENGINE_PERCENT / 1);
	}

	public float calculatePercent(int level, float addingPercent)
	{
		return (float) Math.pow((100f + addingPercent) / 100f, level);
	}

	public void pause()
	{
		carSound.pause();
	}

	public void resume()
	{
//		carSound.pause(carSoundID);
	}
}
