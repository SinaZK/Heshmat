package EnemyBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import BaseLevel.BaseLevel;
import BaseLevel.ShootingMode;
import Bullets.PistolBullet;
import Bullets.RocketBullet;
import Enemy.Pigeon;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import WeaponBase.BaseBullet;
import WeaponBase.BaseGun;
import WeaponBase.BulletFactory;
import heshmat.MainActivity;

public class EnemyFactory
{
	public GameScene gameScene;
	public GameManager gameManager;
	public ShootingMode shootingMode;
	public BaseLevel level;

	public Texture PigeonEnemyTexture;

	public ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public EnemyFactory(GameManager gameManager)
	{
		gameScene = gameManager.gameScene;
		this.gameManager = gameManager;
		PigeonEnemyTexture = TextureHelper.loadTexture("gfx/pigeon.png", gameScene.disposeTextureArray);
	}

	public void create()
	{
		level = gameManager.levelManager.currentLevel;

		if(level == null)
			Log.e("EnemyFactory.java", "NULL");
	}

	public void run()
	{
		for(int i = 0;i < enemies.size();i++)
			if(!enemies.get(i).isFree)
			{
				if(enemies.get(i).enemyType == BaseEnemy.EnemyType.Pigeon)
				{
					Pigeon pigeon = (Pigeon) enemies.get(i);
					pigeon.run();
				}
			}
	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < enemies.size();i++)
			if(!enemies.get(i).isFree)
				enemies.get(i).draw(batch);
	}

	public BaseEnemy getEnemyByType(BaseEnemy.EnemyType enemyType, ArrayList<String> attr)
	{
		shootingMode = (ShootingMode)level.getCurrentPart(); //necessary

		for(int i = 0;i < enemies.size();i++)
			if(enemies.get(i).enemyType == enemyType && enemies.get(i).isFree)
			{
				enemies.get(i).create(shootingMode, attr);
				return enemies.get(i);
			}

		return null;
	}

	public boolean haveEnemy(BaseEnemy.EnemyType enemyType)
	{
		shootingMode = (ShootingMode)level.getCurrentPart(); //necessary
		for(int i = 0;i < enemies.size();i++)
			if(enemies.get(i).enemyType == enemyType && enemies.get(i).isFree)
				return true;

		return false;
	}

	public void damageArea(Vector2 centreOfExplosion, float length, float damage)
	{
		for(int i = 0;i < enemies.size();i++)
			if(!enemies.get(i).isFree)
			{
				if(CameraHelper.distance(centreOfExplosion.x, centreOfExplosion.y, enemies.get(i).x, enemies.get(i).y) <= length)
					enemies.get(i).damage(damage);
			}
	}

	public Pigeon getPigeon(ArrayList<String> attr)
	{
		if(haveEnemy(BaseEnemy.EnemyType.Pigeon))
			return (Pigeon) getEnemyByType(BaseEnemy.EnemyType.Pigeon, attr);

		Pigeon pigeon = new Pigeon(gameManager, enemies.size());
		pigeon.create(shootingMode, attr);
		enemies.add(pigeon);

		return  pigeon;
	}

	public static String PIGEON = "PIGEON";

	public BaseEnemy getEnemy(String type, ArrayList<String> attr)
	{
		if(type.equals(PIGEON))
		{
			getPigeon(attr);
		}
		return null;
	}
}
