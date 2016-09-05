package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BaseCar.SizakCarModel;
import BaseLevel.LoopLevel;
import BaseLevel.Modes.DrivingMode;
import Countly.CountlyStrings;
import Entity.AnimatedSprite;
import Entity.Button;
import Entity.Logo;
import HUD.DrivingHUD;
import HUD.ShootingHUD;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import Scene.BaseScene;
import Scene.EndGameScene;
import SceneManager.SceneManager;
import Sorter.CarSorter;

public class GameScene extends BaseScene
{
	public static boolean isDebugRender = false;
	public static boolean isUsefulDebugLog = true;

	public SceneManager mSceneManager;
	public SizakCarModel carModel;//giving it from garageScene

	public GameScene(SceneManager sceneManager, Viewport v, SizakCarModel carModel)
	{
		super(sceneManager.act, v);
		mSceneManager = sceneManager;
		this.carModel = carModel;
	}

	public OrthographicCamera camera;
	public Box2DDebugRenderer debugRenderer;
	public World world;
	public GameSceneContactManager GSCM;
	public GameSceneInput gameSceneInput = new GameSceneInput(this);
	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public Batch spriteBatch;
	public ShapeRenderer shapeRenderer;//for cinematic mode Black Bars
	public PolygonSpriteBatch polygonSpriteBatch;
	public float gameSpeed = 60f;

	public GAME_STAT gameStat;
	public GameManager gameManager;
	public DrivingHUD drivingModeHUD;
	public ShootingHUD shootingModeHUD;

	public EndGameScene endGameScene;


	String add = "gfx/scene/game/";

	public Logo logo;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
		loadUI();
		drivingModeHUD = new DrivingHUD(this, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		shootingModeHUD = new ShootingHUD(this, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		camera = (OrthographicCamera) getCamera();
		spriteBatch = getBatch();
		shapeRenderer = new ShapeRenderer();
		polygonSpriteBatch = new PolygonSpriteBatch();

		if(isDebugRender)
			debugRenderer = new Box2DDebugRenderer(true, true, false, true, false, false);

		world = new World(new Vector2(0, -9.8f), false);

		gameManager = new GameManager(this);
		gameManager.create();

		mSceneManager.dialogManager.loadGameSceneDialogs(this);

		GSCM = new GameSceneContactManager(act, this);
		world.setContactListener(GSCM.makeContact());

		endGameScene = gameManager.levelManager.CreateEndGameScene();
		endGameScene.loadResources();

		logo = new Logo(this);
		logo.load();

		loadPopRestartButton();
	}

	@Override
	public void create()
	{
		gameStat = GAME_STAT.PLAY;

		createHUD();
		setInput();

		sendCountly();
	}

	@Override
	public void run()
	{
		draw();
		update();

		if(gameStat == GAME_STAT.PLAY)
			act.disableAds();
		else
		{
			if(gameStat != GAME_STAT.END_GAME)
				act.enableAds();
		}
	}

	public void pause(boolean withPauseDialog)
	{
		if(isUsefulDebugLog)
		{
			Log.e("GameScene.java", "Pause: BulletQSize = " + gameManager.bulletFactory.bullets.size()
					+ " EnemyQSize = " + gameManager.enemyFactory.enemies.size() + " VBO size = " + disposeTextureArray.size() + " WorldSize = " + world.getBodyCount());
		}

		gameStat = GAME_STAT.PAUSE;
		gameManager.pause();

		if(withPauseDialog)
		{
			mSceneManager.dialogManager.addPauseDialog();

			act.audioManager.playBgMusic();
		}
	}

	public void resume()
	{
		gameStat = GAME_STAT.PLAY;
		gameManager.resume();
	}

	public void restart()
	{
		gameStat = GAME_STAT.PLAY;
		gameManager.restart();

		sendCountly();
	}

	public void EndTheGame(boolean isLevelFinished)
	{
//        Log.e("GameScene.java", "EndTheGame startingEndGameScene");
		pause(false);
		gameStat = GAME_STAT.END_GAME;

		endGameScene.start(isLevelFinished);
	}

	public void goToGarageScene()
	{
		mSceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
		dispose();
	}

	public BitmapFont font10 = act.font10;
	public BitmapFont font12 = act.font12;
	public BitmapFont font14 = act.font14;
	public BitmapFont font16 = act.font16;
	public BitmapFont font18 = act.font18;
	public BitmapFont font22 = act.font22;
	public BitmapFont font24 = act.font24;

	public void setInput()
	{
		if(gameStat == GAME_STAT.PLAY)
		{
			inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(gameSceneInput);
			inputMultiplexer.addProcessor(HUD);
			gameManager.setInput(inputMultiplexer);
			Gdx.input.setInputProcessor(inputMultiplexer);
		}
	}

	public void update()
	{
		if(gameStat == GAME_STAT.PLAY)
		{
			camera.update();
			spriteBatch.setProjectionMatrix(camera.combined);
			world.step(1 / gameSpeed, 6, 2);
			gameManager.run();
		}
		if(gameStat == GAME_STAT.END_GAME)
		{
			endGameScene.run();
		}
	}

	public void draw()
	{
		spriteBatch.setProjectionMatrix(camera.combined);

		if(gameManager.levelManager.currentLevel.terrain.bgSpriteBatch != null)
			gameManager.levelManager.currentLevel.terrain.drawBG();

		spriteBatch.begin();
		gameManager.draw();
		spriteBatch.end();

		polygonSpriteBatch.setProjectionMatrix(camera.combined);
		polygonSpriteBatch.begin();
		gameManager.drawOnPolygonBatch(polygonSpriteBatch);
		polygonSpriteBatch.end();

		if(isDebugRender)
		{
			Matrix4 debugMatrix = camera.combined.cpy();
			debugRenderer.render(world, debugMatrix.scl(1 * PhysicsConstant.PIXEL_TO_METER));
		}

		gameManager.drawHUD();


		if(gameManager.levelManager.currentLevel.getCurrentPart().mode == LevelModeEnum.Cinematic)
		{
			shapeRenderer.setProjectionMatrix(camera.combined);

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 1);

			float x1 = CameraHelper.getXMin(camera);
			float x2 = CameraHelper.getXMax(camera);
			float y1 = CameraHelper.getYMin(camera);
			float y2 = CameraHelper.getYMax(camera);

			float height = 50 * camera.zoom;
			shapeRenderer.rect(x1 - 100, y1, x2 - x1 + 100, height + 10);//down
			shapeRenderer.rect(x1 - 100, y2 - height, x2 - x1 + 100, height + 10);//up

			shapeRenderer.end();

		} else
		{
			HUD.getBatch().begin();
			mSceneManager.drawGoldSprite(HUD.getBatch(), false);
			drawCarHP(HUD.getBatch());
//			font22.draw(HUD.getBatch(), "gold = " + act.getShowGold(), 10, 460);

			if(gameManager.levelManager.levelType == LevelManager.LevelType.LOOP)
			{
				LoopLevel level = (LoopLevel) gameManager.levelManager.currentLevel;
				level.waveModeSplashImage.draw(HUD.getBatch());
			}
			HUD.getBatch().end();
			HUD.draw();
		}

		if(gameStat == GAME_STAT.END_GAME)
		{
			endGameScene.draw();
		}

//		if(ga)
	}

	public enum GAME_STAT
	{
		PAUSE,
		PLAY,
		END_GAME,
	}

	public enum LevelModeEnum
	{
		Shooting, Driving, Finish, Cinematic
	}


	public float getDeltaTime()
	{
		if(gameStat == GAME_STAT.PAUSE)
			return 0;

		return Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
	}

	@Override
	public void dispose()
	{
		if(isDebugRender)
			debugRenderer.dispose();

		world.dispose();

		shapeRenderer.dispose();

		gameManager.dispose();

		super.dispose();
	}

	@Override
	public void createHUD()
	{
		Button pauseButton = new Button(loadTexture(add + "pause1.png"), loadTexture(add + "pause2.png"));
		pauseButton.setPosition(DX + 700, DY + 400);

		pauseButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				pause(true);

			}
		});

		HUD.addActor(pauseButton);
	}

	public Texture loadTexture(String add)
	{
		return TextureHelper.loadTexture(add, disposeTextureArray);
	}

	Texture carHPTexture1, carHPTexture2;
	Sprite carHP1Sprite;

	Sprite distanceSprite;
	Texture distanceTexture;

	public Texture nullTexture;
	public Texture selectedGunButtonTexture, nextGunButtonTexture;

	private void loadUI()
	{
		carHPTexture1 = loadTexture(add + "hp1.png");
		carHPTexture2 = loadTexture(add + "hp2.png");
		carHP1Sprite = new Sprite(carHPTexture1);

		distanceTexture = loadTexture(add + "record.png");
		distanceSprite = new Sprite(distanceTexture);

		selectedGunButtonTexture = loadTexture(add + "shooting/selected.png");
		nextGunButtonTexture = loadTexture(add + "shooting/next.png");
		nullTexture = loadTexture("gfx/coin.png");
	}

	public void drawCarHP(Batch batch)
	{
		float percent = gameManager.selectedCar.hitpoint / gameManager.selectedCar.getMaxHitPoint();

		float w = carHPTexture1.getWidth() * percent;

		if(percent < 0)
			w = 0;

		TextureRegion t = new TextureRegion(carHPTexture2, (int) w, carHPTexture2.getHeight());
		Sprite sprite = new Sprite(t);

		carHP1Sprite.setSize(60, 60);
		carHP1Sprite.setPosition(DX + (SceneManager.WORLD_X - carHP1Sprite.getWidth()) / 2, DY + 390);

		sprite.setPosition(carHP1Sprite.getX(), carHP1Sprite.getY());
		sprite.setSize(carHP1Sprite.getWidth() * percent, carHP1Sprite.getHeight());

		sprite.draw(batch);
		carHP1Sprite.draw(batch);

		float x = carHP1Sprite.getX();
		float y = carHP1Sprite.getY();

		long digitNum = SceneManager.getDigitNum((long)gameManager.selectedCar.hitpoint);

		float dX = digitNum * 4;

		if(gameManager.selectedCar.hitpoint >= 0)
			font10.draw(batch, "" + (int)gameManager.selectedCar.hitpoint, x + 35 - dX, y + 40);
		else
			font10.draw(batch, "0", x + 30, y + 40);
	}

	public void drawDist(Batch batch, float dist, float maxDist)
	{
		int intMax = (int) maxDist;

		if(dist > maxDist)
			dist = intMax;

		if(dist < 0)
			dist = 0;

		distanceSprite.setPosition(DX + 28, DY + 360);
		distanceSprite.draw(batch);

        if(maxDist != DrivingMode.INF_DIST) {
            font16.draw(batch, "( " + (int) dist, distanceSprite.getX() + 45, distanceSprite.getY() + 28);

            float fontSize = 12;
            float tW = SceneManager.getDigitNum((int) dist + 1) * fontSize;// + (SceneManager.getDigitNum((int)dist) - 1) * font16.getSpaceWidth();

            font16.setColor(0, 0, 0, 1);
            font16.draw(batch, "/ " + intMax + ")", distanceSprite.getX() + 55 + tW, distanceSprite.getY() + 28);
        }
        else {
            font16.setColor(0, 0, 0, 1);
            font16.draw(batch, "( " + (int) dist + " )", distanceSprite.getX() + 45, distanceSprite.getY() + 28);
        }

	}

	public void sendCountly()
	{
		act.googleServices.Countly("P " + CountlyStrings.LevelString + " " + act.selectorStatData.selectedLevel);
		act.googleServices.Countly("P " + CountlyStrings.CarSelectString[CarSorter.carPos[act.selectorStatData.selectedCar]]);
	}


	public Button popRestartButton;
	public boolean shouldPopRestartButtonDraw = false;

	public void loadPopRestartButton()
	{
		popRestartButton = new Button(mSceneManager.dialogManager.pauseMenuDialog.RestartButtonTexture1,
				mSceneManager.dialogManager.pauseMenuDialog.RestartButtonTexture2)
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
//				Log.e("Tag", "Draw");
				if(shouldPopRestartButtonDraw && gameStat == GAME_STAT.PLAY)
					super.draw(batch, parentAlpha);
			}

		};

		popRestartButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				if(!shouldPopRestartButtonDraw)
					return;
				GameScene.this.restart();
			}
		});

		popRestartButton.setPosition(DX + (SceneManager.WORLD_X - popRestartButton.getWidth()) / 2,
				DY + (SceneManager.WORLD_Y - popRestartButton.getHeight()) / 2);

		HUD.addActor(popRestartButton);
	}

}//class
