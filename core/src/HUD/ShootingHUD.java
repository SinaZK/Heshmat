package HUD;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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

		float y = 20;
		float x = 30;

		final float alpha = 0.6f;

		prevGunButton = new Button(gameScene.nextGunButtonTexture,
				gameScene.nextGunButtonTexture)
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				super.draw(batch, alpha);
			}
		};
		prevGunButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.prevGun();
			}
		});
		prevGunButton.setPosition(DX + x, DY + y + 4);
		addActor(prevGunButton);



		reloadButton = new Button(gameScene.selectedGunButtonTexture,
				gameScene.selectedGunButtonTexture)
		{
				@Override
				public void draw(Batch batch, float parentAlpha)
				{
					super.draw(batch, alpha);
				}
		};
		reloadButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.getSelectedGun().reload();
			}
		});
		reloadButton.setPosition(DX + x + 70, DY + y);
		addActor(reloadButton);




		nextGunButton = new Button(gameScene.nextGunButtonTexture,
				gameScene.nextGunButtonTexture)
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				super.draw(batch, alpha);
			}
		};
		nextGunButton.setRunnable(gameScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				ShootingHUD.this.gameScene.gameManager.gunManager.nextGun();
			}
		});
		nextGunButton.setPosition(DX + x + 150, DY + y + 4);
		addActor(nextGunButton);

		initSwipeInput();

		nextGunButton.addListener(swipeDetector);
		prevGunButton.addListener(swipeDetector);
		reloadButton.addListener(swipeDetector);
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

		int nextID = gameScene.gameManager.gunManager.getNextID();
		int prevID = gameScene.gameManager.gunManager.getPrevID();
		int id = gameScene.gameManager.gunManager.getID();

		nextSprite = gameScene.gameManager.gunManager.getNextGunSelectSprite();
		selectSprite = gameScene.gameManager.gunManager.getSelectedGun().selectSprite;
		prevSprite = gameScene.gameManager.gunManager.getPrevGunSelectSprite();

		if(nextSprite != null)
		{
			nextSprite.setSize(nextGunButton.getWidth(), nextGunButton.getHeight());
			nextSprite.setPosition(nextGunButton.getX(), nextGunButton.getY());

			if(id != nextID)
				nextSprite.draw(getBatch());
		}

		if(prevSprite != null)
		{
			prevSprite.setSize(prevGunButton.getWidth(), prevGunButton.getHeight());
			prevSprite.setPosition(prevGunButton.getX(), prevGunButton.getY());

			if(id != prevID)
				prevSprite.draw(getBatch());
		}

		selectSprite.setSize(reloadButton.getWidth(), reloadButton.getHeight());
		selectSprite.setPosition(reloadButton.getX(), reloadButton.getY());
		selectSprite.draw(getBatch());

		gameScene.font12.setColor(1, 1, 1, 1);
		gameScene.font12.draw(getBatch(), ""+ (int)gameScene.gameManager.gunManager.getSelectedGun().ammo + "/" +
				(int)gameScene.gameManager.gunManager.getSelectedGun().getClipSize()
				, selectSprite.getX() + selectSprite.getWidth() / 2 - 15, selectSprite.getY() + 14);

		getBatch().end();
	}

	public float swipeVX = 5;
	ActorGestureListener swipeDetector;
	public void initSwipeInput()
	{
		swipeDetector = new ActorGestureListener()
		{
			@Override
			public void fling(InputEvent event, float velocityX, float velocityY, int button)
			{
				if(velocityX < -swipeVX)
					gameScene.gameManager.gunManager.nextGun();

				if(velocityX > swipeVX)
					gameScene.gameManager.gunManager.prevGun();
			}
		};
	}
}
