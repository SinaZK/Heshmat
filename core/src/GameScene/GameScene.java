package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.AnimatedSprite;
import Misc.BodyStrings;
import Physics.SizakBody;
import Physics.SizakBodyLoader;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Scene.BaseScene;
import SceneManager.SceneManager;

public class GameScene extends BaseScene
{

	public boolean isDebugRender = true;

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
	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public Batch spriteBatch;
	public PolygonSpriteBatch polygonSpriteBatch;
	public float gameSpeed = 60f;

	public GAME_STAT gameStat;
	public GameManager gameManager;

	AnimatedSprite animatedSprite;


	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
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

	SizakBody body;

	@Override
	public void create()
	{
		PhysicsFactory.createBoxBody(world, 0, 0, 800, 50, BodyDef.BodyType.StaticBody).setUserData(BodyStrings.GroundString);

		gameStat = GAME_STAT.PLAY;

		setInput();

		body = SizakBodyLoader.loadBodyFile("gfx/jointbody.body", world, disposeTextureArray);
//		((RevoluteJoint) body.joints.get(0)).enableMotor(true);
//		((RevoluteJoint) body.joints.get(0)).setMotorSpeed(-1);
//		((RevoluteJoint) body.joints.get(0)).setMaxMotorTorque(100);

//		((RevoluteJoint) body.joints.get(1)).enableMotor(true);
//		((RevoluteJoint) body.joints.get(1)).setMotorSpeed(-1);
//		((RevoluteJoint) body.joints.get(1)).setMaxMotorTorque(100);
//		body.bodies.get(0).getmBody().getFixtureList().get(0).setsetFilterData(new Filter());
//		body.getBodyByName("pare1").getmBody().getFixtureList().get(0).setSensor(true);
//		body.getBodyByName("pare2").getmBody().getFixtureList().get(0).setSensor(true);
//		((WheelJoint)body.joints.get(0)).enableMotor(true);
//		((WheelJoint)body.joints.get(0)).setMotorSpeed(-5);
//		((WheelJoint)body.joints.get(0)).setMaxMotorTorque(100);
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
			inputMultiplexer = null;
			inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(new GameSceneInput(this));
			inputMultiplexer.addProcessor(gameManager.selectedGun);
			Gdx.input.setInputProcessor(inputMultiplexer);
		}
	}

	public BitmapFont font16 = new BitmapFont(Gdx.files.internal("font/16w.fnt"));
	public BitmapFont font22 = new BitmapFont(Gdx.files.internal("font/22w.fnt"));
	public BitmapFont font24 = new BitmapFont(Gdx.files.internal("font/24w.fnt"));
	public BitmapFont font24Gold = new BitmapFont(Gdx.files.internal("font/24gold.fnt"));

	public void update()
	{
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
		body.draw(getBatch());
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
		drawFPS();
		spriteBatch.end();
	}

	public void drawFPS()
	{
		font16.draw(getBatch(), "" + Gdx.graphics.getFramesPerSecond(), 10, 30);
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
