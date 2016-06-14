package Entity.CarUpgradeButtons;

import DataStore.CarStatData;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/7/16.
 * 19:19 Am i going right? :)
 */
public class HitPointUpgradeButton extends CarUpgradeButton
{
	public HitPointUpgradeButton(GarageScene garageScene)
	{
		super(garageScene, garageScene.HitPointUpgradeButtonTexture1, garageScene.HitPointUpgradeButtonTexture2);

	}

	public void setCarStatData(CarStatData carStatData)
	{
		super.setCarStatData(carStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				HitPointUpgradeButton.this.carStatData.hitPointLVL++;
			}
		});
	}
}
