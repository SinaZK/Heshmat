package BaseLevel.Modes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.BodyStrings;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 5/23/16.
 * -
 */
public class FinishMode extends LevelMode
{
	public FinishMode(LevelManager levelManager)
	{
		super(levelManager);
	}

	@Override
	public void run()
	{

	}

	@Override
	public void start()
	{
		Log.e("FinishMode.java", "Starting FinishPart");
		super.start();

		GameManager gameManager = levelManager.gameManager;
		World world = gameManager.gameScene.world;
		float ratio = PhysicsConstant.PIXEL_TO_METER;

		float bX = firstCarX * ratio + 500;
		float bY = firstCarY * ratio - 500;
		Body body = PhysicsFactory.createBoxBody(world, bX, bY, 200, 1000, BodyDef.BodyType.StaticBody);
		body.setUserData(BodyStrings.FINISH_MODE_STRING);

		Log.e("FinishMode.java", "creating at : " + bX + " " + bY);
		Log.e("FinishMode.java", "car is at : " + firstCarX + " " + firstCarY);

		levelManager.levelModeEnum = GameScene.LevelModeEnum.Finish;
	}
}
