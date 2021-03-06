package Entity.GunUpgradeButtons;

import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.GunStatData;
import Entity.CarUpgradeButtons.CollisionDamageUpgradeButton;
import Misc.TextureHelper;
import Scene.Garage.GunSelectorTab;

/**
 * Created by sinazk on -/-/16.
 * 06:55
 */
public class FireRateUpgradeButton extends GunUpgradeButton
{
	public FireRateUpgradeButton(GunSelectorTab gunSelectorTab, int price)
	{
		super(gunSelectorTab, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture1, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture2, price);

		upgradeImage = new Sprite(TextureHelper.loadTexture(garageScene.add + "guntab/firerate.png", garageScene.disposeTextureArray));
	}

	public void setGunStatData(GunStatData gunStatData)
	{
		super.setGunStatData(gunStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{

				if(FireRateUpgradeButton.this.gunStatData.fireRateLVL < lastLevel)
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
			gunStatData.fireRateLVL++;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}

	@Override
	public Sprite getStarImage()
	{
		if(gunStatData.fireRateLVL >= 0 && gunStatData.fireRateLVL <= 5)
			return garageScene.starSprites[gunStatData.fireRateLVL];

		return null;
	}

	@Override
	public boolean isLevelOk()
	{
		return gunStatData.fireRateLVL < lastLevel;
	}

	@Override
	public int getLevel()
	{
		return gunStatData.fireRateLVL;
	}
}
