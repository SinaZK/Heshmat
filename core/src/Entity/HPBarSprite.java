package Entity;

import com.badlogic.gdx.Gdx;
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
		if(isDisabled || HP <= 0)
			return;

		float w2 = width * (HP / maxHP);

		hpBar1Sprite.setPosition(x, y);
		hpBar2Sprite.setPosition(x, y);

		hpBar1Sprite.setSize(width, height);
		hpBar2Sprite.setSize(w2, height);

		hpBar1Sprite.draw(batch);
		hpBar2Sprite.draw(batch);
	}

}
