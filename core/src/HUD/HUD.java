package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import Entity.Button;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.TextureHelper;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/21/16.
 * 5:57
 */
public class HUD extends Stage
{
	MainActivity act;
	GameScene gameScene;
	ArrayList <Texture> textureQ;


	public HUD(final GameScene gameScene, Viewport viewport)
	{
		super(viewport);
		this.gameScene = gameScene;
		textureQ = gameScene.disposeTextureArray;
	}

	@Override
	public void draw()
	{
		if(gameScene.gameManager.levelManager.currentLevel.getCurrentPart().isFinished)
			return;
		super.draw();
//		getBatch().begin();
//		gameScene.font16.draw(getBatch(), "" + Gdx.graphics.getFramesPerSecond(), 10, 30);
//		getBatch().end();
	}
}
