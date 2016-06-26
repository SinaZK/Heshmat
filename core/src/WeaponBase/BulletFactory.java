package WeaponBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

import Bullets.RocketBullet;
import GameScene.GameScene;
import Misc.Log;
import Misc.TextureHelper;
import Sorter.GunSorter;
import Weapons.RocketLauncher;
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
				bullets.get(i).run();
			}
	}

	public void draw(Batch batch)
	{
		for(int i = 0;i < bullets.size();i++)
			if(!bullets.get(i).isFree)
				bullets.get(i).draw(batch);
	}

	public NormalBullet getNormalBullet(BaseGun gun, GunSorter.GunType gunType)
	{
		for(int i = 0;i < bullets.size();i++)
			if(bullets.get(i).bulletType == gunType && bullets.get(i).isFree)
			{
				bullets.get(i).create();
				return (NormalBullet)bullets.get(i);
			}

		NormalBullet normalBullet = new NormalBullet(bullets.size(), act, gun, gunType);
		normalBullet.create();
		bullets.add(normalBullet);
		return normalBullet;
	}

	public RocketBullet getRocketBullet(RocketLauncher rocketLauncher)
	{
		for(int i = 0;i < bullets.size();i++)
			if(bullets.get(i).bulletType == GunSorter.GunType.RocketLauncher && bullets.get(i).isFree)
			{
				bullets.get(i).create();
				return (RocketBullet)bullets.get(i);
			}

		RocketBullet rocketBullet = new RocketBullet(bullets.size(), act, rocketLauncher);
		rocketBullet.create();
		bullets.add(rocketBullet);

		return  rocketBullet;
	}

}
