package Entity.CarUpgradeButtons;

import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.CarStatData;
import Misc.TextureHelper;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/7/16.
 * 19:19 Am i going right? :)
 */
public class HitPointUpgradeButton extends CarUpgradeButton
{
	public HitPointUpgradeButton(GarageScene garageScene, int price)
	{
		super(garageScene, garageScene.HitPointUpgradeButtonTexture1, garageScene.HitPointUpgradeButtonTexture2, price);

		upgradeImage = new Sprite(TextureHelper.loadTexture(garageScene.add + "cartab/health.png", garageScene.disposeTextureArray));
	}

	public void setCarStatData(CarStatData carStatData)
	{
		super.setCarStatData(carStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{

				if(HitPointUpgradeButton.this.carStatData.hitPointLVL < lastLevel)
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
			carStatData.hitPointLVL++;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}

	@Override
	public Sprite getStarImage()
	{
		if(carStatData.hitPointLVL >= 0 && carStatData.hitPointLVL <= 5)
			return garageScene.starSprites[carStatData.hitPointLVL];

		return null;
	}

	@Override
	public boolean isLevelOk()
	{
		return carStatData.hitPointLVL < lastLevel;
	}

	@Override
	public int getLevel()
	{
		return carStatData.hitPointLVL;
	}
}
