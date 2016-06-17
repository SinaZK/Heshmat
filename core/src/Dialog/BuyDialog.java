package Dialog;

import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.PlayerStatData;
import Entity.Button;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/14/16.
 * 09:34
 */
public class BuyDialog extends Dialog
{
	PlayerStatData playerStatData;
	long price;

	float DX, DY;
	String add = "gfx/scene/dialog/buy/";

	Button canOkButton, canCancelButton;
	Button cantOkButton;

	public boolean isFinished = false;
	public boolean isBought = false;

	public BuyDialog(DialogManager dialogManager)
	{
		super(dialogManager);

		playerStatData = dialogManager.activity.playerStatData;
		loadResources();
	}

	static float buttonWidth  = 80;
	static float buttonHeight = 30;
	public void loadResources()
	{
		canOkButton = new Button(TextureHelper.loadTexture(add + "canok1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "canok2.png", dialogManager.disposalTexture));
		cantOkButton = new Button(TextureHelper.loadTexture(add + "cantok1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "cantok2.png", dialogManager.disposalTexture));
		canCancelButton = new Button(TextureHelper.loadTexture(add + "cantcancel1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "cancancel2.png", dialogManager.disposalTexture));

		canOkButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				long money = playerStatData.getMoney();
				money -= price;
				playerStatData.setMoney(money);
				dialogManager.activity.savePlayerStatData();
				Log.e("BuyDialog.java", "Purchase Completed : " + playerStatData.getMoney());
				isFinished = true;
				isBought = true;
			}
		});

		canCancelButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				isFinished = true;
				dialogManager.popQ();
			}
		});

		canOkButton.setSize(buttonWidth, buttonHeight);
		cantOkButton.setSize(buttonWidth, buttonHeight);
		canCancelButton.setSize(buttonWidth, buttonHeight);

		backSprite = new Sprite(dialogManager.backGroundTexture);
		backSprite.setSize(300, 300);

		super.create();//adding exitButton
	}

	public void create(float DXx, float DYy, long price)
	{
		DX = DXx;
		DY = DYy;
		this.price = price;
		isFinished = false;
		isBought = false;

		scene.getActors().clear();

		if(canBuy())
		{
			scene.addActor(canOkButton);
			scene.addActor(canCancelButton);
		}
		else
		{
			scene.addActor(cantOkButton);
		}

		scene.addActor(exitButton);

		setButtonPositions();
	}

	public boolean canBuy()
	{
		return price <= playerStatData.getMoney();
	}

	public void setButtonPositions()
	{
		backSprite.setPosition(DX + (SceneManager.WORLD_X - backSprite.getWidth()) / 2, DY + (SceneManager.WORLD_Y - backSprite.getHeight()) / 2);

		exitButton.setPosition(DX + 520, DY + 350);
		canOkButton.setPosition(DX + 450, DY + 100);
		cantOkButton.setPosition(DX + 450, DY + 100);
		canCancelButton.setPosition(DX + 250, DY + 100);
	}
}
