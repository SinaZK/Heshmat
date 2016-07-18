package Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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
		
		mScene.act.font22.draw(batch, "" + discount , getX() + 22, getY() + 64);
		mScene.act.font22.draw(batch, "" + price, getX() + 22, getY() + 95);
		mScene.act.font22.draw(batch, "" + coin , getX() + 22, getY() + 124);
	}

}
