package BaseLevel.Modes;


import Enemy.Fly;
import GameScene.LevelManager;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class StartGameCinematic extends CinematicMode
{
	Fly enemy;

	int currentState = 0;

	boolean isEnemyShoot;//oneTime

	public StartGameCinematic(LevelManager levelManager)
	{
		super(levelManager);

//		Log.e("StartGameCinematic.java", "startGameCinematic bitch!");
	}

	@Override
	public void run()
	{
		super.run();
		enemy.run();

		stage.getBatch().begin();
		enemy.draw(stage.getBatch());
		stage.getBatch().end();



		//SHOOOOOOOOOOOOOOOOTING part
		if(isEnemyShoot)
			return;

		if(enemy.x - gameManager.selectedCar.getXInPixel() < 300)
		{
			currentState = 1;
			isEnemyShoot = true;
			enemy.attack();
		}
	}

	@Override
	public void start()
	{
		super.start();

		time = fullTime;
		cameraSpeedX = 2;
		cameraSpeedY = 1;

		currentState = 0;

		enemy = gameManager.enemyFactory.getFly(25, null);
		enemy.setPosition(CameraHelper.getXMax(camera) - 100, CameraHelper.getYMax(camera));
		enemy.gunTeta = -90;
	}

	@Override
	public void reset()
	{
		super.reset();
	}

	@Override
	public void setCamera()
	{
		camera.zoom = 3;

		if(currentState == 1)
		{
			cameraPos.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
			cameraPos.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
		}
		if(currentState == 0)
		{
			cameraPos.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
			cameraPos.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 120;
		}

		super.setCamera();

	}

	@Override
	public void onFinished()
	{
		super.onFinished();

		enemy.isFree = true;
	}
}
