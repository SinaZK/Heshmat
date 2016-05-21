package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BaseCar.CarLoader;
import Cars.Train;
import Entity.AnimatedSprite;
import HUD.*;
import PhysicsFactory.PhysicsConstant;
import Scene.BaseScene;
import SceneManager.SceneManager;

public class GameScene extends BaseScene
{

	public boolean isDebugRender = false;

	SceneManager mSceneManager;

	public GameScene(SceneManager sceneManager, Viewport v)
	{
		super(sceneManager.act, v);
		mSceneManager = sceneManager;
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
	public boolean isGas, isBrake;
	AnimatedSprite animatedSprite;


	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
		drivingModeHUD = new DrivingHUD(this, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		camera = (OrthographicCamera) getCamera();
		spriteBatch = getBatch();
		polygonSpriteBatch = new PolygonSpriteBatch();

		if(isDebugRender)
			debugRenderer = new Box2DDebugRenderer(true, true, false, true, false, false);

		world = new World(new Vector2(0, -9.8f), false);

		gameManager = new GameManager(this);

		GSCM = new GameSceneContactManager(act, this);
		world.setContactListener(GSCM.makeContact());
	}

	public Train train;
	@Override
	public void create()
	{
//		PhysicsFactory.createBoxBody(world, 0, 0, 800, 50, BodyDef.BodyType.StaticBody).setUserData(BodyStrings.GroundString);

		gameStat = GAME_STAT.PLAY;

		setInput();

//		testCar = CarLoader.loadCarFile(gameManager, "gfx/car/test/test.car", world, disposeTextureArray);
		train = CarLoader.loadTrainFile(gameManager, "gfx/car/train/train.car", world, disposeTextureArray);
		camera.zoom = 1;
	}

	@Override
	public void run()
	{
		update();
		draw();
	}

	public void pause()
	{
	}

	public void resume()
	{
	}

	public void restart()
	{
	}

	public void EndTheGame()
	{
	}

	public void setInput()
	{
		if(gameStat == GAME_STAT.PLAY)
		{
			inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(gameSceneInput);
			inputMultiplexer.addProcessor(gameManager.selectedGun);
			inputMultiplexer.addProcessor(drivingModeHUD);
			Gdx.input.setInputProcessor(inputMultiplexer);
		}
	}

	public BitmapFont font16 = new BitmapFont(Gdx.files.internal("font/16w.fnt"));
	public BitmapFont font22 = new BitmapFont(Gdx.files.internal("font/22w.fnt"));
	public BitmapFont font24 = new BitmapFont(Gdx.files.internal("font/24w.fnt"));
	public BitmapFont font24Gold = new BitmapFont(Gdx.files.internal("font/24gold.fnt"));

	public void update()
	{
		camera.position.x = train.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 0;
		camera.position.y = train.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 0;
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		world.step(1 / gameSpeed, 6, 2);

		gameManager.run();
	}

	public void draw()
	{
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		gameManager.draw();
		train.draw(getBatch());
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

		spriteBatch.begin();
		drivingModeHUD.draw();
		spriteBatch.end();
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

	public enum GAME_STAT
	{
		PAUSE,
		PLAY,
	}

}//class
