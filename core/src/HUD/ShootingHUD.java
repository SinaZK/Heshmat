package HUD;


import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
import GameScene.*;
import Misc.TextureHelper;

/**
 * Created by sinazk on 5/21/16.
 * 6:03
 */
public class ShootingHUD extends HUD
{

	Button switchGunButton;
	public ShootingHUD(final GameScene gameScene, Viewport viewport)
	{
		super(gameScene, viewport);


		switchGunButton = new Button(TextureHelper.loadTexture("gfx/scene/game/restart1.png", gameScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/game/restart2.png", gameScene.disposeTextureArray));
		switchGunButton.setPosition(300, 10);
		switchGunButton.setSize(70, 70);
		addActor(switchGunButton);

		switchGunButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.swapGun();
			}
		});
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
	}
}
