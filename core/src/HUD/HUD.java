package HUD;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import Entity.Entity;
import GameScene.GameScene;
import SceneManager.SceneManager;
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

	public HUD(GameScene gameScene, Viewport viewport)
	{
		super(viewport);
		this.gameScene = gameScene;
		textureQ = gameScene.disposeTextureArray;
	}

}
