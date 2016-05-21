package BaseCar;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.GameSceneInput;
import HUD.DrivingHUD;
import Physics.CzakBody;
import Physics.SizakBody;
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

	public GameSceneInput gameSceneInput;
	public DrivingHUD HUD;

	public BaseCar(GameManager gm)
	{
		gameManager = gm;
		gameScene = gm.gameScene;
		act = gm.gameScene.act;
		gameSceneInput = gm.gameScene.gameSceneInput;
		HUD = gameScene.drivingModeHUD;
	}

	public void draw(Batch spriteBatch)
	{
		body.draw(spriteBatch);
	}

	public abstract void gas(float rate);

	public abstract void brake(float rate);

	public abstract void relax();
//			wheelJoint[i].enableMotor(true);
//			wheelJoint[i].setMaxMotorTorque(0.05f);
//			wheelJoint[i].setMotorSpeed(0);

	public void run(boolean isGas, boolean isBrake, float rate)
	{
		if(isGas)
			gas(1f);

		if(isBrake)
			brake(1f);

		if(!isGas && !isBrake)
			relax();
	}

	public void dispose(){}
}
