package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import BaseCar.SizakCarModel;
import BaseLevel.Modes.DrivingMode;
import Entity.Button;
import Entity.Entity;
import Entity.VideoButton;
import GameScene.GameScene;
import GameScene.LevelManager;
import GameScene.StarManager;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;

public class EndGameScene extends BaseScene
{
	GameScene gameScene;
	SceneManager sceneManager;
	StarManager starManager;

	public EndGameScene(GameScene gameScene)
	{
		super(gameScene.mSceneManager.act, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		sceneManager = gameScene.mSceneManager;
		this.gameScene = gameScene;
		starManager = act.starManager;
	}


	public float DX;
	public float DY;
	String add = "gfx/scene/endgame/";
	InputProcessor gameSceneInput;

	Button restartButton;
	Button backToMenuButton;
	Button nextLevelButton;

	boolean isLevelFinished;
	int star = 0;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		createBack();

		restartButton = new Button(TextureHelper.loadTexture(add+"restart1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"restart2.png", disposeTextureArray));
		restartButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				restartGameScene();
			}
		});


		nextLevelButton = new Button(TextureHelper.loadTexture(add+"next1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"next2.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				if(isLevelFinished)
					setVisible(true);
				else
					setVisible(false);
				super.draw(batch, parentAlpha);
			}
		};
		nextLevelButton.setRunnable(act, new Runnable() {

			@Override
			public void run()
			{
				goToNextLevel();
			}
		});

		backToMenuButton = new Button(TextureHelper.loadTexture(add+"back1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"back2.png", disposeTextureArray));
		backToMenuButton.setRunnable(act, new Runnable() {

			@Override
			public void run()
			{
				goToGarageScene();
			}
		});


		Button showScoreButton = new Button(TextureHelper.loadTexture(add+"leader1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"leader2.png", disposeTextureArray));
		showScoreButton.setRunnable(act, new Runnable() {

			@Override
			public void run()
			{
				Log.e("EndGameScene.java", "show Score");
			}
		});

		final Entity distEnt = new Entity(TextureHelper.loadTexture(add + "dist.png", disposeTextureArray));
		final Entity coinEnt = new Entity(TextureHelper.loadTexture(add+"coin.png", disposeTextureArray));
		final Entity killEnt = new Entity(TextureHelper.loadTexture(add+"kill.png", disposeTextureArray));

		distEnt.setPosition(DX + 200, DY + 280);
		coinEnt.setPosition(DX + 200, DY + 220);
		killEnt.setPosition(DX + 200, DY + 160);

		backToMenuButton.setPosition(DX + 241, DY + 20);
		showScoreButton.setPosition(DX + 370, DY + 20);
		restartButton.setPosition(DX + 498, DY + 20);
		nextLevelButton.setPosition(DX + 650, DY + 20);


		VideoButton videoButton = new VideoButton(act);
		videoButton.setPosition(DX + 50, DY + 20);

		attachChild(videoButton);

		attachChild(nextLevelButton);
		attachChild(showScoreButton);
		attachChild(backToMenuButton);
		attachChild(restartButton);
		attachChild(killEnt);
		if(gameScene.gameManager.levelManager.levelType == LevelManager.LevelType.NORMAL)
			attachChild(distEnt);
		attachChild(coinEnt);

		for(int i = 0;i <= 3;i++)
		{
			final int II = i;
			Entity starEnt = new Entity(TextureHelper.loadTexture(add + "star" + i + ".png", disposeTextureArray))
			{
				@Override
				public void draw(Batch batch, float parentAlpha)
				{
					if(gameScene.gameManager.levelManager.levelType == LevelManager.LevelType.ENDLESS)
						return;

					if(star == II)
						super.draw(batch, parentAlpha);
				}
			};

			starEnt.setPosition(DX + 280, DY + 350);

			attachChild(starEnt);
		}


	}



	private void goToNextLevel()
	{
		SizakCarModel carModel = gameScene.carModel;

		gameScene.dispose();

		act.selectorStatData.selectedLevel++;
		gameScene.mSceneManager.setCurrentScene(SceneManager.SCENES.GAME_SCENE, carModel);
	}

	@Override
	public void create()
	{
		act.audioManager.playBgMusic();
	}

	@Override
	public void run() 
	{
		super.run();
	}

	@Override
	public void draw()
	{
		super.draw();

		getBatch().begin();
		act.sceneManager.drawGoldSprite(getBatch());

		if(gameScene.gameManager.levelManager.levelType == LevelManager.LevelType.NORMAL)
			gameScene.font22.draw(getBatch(), "" + (int) calculateDist(), 460, 310);
		gameScene.font22.draw(getBatch(), "" + gameScene.gameManager.goldCollect, 460, 250);

		if(gameScene.gameManager.levelManager.levelType == LevelManager.LevelType.NORMAL)
			gameScene.font22.draw(getBatch(), ""+ gameScene.gameManager.enemyKilledCount + " / " + gameScene.gameManager.enemyInitCount, 460, 190);
		else
			gameScene.font22.draw(getBatch(), "" + gameScene.gameManager.enemyKilledCount, 460, 190);

		getBatch().end();

		act.disableAds();
	}

	public float calculateDist()
	{
		if(isLevelFinished)
			return gameScene.gameManager.distanceTraveled;

		if(gameScene.gameManager.levelManager.currentLevel.getCurrentPart().mode != GameScene.LevelModeEnum.Driving)
			return 0;

		DrivingMode mode = (DrivingMode)gameScene.gameManager.levelManager.currentLevel.getCurrentPart();

		return gameScene.gameManager.distanceTraveled + mode.getCurrentPos();
	}

	public void set(boolean isLevelFinished)
	{
		act.checkForVDO();

		star = 0;
		gameSceneInput = Gdx.input.getInputProcessor();

		this.isLevelFinished = isLevelFinished;

		Gdx.input.setInputProcessor(this);

		act.saveAfterGameScene();

		if(isLevelFinished)
			star = countStars();

		if(star > 0)
		{
			starManager.completeNormalLevel(star);
		}

		Log.e("Tag", "set");
	}

	public void restartGameScene()
	{
		Gdx.input.setInputProcessor(gameSceneInput);
		gameScene.restart();
	}

	public void goToGarageScene()
	{
		gameScene.goToGarageScene();
	}

	public int countStars()
	{
		int star = 1;
		float hpPercent = gameScene.gameManager.selectedCar.hitpoint / gameScene.gameManager.selectedCar.getMaxHitPoint() * 100;

		if(hpPercent >= 50)
			star++;

		if(gameScene.gameManager.enemyKilledCount == gameScene.gameManager.enemyInitCount)
			star++;

		return star;
	}

	public void createBack()
	{
		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray));
//		back.setSize(850, 500);
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);

		attachChild(back);
	}
}
