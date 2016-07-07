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
import Misc.Log;
import Misc.TextureHelper;
import Physics.SizakBodyLoader;

public class NormalCar extends BaseCar
{
	Button gasButton, brakeButton;

	public NormalCar(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);

		gasButton = new Button(TextureHelper.loadTexture("gfx/scene/game/gas1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/gas2.png", gameScene.disposeTextureArray));
		gasButton.setPosition(600, 10);

		brakeButton = new Button(TextureHelper.loadTexture("gfx/scene/game/brake1.png", gameScene.disposeTextureArray), TextureHelper.loadTexture("gfx/scene/game/brake2.png", gameScene.disposeTextureArray));
		brakeButton.setPosition(50, 10);

		HUD.addActor(gasButton);
		HUD.addActor(brakeButton);
	}

	@Override
	public void gas(float rate)
	{
		float additionalSpeed = carStatData.engineLVL * 20f;
		for (int i = 0; i < wheelNum; i++)
		{
			if(isWheelDrive[i])
			{
				wheelJoints[i].enableMotor(true);
				wheelJoints[i].setMotorSpeed(-(wheelSpeed[i] + additionalSpeed));
				wheelJoints[i].setMaxMotorTorque(wheelTorque[i]);
			}
		}
	}

	@Override
	public void brake(float rate)
	{
		for (int i = 0; i < wheelNum; i++)
		{
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

		gameScene.HUD.getBatch().begin();
		gameScene.font24Gold.draw(gameScene.HUD.getBatch(), "carHP = " + gameManager.selectedCar.hitpoint, 10, 300);
		gameScene.HUD.getBatch().end();

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

//			NormalCar retCar = new NormalCar(gameManager, carStatData);

		hitpoint = loader.getFloat(1, 1);
		collisionDamageRate = loader.getFloat(2, 1);

		body = SizakBodyLoader.loadBodyFile(loader.getString(3, 1), world, disposableArray);

		wheelNum = loader.getInt(4, 1);

		isWheelDrive = new boolean[wheelNum];
		wheelJoints = new WheelJoint[wheelNum];
		wheelSpeed = new float[wheelNum];
		wheelTorque = new float[wheelNum];

		int lineNumber = 5;

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
}
