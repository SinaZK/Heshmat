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
	public BaseEnemy.EnemyType type;
	public int level;

	public DrivingEnemyQuery(DrivingMode drivingMode, BaseEnemy.EnemyType enemyType, int level)
	{
		mode = drivingMode;
		enemyFactory = mode.gameManager.enemyFactory;
		this.type = enemyType;
		this.level = level;
	}

	public void reset()
	{
	}

	public void getEnemy()
	{
		DrivingModeEnemy enemy = (DrivingModeEnemy)enemyFactory.getDrivingEnemy(type, level, null);

		float x = CameraHelper.getXMax(mode.camera) - 100;
		enemy.attachToGround(x);
	}
}
