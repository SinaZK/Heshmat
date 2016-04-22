package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import Misc.GUI;


public class Entity  extends Actor
{

	Sprite img;

	public Entity(Texture tex) 
	{
		img = new Sprite(tex);
		
		setBounds(0, 0, img.getWidth(), img.getHeight());
		setTouchable(Touchable.enabled);;
		img.setOrigin(img.getWidth()/2, img.getHeight()/2);
		setOrigin(img.getOriginX(), img.getOriginY());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
//		img.setOrigin(getWidth() / 2, getHeight() / 2);
//		setOrigin(getWidth() / 2, getHeight() / 2);
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		img.setRotation(getRotation());
		
		GUI.draw(batch, img);
		
	}
	
	@Override
	protected void sizeChanged() 
	{
		super.sizeChanged();
		img.setSize(getWidth(), getHeight());
	}
	
	@Override
	protected void positionChanged() 
	{
		img.setPosition(getX(), getY());
		super.positionChanged();
	}
	
	@Override
	public void setOrigin(float originX, float originY) 
	{
		img.setOrigin(originX, originY);
		super.setOrigin(originX, originY);
	}
	
	public Sprite getImg()
	{
		return img;
	}
}
