package HUD;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
import GameScene.GameScene;

/**
 * Created by sinazk on 5/21/16.
 * 6:03
 */
public class ShootingHUD extends HUD
{

	Button nextGunButton, prevGunButton, reloadButton;
	float DX, DY;

	public ShootingHUD(final GameScene gameScene, Viewport viewport)
	{
		super(gameScene, viewport);

		DX = gameScene.DX;
		DY = gameScene.DY;

		float y = 270;
		float x = 0;

		prevGunButton = new Button(gameScene.nextGunButtonTexture,
				gameScene.nextGunButtonTexture);
		prevGunButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.prevGun();
			}
		});
		prevGunButton.setPosition(DX + x + 10, DY + y);
		addActor(prevGunButton);



		reloadButton = new Button(gameScene.selectedGunButtonTexture,
				gameScene.selectedGunButtonTexture);
		reloadButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.getSelectedGun().reload();
			}
		});
		reloadButton.setPosition(DX + x + 80, DY + y);
		addActor(reloadButton);




		nextGunButton = new Button(gameScene.nextGunButtonTexture,
				gameScene.nextGunButtonTexture);
		nextGunButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.nextGun();
			}
		});
		nextGunButton.setPosition(DX + x + 165, DY + y);
		addActor(nextGunButton);
	}

	@Override
	public void act()
	{
		super.act();
	}

	Sprite nextSprite, prevSprite, selectSprite;
	@Override
	public void draw()
	{
		super.draw();

		if(gameScene.gameManager.levelManager.currentLevel.getCurrentPart().isFinished)
			return;

		getBatch().begin();

		nextSprite = gameScene.gameManager.gunManager.getNextGunSelectSprite();
		selectSprite = gameScene.gameManager.gunManager.getSelectedGun().selectSprite;
		prevSprite = gameScene.gameManager.gunManager.getPrevGunSelectSprite();

		if(nextSprite != null)
		{
			nextSprite.setSize(nextGunButton.getWidth(), nextGunButton.getHeight());
			nextSprite.setPosition(nextGunButton.getX(), nextGunButton.getY());
			nextSprite.draw(getBatch());
		}

		if(prevSprite != null)
		{
			prevSprite.setSize(prevGunButton.getWidth(), prevGunButton.getHeight());
			prevSprite.setPosition(prevGunButton.getX(), prevGunButton.getY());
			prevSprite.draw(getBatch());
		}

		selectSprite.setSize(reloadButton.getWidth(), reloadButton.getHeight());
		selectSprite.setPosition(reloadButton.getX(), reloadButton.getY());
		selectSprite.draw(getBatch());

		gameScene.font16.setColor(0, 0, 0, 1);
		gameScene.font16.draw(getBatch(), ""+ (int)gameScene.gameManager.gunManager.getSelectedGun().ammo + "/" +
				(int)gameScene.gameManager.gunManager.getSelectedGun().getClipSize()
				, selectSprite.getX() + selectSprite.getWidth() / 2 - 20, selectSprite.getY() - 10);

		getBatch().end();
	}
}
