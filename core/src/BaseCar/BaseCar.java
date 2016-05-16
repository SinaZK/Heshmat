package BaseCar;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import GameScene.GameManager;
import Physics.CzakBody;
import heshmat.MainActivity;

public class BaseCar
{
	public enum CarType
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
	public Float CAR_DOWN_FORCE;
	public Float CAR_BRAKE_FORCE;
	public boolean isDead;

	public float surfMultiplyer = 1;//if on Mud or no

	public int colorNum;
	public Color[] imgColors, shadeColors;

	public BaseCar(GameManager gm)
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

	public void draw(Batch spriteBatch)
	{
		body.draw(spriteBatch, 0);

		for (int i = 0; i < wheelNum; i++)
			wheelBody[i].draw(spriteBatch);

		for (int i = 1; i < body.getmSprite().size(); i++)
			body.draw(spriteBatch, i);
	}

	public void gas(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
			if(isWheelDrive[i])
			{
				wheelJoint[i].enableMotor(true);
				wheelJoint[i].setMaxMotorTorque(rate * CAR_FORWARDS_TORQUE);
				wheelJoint[i].setMotorSpeed(rate * -CAR_FORWARDS_SPEED);
			} else
				wheelJoint[i].enableMotor(false);
		}
	}

	public void brake(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
			if((body.getmBody().getLinearVelocity().len() > 1 &&
					body.getmBody().getLinearVelocity().x > 0))
			{
				wheelJoint[i].enableMotor(true);
				wheelJoint[i].setMaxMotorTorque(100);
				wheelJoint[i].setMotorSpeed(0);


				float FORCE = 10;
				double teta = body.getmBody().getAngle();
				body.getmBody().applyForceToCenter(-FORCE * (float) Math.cos(teta), FORCE * (float) Math.sin(teta), true);
			} else
			{
				if(isWheelDrive[i])
				{
					wheelJoint[i].enableMotor(true);
					wheelJoint[i].setMaxMotorTorque(rate * CAR_REVERSE_TORQUE);
					wheelJoint[i].setMotorSpeed(rate * -CAR_REVERSE_SPEED);
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
		if(isGas)
			gas(1f);

		if(isBrake)
			brake(1f);
	}

	public void dispose(){}
}
