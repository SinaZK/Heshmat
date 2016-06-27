package GameScene;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import DataStore.GunStatData;
import Enums.Enums;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;
import Sorter.GunSorter;
import WeaponBase.BaseGun;

/**
 * Created by sinazk on 5/23/16.
 * -
 */

public class GunManager
{
	GameScene gameScene;
	GameManager gameManager;

	public static int MAX_GUNS = 0;
	int selectedGunNumber = 0;
	ArrayList<BaseGun> guns = new ArrayList<BaseGun>();

	public GunManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
	}

	public Texture reload1Texture, reload2Texture;
	public Sprite reloadSprite1, reloadSprite2;
	public void create()
	{
		initGuns();
		initReloadTextures();
	}

	private void initReloadTextures()
	{
		reload1Texture = TextureHelper.loadTexture("gfx/weapons/reload1.png", gameScene.disposeTextureArray);
		reload2Texture = TextureHelper.loadTexture("gfx/weapons/reload2.png", gameScene.disposeTextureArray);

		reloadSprite1 = new Sprite(reload1Texture);
		reloadSprite2 = new Sprite(reload2Texture);

		reloadSprite2.setSize(reloadSpriteWidth, reloadSpriteHeight);
	}

	private static float reloadSpriteWidth = 80;
	private static float reloadSpriteHeight = 20;
	public void drawReloadBar(Batch batch, float x, float y, float reloadCounter, float reloadTime)
	{
		float percent = reloadCounter / reloadTime;
		float width = percent * reloadSpriteWidth;

		reloadSprite2.setPosition(x, y);
		reloadSprite2.draw(batch);

		reloadSprite1.setPosition(x, y);
		reloadSprite1.setSize(width, reloadSpriteHeight);
		reloadSprite1.draw(batch);
	}

	public void run()
	{
		if(gameManager.levelManager.levelMode == GameScene.LevelMode.Shooting)
			getSelectedGun().run();
		else
			getSelectedGun().isTouched = false;
	}

	public void initGuns()
	{
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
		{
			GunStatData gunStatData = gameScene.act.gunStatDatas[GunSorter.gunPos[i]];
			if(gunStatData.lockStat == Enums.LOCKSTAT.UNLOCK)
			{
//				Log.e("GunManager.java", "id = " + i + " isUnlock");
				MAX_GUNS++;
				BaseGun gun = GunSorter.createSelectedGun(gameManager, i);
				assert gun != null;
				gun.setUpgrade(gunStatData);
				guns.add(gun);
			}
		}
	}

	public void swapGun()
	{
		selectedGunNumber++;
		selectedGunNumber %= MAX_GUNS;

		gameScene.setInput();
	}

	public void setInput(InputMultiplexer inputMultiplexer)
	{
		inputMultiplexer.addProcessor(getSelectedGun());
	}

	public BaseGun getSelectedGun()
	{
		return guns.get(selectedGunNumber);
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;

	public void pause()
	{

	}

	public void resume()
	{

	}

	public void restart()
	{
		for(int i = 0;i < guns.size();i++)
			guns.get(i).reset();
	}
}
