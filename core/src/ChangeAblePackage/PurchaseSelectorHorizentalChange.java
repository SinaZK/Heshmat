package ChangeAblePackage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;

import Entity.PurchaseButton;
import Misc.TextureHelper;
import Scene.BaseScene;
import Scene.PurchaseScene;
import heshmat.MainActivity;

public class PurchaseSelectorHorizentalChange extends HorizentalChangablePackage
{
	MainActivity act;
	public PurchaseScene mScene;

	public PurchaseSelectorHorizentalChange(MainActivity acti, BaseScene pScene, float x, float y, float w, float h,
											OrthographicCamera pCamera)
	{
		super(x, y, w, h, pCamera);

		act = acti;
		mScene = (PurchaseScene) pScene;
	}
	
	//This are just for showing to user on purchase scene, the actuale value is in [] coins on purchaseScene.java
	public String [] coins = 
		{
			"6,000", "15,000", "25,000", "45,000", "80,000", "130,000", "180,000", "250,000", "450,000", "900,000", "2,500,000"
		};
	
	public String [] Prices = 
		{
			"1000", "2000", "3000", "5000", "7000", "10,000", "12,000", "15,000", "20,000", "30,000", "50,000"
		};
	
	public String [] Discounts = 
		{
			"0", "20", "28", "33", "47", "53", "60", "64", "73", "80", "88"
		};

	public String [] SpritePrefix =
			{
					"1", "1", "1", "1", "2", "2", "2", "2", "3", "3", "3"
			};

	public ArrayList<PurchaseButton> buttons = new ArrayList<PurchaseButton>();

	public void create()
	{
		SWIPE_TIME = 0.1f;


		for(int i = 0;i < 10;i++)
		{
			Group g1 = createPurchase(i, coins[i], Prices[i], Discounts[i], SpritePrefix[i] + ".png");
			addItem(g1);
			mScene.attachChild(g1);
		}
		
		positions[0] = paddingX + groupWidth + paddingX / 2;

		for(int i = 1;i <= entity.size();i++)
			positions[i] = positions[i - 1] + paddingX + groupWidth;
	}
	
	public Group createPurchase(int id, String coin, String price, String dis, String add)
	{
		PurchaseButton button = new PurchaseButton(mScene, 
				TextureHelper.loadTexture("gfx/scene/purchase/" + add, mScene.disposeTextureArray), id, coin, price, dis);
		buttons.add(button);
		Group g = new Group();
		button.setSize(groupWidth, groupHeight);
		g.addActor(button);
		g.setBounds(0, 0, groupWidth, groupHeight);
		g.setTouchable(Touchable.enabled);
		
		return g;
	}

}
