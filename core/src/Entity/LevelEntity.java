package Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import DataStore.LevelStatData;
import Enums.Enums;
import Misc.TextureHelper;
import Scene.BaseScene;
import Scene.LevelSelectorScene;
import SceneManager.SceneManager;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/4/16.
 * 15:27
 */
public class LevelEntity extends Actor
{
	MainActivity activity;
	ArrayList<Texture> disposeAbleTextures;

	LevelSelectorScene levelSelectorScene;
	LevelStatData levelStatData;
	Button lockSprite;
	Button[] starSprites = new Button[4];
	int id;

	public LevelEntity(MainActivity activity, ArrayList<Texture> disposeAbleTextures, LevelStatData levelStatData, int id)
	{
		this.levelStatData = levelStatData;
		this.activity = activity;
		this.disposeAbleTextures = disposeAbleTextures;
		this.id = id;
		levelSelectorScene = (LevelSelectorScene) activity.sceneManager.currentBaseScene;

		loadResources();
	}

	public void loadResources()
	{
		lockSprite = new Button(TextureHelper.loadTexture("gfx/scene/level/lock1.png", disposeAbleTextures),
				TextureHelper.loadTexture("gfx/scene/level/lock2.png", disposeAbleTextures))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				if(parentAlpha == 1)
					return;

				super.draw(batch, 1);
			}

			public void draw(Batch batch)
			{
				super.draw(batch, 1);
			}
		};

		for (int i = 0; i <= 3; i++)
		{
			starSprites[i] = new Button(TextureHelper.loadTexture("gfx/scene/level/star" + i + "1.png", disposeAbleTextures),
					TextureHelper.loadTexture("gfx/scene/level/star" + i + "2.png", disposeAbleTextures))
			{
				@Override
				public void draw(Batch batch, float parentAlpha)
				{
					if(parentAlpha == 1)
						return;

					super.draw(batch, 1);
				}

				public void draw(Batch batch)
				{
					super.draw(batch, 1);
				}
			};
			starSprites[i].setRunnable(activity, new Runnable()
			{
				@Override
				public void run()
				{
					activity.selectorStatData.selectedLevel = id;
					activity.sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
					levelSelectorScene.dispose();
				}
			});

		}
	}

	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);

		lockSprite.setPosition(x, y);
		for (int i = 0; i <= 3; i++)
			starSprites[i].setPosition(x, y);
	}

	@Override
	public void setSize(float width, float height)
	{
		super.setSize(width, height);

		lockSprite.setSize(width, height);
		for (int i = 0; i <= 3; i++)
			starSprites[i].setSize(width, height);
	}


	@Override
	public void draw(Batch batch, float parentAlpha)
	{

		if(levelStatData == null)
			return;


		if(levelStatData.lockStat == Enums.LOCKSTAT.LOCK)
			lockSprite.draw(batch, 0.5f);
		else
		{
			starSprites[(int) levelStatData.getStar()].draw(batch, 0.5f);

			float x = starSprites[0].getX();
			float y = starSprites[0].getY();
			float fontSize = 11;
			activity.font22.draw(batch, "" + id, x + 37 - (SceneManager.getDigitNum(id) - 1) * fontSize, y + 45);
		}
	}

	@Override
	public void act(float delta)
	{
		lockSprite.setVisible(false);
		for (int i = 0; i <= 3; i++)
			starSprites[i].setVisible(false);

		if(levelStatData.lockStat == Enums.LOCKSTAT.LOCK)
			lockSprite.setVisible(true);
		else
			starSprites[(int) levelStatData.getStar()].setVisible(true);

		super.act(delta);
	}

	public void attachToScene(BaseScene scene)
	{
		scene.attachChild(this);
		scene.attachChild(lockSprite);
		for (int i = 0; i <= 3; i++)
			scene.attachChild(starSprites[i]);
	}
}
