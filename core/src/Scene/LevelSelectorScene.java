package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import BaseLevel.LevelPackage;
import DataStore.LevelStatData;
import Entity.LevelEntity;
import Enums.Enums;
import Misc.Log;
import SceneManager.SceneManager;

public class LevelSelectorScene extends BaseScene
{
	SceneManager mSceneManager;
	public LevelSelectorScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


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

		levelPackage = new LevelPackage(act);
		levelPackage.load("gfx/lvl/pack" + act.selectorStatData.selectedLevelPack + "/level.pckg");

		Log.e("LevelSelectorScene.java", "selectedPack = " + act.selectorStatData.selectedLevelPack + " numOfLevels = " + levelPackage.numberOfLevels);

		act.loadLevelData(levelPackage, act.selectorStatData.selectedLevelPack);

		int padding = 10;
		float startX = 100;
		float startY = 300;
		float width = 50;
		float height = 50;

		int numInRow = 4;

		float posX = startX, posY = startY;
		for(int i = 1;i <= levelPackage.numberOfLevels;i++)
		{
			LevelEntity levelEntity = new LevelEntity(act, disposeTextureArray, act.levelStatDatas.get(i), i);

			levelEntity.setSize(width, height);
			levelEntity.setPosition(posX, posY);

			posX += padding + width;
			if(i % numInRow == 0)
			{
				posY -= height + padding;
				posX = startX;
			}

			levelEntity.attachToScene(this);
		}
	}

	@Override
	public void dispose()
	{
		act.saveSelector();
		act.saveAllLevelDatas(levelPackage.numberOfLevels);
		super.dispose();
	}
}
