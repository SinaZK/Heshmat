package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import Misc.TextureHelper;

public class HPBarSprite
{
	private int FRAME_COLS;
	private int FRAME_ROWS;

	Sprite hpBar1Sprite, hpBar2Sprite;
	public boolean isDisabled;

	public float x, y;


	public HPBarSprite(ArrayList<Texture> disposal)
	{
		hpBar1Sprite = new Sprite(TextureHelper.loadTexture("gfx/enemy/hpbar1.png", disposal));
		hpBar2Sprite = new Sprite(TextureHelper.loadTexture("gfx/enemy/hpbar2.png", disposal));

		isDisabled = false;
	}

	public void setPosition(float X, float Y)
	{
		x = X;
		y = Y;
	}

	public void draw(Batch batch, float x, float y, float HP, float maxHP, float width, float height)
	{
		if(isDisabled)
			return;

		width = hpBar1Sprite.getWidth();
		height = hpBar1Sprite.getHeight();

		float skeletonWidth = 12;

		float w2 = (width - skeletonWidth) * (HP / maxHP);

		hpBar1Sprite.setPosition(x, y);
		hpBar2Sprite.setPosition(x + skeletonWidth, y);

		hpBar1Sprite.setSize(width, height);
		hpBar2Sprite.setSize(w2, height);


		float percent = HP / maxHP * 100;

		hpBar2Sprite.setColor(getBarColor(percent));

		hpBar2Sprite.draw(batch);
		hpBar1Sprite.draw(batch);
	}

	public Color getBarColor(float percent)
	{
		Color color = new Color(1, 1, 1, 1);
		if(percent >= 70)
			color.set(0.2f , 0.7f, 0.2f, 1);

		if(percent >= 40 && percent <= 70)
			color.set(1f , 1f, 0.1f, 1);

		if(percent >= 10 && percent <= 40)
			color.set(0.8f , 0.4f, 0.1f, 1);

		if(percent <= 10)
			color.set(1f , 0.0f, 0.0f, 1);
		return color;
	}

}
