package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import Misc.Log;
import Scene.PurchaseScene;

public class PurchaseButton extends Button 
{
	int purchaseID;
	PurchaseScene mScene;
	
	public String price;
	public String coin;
	public String discount;
	
	public PurchaseButton(PurchaseScene pScene, Texture normalTex, int id, String coin, String price, String Dis) 
	{
		super(normalTex, normalTex);
		
		mScene = pScene;
		purchaseID = id;
		this.coin = coin;
		this.price = price;
		discount = Dis;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(isClicked)
			super.draw(batch, 0.8f);
		else
			super.draw(batch, parentAlpha);

		Color c = mScene.act.font22.getColor();

		mScene.act.font22.setColor(Color.BLACK);
		mScene.act.font22.draw(batch, "" + coin, getX() + 22, getY() + 100);
		mScene.act.font22.draw(batch, "" + price, getX() + 22, getY() + 70);
		mScene.act.font22.draw(batch, "" + discount , getX() + 50, getY() + 44);

		mScene.act.font22.setColor(c);
	}

}
