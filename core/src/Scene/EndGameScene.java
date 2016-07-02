package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import Entity.Button;
import Entity.Entity;
import GameScene.GameScene;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;

public class EndGameScene extends BaseScene
{
	GameScene gameScene;
	SceneManager sceneManager;
	public EndGameScene(GameScene gameScene)
	{
		super(gameScene.mSceneManager.act, new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		sceneManager = gameScene.mSceneManager;
		this.gameScene = gameScene;
	}


	public float DX;
	public float DY;
	String add = "gfx/scene/endgame/";
	InputProcessor gameSceneInput;

	Button restartButton;
	Button backToMenuButton;

	boolean isLevelFinished;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;


		Entity back = new Entity(TextureHelper.loadTexture(add+"back.jpg", disposeTextureArray));
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);
		attachChild(back);


		restartButton = new Button(TextureHelper.loadTexture(add+"restart1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"restart2.png", disposeTextureArray));
		restartButton.setRunnable(act, new Runnable() {

			@Override
			public void run()
			{
				restartGameScene();
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

		final Entity recordEnt = new Entity(TextureHelper.loadTexture(add + "dist.png", disposeTextureArray));
		final Entity coinEnt = new Entity(TextureHelper.loadTexture(add+"coin.png", disposeTextureArray));
		final Entity flipEnt = new Entity(TextureHelper.loadTexture(add+"flip.png", disposeTextureArray));
		final Entity airEnt = new Entity(TextureHelper.loadTexture(add+"airtime.png", disposeTextureArray));

		recordEnt.setPosition(DX + 200, DY + 305);
		airEnt.setPosition(DX + 200, DY + 240);
		flipEnt.setPosition(DX + 200, DY + 170);
		coinEnt.setPosition(DX + 200, DY + 105);

		float SZ = 100;
		restartButton.setSize(SZ, SZ);
		backToMenuButton.setSize(SZ, SZ);
		showScoreButton.setSize(SZ, SZ);

		backToMenuButton.setPosition(DX + 241, DY + 20);
		showScoreButton.setPosition(DX + 370, DY + 20);
		restartButton.setPosition(DX + 498, DY + 20);



		attachChild(showScoreButton);
		attachChild(backToMenuButton);
		attachChild(restartButton);
		attachChild(recordEnt);
		attachChild(coinEnt);
		attachChild(flipEnt);
		attachChild(airEnt);
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
	}

	public void set(boolean isLevelFinished)
	{
		gameSceneInput = Gdx.input.getInputProcessor();

		this.isLevelFinished = isLevelFinished;

		Gdx.input.setInputProcessor(this);

		act.saveAfterGameScene();
	}

	public void restartGameScene()
	{
		Log.e("EndGameScene.java", "restarting");
		Gdx.input.setInputProcessor(gameSceneInput);
		gameScene.restart();
	}

	public void goToGarageScene()
	{
		gameScene.goToGarageScene();
	}

}
