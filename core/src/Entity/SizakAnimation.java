package Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Misc.Log;

/**
 * Created by sinazk on 5/29/16.
 * 15:30
 */
public class SizakAnimation
{
	public String name;
	private int FRAME_COLS;
	private int FRAME_ROWS;
	int startX, startY, endX, endY;
	int frameWidth, frameHeight;
	float sheetWidth, sheetHeight;// the size of this animation in the spreadSheet

	public float _FPS, animDuration;

	TextureRegion[] textureFrames;
	public Sprite [] sprites;
	TextureRegion currentFrame;

	public SizakAnimation(Texture textureSheet, int startX, int startY, int endX, int endY, int row, int col, float fps, String name)
	{
		FRAME_ROWS = row;
		FRAME_COLS = col;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.sheetWidth = sheetWidth;
		this.sheetHeight = sheetHeight;
		this.name = name;

		frameWidth = (endX - startX) / FRAME_COLS;
		frameHeight = (endY - startY) / FRAME_ROWS;

		_FPS = 1 / fps;
		animDuration = _FPS * FRAME_COLS * FRAME_ROWS;
		int size = FRAME_COLS * FRAME_ROWS;

		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
//		TextureRegion[][] tmp = TextureRegion.split(textureSheet, (int)frameWidth, (int)frameHeight);

		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		sprites = new Sprite[FRAME_COLS * FRAME_ROWS];

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
			{
				textureFrames[index] = new TextureRegion(textureSheet, startX + j * frameWidth, startY + i * frameHeight, frameWidth, frameHeight);
				sprites[index] = new Sprite(textureFrames[index]);
				index++;
			}

	}

	public void flipAnimation()
	{
		int size = FRAME_COLS * FRAME_ROWS;
		int index = size - 1;

		for(int i = 0;i < size / 2;i++, index--)
		{
			Sprite tmp = sprites[i];
			sprites[i] = sprites[index];
			sprites[index] = tmp;
		}
	}

	public void draw(float stateTime, Batch batch)
	{
		while (stateTime >= animDuration)
			stateTime -= animDuration;

		int ct = (int)(stateTime / _FPS);

//		Log.e("SizakAnimation.java", "i = " + i);

		sprites[ct].draw(batch);
	}

	public void setFPS(float fps)
	{
		_FPS = 1 / fps;
		animDuration = _FPS * FRAME_COLS * FRAME_ROWS;
	}

	public void setSize(float w, float h)
	{
		frameWidth = (int)w;
		frameHeight = (int)h;
		for(int i = 0;i < FRAME_ROWS * FRAME_COLS;i++)
			sprites[i].setSize(w, h);
	}

	public void setPosition(float x, float y)
	{
		for(int i = 0;i < FRAME_ROWS * FRAME_COLS;i++)
			sprites[i].setPosition(x, y);
	}
}
