package Entity.CarUpgradeButtons;

import DataStore.CarStatData;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/7/16.
 * 19:19 Am i going right? :)
 */
public class EngineUpgradeButton extends CarUpgradeButton
{
	public EngineUpgradeButton(GarageScene garageScene)
	{
		super(garageScene, garageScene.EngineUpgradeButtonTexture1, garageScene.EngineUpgradeButtonTexture2);

	}

	public void setCarStatData(CarStatData carStatData)
	{
		super.setCarStatData(carStatData);

		setRunnable(garageScene.act, new Runnable()
		{
			@Override
			public void run()
			{
				EngineUpgradeButton.this.carStatData.engineLVL++;
			}
		});
	}
}
