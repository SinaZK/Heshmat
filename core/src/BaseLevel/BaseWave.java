package BaseLevel;

import java.util.ArrayList;

import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/14/16.
 * 18:05
 *
 * Group of same Enemy!
 */
public class BaseWave
{
	MainActivity act;
	GameManager gameManager;

	public boolean isReleased;
	public float releaseTime;
	public float diffTime;//time between releasing two enemy of this wave!
	ArrayList<BaseEnemy> enemies;

	public void create()
	{
	}

	public void release()
	{

	}

}
