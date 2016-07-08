package Entity.CarUpgradeButtons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.CarStatData;
import Entity.BuyButtons.CarBuyButton;
import Enums.Enums;
import Misc.TextureHelper;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/7/16.
 * 19:19 Am i going right? :)
 */
public class EngineUpgradeButton extends CarUpgradeButton
{
	public EngineUpgradeButton(GarageScene garageScene, int price)
	{
		super(garageScene, garageScene.EngineUpgradeButtonTexture1, garageScene.EngineUpgradeButtonTexture2, price);

		upgradeImage = new Sprite(TextureHelper.loadTexture(garageScene.add + "cartab/engine.png", garageScene.disposeTextureArray));
	}

	public void setCarStatData(final CarStatData carStatData)
	{
		super.setCarStatData(carStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				if(EngineUpgradeButton.this.carStatData.engineLVL < lastLevel)
				{
					dialogManager.addBuyDialog(garageScene.DX, garageScene.DY, calculatePrice(getLevel()));
					isWaitingForBuy = true;
				}
			}
		});
	}

	@Override
	protected void doOnBuyFinished()
	{
		super.doOnBuyFinished();

		if(dialogManager.buyDialog.isBought)
		{
			EngineUpgradeButton.this.carStatData.engineLVL++;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}


	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
	}

	@Override
	public Sprite getStarImage()
	{
		if(carStatData.engineLVL >= 0 && carStatData.engineLVL <= 5)
			return garageScene.starSprites[carStatData.engineLVL];

		return null;
	}

	@Override
	public boolean isLevelOk()
	{
		return carStatData.engineLVL < lastLevel;
	}

	@Override
	public int getLevel()
	{
		return carStatData.engineLVL;
	}
}
