package Entity.CarUpgradeButtons;

import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.CarStatData;
import Misc.TextureHelper;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 8/July/16.
 * 12:00
 */
public class CollisionDamageUpgradeButton extends CarUpgradeButton
{
	public CollisionDamageUpgradeButton(GarageScene garageScene, int price)
	{
		super(garageScene, garageScene.HitPointUpgradeButtonTexture1, garageScene.HitPointUpgradeButtonTexture2, price);

		upgradeImage = new Sprite(TextureHelper.loadTexture(garageScene.add + "cartab/colrate.png", garageScene.disposeTextureArray));
	}

	public void setCarStatData(CarStatData carStatData)
	{
		super.setCarStatData(carStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{

				if(CollisionDamageUpgradeButton.this.carStatData.collisionDamageLVL < lastLevel)
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
			carStatData.collisionDamageLVL++;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}

	@Override
	public Sprite getStarImage()
	{
		if(carStatData.collisionDamageLVL >= 0 && carStatData.collisionDamageLVL <= 5)
			return garageScene.starSprites[carStatData.collisionDamageLVL];

		return null;
	}

	@Override
	public boolean isLevelOk()
	{
		return carStatData.collisionDamageLVL < lastLevel;
	}

	@Override
	public int getLevel()
	{
		return carStatData.collisionDamageLVL;
	}
}
