package BaseLevel;


import BaseCar.BaseCar;
import GameScene.GameScene;
import GameScene.LevelManager;

/**
 * Created by sinazk on 5/22/16.
 * -
 */
public abstract class LevelMode
{
	public LevelManager levelManager;
	public boolean isFinished;
	public float firstCarX, firstCarY;
	public BaseCar car;
	GameScene.LevelMode mode;

	public LevelMode(LevelManager levelManager)
	{
		this.levelManager = levelManager;
		car = levelManager.gameManager.selectedCar;
	}

	public abstract void run();
	public void start()
	{
		levelManager.levelMode = mode;
		levelManager.gameScene.setInput();
		firstCarX = car.body.bodies.get(0).getmBody().getWorldCenter().x;
		firstCarY = car.body.bodies.get(0).getmBody().getWorldCenter().y;
	}

	public void reset()
	{
		isFinished = false;
	}

	public void pause(){}
	public void resume(){}

}
