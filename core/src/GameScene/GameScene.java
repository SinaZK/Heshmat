package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BaseCar.SizakCarModel;
import Entity.AnimatedSprite;
import Entity.Button;
import HUD.DrivingHUD;
import HUD.ShootingHUD;
import Misc.Log;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import Scene.BaseScene;
import Scene.EndGameScene;
import SceneManager.SceneManager;

public class GameScene extends BaseScene
{
	public boolean isDebugRender = false;
	public boolean isUsefulDebugLog = true;

	public SceneManager mSceneManager;
	SizakCarModel carModel;//giving it from garageScene

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
	public PolygonSpriteBatch polygonSpriteBatch;
	public float gameSpeed = 60f;

	public GAME_STAT gameStat;
	public GameManager gameManager;
	public DrivingHUD drivingModeHUD;
	public ShootingHUD shootingModeHUD;
	AnimatedSprite animatedSprite;

	public EndGameScene endGameScene;


	String add = "gfx/scene/game/";

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
		drivingModeHUD = new DrivingHUD(this, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		shootingModeHUD = new ShootingHUD(this, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		camera = (OrthographicCamera) getCamera();
		spriteBatch = getBatch();
		polygonSpriteBatch = new PolygonSpriteBatch();

		if(isDebugRender)
			debugRenderer = new Box2DDebugRenderer(true, true, false, true, false, false);

		world = new World(new Vector2(0, -9.8f), false);

		gameManager = new GameManager(this);
		gameManager.create();

		mSceneManager.dialogManager.loadGameSceneDialogs(this);

		GSCM = new GameSceneContactManager(act, this);
		world.setContactListener(GSCM.makeContact());

		endGameScene = new EndGameScene(this);
		endGameScene.loadResources();
	}

	@Override
	public void create()
	{
		gameStat = GAME_STAT.PLAY;

		createHUD();
		setInput();
	}

	@Override
	public void run()
	{
		draw();
		update();
	}

	public void pause(boolean withPauseDialog)
	{
		if(isUsefulDebugLog)
			Log.e("GameScene.java", "Pause: BulletQSize = " + gameManager.bulletFactory.bullets.size() + " EnemyQSize = " + gameManager.enemyFactory.enemies.size());

		gameStat = GAME_STAT.PAUSE;
		gameManager.pause();

		if(withPauseDialog)
			mSceneManager.dialogManager.addPauseDialog();
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

	}

	public void EndTheGame(boolean isLevelFinished)
	{
		pause(false);
		gameStat = GAME_STAT.END_GAME;

		endGameScene.set(isLevelFinished);
	}

	public void goToGarageScene()
	{
		mSceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
		dispose();
	}

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

//		Log.e("GameScene.java", "DA size = " + disposeTextureArray.size());
	}

	public void draw()
	{
		spriteBatch.setProjectionMatrix(camera.combined);
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


		HUD.getBatch().begin();
		font22.draw(HUD.getBatch(), "gold = " + act.getShowGold(), 10, 460);
		HUD.getBatch().end();
		HUD.draw();

		if(gameStat == GAME_STAT.END_GAME)
		{
			endGameScene.draw();
		}
	}

	public enum GAME_STAT
	{
		PAUSE,
		PLAY,
		END_GAME,
	}

	public enum LevelModeEnum
	{
		Shooting, Driving, Finish
	}

	public BitmapFont font16 = new BitmapFont(Gdx.files.internal("font/16w.fnt"));
	public BitmapFont font22 = new BitmapFont(Gdx.files.internal("font/22w.fnt"));
	public BitmapFont font24 = new BitmapFont(Gdx.files.internal("font/24w.fnt"));
	public BitmapFont font24Gold = new BitmapFont(Gdx.files.internal("font/24gold.fnt"));

	public float getDeltaTime()
	{
		if(gameStat == GAME_STAT.PAUSE)
			return 0;

		return Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
	}

	@Override
	public void dispose()
	{
		font22.dispose();
		font24.dispose();
		font24Gold.dispose();
		font16.dispose();

		if(isDebugRender)
			debugRenderer.dispose();

		world.dispose();

		super.dispose();
	}

	@Override
	public void createHUD()
	{
		Button pauseButton = new Button(loadTexture(add + "pause1.png"), loadTexture(add + "pause2.png"));
		pauseButton.setSize(40, 40);
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
}//class
