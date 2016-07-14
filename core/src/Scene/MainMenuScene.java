package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import Countly.CountlyStrings;
import DataStore.DataKeyStrings;
import Entity.Button;
import Entity.Entity;
import Misc.TextureHelper;
import SceneManager.SceneManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;

public class MainMenuScene extends BaseScene
{
	SceneManager mSceneManager;

	public MainMenuScene(SceneManager sceneManager, Viewport v)
	{
		super(sceneManager.act, v);
		mSceneManager = sceneManager;
	}


	boolean isUploaded = false;
	boolean isSettingRunning = false, isSetting = false;

	static int rateBonus = 25000;

	public float DX;
	public float DY;
	String add = "gfx/scene/main/";

//	MainMenuExit exitDialog;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;


		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray));
		back.setSize(850, 500);
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);

		attachChild(back);
	}

	@Override
	public void create()
	{
		Button levelSelectButton = new Button(TextureHelper.loadTexture(add + "next1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "next2.png", disposeTextureArray));
		levelSelectButton.setPosition(DX + 674, DY + 184);
		levelSelectButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.LEVEL_PACKAGE_SELECTOR, null);
			}
		});
		levelSelectButton.setSize(155 / 2, 324 / 2);

		Button showLeaderBoardButton = new Button(TextureHelper.loadTexture(add + "leader1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "leader2.png", disposeTextureArray));
		showLeaderBoardButton.setPosition(DX + 50 / 2, DY + 595 / 2);
		showLeaderBoardButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
//				act.googleServices.showTapsell();

				if(act.googleServices.isSignedIn())
				{

//					act.googleServices.Countly(CountlyStrings.GoogleLeaderBoardMain);
					mSceneManager.act.googleServices.showScores();
				} else
					act.googleServices.signIn();
			}
		});
		showLeaderBoardButton.setSize(50, 50);

//		final Button signInButton = new Button(TextureHelper.loadTexture(add+"sign1.png", disposeTextureArray),
//				TextureHelper.loadTexture(add+"sign2.png", disposeTextureArray))
//		{
//			@Override
//			public void draw(Batch batch, float parentAlpha) 
//			{
//				isClicked = mSceneManager.act.googleServices.isSignedIn();
//				if(isClicked)
//				{
//					act.googleServices.Countly(CountlyStrings.GoogleSignIn);
//				}
//				super.draw(batch, parentAlpha);
//			}
//		};
//		signInButton.setPosition(DX + 35, DY + 375);
//		signInButton.setRunnable(act, new Runnable() {
//
//			@Override
//			public void run() 
//			{
//				if(isInfo)
//					return;
//				mSceneManager.act.googleServices.signIn();
//			}
//		});
//		signInButton.setSize(65, 65);

		final Entity cmBonus = new Entity(TextureHelper.loadTexture(add + "gift.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				if(!act.gameStatData.hasRate)
					super.draw(batch, parentAlpha);
			}
		};

		cmBonus.setSize(65, 65);
		cmBonus.setPosition(DX + 50 / 2, DY + 480 / 2);

		attachChild(cmBonus);

		final Button cmButton = new Button(TextureHelper.loadTexture(add + "nazar1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "nazar2.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				if(!act.gameStatData.hasRate)
					super.draw(batch, parentAlpha);
			}
		};
		cmButton.setPosition(DX + 20, DY + 35);
		cmButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
//				act.googleServices.Countly(CountlyStrings.SceneCommentButton);
				mSceneManager.act.googleServices.rateGame();


				Timer.schedule(new Task()
				{

					@Override
					public void run()
					{
						act.gameStatData.hasRate = true;
						act.saveManager.saveDataValue(DataKeyStrings.GameStatData, act.gameStatData);
						act.googleServices.makeToastShorts("ممنون که نظر دادی");
						act.addMoney(rateBonus, true);
					}
				}, 3f);
			}
		});
		cmButton.setSize(65, 65);

		Button soundButton = new Button(TextureHelper.loadTexture(add + "sound1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "sound2.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				isClicked = act.settingStatData.isSoundOn;
				super.draw(batch, parentAlpha);
			}
		};
		soundButton.setPosition(DX + 1385 / 2 - 20, DY + 828 / 2);
		setAlpha(soundButton, 0);
		soundButton.setRotation(-90);

		soundButton.setSize(35, 35);

		soundButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				if(!isSetting || isSettingRunning)
					return;


				if(!act.settingStatData.isSoundOn)//trying to change sound setting
					act.googleServices.Countly(CountlyStrings.SettingSoundOn);
				else
					act.googleServices.Countly(CountlyStrings.SettingSoundOff);


				act.settingStatData.isSoundOn = !act.settingStatData.isSoundOn;
				act.saveSetting();
			}
		});

		Button musicButton = new Button(TextureHelper.loadTexture(add + "music1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "music2.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{

				isClicked = act.settingStatData.isMusicOn;
				super.draw(batch, parentAlpha);
			}
		};
		musicButton.setSize(35, 35);

		musicButton.setPosition(DX + 1310 / 2 - 40, DY + 828 / 2);
		setAlpha(musicButton, 0);
		musicButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{

				if(!isSetting || isSettingRunning)
					return;

				if(!act.settingStatData.isMusicOn)//trying to change sound setting
					act.googleServices.Countly(CountlyStrings.SettingMusicOn);
				else
					act.googleServices.Countly(CountlyStrings.SettingMusicOff);

				act.settingStatData.isMusicOn = !act.settingStatData.isMusicOn;
				act.saveSetting();

				act.audioManager.setVolume();
			}
		});

		musicButton.setRotation(-90);

		final ArrayList<Button> settingArr = new ArrayList<Button>();
		settingArr.add(musicButton);
		settingArr.add(soundButton);

		attachChild(musicButton);
		attachChild(soundButton);


		final Button settingButton = new Button(TextureHelper.loadTexture(add + "sett1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "sett2.png", disposeTextureArray));
		settingButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				if(isSettingRunning)
					return;

				isSettingRunning = true;

				if(!isSetting)
				{
					for (int i = 0; i < settingArr.size(); i++)
					{
						settingArr.get(i).addAction(parallel(rotateBy(90, 0.5f)));
						settingArr.get(i).addAction(parallel(alpha(1, 0.5f)));
					}

					settingButton.addAction(parallel(rotateBy(-90, 0.5f)));

					Timer.schedule(new Task()
					{

						@Override
						public void run()
						{
							isSetting = !isSetting;
							isSettingRunning = false;
						}
					}, 0.5f);
				}

				if(isSetting)
				{
					for (int i = 0; i < settingArr.size(); i++)
					{
						settingArr.get(i).addAction(parallel(rotateBy(-90, 0.5f)));
						settingArr.get(i).addAction(parallel(alpha(0, 0.5f)));
					}

					settingButton.addAction(parallel(rotateBy(+90, 0.5f)));

					Timer.schedule(new Task()
					{

						@Override
						public void run()
						{
							isSetting = !isSetting;
							isSettingRunning = false;
						}
					}, 0.5f);
				}

			}
		});

		settingButton.setPosition(DX + 1460 / 2, DY + 816 / 2);
		settingButton.setSize(50, 50);

		if(!act.gameStatData.hasRate)
			attachChild(cmButton);
		attachChild(levelSelectButton);
		attachChild(showLeaderBoardButton);
		attachChild(settingButton);

		input.addProcessor(this);
		Gdx.input.setInputProcessor(input);
	}

	InputMultiplexer input = new InputMultiplexer();

	BitmapFont font = new BitmapFont();

	@Override
	public void run()
	{
		if(Gdx.input.isKeyPressed(Keys.BACK))
		{
			Gdx.app.exit();
		}

		for (int i = 0; i < getActors().size; i++)
			getActors().items[i].setVisible(true);
		act(Gdx.graphics.getDeltaTime());
		draw();

//		exitDialog.run();
//		exitDialog.draw(DX + 171, DY + 179);

	}

	private void setAlpha(Button b, float alpha)
	{
		b.setColor(b.getColor().r, b.getColor().g, b.getColor().b, alpha);
	}

	@Override
	public void dispose()
	{
		font.dispose();
		super.dispose();
	}
}