package Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import Bullets.PistolBullet;
import Entity.Entity;
import GameScene.GameManager;
import Misc.Log;
import Misc.TextureHelper;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

public class Pistol extends BaseGun
{
	public Pistol(MainActivity a, GameManager gm) {
		super(a, gm);

		loadResources();

		setPosition(100, 250);
		shootingPoint.set(169, 100);
	}

	@Override
	public void run() {

	}

	@Override
	public void shoot()
	{
		PistolBullet p = gameManager.bulletFactory.getPistolBullet();
		p.shoot(getShootingX(), getShootingY(), image.getRotation());
	}

	@Override
	public void loadResources()
	{
		image = new Entity(TextureHelper.loadTexture("gfx/pistol.png", act.sceneManager.gameScene.disposeTextureArray));
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		shoot();
		return super.touchDown(screenX, screenY, pointer, button);
	}
}