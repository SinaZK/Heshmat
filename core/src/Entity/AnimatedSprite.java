package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import Misc.Log;
import Misc.TextureHelper;

public class AnimatedSprite
{
	private int FRAME_COLS;
	private int FRAME_ROWS;

	Animation animation;
	Texture textureSheet;
	TextureRegion[] textureFrames;
	TextureRegion currentFrame;
	float TTL;
	public boolean isDisabled;

	public float x, y;
	float stateTime;


	public AnimatedSprite(String add, int row, int col, float fps, float ttl, ArrayList<Texture> disposal)
	{
		FRAME_ROWS = row;
		FRAME_COLS = col;
		TTL = ttl;

		textureSheet = TextureHelper.loadTexture(add, disposal);
		TextureRegion[][] tmp = TextureRegion.split(textureSheet, textureSheet.getWidth() / FRAME_COLS, textureSheet.getHeight() / FRAME_ROWS);
		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				textureFrames[index++] = tmp[i][j];

		animation = new Animation(1 / fps, textureFrames);
		stateTime = 0f;
		isDisabled = false;
	}

	public void setPosition(float X, float Y)
	{
		x = X;
		y = Y;
	}

	public void draw(Batch batch, float delta)
	{
		if(isDisabled)
			return;

		stateTime += delta;
		if(stateTime >= TTL)
			isDisabled = true;

		currentFrame = animation.getKeyFrame(stateTime, true);

		batch.draw(currentFrame, x, y);
	}

	public void reset()
	{
		stateTime = 0f;
		isDisabled = false;
	}

}
