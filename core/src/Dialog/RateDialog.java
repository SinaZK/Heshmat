package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

import Countly.CountlyStrings;
import DataStore.PlayerStatData;
import Entity.Button;
import Misc.TextureHelper;
import Scene.PurchaseScene;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 6/14/16.
 * 09:34
 */
public class RateDialog extends Dialog
{
	PlayerStatData playerStatData;
	long price;

	float DX, DY;
	String add = "gfx/scene/dialog/rate/";

	Button rateButton;

	public boolean isFinished = false;

	public RateDialog(DialogManager dialogManager)
	{
		super(dialogManager);

		playerStatData = dialogManager.activity.playerStatData;
		loadResources();
	}

	public void loadResources()
	{
		rateButton = new Button(TextureHelper.loadTexture(add + "cm1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "cm2.png", dialogManager.disposalTexture));


		rateButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{

				dialogManager.popQ();

				dialogManager.activity.googleServices.Countly(CountlyStrings.EventRateDialogCRate);

				dialogManager.activity.googleServices.rateGame();

				isFinished = true;
			}
		});

		super.create();//adding exitButton

		exitButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				dialogManager.popQ();

				dialogManager.activity.googleServices.Countly(CountlyStrings.EventRateDialogCancel);
			}
		});
	}

	public void create(float DXx, float DYy)
	{
		DX = DXx;
		DY = DYy;
		this.price = price;
		isFinished = false;

		scene.getActors().clear();

		scene.addActor(rateButton);
		backSprite = new Sprite(dialogManager.rateBackTexture);

		scene.addActor(exitButton);

		setButtonPositions();
	}

	public void setButtonPositions()
	{
		backSprite.setPosition(DX + (SceneManager.WORLD_X - backSprite.getWidth()) / 2, DY + (SceneManager.WORLD_Y - backSprite.getHeight()) / 2);

		rateButton.setPosition(DX + 350, DY + 140);
		exitButton.setPosition(DX + 470, DY + 320);
	}
}
