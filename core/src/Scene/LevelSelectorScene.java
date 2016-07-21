package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import BaseLevel.LevelPackage;
import DataStore.LevelStatData;
import Entity.Button;
import Entity.Entity;
import Entity.LevelEntity;
import Enums.Enums;
import Misc.TextureHelper;
import SceneManager.SceneManager;

public class LevelSelectorScene extends BaseScene
{
	SceneManager mSceneManager;

	public LevelSelectorScene(SceneManager sceneManager, Viewport v)
	{
		super(sceneManager.act, v);
		mSceneManager = sceneManager;
	}

	public float DX;
	public float DY;
	String add = "gfx/scene/level/";

	LevelPackage levelPackage;
	LevelStatData levelStatData;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		createBack();

		levelPackage = new LevelPackage(act);
		levelPackage.load("gfx/lvl/pack" + act.selectorStatData.selectedLevelPack + "/level.pckg");

		act.loadLevelData(levelPackage, act.selectorStatData.selectedLevelPack);

		int paddingX = 10;
		int paddingY = 15;
		float startX = 241;
		float startY = 300;
		float width = 83;
		float height = 89;

		int numInRow = 4;

		float posX = startX, posY = startY;
		for (int i = 1; i <= levelPackage.numberOfLevels; i++)
		{
			LevelEntity levelEntity = new LevelEntity(act, disposeTextureArray, act.levelStatDatas.get(i), i);

			levelEntity.setSize(width, height);
			levelEntity.setPosition(posX, posY);

			posX += paddingX + width;
			if(i % numInRow == 0)
			{
				posY -= height + paddingY;
				posX = startX;
			}

			levelEntity.attachToScene(this);
		}

		Button endLess = new Button(TextureHelper.loadTexture("gfx/scene/level/endless1.png", disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/level/endless2.png", disposeTextureArray));

		endLess.setSize(width, height);
		endLess.setPosition(650, 196);

		endLess.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				if(act.starManager.getStarNum() < act.sceneManager.ENDLESS_STARS)
				{
					String second = " " + (act.sceneManager.ENDLESS_STARS - act.starManager.getStarNum())+" ";
					String third = "تا ستاره دیگه نیاز داری";
					String mark = "!!!";
					act.googleServices.makeToastLong(second + third + mark);
					return;
				}

				act.selectorStatData.selectedLevel = -1;
				act.sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
				LevelSelectorScene.this.dispose();
			}
		});

		attachChild(endLess);
	}

	@Override
	public void create()
	{
		super.create();

		addBackToMenuButton();
	}

	@Override
	public void dispose()
	{
		act.saveSelector();
		act.saveAllLevelDatas(levelPackage.numberOfLevels);
		super.dispose();
	}

	public void addBackToMenuButton()
	{
		Button menuButton = new Button(TextureHelper.loadTexture(add + "menu1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "menu2.png", disposeTextureArray));
		menuButton.setPosition(DX + 28, DY + 28);
		menuButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.MAIN_MENU, null);
			}
		});

		attachChild(menuButton);

		Button backButton = new Button(TextureHelper.loadTexture(add + "back1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "back2.png", disposeTextureArray));
		backButton.setPosition(DX + 98, DY + 28);
		backButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.LEVEL_PACKAGE_SELECTOR, null);
			}
		});

		attachChild(backButton);
	}

	@Override
	public void draw()
	{
		super.draw();

		getBatch().begin();
		mSceneManager.drawGoldSprite(getBatch());
		getBatch().end();
	}

	public void createBack()
	{
		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray));
		back.setSize(850, 500);
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);

		attachChild(back);
	}
}
