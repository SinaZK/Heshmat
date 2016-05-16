package BaseCar;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import java.util.ArrayList;

import GameScene.GameManager;
import Physics.CzakBody;
import heshmat.MainActivity;

public class NormalCar
{
	/*public enum CarType
	{
		MotorCycle, NormalCar, NoRoof
	}

	;

	MainActivity act;
	GameManager gameManager;
	public CzakBody body;

	public Body ceilBody;
	public WeldJoint weldJoint;
	public FixtureDef collideWheelFixture;

	public float rotationFlipTorque;
	public float ATTR_WheelFriction;
	public float ATTR_SuspentionLength;
	public float ATTR_GasV;

	public boolean isWheelsDifferent;
	public Sprite[] wheelSprite;
	public int wheelNum;
	public boolean[] isWheelDrive;
	public WheelJoint[] wheelJoint;
	public CzakBody[] wheelBody;
	public Body cabinBody;

	public float upgradeSceneX;
	public float upgradeSceneY;

	public CarType carType;
	public Float CAR_FORWARDS_TORQUE;
	public Float CAR_FORWARDS_SPEED;
	public Float CAR_BRAKE_TORQUE;
	public Float CAR_REVERSE_TORQUE;
	public Float CAR_REVERSE_SPEED;
	public float gasTankFull;
	public float fuel;
	public Float CAR_DOWN_FORCE;
	public Float CAR_BRAKE_FORCE;
	public boolean isDead;

	static float FUEL_STEP = 0.02f;

	public float surfMultiplyer = 1;//if on Mud or no

	public int colorNum;
	public Color[] imgColors, shadeColors;

	public ArrayList<Texture> disposalTexture = new ArrayList<Texture>();

	public NormalCar(GameManager gm)
	{
		gameManager = gm;
		act = gm.gameScene.act;

		body = new CzakBody();
	}

	public float getWidth()
	{
		return body.getWidth();
	}

	public float getHeight()
	{
		return body.getHeight();
	}

	public void draw(SpriteBatch spriteBatch)
	{
		for (int i = 0; i < wheelNum; i++)
			wheelBody[i].draw(spriteBatch);

		body.draw(spriteBatch);
	}

	public void draw(Batch spriteBatch)
	{
		body.draw(spriteBatch, 0);

		for (int i = 0; i < wheelNum; i++)
			wheelBody[i].draw(spriteBatch);

		for (int i = 1; i < body.getmSprite().size(); i++)
			body.draw(spriteBatch, i);
	}

	public void setBody(Body b)
	{
		body.setBody(b);
	}

	public void loadWheelSprites()
	{
		if(isWheelsDifferent)
		{
			for (int i = 1; i <= wheelNum; i++)
			{
				Texture t = new Texture(Gdx.files.internal(wheelAddress + i + ".png"));
				disposalTexture.add(t);
				wheelSprite[i - 1] = new Sprite(t);
			}
			//			Log.e("Tag", "WHEEEEEELS ARE DIFF");
		} else
		{
			Texture t = new Texture(Gdx.files.internal(wheelAddress + ".png"));
			disposalTexture.add(t);
			for (int i = 1; i <= wheelNum; i++)
				wheelSprite[i - 1] = new Sprite(t);
		}
	}

	public void gas(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
			if(isWheelDrive[i] && fuel > 0)
			{
//				Log.e("Tag", "i = " + i + " isWheelDrive");
				fuel -= FUEL_STEP;
				wheelJoint[i].enableMotor(true);
				wheelJoint[i].setMaxMotorTorque(rate * CAR_FORWARDS_TORQUE + upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTorque);
				wheelJoint[i].setMotorSpeed(rate * -CAR_FORWARDS_SPEED - upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTireSpeed);
			} else
				wheelJoint[i].enableMotor(false);
		}

	}

	public void brake(float rate)
	{

		for (int i = 0; i < wheelNum; i++)
		{

			if((body.getmBody().getLinearVelocity().len() > 1 &&
					body.getmBody().getLinearVelocity().x > 0) || fuel == 0)
			{
				wheelJoint[i].enableMotor(true);
				wheelJoint[i].setMaxMotorTorque(100);
				wheelJoint[i].setMotorSpeed(0);


				if(fuel > 0)
				{
					float FORCE = 10;
					double teta = body.getmBody().getAngle();
					body.getmBody().applyForceToCenter(-FORCE * (float) Math.cos(teta), FORCE * (float) Math.sin(teta), true);
				}
			} else
			{
				if(isWheelDrive[i] && fuel > 0)
				{
					fuel -= FUEL_STEP;
					wheelJoint[i].enableMotor(true);
					wheelJoint[i].setMaxMotorTorque(rate * CAR_REVERSE_TORQUE + upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTorque);
					wheelJoint[i].setMotorSpeed(rate * -CAR_REVERSE_SPEED + upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTireSpeed);
				} else
					wheelJoint[i].enableMotor(false);
			}
		}

	}

	public void relax()
	{
		for (int i = 0; i < wheelNum; i++)
		{
			wheelJoint[i].enableMotor(true);
			wheelJoint[i].setMaxMotorTorque(0.05f);
			wheelJoint[i].setMotorSpeed(0);
		}
	}

	public void run(boolean isGas, boolean isBrake, float rate)
	{
		if(fuel <= 0)
		{
			fuel = 0;
			act.sceneManager.gameScene.carGasTimer.scheduleTask(new Task()
			{

				@Override
				public void run()
				{
					if(fuel == 0)
						act.sceneManager.gameScene.shouldEndGame = true;
				}
			}, 3f);
		}

		if(isDead)
		{
			isBrake = false;
			isGas = false;
		}

		if(isGas)
		{
			gas(rate);
		} else if(isBrake)
			brake(rate);
		else
			relax();

		//		Log.e("Tag", "Speed = " + body.getmBody().getLinearVelocity().x);

//		float FORCE = CAR_DOWN_FORCE / 10;
//		double teta = body.getmBody().getAngle() - Math.PI / 2;
//		for(int i = 0;i < wheelNum;i++)
//			wheelBody[i].getmBody().applyForceToCenter(-FORCE * (float)Math.cos(teta), FORCE * (float)Math.sin(teta), true);
	}

	public void dispose()
	{
		//		Log.e("Tag", "Disposing BaseCar : " + disposalTexture.size());
		for (int i = 0; i < disposalTexture.size(); i++)
			disposalTexture.get(i).dispose();

		disposalTexture.removeAll(disposalTexture);
	}

	public float getMotorSpeed()
	{
		for (int i = 0; i < wheelNum; i++)
			if(isWheelDrive[i])
				return -CAR_FORWARDS_SPEED - upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTireSpeed;

		return 1;
	}

	public float getMaxMotorSpeed()
	{
		for (int i = 0; i < wheelNum; i++)
			if(isWheelDrive[i])
				return -CAR_FORWARDS_SPEED - act.priceManager.sz1 * upgrade.upgradeStep.engineTireSpeed;

		return 1;
	}

	public float getMotorTorque()
	{
		for (int i = 0; i < wheelNum; i++)
			if(isWheelDrive[i])
				return CAR_FORWARDS_TORQUE + upgrade.carATTLVLs.engineLVL * upgrade.upgradeStep.engineTorque;

		return 1;
	}

	public float getMaxMotorTorque()
	{
		for (int i = 0; i < wheelNum; i++)
			if(isWheelDrive[i])
				return CAR_FORWARDS_TORQUE + act.priceManager.sz1 * upgrade.upgradeStep.engineTorque;

		return 1;
	}
	*/
}
