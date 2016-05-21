package BaseCar;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;

import Entity.Button;
import GameScene.GameManager;
import HUD.DrivingHUD;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import heshmat.MainActivity;

public class NormalCar extends BaseCar
{
	Button gasButton, brakeButton;
	public NormalCar(GameManager gm)
	{
		super(gm);

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
		for(int i = 0;i < wheelNum;i++)
		{
			if(isWheelDrive[i])
			{
				wheelJoints[i].enableMotor(true);
				wheelJoints[i].setMotorSpeed(-wheelSpeed[i]);
				wheelJoints[i].setMaxMotorTorque(wheelTorque[i]);
			}
		}
	}

	@Override
	public void brake(float rate)
	{
		for(int i = 0;i < wheelNum;i++)
		{
			wheelJoints[i].enableMotor(true);
			wheelJoints[i].setMotorSpeed(0);
			wheelJoints[i].setMaxMotorTorque(20);
		}
	}

	@Override
	public void relax()
	{
		for(int i = 0;i < wheelNum;i++)
			if(isWheelDrive[i])
				wheelJoints[i].enableMotor(false);
	}

	@Override
	public void draw(Batch spriteBatch)
	{
		super.draw(spriteBatch);

		run(gameSceneInput.isKeyPressed(Input.Keys.UP) | gasButton.isClicked, gameSceneInput.isKeyPressed(Input.Keys.DOWN) | brakeButton.isClicked, 1);
	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		super.run(isGas, isBrake, rate);



//		Log.e("NormalCar.java", "isGas = " + isGas + " isBrake = " + isBrake);
	}
}
