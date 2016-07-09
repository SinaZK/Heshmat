package Entity.CarUpgradeButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import DataStore.CarStatData;
import Dialog.DialogManager;
import Entity.Button;
import Enums.Enums;
import Scene.Garage.GarageScene;

/**
 * Created by sinazk on 6/6/16.
 * 07:16
 * A button for upgrading cars in garageScene!
 */
public abstract class CarUpgradeButton extends Button
{
	GarageScene garageScene;
	CarStatData carStatData;

	Sprite upgradeImage;
	Button plusButton;

	public int lastLevel = 5;
	public int firstLevel = 0;
	public int price;

	boolean isWaitingForBuy;
	public DialogManager dialogManager;

	public CarUpgradeButton(GarageScene garageScene, Texture normalTex, Texture clickedTex, int price)
	{
		super(normalTex, clickedTex);

		dialogManager = garageScene.mSceneManager.dialogManager;
		this.garageScene = garageScene;
		this.price = price;

		plusButton = new Button(garageScene.plusUpgrade1, garageScene.plusUpgrade2);

	}

	public void setCarStatData(CarStatData carStatData)
	{
		this.carStatData = carStatData;
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);

		if(carStatData.lockStat == Enums.LOCKSTAT.LOCK)
			setVisible(false);
		else
			setVisible(true);

		if(isWaitingForBuy)
			if(dialogManager.buyDialog.isFinished)
				doOnBuyFinished();
	}

	protected void doOnBuyFinished()
	{
		isWaitingForBuy = false;
		dialogManager.buyDialog.isFinished = false;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(carStatData.lockStat != Enums.LOCKSTAT.UNLOCK)
			return;

		upgradeImage.setPosition(getX(), getY());

		Sprite star = getStarImage();
		star.setPosition(upgradeImage.getX() + upgradeImage.getWidth() + 10, getY() + 40);

		plusButton.setPosition(star.getX() + star.getWidth() + 1, star.getY() + 3);

		if(isClicked)
		{
			upgradeImage.setAlpha(0.5f);
			plusButton.isClicked = true;
			star.setAlpha(0.5f);
		}
		else
		{
			upgradeImage.setAlpha(1);
			plusButton.isClicked = false;
			star.setAlpha(1f);
		}

		setSize(upgradeImage.getWidth() + plusButton.getWidth() + garageScene.starSprites[0].getWidth(), upgradeImage.getHeight());


		upgradeImage.draw(batch);
		star.draw(batch);

		if(isLevelOk())
		{
			garageScene.goldBackSprite.setPosition(star.getX(), getY() + 10);
			garageScene.goldBackSprite.setSize(100, 17);
			garageScene.goldBackSprite.draw(batch);
			plusButton.draw(batch, parentAlpha);

			garageScene.act.font22.draw(batch, "" + calculatePrice(getLevel()), getX() + upgradeImage.getWidth() + 25, getY() + 25);
		}

	}

	public abstract Sprite getStarImage();
	public abstract boolean isLevelOk();

	public static float AddPercent = 20;
	public int calculatePrice(int level)
	{
		float p = (float)Math.pow((100f + AddPercent) / 100f, (float)level) * price;

		return (int)p;
	}

	public abstract int getLevel();
}
