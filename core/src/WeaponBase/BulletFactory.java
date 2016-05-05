package WeaponBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

import Bullets.PistolBullet;
import Bullets.RocketBullet;
import GameScene.GameScene;
import Misc.Log;
import Misc.TextureHelper;
import heshmat.MainActivity;

public class BulletFactory
{
	public MainActivity act;
	public GameScene mScene;

	public Texture PistolBulletTexture;
	public Texture RocketBulletTexture;

	public ArrayList <BaseBullet> bullets = new ArrayList<BaseBullet>();

	public BulletFactory(MainActivity a, GameScene pSceneNormal)
	{
		act = a;
		mScene = pSceneNormal;

		PistolBulletTexture = TextureHelper.loadTexture("gfx/pistolbullet.png", act.sceneManager.gameScene.disposeTextureArray);
		RocketBulletTexture = TextureHelper.loadTexture("gfx/rocketbullet.png", act.sceneManager.gameScene.disposeTextureArray);
	}

	public void run()
	{
		for(int i = 0;i < bullets.size();i++)
			if(!bullets.get(i).isFree)
			{
				if(bullets.get(i).bulletType == BaseBullet.BulletType.PISTOL)
				{
					PistolBullet pistolBullet = (PistolBullet) bullets.get(i);
					pistolBullet.run();
				}
				else
				if(bullets.get(i).bulletType == BaseBullet.BulletType.ROCKET_LAUNCHER)
				{
					RocketBullet rocketBullet = (RocketBullet) bullets.get(i);
					rocketBullet.run();
				}
			}
	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < bullets.size();i++)
			if(!bullets.get(i).isFree)
				bullets.get(i).draw(batch);
	}

	public PistolBullet getPistolBullet()
	{
		for(int i = 0;i < bullets.size();i++)
			if(bullets.get(i).bulletType == BaseBullet.BulletType.PISTOL && bullets.get(i).isFree)
			{
				bullets.get(i).create();
				return (PistolBullet)bullets.get(i);
			}

		PistolBullet pistolBullet = new PistolBullet(bullets.size(), act, 5, 10);
		pistolBullet.create();
		bullets.add(pistolBullet);

		return  pistolBullet;
	}

	public RocketBullet getRocketBullet()
	{
		for(int i = 0;i < bullets.size();i++)
			if(bullets.get(i).bulletType == BaseBullet.BulletType.ROCKET_LAUNCHER && bullets.get(i).isFree)
			{
				bullets.get(i).create();
				return (RocketBullet)bullets.get(i);
			}

		RocketBullet rocketBullet = new RocketBullet(bullets.size(), act, 10, 8, 5);
		rocketBullet.create();
		bullets.add(rocketBullet);

		return  rocketBullet;
	}

}
