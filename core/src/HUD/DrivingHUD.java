package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
import GameScene.GameScene;
import Misc.TextureHelper;

/**
 * Created by sinazk on 5/21/16.
 * 6:03
 */
public class DrivingHUD extends HUD
{
	public Button gasButton, brakeButton;
	public DrivingHUD(GameScene gameScene, Viewport viewport)
	{
		super(gameScene, viewport);
	}

	@Override
	public void act()
	{
		super.act();
	}

	@Override
	public void draw()
	{
		super.draw();
		getBatch().begin();
		gameScene.font16.draw(getBatch(), "" + Gdx.graphics.getFramesPerSecond(), 10, 30);
		getBatch().end();
	}
}
