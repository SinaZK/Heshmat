package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.Viewport;

import BaseLevel.LevelPackage;
import DataStore.LevelStatData;
import Entity.Button;
import Entity.Entity;
import Entity.LevelEntity;
import Misc.TextureHelper;
import Scene.Garage.GarageScene;
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

		loadLeaderBoardLevelButtons(width, height);
	}

	@Override
	public void create()
	{
		super.create();

		addBackToMenuButton();
		attachChild(mSceneManager.goldShowButton);

		mSceneManager.goldShowButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				InputProcessor inputProcessor = Gdx.input.getInputProcessor();
				mSceneManager.setCurrentScene(SceneManager.SCENES.PURCHASE_SCENE, null);

				PurchaseScene scene = (PurchaseScene) mSceneManager.currentBaseScene;
				scene.lastScene = LevelSelectorScene.this;
				scene.lastInput = inputProcessor;
			}
		});
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
		mSceneManager.drawGoldSprite(getBatch(), true);
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

    public void loadLeaderBoardLevelButtons(float width, float height)
    {
        Button lineButton = new Button(TextureHelper.loadTexture("gfx/scene/level/line1.png", disposeTextureArray),
                TextureHelper.loadTexture("gfx/scene/level/line2.png", disposeTextureArray));

        lineButton.setSize(width, height);
        lineButton.setPosition(DX + 650, DY + 196 + 100);

        lineButton.setRunnable(act, new Runnable()
        {
            @Override
            public void run()
            {
                if(act.starManager.getStarNum() < SceneManager.LINE_STARS)
                {
                    String second = " " + (SceneManager.LINE_STARS - act.starManager.getStarNum())+" ";
                    String third = "تا ستاره دیگه نیاز داری";
                    String mark = "!!!";
                    act.googleServices.makeToastLong(second + third + mark);
                    return;
                }

                act.selectorStatData.selectedLevel = LINE_INT;
                act.sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
                LevelSelectorScene.this.dispose();
            }
        });
        attachChild(lineButton);

        Button LoopButton = new Button(TextureHelper.loadTexture("gfx/scene/level/loop1.png", disposeTextureArray),
                TextureHelper.loadTexture("gfx/scene/level/loop2.png", disposeTextureArray));

        LoopButton.setSize(width, height);
        LoopButton.setPosition(DX + 650, DY + 196);

        LoopButton.setRunnable(act, new Runnable()
        {
            @Override
            public void run()
            {
                if(act.starManager.getStarNum() < SceneManager.LOOP_STARS)
                {
                    String second = " " + (SceneManager.LOOP_STARS - act.starManager.getStarNum())+" ";
                    String third = "تا ستاره دیگه نیاز داری";
                    String mark = "!!!";
                    act.googleServices.makeToastLong(second + third + mark);
                    return;
                }

                act.selectorStatData.selectedLevel = LOOP_INT;
                act.sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
                LevelSelectorScene.this.dispose();
            }
        });

        attachChild(LoopButton);
    }

    public static final int LOOP_INT = -1;
    public static final int LINE_INT = -2;
}
