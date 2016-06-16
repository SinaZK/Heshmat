package Dialog;

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
		Log.e("BuyDialog.java", "loading res");
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
			}
		});

		canCancelButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				dialogManager.popQ();
			}
		});

		canOkButton.setSize(buttonWidth, buttonHeight);
		cantOkButton.setSize(buttonWidth, buttonHeight);
		canCancelButton.setSize(buttonWidth, buttonHeight);

		super.create();//adding exitButton
	}

	public void create(float DXx, float DYy, long price)
	{
		Log.e("BuyDialog.java" ,"Buying price = " + price + " money = " + playerStatData.getMoney());
		DX = DXx;
		DY = DYy;
		this.price = price;
		isFinished = false;

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
		canOkButton.setPosition(DX + 400, DY + 100);
		cantOkButton.setPosition(DX + 400, DY + 100);

		canCancelButton.setPosition(DX + 200, DY + 100);
	}
}
