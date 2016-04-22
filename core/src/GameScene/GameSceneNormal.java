package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Entity;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Scene.BaseScene;
import SceneManager.SceneManager;

public class GameSceneNormal extends BaseScene
{
	
	public boolean isDebugRender = true;

	SceneManager mSceneManager;
	public GameSceneNormal(SceneManager sceneManager, Viewport v){super(sceneManager.act, v); mSceneManager = sceneManager;}
	
	public OrthographicCamera camera;
	public Box2DDebugRenderer debugRenderer;
	public World world;
	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	Batch spriteBatch;
	public float gameSpeed = 60f;
	
	GAME_STAT gameStat;
	GameManager gameManager;
	
	@Override
	public void loadResources() 
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
		camera = (OrthographicCamera) getCamera();
		spriteBatch = getBatch();
		
		if(isDebugRender)
			debugRenderer = new Box2DDebugRenderer(true, true, false, true, true, false);

		gameManager = new GameManager(this);
		gameManager.loadResources();
	}

	Entity tx;
	@Override
	public void create()
	{
		world = new World(new Vector2(0, -9.8f), false);
		PhysicsFactory.createBoxBody(world, 0, 0, 800, 50, BodyDef.BodyType.StaticBody);

		gameStat = GAME_STAT.PLAY;

		setInput();
	}
	
	@Override
	public void run()
	{
		update();
		draw();
	}
	
	public  void pause()
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
		world.step(1/gameSpeed, 6, 2);

		gameManager.run();
	}
	
	public void draw()
	{
		spriteBatch.begin();


		gameManager.draw();


		drawFPS();

		spriteBatch.end();
		
		if(isDebugRender)
		{
			Matrix4 debugMatrix = camera.combined.cpy();
			debugRenderer.render(world, debugMatrix.scl(1 * PhysicsConstant.PIXEL_TO_METER));
		}
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
