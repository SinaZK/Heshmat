package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import Misc.GUI;
import heshmat.MainActivity;

public class Button extends Entity 
{
	public boolean isClicked;
	public Sprite clickedSprite;
	public Sprite normalSprite;
	public boolean isSoundEnable = true;
	
	public Button(Texture normalTex, Texture clickedTex)
	{
		super(normalTex);
		normalSprite = getImg();
		clickedSprite = new Sprite(clickedTex);
		clickedSprite.setOrigin(img.getWidth() / 2, img.getHeight() / 2);
		setOrigin(getWidth() / 2, getHeight() / 2);
		
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) 
			{
				isClicked = true;
				return true;
				
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) 
			{
				isClicked = false;
				
				if(x >= 0 && y >= 0 && x <= getWidth() && y <= getHeight())
				{
					doOnClicked();
				}
								
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	@Override
	protected void positionChanged() 
	{
		super.positionChanged();
		
		clickedSprite.setPosition(getX(), getY());
	}
	
	@Override
	public void setSize(float width, float height) 
	{
		super.setSize(width, height);
		
		normalSprite.setSize(width, height);
		clickedSprite.setSize(width, height);
	}
	
	@Override
	public void rotateBy(float amountInDegrees) 
	{
		normalSprite.rotate(amountInDegrees);
		clickedSprite.rotate(amountInDegrees);
		super.rotateBy(amountInDegrees);
	}
	
	@Override
	public void setRotation(float degrees) 
	{
		normalSprite.setRotation(degrees);
		clickedSprite.setRotation(degrees);
		super.setRotation(degrees);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		clickedSprite.setOrigin(getWidth() / 2, getHeight() / 2);
		normalSprite.setOrigin(getWidth() / 2, getHeight() / 2);
		setOrigin(getWidth() / 2, getHeight() / 2);
		Sprite toDraw;
		
		if(isClicked)
			toDraw = clickedSprite;
		else
			toDraw = normalSprite;
		
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		GUI.draw(batch, toDraw);
	}

	Runnable runOnClick;
	public void doOnClicked()
	{
		if(isSoundEnable && act != null)
			act.audioManager.playClick();

		if(runOnClick != null)
		{
			runOnClick.run();
		}
	}

	MainActivity act;
	public void setRunnable(MainActivity act, Runnable r)
	{
		this.act = act;
		runOnClick = r;
	}
	
	@Override
	public void setColor(Color color) 
	{
		clickedSprite.setColor(color);
		normalSprite.setColor(color);
		super.setColor(color);
	}
	
	@Override
	public void setColor(float r, float g, float b, float a) {
		clickedSprite.setColor(r, g, b, a);
		normalSprite.setColor(r, g, b, a);
		super.setColor(r, g, b, a);
	}

}
