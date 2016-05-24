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
	public MainActivity act;
	public GameScene mScene;
	public ShootingMode shootingMode;
	public BaseLevel level;

	public Texture PigeonEnemyTexture;

	public ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public EnemyFactory(MainActivity a, GameScene pSceneNormal)
	{
		act = a;
		mScene = pSceneNormal;
		PigeonEnemyTexture = TextureHelper.loadTexture("gfx/pigeon.png", mScene.disposeTextureArray);
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

	public Pigeon getPigeon()
	{
		level = mScene.gameManager.levelManager.currentLevel;
		shootingMode = (ShootingMode)level.levelParts.get(level.currentPart);//necessary

		for(int i = 0;i < enemies.size();i++)
			if(enemies.get(i).enemyType == BaseEnemy.EnemyType.Pigeon && enemies.get(i).isFree)
			{
				enemies.get(i).create(shootingMode);
				return (Pigeon)enemies.get(i);
			}

		Pigeon pigeon = new Pigeon(act, enemies.size());
		pigeon.create(shootingMode);
		enemies.add(pigeon);

		return  pigeon;
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
}
