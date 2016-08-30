package BaseCar;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

import java.util.ArrayList;

import DataStore.CarStatData;
import Entity.Button;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.FileLoader;
import Misc.TextureHelper;
import Physics.SizakBodyLoader;
import PhysicsFactory.PhysicsConstant;

public class NormalCar extends BaseCar
{
	Button gasButton, brakeButton;

	public NormalCar(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);

		gasButton = new Button(TextureHelper.loadTexture("gfx/scene/game/gas1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/gas2.png", gameScene.disposeTextureArray));
		gasButton.setPosition(gameScene.DX + 500, gameScene.DY + -90);


		brakeButton = new Button(TextureHelper.loadTexture("gfx/scene/game/brake1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/brake2.png", gameScene.disposeTextureArray))
		{
			@Override
			public void setSize(float width, float height)
			{
				float w = img.getTexture().getWidth();
				float h = img.getTexture().getHeight();

				super.setSize(width, height);
				clickedSprite.setSize(w, h);
				normalSprite.setSize(w, h);
			}
		};
		brakeButton.setPosition(gameScene.DX - 100, gameScene.DY + -90);

		HUD.addActor(gasButton);
		HUD.addActor(brakeButton);
	}

	@Override
	public void gas(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
			if(wheelJoints[i] == null)
				continue;
			if(isWheelDrive[i])
			{
				wheelJoints[i].enableMotor(true);
				wheelJoints[i].setMotorSpeed(-getWheelSpeed(wheelSpeed[i]) * rate);
				wheelJoints[i].setMaxMotorTorque(getWheelMaxTorque(wheelTorque[i]));
			} else
				wheelJoints[i].enableMotor(false);
		}
	}

	@Override
	public void brake(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
			if(wheelJoints[i] == null)
				continue;

			wheelJoints[i].enableMotor(true);
			wheelJoints[i].setMotorSpeed(0);
			wheelJoints[i].setMaxMotorTorque(20);
		}
	}



	@Override
	public void relax()
	{
		for (int i = 0; i < wheelNum; i++)
			if(isWheelDrive[i])
				if(wheelJoints[i] != null)
					wheelJoints[i].enableMotor(false);
	}

	@Override
	public void draw(Batch spriteBatch)
	{
		super.draw(spriteBatch);

		run(gameSceneInput.isKeyPressed(Input.Keys.UP) | gasButton.isClicked, gameSceneInput.isKeyPressed(Input.Keys.DOWN) | brakeButton.isClicked, 1);
	}

	public void drawOnHUD(SpriteBatch batch)
	{
		batch.end();

		batch.begin();
	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		super.run(isGas, isBrake, rate);

		if(gameManager.levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
		{
			gasButton.isClicked = false;
			brakeButton.isClicked = false;
		}

		for (int i = 0; i < slots.size(); i++)
			slots.get(i).run();
	}

	public FileLoader load(String path, GameManager gameManager, World world, ArrayList<Texture> disposableArray)
	{
		FileLoader loader = new FileLoader();
		loader.loadFile(path);

		driverX = loader.getFloat(0, 1);
		driverY = loader.getFloat(0, 2);
		driverScale = loader.getFloat(0, 3);
		rotationForce = loader.getFloat(1, 1);

		MAX_HITPOINT = loader.getFloat(3, 1);
		hitpoint = getMaxHitPoint();
		collisionDamageRate = loader.getFloat(4, 1);

		body = SizakBodyLoader.loadBodyFile(loader.getString(5, 1), world, disposableArray);

		wheelNum = loader.getInt(6, 1);

		isWheelDrive = new boolean[wheelNum];
		wheelJoints = new WheelJoint[wheelNum];
		wheelSpeed = new float[wheelNum];
		wheelTorque = new float[wheelNum];

		int lineNumber = 7;

		for (int i = 0; i < wheelNum; i++)
		{
			String jointName = BodyStrings.getPartOf(loader.getLine(lineNumber), 1);

			wheelJoints[i] = (WheelJoint) body.getJointByName(jointName);

			isWheelDrive[i] = Boolean.valueOf(BodyStrings.getPartOf(loader.getLine(lineNumber), 2));

			wheelTorque[i] = Float.valueOf(BodyStrings.getPartOf(loader.getLine(lineNumber + 1), 1));
			wheelSpeed[i] = Float.valueOf(BodyStrings.getPartOf(loader.getLine(lineNumber + 2), 1));

			lineNumber += 3;
		}

		create();

		return loader;
	}

	@Override
	public void onStop()
	{
		for (int i = 0; i < wheelNum - 1; i++)
		{
			wheelJoints[i].enableMotor(true);
			wheelJoints[i].setMaxMotorTorque(100);
			wheelJoints[i].setMotorSpeed(0);
		}

		{
			int i = wheelNum - 1;
			wheelJoints[i].enableMotor(true);
			wheelJoints[i].setMaxMotorTorque(5);
			wheelJoints[i].setMotorSpeed(0);
		}
	}
}
