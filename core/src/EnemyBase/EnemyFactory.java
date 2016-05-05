package EnemyBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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
		for(int i = 0;i < enemies.size();i++)
			if(enemies.get(i).enemyType == BaseEnemy.EnemyType.Pigeon && enemies.get(i).isFree)
			{
				enemies.get(i).create();
				return (Pigeon)enemies.get(i);
			}

		Pigeon pigeon = new Pigeon(act, enemies.size());
		pigeon.create();
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
