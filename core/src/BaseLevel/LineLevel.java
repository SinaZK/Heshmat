package BaseLevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import BaseLevel.Modes.DrivingMode;
import BaseLevel.Modes.LevelMode;
import BaseLevel.Modes.ShootingMode;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import Misc.FileLoader;
import Misc.Log;
import Misc.TextureHelper;
import Scene.EndGameScene;
import Scene.EndGameScenes.LineEndGameScene;
import Scene.EndGameScenes.LoopEndGameScene;

/**
 * Created by sinazk on 10/8/16.
 * 09:21
 */
public class LineLevel extends BaseLevel
{
	public LineLevel(GameManager gameManager)
	{
		super(gameManager);
	}

	@Override
	public void start()
	{
		super.start();

        Log.e("LineLevel", "Starting");
	}

	@Override
	public void run()
	{
        super.run();
	}

    @Override
    public void load(String add) {
        super.load(add);
    }

    @Override
    public EndGameScene createEndGameScene()
    {
        return new LineEndGameScene(gameScene);
    }

}
