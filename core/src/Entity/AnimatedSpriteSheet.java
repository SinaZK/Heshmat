package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import Misc.TextureHelper;

public class AnimatedSpriteSheet
{
	private int FRAME_COLS;
	private int FRAME_ROWS;

	ArrayList<SizakAnimation> animations = new ArrayList<SizakAnimation>();
	Texture textureSheet;
	public boolean isDisabled;
	int selectedAnimation = 0;

	public float x, y;
	float stateTime;


	public AnimatedSpriteSheet(String add, ArrayList<Texture> disposal)
	{

		textureSheet = TextureHelper.loadTexture(add, disposal);
	}

	public void addAnimation(String name, int startX, int startY, int endX, int endY, int row, int col, float fps)
	{
		animations.add(new SizakAnimation(textureSheet, startX, startY, endX, endY, row, col, fps, name));
	}

	public void setPosition(float X, float Y)
	{
		x = X;
		y = Y;
		for(int i = 0;i < animations.size();i++)
			animations.get(i).setPosition(x, y);
	}

	public void draw(Batch batch)
	{
		if(isDisabled || animations.size() == 0)
			return;

		stateTime += Gdx.graphics.getDeltaTime();
		animations.get(selectedAnimation).draw(stateTime, batch);

	}

	public void draw(Batch batch, float stateTime, int selectedAnimation)
	{
		if(isDisabled || animations.size() == 0)
			return;

		animations.get(selectedAnimation).draw(stateTime, batch);

	}

	public void reset()
	{
		stateTime = 0f;
		isDisabled = false;
	}

	public int getWidth()
	{
		return animations.get(selectedAnimation).frameWidth;
	}

	public int getHeight()
	{
		return animations.get(selectedAnimation).frameHeight;
	}

	public void setCurrentAnimation(String name)
	{
		for(int i = 0;i < animations.size();i++)
			if(animations.get(i).name.equals(name))
			{
				selectedAnimation = i;
				return;
			}

		selectedAnimation = 0;
	}

	public SizakAnimation getAnimation(String name)
	{
		for(int i = 0;i < animations.size();i++)
			if(animations.get(i).name.equals(name))
				return animations.get(i);

		return null;
	}

	public int getAnimationID(String name)
	{
		for(int i = 0;i < animations.size();i++)
			if(animations.get(i).name.equals(name))
				return i;

		return -1;
	}

	public void setFPS(String name, float fps)
	{
		getAnimation(name).setFPS(fps);
	}

	public void setSize(float w, float h)
	{
		for(int i = 0;i < animations.size();i++)
			animations.get(i).setSize(w, h);
	}

}