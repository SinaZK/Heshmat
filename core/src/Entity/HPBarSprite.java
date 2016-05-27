package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class HPBarSprite
{
	private int FRAME_COLS;
	private int FRAME_ROWS;

	int numberOfTextures;

	Texture textureSheet;
	TextureRegion[] textureFrames;
	Sprite [] sprites;
	TextureRegion currentFrame;
	public boolean isDisabled;

	public float x, y;


	public HPBarSprite(String add, int row, int col, ArrayList<Texture> disposal)
	{
		FRAME_ROWS = row;
		FRAME_COLS = col;

		textureSheet = new Texture(Gdx.files.internal(add));
		TextureRegion[][] tmp = TextureRegion.split(textureSheet, textureSheet.getWidth() / FRAME_COLS, textureSheet.getHeight() / FRAME_ROWS);
		textureFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		sprites = new Sprite[FRAME_COLS * FRAME_ROWS];

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
			{
				textureFrames[index] = tmp[i][j];
				sprites[index] = new Sprite(textureFrames[index]);

				index++;
			}

		isDisabled = false;
		numberOfTextures = FRAME_COLS * FRAME_ROWS;
	}

	public void setPosition(float X, float Y)
	{
		x = X;
		y = Y;
	}

	public void draw(Batch batch, float HP, float maxHP)
	{
		if(isDisabled)
			return;

		int id = (int)(HP / maxHP) * numberOfTextures - 1;

		if(id < 0) id = 0;
		if(id > numberOfTextures - 1) id = numberOfTextures - 1;
		id = numberOfTextures - id - 1;

		currentFrame = textureFrames[id];

		//Log.e("Tag","drawing id = " + id);
		batch.draw(currentFrame, x, y);
	}

	public void draw(Batch batch, float x, float y, float HP, float maxHP, float width, float height)
	{
		if(isDisabled)
			return;

		int id = (int)((HP / maxHP) * numberOfTextures) - 1;

		if(id < 0) id = 0;
		if(id > numberOfTextures - 1) id = numberOfTextures - 1;
		id = numberOfTextures - id - 1;

		sprites[id].setSize(width, height);
		sprites[id].setPosition(x, y);

		sprites[id].draw(batch);
	}

}
