package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.PlayerStatData;
import Entity.Button;
import Misc.TextureHelper;
import Scene.PurchaseScene;
import SceneManager.SceneManager;

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

	public void loadResources()
	{
		canOkButton = new Button(TextureHelper.loadTexture(add + "canok1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "canok2.png", dialogManager.disposalTexture));
		cantOkButton = new Button(TextureHelper.loadTexture(add + "cantok1.png", dialogManager.disposalTexture),
				TextureHelper.loadTexture(add + "cantok2.png", dialogManager.disposalTexture));

		cantOkButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				dialogManager.popQ();
				InputProcessor inputProcessor = Gdx.input.getInputProcessor();
				dialogManager.activity.sceneManager.setCurrentScene(SceneManager.SCENES.PURCHASE_SCENE, null);

				PurchaseScene scene = (PurchaseScene) dialogManager.activity.sceneManager.currentBaseScene;
				scene.lastScene = dialogManager.activity.sceneManager.garageScene;
				scene.lastInput = inputProcessor;
				scene.prevScene = SceneManager.SCENES.GARAGE_SCENE;

				isFinished = true;
				isBought = false;
			}
		});

		canOkButton.setRunnable(dialogManager.activity, new Runnable()
		{
			@Override
			public void run()
			{
				long money = playerStatData.getMoney();
				money -= price;
				playerStatData.setMoney(money);
				dialogManager.activity.savePlayerStatData();
//				Log.e("BuyDialog.java", "Purchase Completed : " + playerStatData.getMoney());
				isFinished = true;
				isBought = true;
			}
		});

		super.create();//adding exitButton

		canCancelButton = exitButton;
		canCancelButton.setSize(canOkButton.getWidth(), canOkButton.getHeight());
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
			backSprite = new Sprite(dialogManager.canBuybackTexture);
		}
		else
		{
			scene.addActor(cantOkButton);
			backSprite = new Sprite(dialogManager.cantBuyBackTexture);
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

		canOkButton.setPosition(DX + 315, DY + 155);
		canCancelButton.setPosition(DX + 410, DY + 155);

		cantOkButton.setPosition(DX + 315, DY + 155);
	}
}
