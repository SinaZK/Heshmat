package Entity.GunUpgradeButtons;

import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.GunStatData;
import Misc.TextureHelper;
import Scene.Garage.GunSelectorTab;

/**
 * Created by sinazk on -/-/16.
 * 06:55
 */
public class ClipSizeUpgradeButton extends GunUpgradeButton
{
	public ClipSizeUpgradeButton(GunSelectorTab gunSelectorTab, int price)
	{
		super(gunSelectorTab, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture1, gunSelectorTab.garageScene.FireRateUpgradeButtonTexture2, price);

		upgradeImage = new Sprite(TextureHelper.loadTexture(garageScene.add + "guntab/clipsize.png", garageScene.disposeTextureArray));
	}

	public void setGunStatData(GunStatData gunStatData)
	{
		super.setGunStatData(gunStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{

				if(ClipSizeUpgradeButton.this.gunStatData.clipSizeLVL < lastLevel)
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
			gunStatData.clipSizeLVL++;
			garageScene.act.saveCarDatas();
			dialogManager.popQ();
		}
	}

	@Override
	public Sprite getStarImage()
	{
		if(gunStatData.clipSizeLVL >= 0 && gunStatData.clipSizeLVL <= 5)
			return garageScene.starSprites[gunStatData.clipSizeLVL];

		return null;
	}

	@Override
	public boolean isLevelOk()
	{
		return gunStatData.clipSizeLVL < lastLevel;
	}

	@Override
	public int getLevel()
	{
		return gunStatData.clipSizeLVL;
	}
}
