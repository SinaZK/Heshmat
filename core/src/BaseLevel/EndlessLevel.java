package BaseLevel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import BaseLevel.Modes.DrivingMode;
import BaseLevel.Modes.LevelMode;
import BaseLevel.Modes.ShootingMode;
import Enemy.EnemyState.StreetLight;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.FileLoader;
import Misc.Log;
import Misc.TextureHelper;
import Terrain.Terrain;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/14/16.
 * 17:27
 */
public class EndlessLevel extends BaseLevel
{

	float drivingDistance, drivingStep;

	ArrayList<String> enemyStrings = new ArrayList<String>();
	ArrayList<Integer> startLevels = new ArrayList<Integer>();

	public ModeSplashImage waveModeSplashImage;

	public int currentWave = 1;

	public EndlessLevel(GameManager gameManager, long currentWave)
	{
		super(gameManager);

		this.currentWave = (int)currentWave;
	}

	@Override
	public void start()
	{
        addDrivingMode((int)(drivingDistance + drivingStep * currentWave), 100);
		addShootingMode(2, 5);

		super.start();
		levelParts.get(0).modeSplashImage.time = -1;//do not Draw it!!!
		setWaveSplash();
	}

	public void setWaveSplash()
	{
		waveModeSplashImage.set(0.9f, 1.1f, 0.01f, 3.0f);
	}

	@Override
	public void load(String add)
	{
		loadTerrain(add);
		terrain.level = this;
		terrain.BGSprite = new Sprite(TextureHelper.loadTexture("gfx/lvl/pack" + act.selectorStatData.selectedLevelPack + "/back.png", gameScene.disposeTextureArray));

		FileLoader loader = new FileLoader();
		loader.loadFile(add + "level.lvl");

		drivingDistance = loader.getFloat(0, 1);
		drivingStep = loader.getFloat(1, 1);
		//line 3 is Blank

		int lineNumber = 3;
		while(true)
		{
			if(loader.getLine(lineNumber).equals("END"))
				break;

			enemyStrings.add(loader.getString(lineNumber, 0));
			startLevels.add(loader.getInt(lineNumber, 1));

			lineNumber++;
		}


		waveModeSplashImage = new ModeSplashImage(levelManager, levelManager.WaveModeSplashTexture)
		{
			@Override
			public void draw(Batch batch)
			{
				super.draw(batch);

				if(isDrawing())
				{
					gameScene.font22.setColor(0, 0, 0, 1);
					gameScene.font22.draw(batch, ""+ (currentWave),
							getX() + getWidth() / 2, getY() + getHeight() / 2);
				}
			}
		};
	}

	@Override
	public void restart()
	{
		super.restart();
	}

	@Override
	public void run()
	{
		terrain.run();

		if(currentPart >= levelParts.size())
			return;

		if(levelParts.get(currentPart).isFinished && levelParts.get(currentPart).isCameraDone)
		{
			currentPart++;
			if(currentPart < levelParts.size())
			{
				levelParts.get(currentPart).start();

				levelParts.get(currentPart).modeSplashImage.time = -1;//do not Draw it!!!
			}

			if(currentPart % 2 == 1)
			{
				addDrivingMode((int)(drivingDistance + drivingStep * currentWave), 100);
				addShootingMode(2, 5);
			}

			if(currentPart % 2 == 0)
			{
				setWaveSplash();
				currentWave++;
				act.levelPackageStatDatas[act.selectorStatData.selectedLevelPack].addToEndlessStartingWave();
			}
		}

		if(currentPart < levelParts.size())
			levelParts.get(currentPart).run();
	}

	public DrivingMode getDrivingMode(int distance, int time)
	{
		DrivingMode drivingMode = new DrivingMode(levelManager);

		drivingMode.distance = distance;
		drivingMode.fullTime = time;


		return drivingMode;
	}

	public void addDrivingMode(int distance, int time)
	{
		addMode(getDrivingMode(distance, time));
	}

	public ShootingMode getShootingMode(int numberOfBirdsInEachWave, int level)
	{
		ShootingMode shootingMode = new ShootingMode(levelManager);

		float currentTime = 0;
		for(int i = 0;i < enemyStrings.size();i++)
		{
			if(currentWave + 1 >= startLevels.get(i))
			{
				addWave(shootingMode, enemyStrings.get(i), 1 + currentWave / 5, 4 + currentWave / 20, 0.7f, currentTime);
				currentTime += 4 + currentWave / 30.0;
			}
		}


		return shootingMode;
	}

	public void addShootingMode(int numberOfBirdsInEachWave, int level)
	{
		addMode(getShootingMode(numberOfBirdsInEachWave, level));
	}

	public BaseWave addWave(ShootingMode shootingMode, String type, int level, int count, float diffTime, float releaseTime)
	{
		BaseWave wave = new BaseWave(gameManager, shootingMode);

		wave.create(type, level, count, null);
		wave.diffTime = diffTime;
		wave.releaseTime = releaseTime;

		shootingMode.numberOfWaves++;
		shootingMode.waves.add(wave);

		return wave;
	}

	public void addMode(LevelMode levelMode)
	{
		levelParts.add(levelMode);
	}

}
