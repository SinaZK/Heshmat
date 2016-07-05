package BaseLevel.Modes;

import EnemyBase.BaseEnemy;
import EnemyBase.DrivingModeEnemy;
import EnemyBase.EnemyFactory;
import Misc.CameraHelper;
import Misc.Log;

/**
 * Created by sinazk on 7/5/16.
 *
 */
public class DrivingEnemyQuery
{
	EnemyFactory enemyFactory;
	DrivingMode mode;
	public float dist;
	public BaseEnemy.EnemyType type;
	public int level;
	public float counter = 0;

	public DrivingEnemyQuery(DrivingMode drivingMode, BaseEnemy.EnemyType enemyType, int level, float dist)
	{
		mode = drivingMode;
		enemyFactory = mode.gameManager.enemyFactory;
		this.dist = dist;
		this.type = enemyType;
		this.level = level;
	}

	public void reset()
	{
		counter = dist;
	}

	public void run(float currentDistance)
	{
		if(currentDistance >= counter)
		{
			counter += dist;

//			Log.e("DrivingEnemyQuery.java", "initing " + type + " counter = " + counter + " dist = " + dist);

			DrivingModeEnemy enemy = (DrivingModeEnemy)enemyFactory.getDrivingEnemy(type, level, null);

			float x = CameraHelper.getXMax(mode.camera) - 100;
			enemy.attachToGround(x);

		}
	}
}
